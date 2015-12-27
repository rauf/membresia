package views.formData;

import models.User;
import play.i18n.Messages;
import services.UserService;

/**
 * Class that handles the password recovery form valdiation
 */
public class PasswordRecoveryFormData {

    public String email;

    /**
     * Check that the user email is a real user email to send the new password to.
     *
     * @return String|null
     */
    public String validate() {
        UserService userService = new UserService();
        User user = userService.setPassword(email);
        if (user == null) {
            return Messages.get("login.form.validation.recovery");
        }

        return null;
    }
}
