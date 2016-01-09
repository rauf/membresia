package controllers.statistic;

import com.avaje.ebean.SqlRow;
import controllers.user.SecuredAction;
import models.subscription.Installment;
import models.subscription.Payment;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.statistic.StatisticService;

import java.util.List;

@With(SecuredAction.class)
public class StatisticController extends Controller {

    public Result index() {
        StatisticService statisticService = new StatisticService();

        List<Payment> latestPayments = statisticService.getLatestPayments();
        List<SqlRow> paymentsByPeriod = statisticService.getTotalPaymentsByPeriod();
        List<Installment> installments = statisticService.getInstallmentsByPeriod();

        return ok(views.html.statistics.dashboard.render(latestPayments, paymentsByPeriod, installments));
    }
}
