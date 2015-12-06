package models;

import play.data.validation.*;
import services.formData.MemberFormData;
import services.Pager;
import com.avaje.ebean.Ebean;

import java.util.*;
import javax.persistence.*;

/**
 * Model class for member entity
 */
@Entity
@DiscriminatorValue("member")
public class Member extends User {

    public static final String ID_PREFIX = "MEM";

    @Constraints.Required
    @Column(unique = true)
    protected String memberId;

    @Constraints.Required
    protected String address;

    @Constraints.Required
    protected String cp;

    @Constraints.Required
    protected String city;

    @Constraints.Required
    protected String state;

    @Constraints.Required
    protected String country;

    @Constraints.Required
    protected String nif;

    @Constraints.Required
    protected String phone;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    protected List<Payment> payments = new ArrayList<Payment>();

    @ManyToMany(cascade = CascadeType.ALL)
    protected List<Subscription> subscriptions = new ArrayList<Subscription>();

    /**
     * Generic constructor
     */
    public Member() {

        super();
    }

    /**
     * @param formData Data from user form as a MemberFormData object
     */
    public void setData(MemberFormData formData) {

        this.setId(formData.getId());
        this.setMemberId(formData.getMemberId());
        this.setName(formData.getName());
        this.setLastName(formData.getLastName());
        this.setEmail(formData.getEmail());
        this.setAddress(formData.getAddress());
        this.setCity(formData.getCity());
        this.setState(formData.getState());
        this.setCp(formData.getCp());
        this.setCountry(formData.getCountry());
        this.setNif(formData.getNif());
        this.setPhone(formData.getPhone());
        this.setToken(formData.getToken());

        Subscription subscription = new Subscription();
        for (String subscriptionToken : formData.getSubscriptions()) {
            this.addSubscription(subscription.getSubscriptionByToken(subscriptionToken));
        }
    }

    /**
     * Gets Member object from primary key
     *
     * @param id primary key
     * @return member
     */
    public Member getMemberById(Long id) {

        return Ebean.find(Member.class).where().eq("id", id).findUnique();

    }

    /**
     * Gets Member object from hash token
     *
     * @param token Unique Member identifier hash
     * @return member
     */
    public Member getMemberByToken(String token) {

        return Ebean.find(Member.class).where().eq("token", token).findUnique();
    }

    /**
     * Get a list of current members
     *
     * @param pager indicates query paging parameter
     * @return List
     */
    public List<Member> getMemberList(Pager pager) {
        pager.setRecordCount(Ebean.find(Member.class).findRowCount());
        pager.resolvePager();
        return Ebean.find(Member.class).setFirstRow(pager.getOffset()).setMaxRows(pager.getRows()).findList();
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
     * @param token Unique member token
     * @return boolean
     */
    public boolean remove(String token) {

        Member member = Ebean.find(Member.class).where().eq("token", token).findUnique();
        if (member != null) {
            Ebean.delete(member);
            return true;
        }
        return false;
    }

    /**
     * Generate a member id based on next available primary key
     *
     * @return String
     */
    public String generateMemberId() {

        String memberId;
        memberId = ID_PREFIX + "-" + String.format("%04d", Ebean.find(Member.class).findRowCount() + 1);
        return memberId;
    }

    /**
     * Determines if a member is subscribed to a specific subscription
     *
     * @param subscriptionId Subscrition id to find
     * @return boolean
     */
    public boolean hasSubscription(Long subscriptionId) {

        for (Subscription subscription : this.subscriptions) {
            if (subscriptionId.equals(subscription.getId()))
                return true;
        }
        return false;
    }

    /**
     * Adds subscription to current member
     *
     * @param subscription Subscription to add
     */
    private void addSubscription(Subscription subscription) {

        this.subscriptions.add(subscription);
    }

    public String getMemberId() {

        return memberId;
    }

    public void setMemberId(String memberId) {

        this.memberId = memberId;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public String getCp() {

        return cp;
    }

    public void setCp(String cp) {

        this.cp = cp;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public String getState() {

        return state;
    }

    public void setState(String state) {

        this.state = state;
    }

    public String getNif() {

        return nif;
    }

    public void setNif(String nif) {

        this.nif = nif;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public List<Payment> getPayments() {

        return payments;
    }

    public void setPayments(List<Payment> payments) {

        this.payments = payments;
    }

    public List<Subscription> getSubscriptions() {

        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {

        this.subscriptions = subscriptions;
    }
}
