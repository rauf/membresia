package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;

import play.data.format.*;
import play.data.validation.*;
import services.MD5;
import services.Pager;
import services.formData.SubscriptionFormData;

@Entity
@Table(name = "subscrition")
public class Subscription extends Model {

    public static final String ID_PREFIX = "SUB";

    @Id
    private Long id;

    @Constraints.Required
    @Column(unique = true)
    protected String subscriptionId;

    @Constraints.Required
    public String title;

    @Constraints.Required
    public String description;

    @Constraints.Required
    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    public Double amount;

    @Constraints.Required
    public String periodicity;

    @Constraints.Required
    public String token;

    @Constraints.Required
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    public Date dueDatePeriod;

//    @Formats.DateTime(pattern = "dd/MM/yyyy")
//    public Date endDate = null;

    protected Boolean isPaid = false;


    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date updated_at = new Date();

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    public List<Installment> installments;

    @ManyToMany(mappedBy = "subscriptions", cascade = CascadeType.ALL)
    public List<Member> members;

    /**
     * Generic constructor
     */
    public Subscription() {

        super();
    }

    /**
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
        //this.setEndDate(formData.getEndDate());
        this.setToken(formData.getToken());
    }

    /**
     * Gets Subscription object from primary key
     *
     * @param id primary key
     * @return Subscription
     */
    public Subscription getSubscriptionById(Long id) {

        return Ebean.find(Subscription.class).where().eq("id", id).findUnique();

    }

    /**
     * Gets Subscription object from hash token
     *
     * @param token Unique Member identifier hash
     * @return member
     */
    public Subscription getSubscriptionByToken(String token) {

        return Ebean.find(Subscription.class).where().eq("token", token).findUnique();
    }

    /**
     * Get a list of current subscriptions
     *
     * @param pager indicates query paging parameter
     * @return List
     */
    public List<Subscription> getSubscriptionList(Pager pager) {
        pager.setRecordCount(Ebean.find(Subscription.class).findRowCount());
        pager.resolvePager();
        return Ebean.find(Subscription.class).setFirstRow(pager.getOffset()).setMaxRows(pager.getRows()).findList();
    }

    public List<Subscription> getSubscriptionRawList() {
        return Ebean.find(Subscription.class).findList();
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
     * Determines if a member is subscribed to a specific subscription
     *
     * @param memberId member id to find
     * @return boolean
     */
    public boolean hasMember(Long memberId) {

        for (Member member : this.members) {
            if (memberId.equals(member.getId()))
                return true;
        }
        return false;
    }

    /**
     * Adds a member to current subscription
     *
     * @param member member to add
     */
    public void addSubscription(Member member) {

        this.members.add(member);
    }

    /**
     * Create member string
     *
     * @return String
     */
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

//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }

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

    public List<Installment> getInstallments() {
        return installments;
    }

    public void setInstallments(LinkedList<Installment> installments) {
        this.installments = installments;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
