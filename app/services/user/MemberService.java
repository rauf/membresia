package services.user;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import lib.Pager;
import models.user.Member;
import models.subscription.MemberInstallment;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import play.i18n.Messages;
import play.libs.mailer.Email;
import services.user.contract.MemberServiceInterface;
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
        if (hasPayments(token)) return archiveMember(token);

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
        String body = views.html.user.member.newMemberMail.render(subject, member).body();
        email.setBodyHtml(body);

        mailer.send(email);
    }

    /**
     * Checks is a specific member has realized any payments in the past
     *
     * @param token Unique member identifier
     * @return boolean
     */
    private boolean hasPayments(String token) {
        Member member = getModel().get("token", token);
        for (MemberInstallment memberInstallment : member.getMemberInstallments()) {
            if (!memberInstallment.getPayments().isEmpty()) return true;
        }
        return false;
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
     * Archive member and set its attributes to null
     *
     * @param token Unique user identifier
     */
    private boolean archiveMember(String token) {
        Member member = getModel().get("token", token);
        if (member != null) {
            member.setAddress(null);
            member.setCity(null);
            member.setCountry(null);
            member.setState(null);
            member.setCp(null);
            member.setPassword(null);
            member.setEmail(null);
            member.setNif(null);
            member.setPhone(null);
            member.setStatus(false);
            member.setName(Messages.get("member.archive.name.placeholder"));
            member.setLastName(Messages.get("member.archive.lastName.placeholder"));
            member.save();
            return true;
        }
        return false;
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
