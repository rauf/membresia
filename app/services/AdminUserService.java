package services;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.AdminUser;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import play.i18n.Messages;
import services.contract.AdminUserServiceInterface;

import java.util.List;

import play.libs.mailer.Email;

/**
 * Middleware class for controller model interaction and other adminUser related business logic
 */
public class AdminUserService implements AdminUserServiceInterface {

    public Form<AdminUserFormData> setFormData(AdminUserFormData adminUserData) {
        return Form.form(AdminUserFormData.class).fill(adminUserData);
    }

    public AdminUserFormData setAdminUserData(String token) {
        return new AdminUserFormData(getModel().getAdminUserByToken(token));
    }

    public List<AdminUser> getAdminUserList() {
        return getModel().getAdminUserList();
    }

    public AdminUser save(Form<AdminUserFormData> formData) {
        AdminUser adminUser = (formData.get().getId() != null) ? getModel().getAdminUserById(formData.get().getId()) : getModel();
        adminUser.setData(formData.get());
        adminUser.save();
        return adminUser;
    }


    public boolean isAdminUserEmailUsed(String email, String token) {
        if (getModel().getAdminUserEmailUseCount(email, token) > 0) {
            return true;
        }
        return false;
    }

    public boolean remove(String token) {
        return getModel().remove(token);
    }

    public AdminUser getAdminUser(String token) {
        return getModel().getAdminUserByToken(token);
    }

    private AdminUser getModel() {
        return new AdminUser();
    }

    public void sendNewAccountMail(MailerClient mailer, AdminUser user) {
        Config conf = ConfigFactory.load();
        String sendFromEmail = conf.getString("play.mailer.user");
        String sendFromName = Messages.get("app.global.title");
        String subject = sendFromName + ": " + Messages.get("adminUser.mail.subject.newAccount");

        Email email = new Email();
        email.setSubject(subject);
        email.setFrom(sendFromName + "<" + sendFromEmail + ">");
        email.addTo(user.toString() + "<" + user.getEmail() + ">");
        String body = views.html.adminUser.newAdminUserMail.render(subject, user).body();
        email.setBodyHtml(body);

        mailer.send(email);

    }
}
