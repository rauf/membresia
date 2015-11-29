package services;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.api.libs.mailer.MailerClient;
import play.i18n.Messages;
import play.libs.mailer.Email;
import models.Member;
import play.data.Form;
import services.contract.MemberServiceInterface;

import java.util.List;

/**
 * Middleware class for controller model interaction and other member related business logic
 */
public class MemberService implements MemberServiceInterface {

    public Form<MemberFormData> setFormData(MemberFormData memberData) {
        return Form.form(MemberFormData.class).fill(memberData);
    }

    public MemberFormData setMemberData(String token) {
        return new MemberFormData(getModel().getMemberByToken(token));
    }

    public List<Member> getMemberList() {
        return getModel().getMemberList();
    }

    public Member save(Form<MemberFormData> formData) {
        Member member = (formData.get().getId() != null) ? getModel().getMemberById(formData.get().getId()) : getModel();
        member.setData(formData.get());
        member.save();
        return member;
    }

    public boolean remove(String token) {
        return getModel().remove(token);
    }

    public Member getMember(String token) {
        return getModel().getMemberByToken(token);
    }

    public boolean isMemberEmailUsed(String email, String token) {
        if (getModel().getMemberAdminUsrEmailUseCount(email, token) > 0) {
            return true;
        }
        return false;
    }

    private Member getModel() {
        return new Member();
    }

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
}

