package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.StatisticService;

import java.util.List;

@With(SecuredAction.class)
public class StatisticController extends Controller {

    public Result index() {
        StatisticService statisticService = new StatisticService();

        List latestPayments = statisticService.getLatestPayments();
        List paymentsByPeriod = statisticService.getTotalPaymentsByPeriod();
        List installments = statisticService.getInstallmentsByPeriod();

        return ok(views.html.statistics.dashboard.render(latestPayments, paymentsByPeriod, installments));
    }
}
