package services.contract;

import models.Subscription;
import play.data.Form;
import services.formData.SubscriptionFormData;
import services.Pager;

import java.util.List;

/**
 * Middleware interface class for controller model interaction and other subscription related business logic
 */
public interface SubscriptionServiceInterface {

    /**
     * Sets a SubscriptionFormData object from model data
     *
     * @param token Unique subscription token identifier
     * @return SubscriptionFormData
     */
    SubscriptionFormData setSubscriptionData(String token);

    /**
     * Generates a form object from a SubscriptionFormData object populated from model
     *
     * @param subscriptionData Data from model object
     * @return Form
     */
    Form<SubscriptionFormData> setFormData(SubscriptionFormData subscriptionData);


    /**
     * Gets subscription paginated list from DB
     *
     * @param pager Pager object for query pagination
     * @return List
     */
    List<Subscription> getSubscriptionList(Pager pager);

    /**
     * Get a specific subscription by token
     *
     * @param token Unique subscription identifier
     * @return Subscription
     */
    Subscription getSubscription(String token);

    /**
     * Saves form data into persistence object
     *
     * @param formData Subscription data from UI form
     * @return Subscription
     */
    Subscription save(Form<SubscriptionFormData> formData);

    /**
     * Removes a specific subscription by token
     *
     * @param token Unique subscription identifier
     * @return boolean
     */
    boolean remove(String token);
}
