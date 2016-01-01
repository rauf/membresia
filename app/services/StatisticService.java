package services;

import models.Statistic;
import services.contract.StatisticServiceInterface;

import java.util.List;

public class StatisticService implements StatisticServiceInterface {

    /**
     * {@inheritDoc}
     **/
    public List getTotalPaymentsBySubscription(String token) {
        return getModel().getTotalPaymentsBySubscriptionAndPeriod(token);
    }

    /**
     * {@inheritDoc}
     **/
    public List getLatestPayments() {
        int limit = 10;
        return getModel().getLatestPayments(limit);
    }

    /**
     * {@inheritDoc}
     **/
    public List getTotalPaymentsByPeriod() {
        return getModel().getTotalPaymentsByPeriod();
    }

    /**
     * {@inheritDoc}
     **/
    public List getInstallmentsByPeriod() {
        int limit = 10;
        return getModel().getInstallmentsByPeriod(limit);
    }

    /**
     * Gets model object
     *
     * @return Statistic
     */
    private Statistic getModel() {
        return new Statistic();
    }
}
