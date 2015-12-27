package services;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import models.Member;
import models.Subscription;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import play.i18n.Messages;
import play.libs.mailer.Email;
import services.contract.MemberServiceInterface;
import views.formData.MemberFormData;

import java.util.List;

public class MemberService implements MemberServiceInterface {

    /**
     * {@inheritDoc}
     */
    public MemberFormData setMemberData(String token) {
        return new MemberFormData(getModel().get("token", token));
    }

    /**
     * {@inheritDoc}
     */
    public Form<MemberFormData> setFormData(MemberFormData memberData) {
        return Form.form(MemberFormData.class).fill(memberData);
    }

    /**
     * {@inheritDoc}
     */
    public Member getMember(String token) {
        return getModel().get("token", token);
    }

    /**
     * {@inheritDoc}
     */
    public List<Member> getMemberList(Pager pager) {
        return getModel().getMemberList(pager);
    }

    /**
     * {@inheritDoc}
     */
    public Member save(Form<MemberFormData> formData) {
        clearSubscriptions(formData.get().getToken());
        Member member = (formData.get().getId() != null) ? getModel().getByPk(formData.get().getId()) : getModel();
        member.setData(formData.get());
        member.save();
        return member;
    }

    /**
     * {@inheritDoc}
     */
    public boolean remove(String token) {
        clearSubscriptions(token);
        return getModel().remove(token);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isMemberEmailUsed(String email, String token) {
        return getModel().getUserEmailCount(email, token) > 0;
    }

    /**
     * {@inheritDoc}
     */
    public void sendNewAccountMail(MailerClient mailer, Member member) {
        Config conf = ConfigFactory.load();
        String sendFromEmail = conf.getString("play.mailer.user");
        String sendFromName = Messages.get("app.global.title");
        String subject = sendFromName + ": " + Messages.get("adminUser.mail.subject.newAccount");

        Email email = new Email();
        email.setSubject(subject);
        email.setFrom(sendFromName + "<" + sendFromEmail + ">");
        email.addTo(member.toString() + "<" + member.getEmail() + ">");
        String body = views.html.member.newMemberMail.render(subject, member).body();
        email.setBodyHtml(body);

        mailer.send(email);
    }

    /**
     * Removes all subscription relations from member
     *
     * @param token Unique member identifier
     */
    private void clearSubscriptions(String token) {
        Member member = getModel().get("token", token);
        if (member != null) {
            member.getSubscriptions().clear();
            member.save();
        }
    }

    /**
     * Creates Member model object
     *
     * @return Member
     */
    private Member getModel() {

        return new Member();
    }
}
