package services;

import models.Installment;
import models.Member;
import models.Periodicity;
import models.Subscription;
import services.contract.InstallmentServiceInterface;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InstallmentService implements InstallmentServiceInterface {

    @Inject
    MemberInstallmentService memberInstallmentService;

    /**
     * {@inheritDoc}
     **/
    public void createInstallment(Subscription subscription) {
        Integer periodIncrement = (int) Periodicity.valueOf(subscription.getPeriodicity()).getValue();
        Date lastInstallmentDueDate = getLastInstallmentDueDate(subscription);
        Date newInstallmentDueDate = (subscription.getInstallments().isEmpty()) ? lastInstallmentDueDate : addPeriodDateIncrement(lastInstallmentDueDate, periodIncrement);

        Installment installment = new Installment(newInstallmentDueDate, subscription.getAmount(), subscription);
        installment.save();

        for (Member member : subscription.getMembers()) {
            memberInstallmentService.createMemberInstallment(member, installment);
        }
    }

    /**
     * {@inheritDoc}
     **/
    public void updateInstallments(Subscription subscription) {
        Integer periodIncrement = (int) Periodicity.valueOf(subscription.getPeriodicity()).getValue();
        Date lastInstallmentDueDate = getLastInstallmentDueDate(subscription);

        if (isInstallmentDue(lastInstallmentDueDate)) {
            createInstallment(subscription);
        } else {
            Date previousInstallmentDueDate = getPreviousToLastInstallmentDueDate(subscription);
            Installment installment = getSubscriptionLastInstallment(subscription);

            if (installment.getPayments().isEmpty()) {
                Date installmentNewDueDate = (subscription.getInstallments().size() == 1) ? previousInstallmentDueDate : addPeriodDateIncrement(previousInstallmentDueDate, periodIncrement);
                installment.setAmount(subscription.getAmount());
                installment.setDueDate(installmentNewDueDate);
                installment.save();
            }
        }
    }

    /**
     * {@inheritDoc}
     **/
    public void generateInstallments() {
        SubscriptionService subscriptionService = new SubscriptionService();

        List<Subscription> subscriptions = subscriptionService.getActiveSubscriptions();
        for (Subscription subscription : subscriptions) {
            Installment lastInstallment = getSubscriptionLastInstallment(subscription);
            Calendar installmentDate = Calendar.getInstance();
            installmentDate.setTime(lastInstallment.getDueDate());

            if (installmentDate.before(Calendar.getInstance())) {
                createInstallment(subscription);
            }
        }
    }

    /**
     * {@inheritDoc}
     **/
    public Installment getSubscriptionLastInstallment(Subscription subscription) {
        return (subscription.getInstallments().isEmpty()) ? new Installment(subscription) : subscription.getInstallments().get(subscription.getInstallments().size() - 1);
    }

    /**
     * Checks if installment due date is due
     *
     * @param lastInstallmentDueDate Installment due date
     * @return boolean
     */
    private boolean isInstallmentDue(Date lastInstallmentDueDate) {
        Calendar installmentDate = Calendar.getInstance();
        installmentDate.setTime(lastInstallmentDueDate);
        Calendar today = Calendar.getInstance();

        return installmentDate.before(today);
    }

    /**
     * Adds a subscription increment period to a specific due date
     *
     * @param dueDate         Due date to increment
     * @param periodIncrement Period to add
     * @return Date
     */
    private Date addPeriodDateIncrement(Date dueDate, Integer periodIncrement) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dueDate);
        cal.add(Calendar.MONTH, periodIncrement);

        return cal.getTime();
    }

    /**
     * Gets a subscription las installment due date
     *
     * @param subscription Subscription to get the installment date from
     * @return Date
     */
    private Date getLastInstallmentDueDate(Subscription subscription) {
        Date dueDate = subscription.getDueDatePeriod();
        if (!subscription.getInstallments().isEmpty())
            dueDate = subscription.getInstallments().get(subscription.getInstallments().size() - 1).getDueDate();

        return dueDate;
    }

    /**
     * The due date for installment previous to a subscription last installment
     *
     * @param subscription Subscription to get the installment date from
     * @return Date
     */
    private Date getPreviousToLastInstallmentDueDate(Subscription subscription) {
        Date dueDate = subscription.getDueDatePeriod();
        if (subscription.getInstallments().size() > 1)
            dueDate = subscription.getInstallments().get(subscription.getInstallments().size() - 2).getDueDate();

        return dueDate;
    }
}
