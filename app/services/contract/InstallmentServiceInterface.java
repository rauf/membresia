package services.contract;

import models.Installment;
import models.Subscription;

public interface InstallmentServiceInterface {

    /**
     * Generate a new installment whenever is needed among all available subscriptions
     */
    void generateInstallments();

    /**
     * Add a new installment to a subscription
     *
     * @param subscription Subscription to add the installment to
     */
    void createInstallment(Subscription subscription);

    /**
     * Update last installment informtion upon subscription due date and amount modification
     *
     * @param subscription Subscription to update the installment from
     */
    void updateInstallments(Subscription subscription);


    /**
     * Retrieves a subscrition las installment or generate a new one
     *
     * @param subscription Subscription to generate installment for
     * @return Installment
     */
    Installment getSubscriptionLastInstallment(Subscription subscription);
}
