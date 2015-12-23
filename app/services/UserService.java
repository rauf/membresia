package services;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import play.api.libs.mailer.MailerClient;
import play.i18n.Messages;
import play.libs.mailer.Email;
import services.contract.UserServiceInterface;

import static play.mvc.Controller.session;

public class UserService implements UserServiceInterface {

    @Inject
    private MailerClient mailer;

    /**
     * {@inheritDoc}
     */
    public User getUser(String key, String value) {
        return getModel().get(key, value);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    public User setPassword(String email) {
        User user = getModel().get("email", email);
        if (user != null) {
            user.setPasswordRaw(this.generatePassword());
            user.setPassword(this.encryptPassword(user.getPasswordRaw()));
            user.save();
            return user;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void logout() {
        session().clear();
    }

    /**
     * {@inheritDoc}
     */
    public String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    /**
     * {@inheritDoc}
     */
    public String encryptPassword(String clearString) {
        return BCrypt.hashpw(clearString, BCrypt.gensalt());
    }

    /**
     * {@inheritDoc}
     */
    public boolean checkPassword(String candidate, String encryptedPassword) {
        return candidate != null && encryptedPassword != null && BCrypt.checkpw(candidate, encryptedPassword);
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
