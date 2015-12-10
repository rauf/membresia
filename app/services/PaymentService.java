package services;


import models.Payment;
import play.data.Form;
import services.contract.PaymentServiceInterface;

public class PaymentService implements PaymentServiceInterface {

    /**
     * {@inheritDoc}
     */
    public Payment save(Form<Payment> formData) {
        Payment payment = getModel();
        payment.save();
        return payment;
    }

    /**
     * Creates Payment model object
     *
     * @return Payment
     */
    private Payment getModel() {

        return new Payment();
    }
}
