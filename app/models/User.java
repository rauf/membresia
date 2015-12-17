package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import play.data.format.Formats;
import play.data.validation.Constraints;
import services.MD5;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "userPerson")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
public class User extends Model {

    @Id
    private Long id;

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
    public Date updated_at = new Date();

    public List<User> getUserRawList() {
        return Ebean.find(User.class).findList();
    }

    /**
     * Gets User object from hash token
     *
     * @param token Unique User identifier hash
     * @return member
     */
    public User getUserByToken(String token) {

        return Ebean.find(User.class).where().eq("token", token).findUnique();
    }

    public User get(String key, String value) {

        return Ebean.find(User.class).where().eq(key, value).findUnique();
    }

    /**
     * Gets email use count
     *
     * @param email member email
     * @return Integer
     */
    public Integer getUserEmailCount(String email, String token) {

        return Ebean.find(User.class).where().eq("email", email).ne("token", token).findRowCount();
    }

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
     * Generates unique member  token
     *
     * @return String
     */
    public String generateToken() {

        return MD5.getMD5((new Date()).toString());
    }

    /**
     * Create admin user string
     *
     * @return String
     */
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

}
