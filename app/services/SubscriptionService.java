package services;

import models.Subscription;
import play.data.Form;
import services.contract.SubscriptionServiceInterface;
import services.formData.SubscriptionFormData;

import java.util.*;

public class SubscriptionService implements SubscriptionServiceInterface {

    /**
     * {@inheritDoc}
     */
    public SubscriptionFormData setSubscriptionData(String token) {
        return new SubscriptionFormData(getModel().get("token", token));
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
    public Subscription getSubscription(String token) {
        return getModel().get("token", token);
    }

    /**
     * {@inheritDoc}
     */
    public List<Subscription> getSubscriptionList(Pager pager) {
        return getModel().getSubscriptionList(pager);
    }

    /**
     * {@inheritDoc}
     */
    public List<Subscription> getActiveSubscriptions() {
        return getModel().getList();
    }

    /**
     * {@inheritDoc}
     */
    public Subscription save(Form<SubscriptionFormData> formData) {
        Subscription subscription = (formData.get().getId() != null) ? getModel().getByPk(formData.get().getId()) : getModel();
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
     * Creates Subscription model object
     *
     * @return Subscription
     */
    private Subscription getModel() {
        return new Subscription();
    }
}
