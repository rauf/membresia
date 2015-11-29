package controllers;

import play.api.libs.mailer.MailerClient;
import play.mvc.*;
import play.data.Form;
import play.i18n.Messages;

import java.util.List;

import services.MemberFormData;
import models.Member;
import models.Subscription;
import services.MemberService;

import javax.inject.Inject;

/**
 * Controller for MemberController component
 */
public class MemberController extends Controller {

    private MemberService memberService = new MemberService();

    private MemberFormData memberData;

    private Form<MemberFormData> formData;

    private final MailerClient mailer;

    @Inject
    public MemberController(MailerClient mailer) {
        this.mailer = mailer;
    }


    /**
     * Show all members list
     *
     * @return Result
     */
    public Result index() {
        List<Member> members = memberService.getMemberList();
        return ok(views.html.member.index.render(members, Messages.get("member.list.global.title")));
    }

    /**
     * Renders member form in creation mode
     *
     * @return Result
     */
    public Result create() {
        memberData = new MemberFormData();
        formData = memberService.setFormData(memberData);
        memberData.setMode(0);

        return ok(views.html.member.form.render(Messages.get("member.form.global.new.title"), formData, Subscription.makeSubscriptionMap(memberData)));
    }

    /**
     * Renders member form in editing mode by member token
     *
     * @param token Unique member token identifier
     * @return Result
     */
    public Result edit(String token) {
        memberData = new MemberFormData();
        memberData = memberService.setMemberData(token);
        formData = memberService.setFormData(memberData);
        memberData.setMode(1);

        return ok(views.html.member.form.render(Messages.get("member.form.global.new.title"), formData, Subscription.makeSubscriptionMap(memberData)));
    }

    /**
     * Saves member form data after create or edit
     *
     * @return Result
     */
    public Result save() {
        memberData = new MemberFormData();
        formData = Form.form(MemberFormData.class).bindFromRequest();

        if (formData.hasErrors()) {
            flash("error", Messages.get("app.global.validation.message"));

            return badRequest(views.html.member.form.render(Messages.get("member.form.global.new.title"), formData, Subscription.makeSubscriptionMap(null)));
        } else {
            Member member = memberService.save(formData);
            if (formData.get().getMode() == 0)
                memberService.sendNewAccountMail(mailer, member);

            flash("success", Messages.get("member.form.save.message.notification", member));
            List<models.Member> members = memberService.getMemberList();

            return ok(views.html.member.index.render(members, Messages.get("member.list.global.title")));
        }
    }

    /**
     * Show member details by member token
     *
     * @param token Unique member token identifier
     * @return Result
     */
    public Result show(String token) {
        Member member = memberService.getMember(token);

        return ok(views.html.member.show.render(member));
    }

    /**
     * Removes a specific member by member token
     *
     * @param token Unique member token identifier
     * @return Result
     */
    public Result remove(String token) {
        if (memberService.remove(token)) {
            flash("success", Messages.get("member.form.remove.message.success"));
        } else {
            flash("error", Messages.get("member.form.remove.message.error"));
        }
        List<models.Member> members = memberService.getMemberList();

        return ok(views.html.member.index.render(members, Messages.get("member.list.global.title")));
    }

    /**
     * Displays member's payment view
     *
     * @param token Unique member token identifier
     * @return
     */
    public Result makePayment(String token) {

        return redirect("/");
    }
}
