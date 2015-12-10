package services;

import models.Installment;
import models.Periodicity;
import models.Subscription;
import play.Logger;
import services.contract.InstallmentServiceInterface;

import java.util.Calendar;
import java.util.Date;

public class InstallmentService implements InstallmentServiceInterface {


    public Installment createInstallment(Subscription subscription) {
        SubscriptionService subscriptionService = new SubscriptionService();

        Integer periodIncrement = (int) Periodicity.valueOf(subscription.getPeriodicity()).getValue();
        Date lastDueDate = subscriptionService.getLastInstallmentDueDate(subscription);
        Date installmentDueDate = (subscription.getInstallments().isEmpty()) ? lastDueDate : addPeriodDateIncrement(lastDueDate, periodIncrement);

        Installment installment = new Installment(installmentDueDate, subscription.getAmount(), subscription);
        installment.save();

        return installment;
    }

    public void updateInstallments(Subscription subscription) {
        SubscriptionService subscriptionService = new SubscriptionService();

        Integer periodIncrement = (int) Periodicity.valueOf(subscription.getPeriodicity()).getValue();
        Date lastDueDate = subscriptionService.getLastInstallmentDueDate(subscription);
        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        cal.setTime(lastDueDate);

        if (cal.before(today)) {
            createInstallment(subscription);
        } else {
            Date previousDueDate = subscriptionService.getPreviousToLastInstallmentDueDate(subscription);
            Date installmentDueDate = (subscription.getInstallments().size() == 1) ? previousDueDate : addPeriodDateIncrement(previousDueDate, periodIncrement);
            Installment installment = subscriptionService.getLastInstallment(subscription);
            if (installment.getPayments().isEmpty()) {
                installment.setAmount(subscription.getAmount());
                installment.setDueDate(installmentDueDate);
                installment.save();
            }
        }
    }

    private Date addPeriodDateIncrement(Date dueDate, Integer periodIncrement) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dueDate);
        cal.add(Calendar.MONTH, periodIncrement);

        return cal.getTime();
    }
}
