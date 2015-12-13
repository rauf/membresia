package controllers;

import models.MailMessage;
import play.api.libs.mailer.MailerClient;
import play.mvc.*;
import play.data.Form;
import play.i18n.Messages;
import services.MailMessageService;
import services.UserService;
import services.formData.MailMessageFormData;

import javax.inject.Inject;

/**
 * Controller for MemberController component
 */

@With(SecuredAction.class)
public class MailMessageController extends Controller {

    private MailMessageService messageService = new MailMessageService();

    private UserService userService = new UserService();

    private MailMessageFormData messageData;

    private Form<MailMessageFormData> formData;

    private final MailerClient mailer;

    @Inject
    public MailMessageController(MailerClient mailer) {

        this.mailer = mailer;
    }

    public Result sendMailForm(String token) {

        messageData = new MailMessageFormData();
        messageData = messageService.setMessageData();
        formData = messageService.setFormData(messageData);
        messageData.setReferrer(request().getHeader("referer"));
        messageService.addRecipient(messageData, token);

        return ok(views.html.mailMessage.form.render(Messages.get("mailMessage.form.send.title"), formData, userService.makeUserMap(messageData)));
    }

    public Result sendMailFormTemplate(String token) {

        messageData = new MailMessageFormData();
        messageData = messageService.setMessageData();
        formData = messageService.setFormData(messageData);
        messageData.setReferrer(request().getHeader("referer"));
        messageService.addRecipient(messageData, token);
        messageService.setMessageFromMessageTemplate(messageData, token);

        return ok(views.html.mailMessage.form.render(Messages.get("mailMessage.form.send.title"), formData, userService.makeUserMap(messageData)));
    }

    public Result sendMail() {

        messageData = new MailMessageFormData();
        formData = Form.form(MailMessageFormData.class).bindFromRequest();

        if (formData.hasErrors()) {
            flash("error", Messages.get("app.global.validation.message"));

            return badRequest(views.html.mailMessage.form.render(Messages.get("mailMessage.form.global.new.title"), formData, userService.makeUserMap(messageData)));
        } else {
            MailMessage mailMessage = messageService.setMessageInstance(formData);
            messageService.sendMessage(mailer, mailMessage);
            flash("success", Messages.get("mailMessage.form.save.message.notification", mailMessage.toString()));

            return redirect(mailMessage.getReferrer());
        }
    }
}
