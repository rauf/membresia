package models;

import com.avaje.ebean.Ebean;
import play.data.validation.*;
import views.formData.MemberFormData;
import services.Pager;

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
    protected List<MemberInstallment> memberInstallments = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    protected List<Subscription> subscriptions = new ArrayList<>();

    /**
     * Generic constructor
     */
    public Member() {

    }

    /**
     * Populates an object instance with form data
     *
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

        this.setPasswordRaw(userService.generatePassword());
        this.setPassword(userService.encryptPassword(getPasswordRaw()));

        Subscription subscription = new Subscription();
        for (String subscriptionToken : formData.getSubscriptions()) {
            this.addSubscription(subscription.get("token", subscriptionToken));
        }
    }

    /**
     * Gets Member object from primary key
     *
     * @param id primary key
     * @return Member
     */
    public Member getByPk(Long id) {
        return Ebean.find(Member.class).where().eq("id", id).findUnique();
    }

    /**
     * Gets Member object by specific key/value pair
     *
     * @param key   Field to search in
     * @param value Value to search for
     * @return Member
     */
    public Member get(String key, String value) {
        return Ebean.find(Member.class).where().eq(key, value).findUnique();
    }

    /**
     * Get a list of  members with pager options
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
     * Adds subscription to current member
     *
     * @param subscription Subscription to add
     */
    public void addSubscription(Subscription subscription) {
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

    public List<MemberInstallment> getMemberInstallments() {
        return memberInstallments;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setMemberInstallments(List<MemberInstallment> memberInstallments) {
        this.memberInstallments = memberInstallments;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
