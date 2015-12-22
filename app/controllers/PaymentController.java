package controllers;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import models.Member;
import models.MemberInstallment;
import models.Payment;
import models.SelectOptionItem;
import services.formData.PaymentFormData;
import services.MemberService;
import services.MemberInstallmentService;
import services.MoneyFormat;
import services.PaymentService;

import java.util.Map;

/**
 * Controller class for Payment entity
 */
public class PaymentController extends Controller {

    @Inject
    private MemberService memberService;

    @Inject
    private MemberInstallmentService memberInstallmentService;

    /**
     * Displays member's payment view
     *
     * @param token Unique member token identifier
     * @return Result
     */
    @With(SecuredAction.class)
    public Result makePayment(String token) {
        PaymentFormData paymentFormData = new PaymentFormData();
        Form<PaymentFormData> formData = Form.form(PaymentFormData.class).fill(paymentFormData);

        paymentFormData.setMemberToken(token);
        Member member = memberService.getMember(token);
        Map<SelectOptionItem, Boolean> memberInstallmentMap = memberService.makeMemberInstallmentMap(member);

        return ok(views.html.payment.makePayment.render(Messages.get("member.pay.global.title"), member, formData, memberInstallmentMap));
    }

    /**
     * Displays mmember's payment public view
     *
     * @param token member installment unique token
     * @return Result
     */
    public Result makePublicPayment(String token) {
        PaymentFormData paymentFormData = new PaymentFormData();
        Form<PaymentFormData> formData = Form.form(PaymentFormData.class).fill(paymentFormData);

        MemberInstallment memberInstallment = memberInstallmentService.get("token", token);
        paymentFormData.setMemberToken(memberInstallment.getMember().generateToken());
        paymentFormData.setMemberInstallmentToken(memberInstallment.getToken());
        String amountDue = MoneyFormat.setMoney(memberInstallmentService.getAmountDue(token));

        return ok(views.html.payment.makePublicPayment.render(Messages.get("member.pay.global.title"), formData, memberInstallment, amountDue));
    }

    /**
     * Register member payment
     *
     * @return Result
     */
    @With(SecuredAction.class)
    public Result registerPayment() {
        Form<PaymentFormData> formData = Form.form(PaymentFormData.class).bindFromRequest();
        if (formData.hasErrors()) {
            flash("error", Messages.get("app.global.validation.message"));
            Member member = memberService.getMember(formData.field("memberToken").value());
            Map<SelectOptionItem, Boolean> memberInstallmentMap = memberService.makeMemberInstallmentMap(member);

            return ok(views.html.payment.makePayment.render(Messages.get("member.pay.global.title"), member, formData, memberInstallmentMap));
        }

        flash("success", Messages.get("payment.form.save.message.notification"));
        Payment payment = new Payment();
        payment.setData(formData.get());
        payment.save();
        Integer page = 1;

        return redirect(routes.MemberController.index(page));
    }

    /**
     * Register member payment from public form with Paypal process
     *
     * @return Result
     */
    public Result registerPublicPayment() {
        Form<PaymentFormData> formData = Form.form(PaymentFormData.class).bindFromRequest();
        if (formData.hasErrors()) {
            flash("error", Messages.get("app.global.validation.message"));
            MemberInstallment memberInstallment = memberInstallmentService.get("token", formData.data().get("token"));
            formData.get().setMemberToken(memberInstallment.getMember().generateToken());
            formData.get().setMemberInstallmentToken(memberInstallment.getToken());
            String amountDue = MoneyFormat.setMoney(memberInstallmentService.getAmountDue(formData.data().get("token")));

            return ok(views.html.payment.makePublicPayment.render(Messages.get("member.pay.global.title"), formData, memberInstallment, amountDue));

        }

        Payment payment = new Payment();
        payment.setData(formData.get());
        payment.save();
        Config conf = ConfigFactory.load();
        String business = conf.getString("paypal.business");
        String url = conf.getString("paypal.url");
        return ok(views.html.payment.paypalForm.render(Messages.get("paymentPublic.pay.paypal.title"), payment, business, url));
    }

    /**
     * Sets payment completed
     *
     * @param token payment token
     * @return
     */
    public Result paymentComplete(String token) {
        boolean paymentStatus = true;
        PaymentService paymentService = new PaymentService();
        paymentService.acceptPayment(token);
        return ok(views.html.payment.paymentComplete.render(Messages.get("paymentPublic.pay.complete.title"), paymentStatus));
    }
}
