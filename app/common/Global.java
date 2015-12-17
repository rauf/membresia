package common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.AdminUser;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.data.Form;
import services.UserService;
import services.formData.AdminUserFormData;

public class Global extends GlobalSettings {

    @Override
    public void beforeStart(Application app) {
        UserService userService = new UserService();
        User user = userService.getUserItem("email", "jfernandez74@uoc.edu");
        if (user == null) {
            Config conf = ConfigFactory.load();

            AdminUser adminUser = new AdminUser();
            adminUser.setEmail(conf.getString("adminUser.user.default.email"));
            adminUser.setName(conf.getString("adminUser.user.default.name"));
            adminUser.setLastName(conf.getString("adminUser.user.default.lastName"));
            adminUser.setPasswordRaw(conf.getString("adminUser.user.default.password"));
            adminUser.setPassword(userService.cryptPassword(adminUser.getPasswordRaw()));
            adminUser.setToken(adminUser.generateToken());
            adminUser.save();
            Logger.debug("USERADMIN " + adminUser.toString() + " CREATED");
        }
    }
}
