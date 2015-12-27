package services;

import models.MessageTemplate;
import play.data.Form;
import views.formData.MessageTemplateFormData;
import services.contract.MessageTemplateServiceInterface;

import java.util.List;

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
}
