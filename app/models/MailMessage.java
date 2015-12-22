package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;
import services.formData.MailMessageFormData;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for MailMessage
 */
public class MailMessage extends Model {

    @Constraints.Required
    protected String subject;

    @Constraints.Required
    protected String body;

    @Constraints.Required
    protected String referrer;

    @Constraints.Required
    protected List<User> recipients = new ArrayList<>();

    /**
     * Generic constructor
     */
    public MailMessage() {

    }

    /**
     * Populates an object instance with form data
     *
     * @param formData Data from user form as a MailMessageFormData object
     */
    public void setData(MailMessageFormData formData) {
        this.setSubject(formData.getSubject());
        this.setBody(formData.getBody());
        this.setReferrer(formData.getReferrer());

        User user = new User();
        for (String recipient : formData.getRecipients()) {
            this.addRecipient(user.get("token", recipient));
        }
    }

    /**
     * Adds a user recipient to message recipient's list
     *
     * @param user User message recipient
     */
    private void addRecipient(User user) {
        this.recipients.add(user);
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

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public List<User> getRecipients() {
        return recipients;
    }
}
