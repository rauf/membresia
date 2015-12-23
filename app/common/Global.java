package common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.Application;
import play.GlobalSettings;
import play.Logger;

import models.AdminUser;
import models.User;
import services.UserService;

/**
 * Global start up class
 */
public class Global extends GlobalSettings {

    /**
     * Executes at application startup and creates a default admin user
     *
     * @param app Application instance
     */
    @Override
    public void beforeStart(Application app) {
        Config conf = ConfigFactory.load();
        UserService userService = new UserService();
        User user = userService.getUser("email", conf.getString("adminUser.user.default.email"));
        if (user == null) {
            AdminUser adminUser = new AdminUser();
            adminUser.setEmail(conf.getString("adminUser.user.default.email"));
            adminUser.setName(conf.getString("adminUser.user.default.name"));
            adminUser.setLastName(conf.getString("adminUser.user.default.lastName"));
            adminUser.setPasswordRaw(conf.getString("adminUser.user.default.password"));
            adminUser.setPassword(userService.encryptPassword(adminUser.getPasswordRaw()));
            adminUser.setToken(adminUser.generateToken());
            adminUser.save();
            Logger.debug("USERADMIN " + adminUser.toString() + " CREATED");
        }
    }
}
