package models;

import play.data.validation.*;
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

    @Constraints.Required
    protected String password;

    protected String passwordRaw;

    public void setData(AdminUserFormData formData) {
        this.setId(formData.getId());
        this.setName(formData.getName());
        this.setLastName(formData.getLastName());
        this.setEmail(formData.getEmail());
        this.setPasswordRaw(RandomStringUtils.randomAscii(8));
        this.setPassword(BCrypt.hashpw(getPassword(), BCrypt.gensalt()));
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

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getPasswordRaw() {

        return passwordRaw;
    }

    public void setPasswordRaw(String passwordRaw) {

        this.passwordRaw = passwordRaw;
    }
}
