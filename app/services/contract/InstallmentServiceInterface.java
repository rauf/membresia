package services.contract;

import models.Installment;
import models.Subscription;

public interface InstallmentServiceInterface {

    Installment createInstallment(Subscription subscription);

    void updateInstallments(Subscription subscription);
}
