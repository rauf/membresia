package models;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import play.data.format.Formats;
import play.data.validation.*;
import services.MD5;
import services.MemberFormData;

import java.util.*;

/**
 * Model class for member entity
 */
@Entity
@Table(name = "member")
public class Member extends Model {

    public static final String ID_PREFIX = "MEM";

    @Id
    private Long id;

    @Constraints.Required
    @Column(unique = true)
    protected String memberId;

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

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date updated_at = new Date();

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
        for (String subscription : formData.getSubscriptions()) {
            this.subscriptions.add(Subscription.findByToken(subscription));
        }
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
     * Gets Member object from primary key
     *
     * @param id primary key
     * @return member
     */
    public Member getMemberById(Long id) {
        return Ebean.find(Member.class).where().eq("id", id).findUnique();
    }

    /**
     * Gets Member object from member email address
     *
     * @param email member email
     * @return member
     */
    public Integer getMemberAdminUsrEmailUseCount(String email, String token) {
        return Ebean.find(Member.class).where().eq("email", email).ne("token", token).findRowCount();
    }

    public void save(MemberFormData formData) {
        Ebean.save(this);
    }

    /**
     * Get a list of current members
     *
     * @return List<Member>
     */
    public List<Member> getMemberList() {
        return Ebean.find(Member.class).findList();
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
     * Generates unique member  token
     *
     * @return String
     */
    public String generateToken() {
        return MD5.getMD5((new Date()).toString());
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
    public void addSubscription(Subscription subscription) {
        this.subscriptions.add(subscription);
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
     * Create member string
     *
     * @return
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
