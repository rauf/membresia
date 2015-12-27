package services;

import models.Payment;
import play.data.Form;
import services.contract.PaymentServiceInterface;
import views.formData.PaymentFormData;

public class PaymentService implements PaymentServiceInterface {

    /**
     * {@inheritDoc}
     */
    public Payment save(Form<PaymentFormData> formData) {
        Payment payment = getModel();
        payment.setData(formData.get());
        payment.save();
        return payment;
    }

    /**
     * {@inheritDoc}
     */
    public void acceptPayment(String token) {
        Payment payment = getModel().get("token", token);
        payment.setStatus(1);
        payment.save();
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
