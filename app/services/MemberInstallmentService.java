package services;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.Installment;
import models.Member;
import models.MemberInstallment;
import play.api.libs.mailer.MailerClient;
import play.i18n.Messages;
import play.libs.mailer.Email;
import services.contract.MemberInstallmentServiceInterface;

import javax.inject.Inject;

public class MemberInstallmentService implements MemberInstallmentServiceInterface {

    private MailerClient mailer;


    public void createMemberInstallment(Member member, Installment installment) {
        MemberInstallment memberInstallment = getModel();
        memberInstallment.setMember(member);
        memberInstallment.setInstallment(installment);
        memberInstallment.save();
        sendMemberInstallmentNotice(memberInstallment);
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

    @Inject
    private MailerClient setMailer(MailerClient mailer) {
        return this.mailer = mailer;
    }

    private MemberInstallment getModel() {
        return new MemberInstallment();
    }
}
