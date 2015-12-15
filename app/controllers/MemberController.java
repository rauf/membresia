package controllers;

import play.api.libs.mailer.MailerClient;
import play.mvc.*;
import play.data.Form;
import play.i18n.Messages;
import models.Member;
import services.*;
import services.formData.MemberFormData;

import java.util.List;
import javax.inject.Inject;
import views.html.*;


/**
 * Controller for MemberController component
 */

@With(SecuredAction.class)
public class MemberController extends Controller {

    @Inject
    private SubscriptionService subscriptionService;

    @Inject
    private MemberInstallmentService memberInstalmentService;

    @Inject
    private MailerClient mailer;

    private MemberService memberService = new MemberService();

    private MemberFormData memberData;

    private Form<MemberFormData> formData;

    private Pager pager;

    private Integer currentPage = 1;


    /**
     * Show all members list
     *
     * @return Result
     */
    public Result index(Integer currentPage) {
        this.currentPage = currentPage;
        pager = new Pager(this.currentPage);
        List<Member> members = memberService.getMemberList(pager);
        return ok(views.html.member.index.render(Messages.get("member.list.global.title"), members, pager));
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
        return ok(views.html.member.form.render(Messages.get("member.form.global.new.title"), formData, subscriptionService.makeSubscriptionMap(memberData)));
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

        return ok(views.html.member.form.render(Messages.get("member.form.global.new.title"), formData, subscriptionService.makeSubscriptionMap(memberData)));
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

            return badRequest(views.html.member.form.render(Messages.get("member.form.global.new.title"), formData, subscriptionService.makeSubscriptionMap(memberData)));
        } else {
            Member member = memberService.save(formData);
            memberInstalmentService.setMemberInstallments(member);

            if (formData.get().getMode() == 0)
                memberService.sendNewAccountMail(mailer, member);

            flash("success", Messages.get("member.form.save.message.notification", member));
            pager = new Pager(this.currentPage);
            List<models.Member> members = memberService.getMemberList(pager);

            return ok(views.html.member.index.render(Messages.get("member.list.global.title"), members, pager));
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

        return ok(views.html.member.show.render(member, Messages.get("member.list.show.title")));
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
        pager = new Pager(1);
        List<models.Member> members = memberService.getMemberList(pager);

        return ok(views.html.member.index.render(Messages.get("member.list.global.title"), members, pager));
    }
}
