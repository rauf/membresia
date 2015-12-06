package services.formData;

import models.MessageTemplate;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import services.MessageTemplateService;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles Members form submission and validation and relates submitted data to the model
 */
public class MessageTemplateFormData {

    protected Long id;
    protected String title;
    protected String body;
    protected String token;
    protected Integer mode;

    protected MessageTemplate messageTemplate = new MessageTemplate();
    protected MessageTemplateService messageTemplateService = new MessageTemplateService();


    /**
     * Creates a new MessageTemplateFormData instance for member create action
     */
    public MessageTemplateFormData() {
        this.token = this.messageTemplate.generateToken();
    }

    /**
     * Created a new MessageTemplateFormData instance for member editing action
     *
     * @param messageTemplate MessageTemplate model object
     */
    public MessageTemplateFormData(MessageTemplate messageTemplate) {
        this.id = messageTemplate.getId();
        this.title = messageTemplate.getTitle();
        this.body = messageTemplate.getBody();
        this.token = messageTemplate.getToken();
    }

    /**
     * Server side validation method
     *
     * @return List<ValidationError>
     */
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if (title == null || title.length() == 0) {
            errors.add(new ValidationError("title", Messages.get("messageTemplate.form.validation.title")));
        }

        if (body == null || body.length() == 0) {
            errors.add(new ValidationError("body", Messages.get("messageTemplate.form.validation.body")));
        }

        if (errors.size() > 0)

            return errors;

        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }
}
