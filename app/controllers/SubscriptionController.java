package controllers;

import play.api.libs.mailer.MailerClient;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import models.SelectOptionItem;
import models.Subscription;
import services.formData.SubscriptionFormData;
import services.InstallmentService;
import services.Pager;
import services.SubscriptionService;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Controller for Subscription entity
 */
@With(SecuredAction.class)
public class SubscriptionController extends Controller {

    @Inject
    private SubscriptionService subscriptionService;

    @Inject
    private InstallmentService installmentService;

    private SubscriptionFormData subscriptionData;

    private Form<SubscriptionFormData> formData;

    private Pager pager;

    private Integer currentPage = 1;

    /**
     * Show all subscriptions
     *
     * @return Result
     */
    public Result index(Integer currentPage) {
        this.currentPage = currentPage;
        pager = new Pager(this.currentPage);
        List<Subscription> subscriptions = subscriptionService.getSubscriptionList(pager);

        return ok(views.html.subscription.index.render(Messages.get("subscription.list.global.title"), subscriptions, pager));
    }

    /**
     * Renders subscription's form in creation mode
     *
     * @return Result
     */
    public Result create() {
        subscriptionData = new SubscriptionFormData();
        formData = subscriptionService.setFormData(subscriptionData);
        subscriptionData.setMode(0);
        Map<SelectOptionItem, Boolean> periodicityMap = subscriptionData.makePeriodicityMap(subscriptionData);

        return ok(views.html.subscription.form.render(Messages.get("subscription.form.global.new.title"), formData, periodicityMap));
    }

    /**
     * Renders subscription's form in editing mode by subscription token
     *
     * @param token Unique subscription token identifier
     * @return Result
     */
    public Result edit(String token) {
        subscriptionData = new SubscriptionFormData();
        subscriptionData = subscriptionService.setSubscriptionData(token);
        formData = subscriptionService.setFormData(subscriptionData);
        subscriptionData.setMode(1);
        Map<SelectOptionItem, Boolean> periodicityMap = subscriptionData.makePeriodicityMap(subscriptionData);

        return ok(views.html.subscription.form.render(Messages.get("subscription.form.global.new.title"), formData, periodicityMap));
    }

    /**
     * Saves subscription's form data after create or edit
     *
     * @return Result
     */
    public Result save() {
        subscriptionData = new SubscriptionFormData();
        formData = Form.form(SubscriptionFormData.class).bindFromRequest();
        if (formData.hasErrors()) {
            flash("error", Messages.get("app.global.validation.message"));
            Map<SelectOptionItem, Boolean> periodicityMap = subscriptionData.makePeriodicityMap(subscriptionData);

            return badRequest(views.html.subscription.form.render(Messages.get("subscription.form.global.new.title"), formData, periodicityMap));
        }
        Subscription subscription = subscriptionService.save(formData);
        if (formData.get().getMode() == 0)
            installmentService.createInstallment(subscription);
        else
            installmentService.updateInstallments(subscription);
        flash("success", Messages.get("subscription.form.save.message.notification", subscription));
        pager = new Pager(this.currentPage);
        List<Subscription> subscriptions = subscriptionService.getSubscriptionList(pager);

        return ok(views.html.subscription.index.render(Messages.get("subscription.list.global.title"), subscriptions, pager));
    }

    /**
     * Create new installments and member installment for new subscription cycle.
     *
     * @return Result
     */
    public Result createInstallments() {
        installmentService.generateInstallments();
        return ok();
    }

    /**
     * Show subscription's details by token
     *
     * @param token Unique subscription token identifier
     * @return Result
     */
    public Result show(String token) {
        Subscription subscription = subscriptionService.getSubscription(token);

        return ok(views.html.subscription.show.render(subscription));
    }

    /**
     * Removes a specific subscription by token
     *
     * @param token Unique subscription token identifier
     * @return Result
     */
    public Result remove(String token) {
        if (subscriptionService.remove(token)) {
            flash("success", Messages.get("subscription.form.remove.message.success"));
        } else {
            flash("error", Messages.get("subscription.form.remove.message.error"));
        }
        pager = new Pager(1);
        List<Subscription> subscriptions = subscriptionService.getSubscriptionList(pager);

        return ok(views.html.subscription.index.render(Messages.get("subscription.list.global.title"), subscriptions, pager));
    }
}
