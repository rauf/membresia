package models;

import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;
import services.MoneyFormat;
import views.formData.SubscriptionFormData;
import services.MD5;
import services.Pager;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;

/**
 * Model class for subscription entity
 */
@Entity
@Table(name = "subscription")
public class Subscription extends Model {

    public static final String ID_PREFIX = "SUB";

    @Id
    protected Long id;

    @Constraints.Required
    @Column(unique = true)
    protected String subscriptionId;

    @Constraints.Required
    protected String title;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    protected String description;

    @Constraints.Required
    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    protected Double amount;

    @Constraints.Required
    protected String periodicity;

    @Constraints.Required
    protected String token;

    @Constraints.Required
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    protected Date dueDatePeriod;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date updated_at = new Date();

    @ManyToMany(mappedBy = "subscriptions", cascade = {CascadeType.ALL})
    protected List<Member> members;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    protected List<Installment> installments;

    /**
     * Generic constructor
     */
    public Subscription() {

    }

    /**
     * Populates an object instance with form data
     *
     * @param formData Data from user form as a SubscriptionFormData object
     */
    public void setData(SubscriptionFormData formData) {
        this.setId(formData.getId());
        this.setSubscriptionId(formData.getSubscriptionId());
        this.setTitle(formData.getTitle());
        this.setDescription(formData.getDescription());
        this.setAmount(formData.getAmount());
        this.setPeriodicity(formData.getPeriodicity());
        this.setDueDatePeriod(formData.getDueDatePeriod());
        this.setToken(formData.getToken());
    }

    /**
     * Gets Subscription object from primary key
     *
     * @param id primary key
     * @return Subscription
     */
    public Subscription getByPk(Long id) {
        return Ebean.find(Subscription.class).where().eq("id", id).findUnique();
    }

    /**
     * Gets Subscription object by specific key/value pair
     *
     * @param key   Field to search in
     * @param value Value to search for
     * @return Member
     */
    public Subscription get(String key, String value) {
        return Ebean.find(Subscription.class).where().eq(key, value).findUnique();
    }

    /**
     * Gets a list of all subscriptions
     *
     * @return List
     */
    public List<Subscription> getList() {
        return Ebean.find(Subscription.class).findList();
    }

    /**
     * Get a list of current subscriptions with pager options
     *
     * @param pager indicates query paging parameter
     * @return List
     */
    public List<Subscription> getSubscriptionList(Pager pager) {
        pager.setRecordCount(Ebean.find(Subscription.class).findRowCount());
        pager.resolvePager();

        return Ebean.find(Subscription.class).setFirstRow(pager.getOffset()).setMaxRows(pager.getRows()).findList();
    }

    /**
     * Saves current object into persistence database
     */
    public void save() {
        Ebean.save(this);
    }

    /**
     * Removes subscription from DB
     *
     * @param token Unique member token
     * @return boolean
     */
    public boolean remove(String token) {
        Subscription subscription = Ebean.find(Subscription.class).where().eq("token", token).findUnique();
        if (subscription != null) {
            Ebean.delete(subscription);
            return true;
        }
        return false;
    }

    /**
     * Generate a Subscription id based on next available primary key
     *
     * @return String
     */
    public String generateSubscriptionId() {
        String subscriptionId;
        subscriptionId = ID_PREFIX + "-" + String.format("%04d", Ebean.find(Subscription.class).findRowCount() + 1);
        return subscriptionId;
    }

    /**
     * Generates unique subscription token
     *
     * @return String
     */
    public String generateToken() {
        return MD5.getMD5((new Date()).toString());
    }

    /**
     * Returns a string with formatted due date for printing purposes
     *
     * @return String
     */
    public String getFormattedDueDate() {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

        return format1.format(this.getDueDatePeriod().getTime());
    }

    /**
     * Returns a string with formatted amount due for printing purposes
     *
     * @return String
     */
    public String getFormattedAmount() {
        return MoneyFormat.setMoney(getAmount());
    }

    public String toString() {
        return "(" + this.getSubscriptionId() + ") " + this.getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDueDatePeriod() {
        return dueDatePeriod;
    }

    public void setDueDatePeriod(Date dueDatePeriod) {
        this.dueDatePeriod = dueDatePeriod;
    }

    public List<Installment> getInstallments() {
        return installments;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
