package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import play.data.format.*;
import play.data.validation.*;
import services.AdminUserFormData;
import services.MD5;

@Entity
@Table(name = "adminUser")
public class AdminUser extends Model {

    @Id
    private Long id;

    @Constraints.Required
    @Column(unique = true)
    protected String email;

    @Constraints.Required
    protected String password;

    @Constraints.Required
    protected String name;

    @Constraints.Required
    protected String lastName;

    @Constraints.Required
    @Column(unique = true)
    protected String token;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date updated_at = new Date();

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
     * Gets AdminUser object from hash token
     *
     * @param token Unique AdminUser identifier hash
     * @return AdminUser
     */
    public AdminUser getAdminUserByToken(String token) {
        return Ebean.find(AdminUser.class).where().eq("token", token).findUnique();
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

    public void save(AdminUserFormData formData) {
        Ebean.save(this);
    }

    /**
     * Get a list of current admin users
     *
     * @return List<AdminUser>
     */
    public List<AdminUser> getAdminUserList() {
        return Ebean.find(AdminUser.class).findList();
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

    /**
     * Create admin user string
     *
     * @return
     */
    public String toString() {
        return this.getName() + " " + this.getLastName();
    }

    /**
     * Generates unique admin user token
     *
     * @return String
     */
    public String generateToken() {
        return MD5.getMD5((new Date()).toString());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPasswordRaw() {
        return passwordRaw;
    }

    public void setPasswordRaw(String passwordRaw) {
        this.passwordRaw = passwordRaw;
    }
}
