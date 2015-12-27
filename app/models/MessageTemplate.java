package models;

import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;
import services.MD5;
import views.formData.MessageTemplateFormData;
import services.Pager;

import java.util.*;
import javax.persistence.*;

/**
 * Model class for MessageTemplate entity
 */
@Entity
@Table(name = "messageTemplate")
public class MessageTemplate extends Model {

    @Id
    protected Long id;

    @Constraints.Required
    protected String title;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    protected String body;

    @Constraints.Required
    @Column(unique = true)
    protected String token;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date updated_at = new Date();

    /**
     * Generic constructor
     */
    public MessageTemplate() {

    }

    /**
     * Populates an object instance with form data
     *
     * @param formData Data from user form as a MessageTemplateFormData object
     */
    public void setData(MessageTemplateFormData formData) {
        this.setId(formData.getId());
        this.setTitle(formData.getTitle());
        this.setBody(formData.getBody());
        this.setToken(formData.getToken());
    }

    /**
     * Gets MessageTemplate object from primary key
     *
     * @param id primary key
     * @return MessageTemplate
     */
    public MessageTemplate getByPk(Long id) {
        return Ebean.find(MessageTemplate.class).where().eq("id", id).findUnique();
    }

    /**
     * Gets MessageTemplate object by specific key/value pair
     *
     * @param key   Field to search in
     * @param value Value to search for
     * @return MessageTemplate
     */
    public MessageTemplate get(String key, String value) {
        return Ebean.find(MessageTemplate.class).where().eq(key, value).findUnique();
    }

    /**
     * Get a list of current messageTemplates with pager options
     *
     * @param pager indicates query paging parameter
     * @return List
     */
    public List<MessageTemplate> getMessageTemplateList(Pager pager) {
        pager.setRecordCount(Ebean.find(MessageTemplate.class).findRowCount());
        pager.resolvePager();

        return Ebean.find(MessageTemplate.class).setFirstRow(pager.getOffset()).setMaxRows(pager.getRows()).findList();
    }

    /**
     * Saves current object into persistence database
     */
    public void save() {
        Ebean.save(this);
    }

    /**
     * Removes user from DB
     *
     * @param token Unique messageTemplate token
     * @return boolean
     */
    public boolean remove(String token) {
        MessageTemplate messageTemplate = Ebean.find(MessageTemplate.class).where().eq("token", token).findUnique();
        if (messageTemplate != null) {
            Ebean.delete(messageTemplate);
            return true;
        }

        return false;
    }

    /**
     * Generates unique member  token
     *
     * @return String
     */
    public String generateToken() {
        return MD5.getMD5((new Date()).toString());
    }

    public String toString() {
        return this.getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
