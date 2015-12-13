package models;

import play.Logger;
import play.data.validation.*;
import services.UserService;
import services.formData.AdminUserFormData;
import services.Pager;
import com.avaje.ebean.Ebean;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;
import javax.persistence.*;

@Entity
@DiscriminatorValue("admin")
public class AdminUser extends User {

    public void setData(AdminUserFormData formData) {
        this.setId(formData.getId());
        this.setName(formData.getName());
        this.setLastName(formData.getLastName());
        this.setEmail(formData.getEmail());

        UserService userService = new UserService();

        setPasswordRaw(null);
        if (formData.getMode() == 0)
            this.setPasswordRaw(userService.generatePassword());
        if (!formData.getPassword().isEmpty())
            this.setPasswordRaw(formData.getPassword());
        if (getPasswordRaw() != null)
            this.setPassword(userService.cryptPassword(getPasswordRaw()));

        this.setToken(formData.getToken());
    }

    /**
     * Gets AdminUser object from primary key
     *
     * @param id primary key
     * @return AdminUser
     */
    public AdminUser getAdminUserById(Long id) {

        return Ebean.find(AdminUser.class).where().eq("id", id).findUnique();
    }

    /**
     * Gets AdminUser object from hash token
     *
     * @param token Unique AdminUser identifier hash
     * @return AdminUser
     */
    public AdminUser getAdminUserByToken(String token) {

        return Ebean.find(AdminUser.class).where().eq("token", token).findUnique();
    }

    /**
     * Get a list of current admin users
     *
     * @param pager indicates query paging parameter
     * @return List
     */
    public List<AdminUser> getAdminUserList(Pager pager) {
        pager.setRecordCount(Ebean.find(AdminUser.class).findRowCount());
        pager.resolvePager();
        return Ebean.find(AdminUser.class).setFirstRow(pager.getOffset()).setMaxRows(pager.getRows()).findList();
    }

    /**
     * Saves current object into persistence database
     */
    public void save() {

        Ebean.save(this);
    }


    /**
     * Removes admin user from DB
     *
     * @param token Unique admin user token
     * @return boolean
     */
    public boolean remove(String token) {

        AdminUser adminUser = Ebean.find(AdminUser.class).where().eq("token", token).findUnique();
        if (adminUser != null) {
            Ebean.delete(adminUser);
            return true;
        }

        return false;
    }
}
