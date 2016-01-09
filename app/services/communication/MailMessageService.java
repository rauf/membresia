package services.communication;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.communication.MailMessage;
import models.communication.MessageTemplate;
import models.user.User;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import play.i18n.Messages;
import play.libs.mailer.Email;
import services.communication.contract.MailMessageServiceInterface;
import views.formData.MailMessageFormData;

public class MailMessageService implements MailMessageServiceInterface {

    /**
     * {@inheritDoc}
     */
    public MailMessageFormData setMessageData() {
        return new MailMessageFormData(getModel());
    }

    /**
     * {@inheritDoc}
     */
    public Form<MailMessageFormData> setFormData(MailMessageFormData messageData) {
        return Form.form(MailMessageFormData.class).fill(messageData);
    }

    /**
     * {@inheritDoc}
     */
    public MailMessage setMessageInstance(Form<MailMessageFormData> formData) {
        MailMessage mailMessage = getModel();
        mailMessage.setData(formData.get());

        return mailMessage;
    }

    public void addRecipient(MailMessageFormData messageData, String token) {
        messageData.getRecipients().add(token);
    }

    public void setMessageFromMessageTemplate(MailMessageFormData messageData, String token) {
        MessageTemplateService messageTemplateService = new MessageTemplateService();
        MessageTemplate messageTemplate = messageTemplateService.getMessageTemplate(token);
        messageData.setBody(messageTemplate.getBody());
        messageData.setSubject(messageTemplate.getTitle());
    }

    /**
     * {@inheritDoc}
     */
    public void sendMessage(MailerClient mailer, MailMessage mailMessage) {
        Config conf = ConfigFactory.load();
        String sendFromEmail = conf.getString("play.mailer.user");
        String sendFromName = Messages.get("app.global.title");

        Email email = new Email();
        email.setSubject(mailMessage.getSubject());
        email.setFrom(sendFromName + "<" + sendFromEmail + ">");
        for (User user : mailMessage.getRecipients()) {
            email.addBcc(user.toString() + "<" + user.getEmail() + ">");
        }
        String body = views.html.communication.mailMessage.mail.render(mailMessage).body();
        email.setBodyHtml(body);

        mailer.send(email);
    }

    /**
     * Creates Member model object
     *
     * @return Member
     */
    private MailMessage getModel() {
        return new MailMessage();
    }
}
