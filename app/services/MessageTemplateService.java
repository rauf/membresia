package services;

import models.MessageTemplate;
import play.data.Form;
import services.contract.MessageTemplateServiceInterface;
import services.formData.MessageTemplateFormData;

import java.util.List;

/**
 * Middleware class for controller model interaction and other member related business logic
 */
public class MessageTemplateService implements MessageTemplateServiceInterface {

    /**
     * {@inheritDoc}
     */
    public MessageTemplateFormData setMessageTemplateData(String token) {

        return new MessageTemplateFormData(getModel().get("token", token));
    }

    /**
     * {@inheritDoc}
     */
    public Form<MessageTemplateFormData> setFormData(MessageTemplateFormData messageTemplateData) {

        return Form.form(MessageTemplateFormData.class).fill(messageTemplateData);
    }

    /**
     * {@inheritDoc}
     */
    public List<MessageTemplate> getMessageTemplateList(Pager pager) {

        return getModel().getMessageTemplateList(pager);
    }

    /**
     * {@inheritDoc}
     */
    public MessageTemplate save(Form<MessageTemplateFormData> formData) {

        MessageTemplate messageTemplate = (formData.get().getId() != null) ? getModel().getByPk(formData.get().getId()) : getModel();
        messageTemplate.setData(formData.get());
        messageTemplate.save();
        return messageTemplate;
    }

    /**
     * {@inheritDoc}
     */
    public boolean remove(String token) {

        return getModel().remove(token);
    }

    /**
     * {@inheritDoc}
     */
    public MessageTemplate getMessageTemplate(String token) {

        return getModel().get("token", token);
    }

    /**
     * Creates Member model object
     *
     * @return Member
     */
    private MessageTemplate getModel() {

        return new MessageTemplate();
    }

    /**
     * {@inheritDoc}
     */
//    public void sendNewAccountMail(MailerClient mailer, MessageTemplate messageTemplate) {
//
//        Config conf = ConfigFactory.load();
//        String sendFromEmail = conf.getString("play.mailer.user");
//        String sendFromName = Messages.get("app.global.title");
//        String subject = sendFromName + ": " + Messages.get("adminUser.mail.subject.newAccount");
//
//        Email email = new Email();
//        email.setSubject(subject);
//        email.setFrom(sendFromName + "<" + sendFromEmail + ">");
//        email.addTo(member.toString() + "<" + member.getEmail() + ">");
//        String body = views.html.member.newMemberMail.render(subject, member).body();
//        email.setBodyHtml(body);
//
//        mailer.send(email);
//    }
}
