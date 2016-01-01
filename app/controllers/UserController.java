package controllers;

import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;

import models.User;
import views.formData.LoginFormData;
import services.UserService;
import views.formData.PasswordRecoveryFormData;

import javax.inject.Inject;

/**
 * Controller for User entity
 */
public class UserController extends Controller {

    @Inject
    private UserService userService;

    /**
     * Renders user system login action
     *
     * @return Result
     */
    public Result login() {
        Form<LoginFormData> formData = Form.form(LoginFormData.class);

        return ok(views.html.user.login.render(Messages.get("user.login.global.title"), formData));
    }

    /**
     * Check user credentials against persistence unit
     *
     * @return Result
     */
    public Result authenticate() {
        Form<LoginFormData> loginForm = Form.form(LoginFormData.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            flash("error", Messages.get("login.form.auth.error"));

            return badRequest(views.html.user.login.render(Messages.get("user.login.global.title"), loginForm));
        }

        return redirect(routes.StatisticController.index());
    }

    /**
     * Renders user password recovery form
     *
     * @return Result
     */
    public Result passwordRecovery() {
        Form<PasswordRecoveryFormData> formData = Form.form(PasswordRecoveryFormData.class);

        return ok(views.html.user.passwordRecovery.render(Messages.get("user.login.passwordRecovery.title"), formData));
    }

    /**
     * Send user a new password upon password recovery action request
     *
     * @return Result
     */
    public Result changePassword() {
        Form<PasswordRecoveryFormData> formData = Form.form(PasswordRecoveryFormData.class).bindFromRequest();
        if (formData.hasErrors()) {
            flash("error", Messages.get("login.form.recovery.error"));
            return badRequest(views.html.user.passwordRecovery.render(Messages.get("user.login.passwordRecovery.title"), formData));
        }
        User user = userService.setPassword(formData.data().get("email"));
        userService.sendPasswordRecoveryEmail(user);

        return redirect(routes.UserController.login());
    }

    /**
     * Logout user from system
     *
     * @return Result
     */
    public Result logout() {
        userService.logout();

        return redirect(routes.UserController.login());
    }
}
