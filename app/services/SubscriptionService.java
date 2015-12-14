package services;

import models.*;
import play.Logger;
import play.data.Form;
import services.contract.SubscriptionServiceInterface;
import services.formData.MemberFormData;
import services.formData.SubscriptionFormData;

import java.util.*;

/**
 * Middleware class for controller model interaction and other subscription related business logic
 */
public class SubscriptionService implements SubscriptionServiceInterface {

    /**
     * {@inheritDoc}
     */
    public SubscriptionFormData setSubscriptionData(String token) {

        return new SubscriptionFormData(getModel().getSubscriptionByToken(token));
    }

    /**
     * {@inheritDoc}
     */
    public Form<SubscriptionFormData> setFormData(SubscriptionFormData subscriptionData) {

        return Form.form(SubscriptionFormData.class).fill(subscriptionData);
    }

    /**
     * {@inheritDoc}
     */
    public List<Subscription> getSubscriptionList(Pager pager) {

        return getModel().getSubscriptionList(pager);
    }

    public List<Subscription> getSubscriptionRawList() {

        return getModel().getSubscriptionRawList();
    }

    public Map<SelectOptionItem, Boolean> makeSubscriptionMap(MemberFormData memberFormData) {
        List<Subscription> allSubscriptions = getModel().getSubscriptionRawList();
        Map<SelectOptionItem, Boolean> subscriptionMap = new HashMap<SelectOptionItem, Boolean>();
        for (Subscription subscription : allSubscriptions) {
            SelectOptionItem selectOptionItem = new SelectOptionItem(subscription.toString(), subscription.getToken());
            subscriptionMap.put(selectOptionItem, (memberFormData != null && memberFormData.getSubscriptions().contains(subscription.getToken())));
        }
        return subscriptionMap;
    }

    public Map<SelectOptionItem, Boolean> makePeriodicityMap(SubscriptionFormData subscriptionFormData) {
        EnumSet<Periodicity> periods = EnumSet.allOf(Periodicity.class);
        Map<SelectOptionItem, Boolean> periodicityMap = new HashMap<SelectOptionItem, Boolean>();
        for (Periodicity period : periods) {
            SelectOptionItem selectOptionItem = new SelectOptionItem(period.toString(), period.name());
            periodicityMap.put(selectOptionItem, (subscriptionFormData == null) ? false : (subscriptionFormData.getPeriodicity() != null) && subscriptionFormData.getPeriodicity().equals(period.name()));
        }
        return periodicityMap;
    }

    /**
     * {@inheritDoc}
     */
    public Subscription save(Form<SubscriptionFormData> formData) {

        Subscription subscription = (formData.get().getId() != null) ? getModel().getSubscriptionById(formData.get().getId()) : getModel();
        subscription.setData(formData.get());
        subscription.save();
        return subscription;
    }

    /**
     * {@inheritDoc}
     */
    public boolean remove(String token) {

        return getModel().remove(token);
    }

    /**
     * {@inheritDoc}
     */
    public Subscription getSubscription(String token) {

        return getModel().getSubscriptionByToken(token);
    }

    public Date getLastInstallmentDueDate(Subscription subscription) {
        Date dueDate = subscription.getDueDatePeriod();
        if (!subscription.getInstallments().isEmpty())
            dueDate = subscription.getInstallments().get(subscription.getInstallments().size() - 1).getDueDate();

        return dueDate;
    }

    public Date getPreviousToLastInstallmentDueDate(Subscription subscription) {
        Date dueDate = subscription.getDueDatePeriod();
        if (subscription.getInstallments().size() > 1)
            dueDate = subscription.getInstallments().get(subscription.getInstallments().size() - 2).getDueDate();

        return dueDate;
    }

    public Installment getLastInstallment(Subscription subscription) {
        return (subscription.getInstallments().isEmpty()) ? new Installment(subscription) : subscription.getInstallments().get(subscription.getInstallments().size() - 1);
    }

    public List<Subscription> getActiveSubscriptions(){
        return getModel().getSubscriptionRawList();
    }

    /**
     * Creates Subscription model object
     *
     * @return Subscription
     */
    private Subscription getModel() {

        return new Subscription();
    }
}
