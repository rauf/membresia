package models;

import com.avaje.ebean.Ebean;
import views.formData.AdminUserFormData;
import services.Pager;

import java.util.*;
import javax.persistence.*;

/**
 * Model class for AdminUser entity
 */
@Entity
@DiscriminatorValue("admin")
public class AdminUser extends User {

    /**
     * Generic constructor
     */
    public AdminUser() {
        super();
    }

    /**
     * Populates an object instance with form data
     *
     * @param formData form data from html form
     */
    public void setData(AdminUserFormData formData) {
        this.setId(formData.getId());
        this.setName(formData.getName());
        this.setLastName(formData.getLastName());
        this.setEmail(formData.getEmail());
        this.setToken(formData.getToken());
        this.setPasswordRaw(null);

        if (formData.getMode() == 0) this.setPasswordRaw(userService.generatePassword());
        if (!formData.getPassword().isEmpty()) this.setPasswordRaw(formData.getPassword());
        if (getPasswordRaw() != null) this.setPassword(userService.encryptPassword(getPasswordRaw()));
    }

    /**
     * Gets a User object from primary key
     *
     * @param id primary key
     * @return AdminUser
     */
    public AdminUser getByPk(Long id) {
        return Ebean.find(AdminUser.class).where().eq("id", id).findUnique();
    }

    /**
     * Gets AdminUser object by specific key/value pair
     *
     * @param key   Field to search in
     * @param value Value to search for
     * @return AdminUser
     */
    public AdminUser get(String key, String value) {
        return Ebean.find(AdminUser.class).where().eq(key, value).findUnique();
    }

    /**
     * Get a list of current admin users with pager options
     *
     * @param pager indicates query paging parameter
     * @return List
     */
    public List<AdminUser> getAdminUserList(Pager pager) {
        pager.setRecordCount(Ebean.find(AdminUser.class).findRowCount());
        pager.resolvePager();

        return Ebean.find(AdminUser.class).setFirstRow(pager.getOffset()).setMaxRows(pager.getRows()).findList();
    }
}
