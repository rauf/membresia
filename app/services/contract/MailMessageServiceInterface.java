package services.contract;

import models.MailMessage;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import views.formData.MailMessageFormData;

public interface MailMessageServiceInterface {

    /**
     * Initializes form validation object with model empty object
     *
     * @return MailMessageFormData
     */
    MailMessageFormData setMessageData();

    /**
     * Fills UI form object with form validation object
     *
     * @param messageData form validation object
     * @return Form
     */
    Form<MailMessageFormData> setFormData(MailMessageFormData messageData);

    /**
     * Creates a mail object from message form data
     *
     * @param formData Form data
     * @return MailMessage
     */
    MailMessage setMessageInstance(Form<MailMessageFormData> formData);

    /**
     * Adds recipient to mail recipient's list
     *
     * @param messageData Form mail data object
     * @param token       User token to add as recipient
     */
    void addRecipient(MailMessageFormData messageData, String token);

    /**
     * Sends message to recipients
     *
     * @param mailer      Mail object
     * @param mailMessage Mail message object
     */
    void sendMessage(MailerClient mailer, MailMessage mailMessage);
}
