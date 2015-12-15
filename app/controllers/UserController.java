package controllers;

import models.User;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import services.formData.LoginFormData;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;
import services.formData.PasswordRecoveryFormData;

import javax.inject.Inject;
import views.html.*;

public class UserController extends Controller {

    @Inject
    private UserService userService;

    public Result login() {
        Form<LoginFormData> formData = Form.form(LoginFormData.class);
        return ok(views.html.user.login.render(Messages.get("user.login.global.title"), formData));
    }

    public Result authenticate() {
        Form<LoginFormData> loginForm = Form.form(LoginFormData.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            flash("error", Messages.get("login.form.auth.error"));
            return badRequest(views.html.user.login.render(Messages.get("user.login.global.title"), loginForm));
        } else {
            return redirect(routes.SubscriptionController.index(1));
        }
    }

    public Result passwordRecovery() {
        Form<PasswordRecoveryFormData> formData = Form.form(PasswordRecoveryFormData.class);
        return ok(views.html.user.passwordRecovery.render(Messages.get("user.login.passwordRecovery.title"), formData));
    }

    public Result changePassword() {
        Form<PasswordRecoveryFormData> formData = Form.form(PasswordRecoveryFormData.class).bindFromRequest();
        if (formData.hasErrors()) {
            flash("error", Messages.get("login.form.recovery.error"));
            return badRequest(views.html.user.passwordRecovery.render(Messages.get("user.login.passwordRecovery.title"), formData));
        } else {
            User user = userService.setPassword(formData.data().get("email"));
            userService.sendPasswordRecoveryEmail(user);
            return redirect(routes.UserController.login());
        }

    }

    public Result logout() {
        userService.logout();
        return redirect(routes.UserController.login());
    }
}
