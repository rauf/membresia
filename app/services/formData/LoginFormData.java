package services.formData;

import models.User;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

public class LoginFormData {

    public String email;
    public String password;
    public boolean isLoggedIn = false;

    public String validate() {

        UserService userService = new UserService();
        User user = userService.authenticate(email, password);
        if (user == null) {
            return Messages.get("login.form.validation.isLoggedIn");
        }

        return null;
    }
}
