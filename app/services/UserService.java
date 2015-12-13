package services;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.SelectOptionItem;
import models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import play.api.libs.mailer.MailerClient;
import play.i18n.Messages;
import play.libs.mailer.Email;
import services.contract.UserServiceInterface;
import services.formData.MailMessageFormData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.mvc.Controller.session;

/**
 * Middleware class for controller model interaction and other member related business logic
 */

public class UserService implements UserServiceInterface {

    @Inject
    private MailerClient mailer;

    /**
     * {@inheritDoc}
     */
    public User getUser(String token) {

        return getModel().getUserByToken(token);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isUserEmailUsed(String email, String token) {

        return getModel().getUserEmailCount(email, token) > 0;
    }

    public Map<SelectOptionItem, Boolean> makeUserMap(MailMessageFormData messageFormData) {
        List<User> allUsers = getModel().getUserRawList();
        Map<SelectOptionItem, Boolean> userMap = new HashMap<SelectOptionItem, Boolean>();
        for (User user : allUsers) {
            SelectOptionItem selectOptionItem = new SelectOptionItem(user.toString() + " <" + user.getEmail() + ">", user.getToken());
            userMap.put(selectOptionItem, (messageFormData != null && messageFormData.getRecipients().contains(user.getToken())));
        }
        return userMap;
    }

    public User authenticate(String email, String password) {
        User user = getModel().get("email", email);
        if (user != null && checkPassword(password, user.getPassword())) {
            session().clear();
            session("email", user.getEmail());
            session("name", user.toString());
            session("token", user.getToken());
            session("X-AUTH-TOKEN", user.getToken());
            session("gravatar", user.getGravatar());
            return user;
        }
        return null;
    }

    public User setPassword(String email) {
        User user = getModel().get("email", email);
        if (user != null) {
            user.setPasswordRaw(this.generatePassword());
            user.setPassword(this.cryptPassword(user.getPasswordRaw()));
            user.save();
            return user;
        }
        return null;
    }

    public void sendPasswordRecoveryEmail(User user) {
        Config conf = ConfigFactory.load();
        String sendFromEmail = conf.getString("play.mailer.user");
        String sendFromName = Messages.get("app.global.title");
        String subject = sendFromName + ": " + Messages.get("login.mail.subject.recovery");

        Email email = new Email();
        email.setSubject(subject);
        email.setFrom(sendFromName + "<" + sendFromEmail + ">");
        email.addTo(user.toString() + "<" + user.getEmail() + ">");
        String body = views.html.user.passwordRecoveryEmail.render(subject, user).body();
        email.setBodyHtml(body);

        mailer.send(email);
    }

    public void logout() {
        session().clear();
    }

    public String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public String cryptPassword(String clearString) {
        return BCrypt.hashpw(clearString, BCrypt.gensalt());
    }

    public boolean checkPassword(String candidate, String encryptedPassword) {
        if (candidate == null) {
            return false;
        }
        if (encryptedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(candidate, encryptedPassword);
    }

    /**
     * Creates User model object
     *
     * @return User
     */
    private User getModel() {

        return new User();
    }
}
