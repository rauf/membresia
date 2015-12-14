package services;

import models.Installment;
import models.Member;
import models.Periodicity;
import models.Subscription;
import play.Logger;
import services.contract.InstallmentServiceInterface;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InstallmentService implements InstallmentServiceInterface {

    @Inject
    MemberInstallmentService memberInstallmentService;

    public void createInstallment(Subscription subscription) {
        SubscriptionService subscriptionService = new SubscriptionService();

        Integer periodIncrement = (int) Periodicity.valueOf(subscription.getPeriodicity()).getValue();
        Date lastInstallmentDueDate = subscriptionService.getLastInstallmentDueDate(subscription);
        Date newInstallmentDueDate = (subscription.getInstallments().isEmpty()) ? lastInstallmentDueDate : addPeriodDateIncrement(lastInstallmentDueDate, periodIncrement);

        Installment installment = new Installment(newInstallmentDueDate, subscription.getAmount(), subscription);
        installment.save();

        for (Member member : subscription.getMembers()) {
            memberInstallmentService.createMemberInstallment(member, installment);
        }
    }

    public void updateInstallments(Subscription subscription) {
        SubscriptionService subscriptionService = new SubscriptionService();

        Integer periodIncrement = (int) Periodicity.valueOf(subscription.getPeriodicity()).getValue();
        Date lastInstallmentDueDate = subscriptionService.getLastInstallmentDueDate(subscription);

        if (isInstallmentDue(lastInstallmentDueDate)) {
            createInstallment(subscription);
        } else {
            Date previousInstallmentDueDate = subscriptionService.getPreviousToLastInstallmentDueDate(subscription);
            Installment installment = subscriptionService.getLastInstallment(subscription);

            if (installment.getPayments().isEmpty()) {
                Date installmentNewDueDate = (subscription.getInstallments().size() == 1) ? previousInstallmentDueDate : addPeriodDateIncrement(previousInstallmentDueDate, periodIncrement);
                installment.setAmount(subscription.getAmount());
                installment.setDueDate(installmentNewDueDate);
                installment.save();
            }
        }
    }

    public void createInstallments() {
        SubscriptionService subscriptionService = new SubscriptionService();

        List<Subscription> subscriptions = subscriptionService.getActiveSubscriptions();
        for (Subscription subscription : subscriptions) {
            Installment lastInstallment = subscriptionService.getLastInstallment(subscription);
            Calendar installmentDate = Calendar.getInstance();
            installmentDate.setTime(lastInstallment.getDueDate());

            if (installmentDate.before(Calendar.getInstance())) {
                createInstallment(subscription);
            }
        }
    }

    private boolean isInstallmentDue(Date lastInstallmentDueDate) {
        Calendar installmentDate = Calendar.getInstance();
        installmentDate.setTime(lastInstallmentDueDate);
        Calendar today = Calendar.getInstance();

        return installmentDate.before(today);
    }

    private Date addPeriodDateIncrement(Date dueDate, Integer periodIncrement) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dueDate);
        cal.add(Calendar.MONTH, periodIncrement);

        return cal.getTime();
    }
}
