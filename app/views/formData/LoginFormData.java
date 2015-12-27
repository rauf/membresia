package views.formData;

import models.User;
import play.i18n.Messages;
import services.UserService;

/**
 * Class for processing the user login form
 */
public class LoginFormData {

    public String email;
    public String password;

    /**
     * Checks if a specific user and password combination ir correct
     *
     * @return String|null
     */
    public String validate() {
        UserService userService = new UserService();
        User user = userService.authenticate(email, password);
        if (user == null) {
            return Messages.get("login.form.validation.isLoggedIn");
        }

        return null;
    }
}
