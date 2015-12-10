package services.contract;

import models.Payment;
import play.data.Form;

public interface PaymentServiceInterface {

    Payment save(Form<Payment> formData);
}
