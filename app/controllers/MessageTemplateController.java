package controllers;

import models.MessageTemplate;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import services.MessageTemplateService;
import services.Pager;
import services.formData.MessageTemplateFormData;

import javax.inject.Inject;
import java.util.List;

/**
 * Controller for MessageTemplateController component
 */
public class MessageTemplateController extends Controller {

    private MessageTemplateService messageTemplateService = new MessageTemplateService();

    private MessageTemplateFormData messageTemplateData;

    private Form<MessageTemplateFormData> formData;

    private Pager pager;

    private Integer currentPage = 1;

    private final MailerClient mailer;

    @Inject
    public MessageTemplateController(MailerClient mailer) {
        this.mailer = mailer;
    }


    /**
     * Show all messageTemplates list
     *
     * @return Result
     */
    public Result index(Integer currentPage) {
        this.currentPage = currentPage;
        pager = new Pager(this.currentPage);
        List<MessageTemplate> messageTemplates = messageTemplateService.getMessageTemplateList(pager);
        return ok(views.html.messageTemplate.index.render(Messages.get("messageTemplate.list.global.title"), messageTemplates, pager));
    }

    /**
     * Renders messageTemplate form in creation mode
     *
     * @return Result
     */
    public Result create() {
        messageTemplateData = new MessageTemplateFormData();
        formData = messageTemplateService.setFormData(messageTemplateData);
        messageTemplateData.setMode(0);

        return ok(views.html.messageTemplate.form.render(Messages.get("messageTemplate.form.global.new.title"), formData));
    }

    /**
     * Renders messageTemplate form in editing mode by messageTemplate token
     *
     * @param token Unique messageTemplate token identifier
     * @return Result
     */
    public Result edit(String token) {
        messageTemplateData = new MessageTemplateFormData();
        messageTemplateData = messageTemplateService.setMessageTemplateData(token);
        formData = messageTemplateService.setFormData(messageTemplateData);
        messageTemplateData.setMode(1);

        return ok(views.html.messageTemplate.form.render(Messages.get("messageTemplate.form.global.new.title"), formData));
    }

    /**
     * Saves messageTemplate form data after create or edit
     *
     * @return Result
     */
    public Result save() {
        messageTemplateData = new MessageTemplateFormData();
        formData = Form.form(MessageTemplateFormData.class).bindFromRequest();

        if (formData.hasErrors()) {
            flash("error", Messages.get("app.global.validation.message"));

            return badRequest(views.html.messageTemplate.form.render(Messages.get("messageTemplate.form.global.new.title"), formData));
        } else {
            MessageTemplate messageTemplate = messageTemplateService.save(formData);

            flash("success", Messages.get("messageTemplate.form.save.message.notification", messageTemplate));
            pager = new Pager(this.currentPage);
            List<MessageTemplate> messageTemplates = messageTemplateService.getMessageTemplateList(pager);

            return ok(views.html.messageTemplate.index.render(Messages.get("messageTemplate.list.global.title"), messageTemplates, pager));
        }
    }

    /**
     * Show messageTemplate details by messageTemplate token
     *
     * @param token Unique messageTemplate token identifier
     * @return Result
     */
    public Result show(String token) {
        MessageTemplate messageTemplate = messageTemplateService.getMessageTemplate(token);

        return ok(views.html.messageTemplate.show.render(messageTemplate));
    }

    /**
     * Removes a specific messageTemplate by messageTemplate token
     *
     * @param token Unique messageTemplate token identifier
     * @return Result
     */
    public Result remove(String token) {
        if (messageTemplateService.remove(token)) {
            flash("success", Messages.get("messageTemplate.form.remove.message.success"));
        } else {
            flash("error", Messages.get("messageTemplate.form.remove.message.error"));
        }
        pager = new Pager(1);
        List<MessageTemplate> messageTemplates = messageTemplateService.getMessageTemplateList(pager);

        return ok(views.html.messageTemplate.index.render(Messages.get("messageTemplate.list.global.title"), messageTemplates, pager));
    }
}