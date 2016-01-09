package services.subscription.contract;

import models.subscription.Payment;
import play.data.Form;
import views.formData.PaymentFormData;

/**
 * Service class for payment operations
 */
public interface PaymentServiceInterface {

    /**
     * Saves paymento from form data object
     *
     * @param formData Form data object for payment
     * @return Payment
     */
    Payment save(Form<PaymentFormData> formData);

    /**
     * Set payment status as true after Paypal verification
     *
     * @param token Payment token
     */
    void acceptPayment(String token);
}
