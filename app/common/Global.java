package common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import models.AdminUser;
import models.User;
import services.InstallmentService;
import services.UserService;

import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import scala.concurrent.duration.FiniteDuration;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Global start up class
 */
public class Global extends GlobalSettings {

    private Config conf = ConfigFactory.load();

    /**
     * Executes at application startup and creates a default admin user
     *
     * @param app Application instance
     */
    @Override
    public void beforeStart(Application app) {
        setAdminUser();
        setInstallmentWatcher();
    }

    /**
     * Creates default admin user
     *
     * @return AdminUser
     */
    private AdminUser setAdminUser() {
        AdminUser adminUser = new AdminUser();
        UserService userService = new UserService();
        User user = userService.getUser("email", conf.getString("adminUser.user.default.email"));
        if (user == null) {
            adminUser.setEmail(conf.getString("adminUser.user.default.email"));
            adminUser.setName(conf.getString("adminUser.user.default.name"));
            adminUser.setLastName(conf.getString("adminUser.user.default.lastName"));
            adminUser.setPasswordRaw(conf.getString("adminUser.user.default.password"));
            adminUser.setPassword(userService.encryptPassword(adminUser.getPasswordRaw()));
            adminUser.setToken(adminUser.generateToken());
            adminUser.save();
            Logger.debug("USERADMIN " + adminUser.toString() + " CREATED");
        }
        return adminUser;
    }

    /**
     * Sets the installment cron job
     */
    private void setInstallmentWatcher() {
        Long delayInSeconds;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date plannedStart = c.getTime();
        Date now = new Date();
        Date nextRun;
        if (now.after(plannedStart)) {
            c.add(Calendar.DAY_OF_WEEK, 1);
            nextRun = c.getTime();
        } else {
            nextRun = c.getTime();
        }
        delayInSeconds = (nextRun.getTime() - now.getTime()) / 1000; //To convert milliseconds to seconds.

        FiniteDuration delay = FiniteDuration.create(delayInSeconds, TimeUnit.SECONDS);
        FiniteDuration frequency = FiniteDuration.create(1, TimeUnit.DAYS);
        Runnable showTime = new Runnable() {
            @Override
            public void run() {
                InstallmentService installmentService = new InstallmentService();
                installmentService.generateInstallments();
                Logger.info("Installment task: " + new Date());
            }
        };

        Akka.system().scheduler().schedule(delay, frequency, showTime, Akka.system().dispatcher());
    }
}
