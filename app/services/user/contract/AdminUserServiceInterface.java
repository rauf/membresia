package services.user.contract;

import models.user.AdminUser;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import views.formData.AdminUserFormData;
import lib.Pager;

import java.util.List;

/**
 * Middleware interface class for controller model interaction and other member related business logic
 */
public interface AdminUserServiceInterface {

    /**
     * Sets form validation object with model object data
     *
     * @param token Unique item identifier
     * @return AdminUserFormData
     */
    AdminUserFormData setAdminUserData(String token);

    /**
     * Fills UI form object with form validation object
     *
     * @param memberData form validation object
     * @return Form
     */
    Form<AdminUserFormData> setFormData(AdminUserFormData memberData);

    /**
     * Gets admin user by token
     *
     * @param token Unique admin user token identifier
     * @return AdminUser
     */
    AdminUser getAdminUser(String token);

    /**
     * Gets list of AdminUser with paging options
     *
     * @param pager Paging options
     * @return List
     */
    List<AdminUser> getAdminUserList(Pager pager);

    /**
     * Saves form data into modl object
     *
     * @param formData UI form data
     * @return AdminUser
     */
    AdminUser save(Form<AdminUserFormData> formData);

    /**
     * Removes user from DB
     *
     * @param token Unique admin user token identifier
     * @return boolean
     */
    boolean remove(String token);

    /**
     * Checks if a user different than current is using a specific email address
     *
     * @param email Email address to check for
     * @param token Unique token identifier of user to exclude in search
     * @return boolean
     */
    boolean isAdminUserEmailUsed(String email, String token);

    /**
     * Sends new admin user account email with login credentials
     *
     * @param mailer Mailer object
     * @param user   User to send the mail to
     */
    void sendNewAccountMail(MailerClient mailer, AdminUser user);
}
