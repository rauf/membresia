package services.statistic;

import com.avaje.ebean.SqlRow;
import models.statistic.Statistic;
import models.subscription.Installment;
import models.subscription.Payment;
import services.statistic.contract.StatisticServiceInterface;

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
    public List<Payment> getLatestPayments() {
        int limit = 10;
        return getModel().getLatestPayments(limit);
    }

    /**
     * {@inheritDoc}
     **/
    public List<SqlRow> getTotalPaymentsByPeriod() {
        return getModel().getTotalPaymentsByPeriod();
    }

    /**
     * {@inheritDoc}
     **/
    public List<Installment> getInstallmentsByPeriod() {
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
