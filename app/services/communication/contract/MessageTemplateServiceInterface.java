package services.communication.contract;

import models.communication.MessageTemplate;
import play.data.Form;
import views.formData.MessageTemplateFormData;
import lib.Pager;

import java.util.List;

/**
 * Service interface class for controller model interaction and other member related business logic
 */
public interface MessageTemplateServiceInterface {

    /**
     * Sets a MessageTemplateFormData object from model data
     *
     * @param token Unique messageTemplate token identifier
     * @return MessageTemplateFormData
     */
    MessageTemplateFormData setMessageTemplateData(String token);

    /**
     * Generates a form object from a MessageTemplateFormData object populated from model
     *
     * @param messageTemplateData Data from model object
     * @return Form
     */
    Form<MessageTemplateFormData> setFormData(MessageTemplateFormData messageTemplateData);

    /**
     * Gets messageTemplate paginated list from DB
     *
     * @param pager Pager object for query pagination
     * @return List
     */
    List<MessageTemplate> getMessageTemplateList(Pager pager);

    /**
     * Get a specific messageTemplate by token
     *
     * @param token Unique messageTemplate identifier
     * @return MessageTemplate
     */
    MessageTemplate getMessageTemplate(String token);

    /**
     * Saves form data into persistence object
     *
     * @param formData MessageTemplate data from UI form
     * @return Member
     */
    MessageTemplate save(Form<MessageTemplateFormData> formData);

    /**
     * Removes a specific MessageTemplate by token
     *
     * @param token Unique MessageTemplate identifier
     * @return boolean
     */
    boolean remove(String token);
}
