package services;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.AdminUser;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import play.i18n.Messages;
import play.libs.mailer.Email;
import services.contract.AdminUserServiceInterface;
import views.formData.AdminUserFormData;

import java.util.List;

public class AdminUserService implements AdminUserServiceInterface {

    /**
     * {@inheritDoc}
     **/
    public AdminUserFormData setAdminUserData(String token) {
        return new AdminUserFormData(getModel().get("token", token));
    }

    /**
     * {@inheritDoc}
     **/
    public Form<AdminUserFormData> setFormData(AdminUserFormData adminUserData) {
        return Form.form(AdminUserFormData.class).fill(adminUserData);
    }

    /**
     * {@inheritDoc}
     **/
    public AdminUser getAdminUser(String token) {
        return getModel().get("token", token);
    }

    /**
     * {@inheritDoc}
     **/
    public List<AdminUser> getAdminUserList(Pager pager) {
        return getModel().getAdminUserList(pager);
    }

    /**
     * {@inheritDoc}
     **/
    public AdminUser save(Form<AdminUserFormData> formData) {
        AdminUser adminUser = (formData.get().getId() != null) ? getModel().getByPk(formData.get().getId()) : getModel();
        adminUser.setData(formData.get());
        adminUser.save();
        return adminUser;
    }

    /**
     * {@inheritDoc}
     **/
    public boolean remove(String token) {
        return getModel().remove(token);
    }

    /**
     * {@inheritDoc}
     **/
    public boolean isAdminUserEmailUsed(String email, String token) {
        return getModel().getUserEmailCount(email, token) > 0;
    }

    /**
     * {@inheritDoc}
     **/
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

    /**
     * Gets model object
     *
     * @return AdminUser
     */
    private AdminUser getModel() {
        return new AdminUser();
    }
}
