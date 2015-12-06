package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;
import services.MD5;
import services.formData.MessageTemplateFormData;
import services.Pager;

@Entity
@Table(name = "messageTemplate")
public class MessageTemplate extends Model {

    @Id
    private Long id;

    @Constraints.Required
    String title;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    String body;

    @Constraints.Required
    @Column(unique = true)
    protected String token;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date updated_at = new Date();

    /**
     * Generic constructor
     */
    public MessageTemplate() {

        super();
    }

    /**
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
    public MessageTemplate getMessageTemplateById(Long id) {

        return Ebean.find(MessageTemplate.class).where().eq("id", id).findUnique();

    }

    /**
     * Gets MessageTemplate object from hash token
     *
     * @param token Unique MessageTemplate identifier hash
     * @return MessageTemplate
     */
    public MessageTemplate getMessageTemplateByToken(String token) {

        return Ebean.find(MessageTemplate.class).where().eq("token", token).findUnique();
    }

    /**
     * Get a list of current messageTemplates
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

    /**
     * Create member string
     *
     * @return String
     */
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
