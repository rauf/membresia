package services;

import models.Installment;
import models.Periodicity;
import models.Subscription;
import services.contract.InstallmentServiceInterface;

import java.util.Calendar;
import java.util.Date;

public class InstallmentService implements InstallmentServiceInterface {

    public Installment createInstallment(Subscription subscription) {
        Date dueDate = calculateInstallmentDueDate(subscription.getDueDatePeriod(), subscription.getPeriodicity());
        Installment installment = new Installment(dueDate, subscription.getAmount(), subscription);
        installment.save();
        return installment;
    }

    private Date calculateInstallmentDueDate(Date subscriptionDueDate, String periodicity) {
        Integer periodIncrement = (int) Periodicity.valueOf(periodicity).getValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(subscriptionDueDate);
        cal.add(Calendar.MONTH, periodIncrement);
        return cal.getTime();
    }
}
