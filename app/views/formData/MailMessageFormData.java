package views.formData;

import models.communication.MailMessage;
import models.user.User;
import play.data.validation.ValidationError;
import play.i18n.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that handles Members form submission and validation and relates submitted data to the model
 */
public class MailMessageFormData {

    protected String subject;
    protected String body;
    protected List<String> recipients = new ArrayList<>();

    protected MailMessage mailMessage = new MailMessage();

    /**
     * Creates a new MessageTemplateFormData instance for member create action
     */
    public MailMessageFormData() {

    }

    /**
     * Created a new MessageTemplateFormData instance for member editing action
     *
     * @param mailMessage MailMessage model object
     */
    public MailMessageFormData(MailMessage mailMessage) {
        this.subject = mailMessage.getSubject();
        this.body = mailMessage.getBody();
        this.recipients.addAll(mailMessage.getRecipients().stream().map(User::getToken).collect(Collectors.toList()));
    }

    /**
     * Server side validation method
     *
     * @return List<ValidationError>
     */
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if (subject == null || subject.length() == 0) {
            errors.add(new ValidationError("subject", Messages.get("message.form.validation.subject")));
        }

        if (body == null || body.length() == 0) {
            errors.add(new ValidationError("body", Messages.get("message.form.validation.body")));
        }

        if (recipients == null || recipients.size() == 0) {
            errors.add(new ValidationError("recipients", Messages.get("message.form.validation.recipients")));
        }

        if (errors.size() > 0)

            return errors;

        return null;
    }

    public String getSubject() {

        return subject;
    }

    public void setSubject(String subject) {

        this.subject = subject;
    }

    public String getBody() {

        return body;
    }

    public void setBody(String body) {

        this.body = body;
    }

    public List<String> getRecipients() {

        return recipients;
    }
}
