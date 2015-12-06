package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import play.data.format.Formats;
import play.data.validation.Constraints;
import services.MD5;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
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

    /**
     * Gets email use count
     *
     * @param email member email
     * @return Integer
     */
    public Integer getUserEmailCount(String email, String token) {

        return Ebean.find(User.class).where().eq("email", email).ne("token", token).findRowCount();
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
}
