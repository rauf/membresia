package common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.Member;
import models.Subscription;
import play.Application;
import play.GlobalSettings;
import play.Logger;

import models.AdminUser;
import models.User;
import services.MemberService;
import services.SubscriptionService;
import services.UserService;

import javax.inject.Inject;
import java.util.Date;

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
        // Subscription subscription = setSubscription();
       // setMember();
    }

    public AdminUser setAdminUser() {
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

    public Subscription setSubscription() {
        Subscription subscription = new Subscription();
        subscription.setAmount(100.00);
        subscription.setTitle("Unique payment subscription");
        subscription.setDueDatePeriod(new Date("2016-01-01"));
        subscription.setPeriodicity("UNIQUE");
        subscription.setToken(subscription.generateToken());
        subscription.setSubscriptionId(subscription.generateSubscriptionId());
        subscription.save();
        Logger.debug("SUBSCRIPTION " + subscription.toString() + " CREATED");

        return subscription;
    }

    public Member setMember() {
        Member member = new Member();
        UserService userService = new UserService();
        String memberEmail = "jfernandez74@outlook.com";
        User user = userService.getUser("email", memberEmail);
        if (user == null) {
            member.setEmail(memberEmail);
            member.setName("Julio");
            member.setLastName("Jimenez");
            member.setPasswordRaw(conf.getString("adminUser.user.default.password"));
            member.setPassword(userService.encryptPassword(member.getPasswordRaw()));
            member.setToken(member.generateToken());
            member.setAddress("Hondillo 32");
            member.setCity("Lanjaron");
            member.setState("Granada");
            member.setCp("18420");
            member.setCountry("Espana");
            member.setMemberId(member.generateMemberId());
            member.setNif("76087206D");
            member.setPhone("628548992");
            // member.addSubscription(subscription);
            member.save();
            Logger.debug("MEMBER " + member.toString() + " CREATED");
        }
        return member;

    }
}
