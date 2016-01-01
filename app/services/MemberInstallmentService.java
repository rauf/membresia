package services;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.*;
import play.i18n.Messages;
import play.libs.mailer.Email;
import play.api.libs.mailer.MailerClient;
import services.contract.MemberInstallmentServiceInterface;

import javax.inject.Inject;

public class MemberInstallmentService implements MemberInstallmentServiceInterface {

    @Inject
    private MailerClient mailer;

    /**
     * {@inheritDoc}
     */
    public MemberInstallment get(String key, String value) {
        return getModel().get(key, value);
    }

    /**
     * {@inheritDoc}
     */
    public void setMemberInstallments(Member member) {
        InstallmentService installmentService = new InstallmentService();
        for (Subscription subscription : member.getSubscriptions()) {
            if (subscription != null) {
                boolean isMemberInstallmentSet = false;
                Installment lastInstallment = installmentService.getSubscriptionLastInstallment(subscription);
                for (MemberInstallment memberInstallment : member.getMemberInstallments()) {
                    isMemberInstallmentSet = (memberInstallment.getInstallment().equals(lastInstallment)) || isMemberInstallmentSet;
                }
                if (!isMemberInstallmentSet) createMemberInstallment(member, lastInstallment);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void createMemberInstallment(Member member, Installment installment) {
        MemberInstallment memberInstallment = getModel();
        memberInstallment.setMember(member);
        memberInstallment.setInstallment(installment);
        memberInstallment.save();
        sendMemberInstallmentNotice(memberInstallment);
    }

    /**
     * {@inheritDoc}
     */
    public Double getTotalPaid(String token) {
        if (token != null) {
            MemberInstallment memberInstallment = getModel().get("token", token);
            if (memberInstallment != null) {
                return memberInstallment.getTotalPaid();
            }
        }

        return 0.0;
    }

    /**
     * {@inheritDoc}
     */
    public Double getAmountDue(String token) {
        if (token != null) {
            MemberInstallment memberInstallment = getModel().get("token", token);
            if (memberInstallment != null) {
                return memberInstallment.getAmountDue();
            }
        }
        return 0.0;
    }

    /**
     * {@inheritDoc}
     */
    public void setPaid(MemberInstallment memberInstallment) {
        memberInstallment.setAsPaid();
    }

    /**
     * Send a member installment to member when a member installment is created
     *
     * @param memberInstallment Member installment with information for mail content
     */
    private void sendMemberInstallmentNotice(MemberInstallment memberInstallment) {
        Config conf = ConfigFactory.load();
        String sendFromEmail = conf.getString("play.mailer.user");
        String sendFromName = Messages.get("app.global.title");
        String subject = sendFromName + ": " + Messages.get("adminUser.mail.subject.newAccount");

        Email email = new Email();
        email.setSubject(subject);
        email.setFrom(sendFromName + "<" + sendFromEmail + ">");
        email.addTo(memberInstallment.getMember().toString() + "<" + memberInstallment.getMember().getEmail() + ">");
        String body = views.html.memberInstallment.memberInstallmentNotice.render(subject, memberInstallment).body();
        email.setBodyHtml(body);

        mailer.send(email);
    }

    /**
     * Gets a memberInstallment object instance
     *
     * @return MemberInstallment
     */
    private MemberInstallment getModel() {
        return new MemberInstallment();
    }
}
