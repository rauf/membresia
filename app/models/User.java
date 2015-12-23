package models;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;
import play.data.format.Formats;
import play.data.validation.Constraints;
import services.MD5;
import services.UserService;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Model class for User entity
 */
@Entity
@Table(name = "userPerson")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
public class User extends Model {

    @Id
    protected Long id;

    @Constraints.Required
    @Constraints.Email
    @Column(unique = true)
    protected String email;

    @Constraints.Required
    protected String password;

    @Transient
    protected String passwordRaw;

    @Constraints.Required
    protected String name;

    @Constraints.Required
    protected String lastName;

    @Constraints.Required
    @Column(unique = true)
    protected String token;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date updated_at = new Date();

    protected UserService userService = new UserService();

    /**
     * Generic constructor
     */
    public User() {

    }

    /**
     * Gets User object by specific key/value pair
     *
     * @param key   Field to search in
     * @param value Value to search for
     * @return User
     */
    public User get(String key, String value) {
        return Ebean.find(User.class).where().eq(key, value).findUnique();
    }

    /**
     * Gets a list of all users
     *
     * @return List
     */
    public List<User> getAll() {
        return Ebean.find(User.class).findList();
    }


    /**
     * Saves current object into persistence database
     */
    public void save() {
        Ebean.save(this);
    }

    /**
     * Removes user from DB by user token
     *
     * @param token Unique user token
     * @return boolean
     */
    public boolean remove(String token) {
        User user = Ebean.find(User.class).where().eq("token", token).findUnique();
        if (user != null) {
            Ebean.delete(user);
            return true;
        }
        return false;
    }

    /**
     * Gets number of users with the provided email address and different given token
     *
     * @param email Email to check for
     * @param token User token to exclude from search
     * @return Integer
     */
    public Integer getUserEmailCount(String email, String token) {
        return Ebean.find(User.class).where().eq("email", email).ne("token", token).findRowCount();
    }

    /**
     * Get user image profile from Gravatar service
     *
     * @return String
     */
    public String getGravatar() {
        Gravatar gravatar = new Gravatar();
        gravatar.setSize(50);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        String imageUrl = gravatar.getUrl(this.getEmail());
        imageUrl = imageUrl.replace("?d=404", "");

        return imageUrl;
    }

    /**
     * Generates unique MD5 token
     *
     * @return String
     */
    public String generateToken() {
        return MD5.getMD5((new Date()).toString());
    }

    public String toString() {
        return this.getName() + " " + this.getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
