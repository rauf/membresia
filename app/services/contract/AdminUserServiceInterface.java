package services.contract;

import models.AdminUser;
import play.data.Form;
import services.formData.AdminUserFormData;
import services.Pager;

import java.util.List;

/**
 * Middleware interface class for controller model interaction and other member related business logic
 */
public interface AdminUserServiceInterface {

    Form<AdminUserFormData> setFormData(AdminUserFormData memberData);

    AdminUserFormData setAdminUserData(String token);

    List<AdminUser> getAdminUserList(Pager pager);

    AdminUser getAdminUser(String token);

    AdminUser save(Form<AdminUserFormData> formData);

    boolean remove(String token);
}
