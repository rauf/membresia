package services.contract;

import models.Payment;
import play.data.Form;

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
    Payment save(Form<Payment> formData);

    /**
     * Set payment status as true after Paypal verification
     *
     * @param token Payment token
     */
    void acceptPayment(String token);
}
