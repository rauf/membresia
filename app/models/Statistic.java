package models;

import com.avaje.ebean.*;

import java.util.List;

public class Statistic {

    /**
     * Returns a list of total money collected per month period and subscription
     *
     * @param token Unique subscription token
     * @return List
     */
    public List<SqlRow> getTotalPaymentsBySubscriptionAndPeriod(String token) {
        String sql =
                "SELECT" +
                        " SUM(payment.amount) AS amount," +
                        " DATE_FORMAT(payment.created_at, '%m/%Y') AS period" +
                        " FROM payment" +
                        " LEFT JOIN installment ON installment_id = installment.id" +
                        " LEFT JOIN subscription ON installment.subscription_id = subscription.id" +
                        " WHERE subscription.token = :token" +
                        " GROUP BY period" +
                        " ORDER BY period" +
                        " LIMIT :limit";

        return Ebean.createSqlQuery(sql)
                .setParameter("token", token)
                .setParameter("limit", 12)
                .findList();
    }

    /**
     * Returns a list is total money collected per month period
     *
     * @return List
     */
    public List<SqlRow> getTotalPaymentsByPeriod() {
        String sql =
                "SELECT" +
                        " SUM(payment.amount) AS amount," +
                        " DATE_FORMAT(payment.created_at, '%m/%Y') AS period" +
                        " FROM payment" +
                        " LEFT JOIN installment ON installment_id = installment.id" +
                        " LEFT JOIN subscription ON installment.subscription_id = subscription.id" +
                        " GROUP BY period" +
                        " ORDER BY period" +
                        " LIMIT :limit";

        return Ebean.createSqlQuery(sql)
                .setParameter("limit", 12)
                .findList();
    }

    /**
     * Gets a lists of most recent payments
     *
     * @param limit Total number of records to show
     * @return List
     */
    public List<Payment> getLatestPayments(int limit) {
        return Ebean.find(Payment.class).setMaxRows(limit).orderBy("created_at DESC").findList();
    }

    /**
     * Gets list of installment for current month period
     *
     * @return List
     */
    public List<Installment> getInstallmentsByPeriod(int limit) {
        return Ebean.find(Installment.class).setMaxRows(limit).orderBy("due_date DESC").findList();
    }
}
