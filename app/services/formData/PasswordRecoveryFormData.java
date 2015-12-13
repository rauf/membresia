package services.formData;

import models.User;
import play.i18n.Messages;
import services.UserService;

public class PasswordRecoveryFormData {

    public String email;

    public String validate() {

        UserService userService = new UserService();
        User user = userService.setPassword(email);
        if (user == null) {
            return Messages.get("login.form.validation.recovery");
        }

        return null;
    }
}
