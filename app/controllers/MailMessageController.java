package controllers;

import play.api.libs.mailer.MailerClient;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.*;

import models.MailMessage;
import models.SelectOptionItem;
import views.formData.MailMessageFormData;
import services.MailMessageService;
import services.UserService;
import views.formData.UserFormData;

import javax.inject.Inject;
import java.util.Map;

/**
 * Controller for MemberController entity
 */
@With(SecuredAction.class)
public class MailMessageController extends Controller {

    @Inject
    private MailerClient mailer;

    private MailMessageService messageService = new MailMessageService();

    private UserService userService = new UserService();

    private MailMessageFormData messageData;

    private Form<MailMessageFormData> formData;

    private UserFormData userFormData = new UserFormData();


    /**
     * Render mail form with default recipient
     *
     * @param token recipient user token
     * @return Result
     */
    public Result sendMailForm(String token) {
        messageData = new MailMessageFormData();
        messageData = messageService.setMessageData();
        formData = messageService.setFormData(messageData);
        messageService.addRecipient(messageData, token);
        Map<SelectOptionItem, Boolean> userMap = userFormData.makeUserMap(messageData);

        return ok(views.html.mailMessage.form.render(Messages.get("mailMessage.form.send.title"), formData, userMap));
    }

    /**
     * Renders mail form with default body message from template
     *
     * @param token message template token
     * @return Result
     */
    public Result sendMailFormTemplate(String token) {
        messageData = new MailMessageFormData();
        messageData = messageService.setMessageData();
        formData = messageService.setFormData(messageData);
        messageService.addRecipient(messageData, token);
        messageService.setMessageFromMessageTemplate(messageData, token);
        Map<SelectOptionItem, Boolean> userMap = userFormData.makeUserMap(messageData);

        return ok(views.html.mailMessage.form.render(Messages.get("mailMessage.form.send.title"), formData, userMap));
    }

    /**
     * Send mail action
     *
     * @return Result
     */
    public Result sendMail() {
        messageData = new MailMessageFormData();
        formData = Form.form(MailMessageFormData.class).bindFromRequest();
        if (formData.hasErrors()) {
            flash("error", Messages.get("app.global.validation.message"));
            Map<SelectOptionItem, Boolean> userMap = userFormData.makeUserMap(messageData);

            return badRequest(views.html.mailMessage.form.render(Messages.get("mailMessage.form.global.new.title"), formData, userMap));
        }

        MailMessage mailMessage = messageService.setMessageInstance(formData);
        messageService.sendMessage(mailer, mailMessage);
        flash("success", Messages.get("mailMessage.form.save.message.notification.message", mailMessage.toString()));

        return ok(views.html.mailMessage.confirm.render(Messages.get("mailMessage.form.save.message.notification")));
    }
}
