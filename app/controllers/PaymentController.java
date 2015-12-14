package controllers;

import models.MemberInstallment;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;

import models.Member;
import models.Payment;
import models.SelectOptionItem;
import play.mvc.With;
import services.MemberInstallmentService;
import services.MemberService;
import services.MoneyFormat;
import services.formData.PaymentFormData;

import java.util.Map;

public class PaymentController extends Controller {

    private MemberService memberService = new MemberService();

    private MemberInstallmentService memberInstallmentService = new MemberInstallmentService();

    /**
     * Displays member's payment view
     *
     * @param token Unique member token identifier
     * @return
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

    public Result makePublicPayment(String token) {
        PaymentFormData paymentFormData = new PaymentFormData();
        Form<PaymentFormData> formData = Form.form(PaymentFormData.class).fill(paymentFormData);

        MemberInstallment memberInstallment = memberInstallmentService.get("token", token);
        paymentFormData.setMemberToken(memberInstallment.getMember().generateToken());
        paymentFormData.setMemberInstallmentToken(memberInstallment.getToken());
        String amountDue = MoneyFormat.setMoney(memberInstallmentService.getAmountDue(token));

        return ok(views.html.payment.makePublicPayment.render(Messages.get("member.pay.global.title"), formData, memberInstallment, amountDue));
    }

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

        boolean paymentStatus = true;
        if (paymentStatus) {
            Payment payment = new Payment();
            payment.setData(formData.get());
            payment.save();
        }
        return redirect(routes.PaymentController.paymentComplete(paymentStatus));
    }

    public Result paymentComplete(boolean paymentStatus) {
        return ok(views.html.payment.paymentComplete.render(Messages.get("member.pay.global.title"), paymentStatus));
    }
}
