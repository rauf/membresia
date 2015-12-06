package services.contract;

import models.MailMessage;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import services.formData.MailMessageFormData;


public interface MailMessageServiceInterface {

    MailMessageFormData setMessageData();

    Form<MailMessageFormData> setFormData(MailMessageFormData messageData);

    MailMessage setMessageInstance(Form<MailMessageFormData> formData);

    void addRecipient(MailMessageFormData messageData, String token);

    void sendMessage(MailerClient mailer, MailMessage mailMessage);


}
