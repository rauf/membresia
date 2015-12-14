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

    public void setMemberInstallments(Member member) {
        SubscriptionService subscriptionService = new SubscriptionService();
        for (Subscription subscription : member.getSubscriptions()) {
            if (subscription != null) {
                boolean isMemberInstallmentSet = false;
                Installment lastInstallment = subscriptionService.getLastInstallment(subscription);
                for (MemberInstallment memberInstallment : member.getMemberInstallments()) {
                    isMemberInstallmentSet = (memberInstallment.getInstallment().equals(lastInstallment)) || isMemberInstallmentSet;
                }
                if (!isMemberInstallmentSet) createMemberInstallment(member, lastInstallment);
            }
        }
    }

    public void createMemberInstallment(Member member, Installment installment) {
        MemberInstallment memberInstallment = getModel();
        memberInstallment.setMember(member);
        memberInstallment.setInstallment(installment);
        memberInstallment.save();
        sendMemberInstallmentNotice(memberInstallment);
    }

    public Double getTotalPaid(String token) {
        if (token != null) {

            MemberInstallment memberInstallment = getModel().get("token", token);
            Double totalPaid = 0.0;
            if (memberInstallment != null) {

                for (Payment payment : memberInstallment.getPayments()) {
                    totalPaid += payment.getAmount();
                }
                return totalPaid;
            }
        }
        return 0.0;
    }

    public Double getAmountDue(String token) {
        if (token != null) {
            MemberInstallment memberInstallment = getModel().get("token", token);

            Double totalPaid = 0.0;
            if (memberInstallment != null) {
                for (Payment payment : memberInstallment.getPayments()) {
                    totalPaid += payment.getAmount();
                }
                return memberInstallment.getInstallment().getAmount() - totalPaid;
            }
        }
        return 0.0;
    }

    public void setPaid(MemberInstallment memberInstallment) {
        memberInstallment.setAsPaid();
    }

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

    public MemberInstallment get(String key, String value) {
        return getModel().get(key, value);
    }

    private MemberInstallment getModel() {
        return new MemberInstallment();
    }
}
