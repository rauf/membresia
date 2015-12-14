package services.contract;

import models.Subscription;

public interface InstallmentServiceInterface {

    void createInstallment(Subscription subscription);

    void updateInstallments(Subscription subscription);

    void createInstallments();
}
