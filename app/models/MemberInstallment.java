package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.Logger;
import play.data.validation.Constraints;
import services.MD5;
import services.MemberInstallmentService;
import services.MoneyFormat;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model class for MemberInstallment entity
 */
@Entity
@Table(name = "member_installment")
public class MemberInstallment extends Model {

    @Id
    protected Long id;

    protected Boolean isPaid = false;

    @Constraints.Required
    @Column(unique = true)
    protected String token;

    @ManyToOne(cascade = CascadeType.PERSIST)
    protected Member member;

    @ManyToOne(cascade = CascadeType.PERSIST)
    protected Installment installment;

    @OneToMany(mappedBy = "memberInstallment", cascade = CascadeType.ALL)
    protected List<Payment> payments = new ArrayList<>();

    private MemberInstallmentService memberInstallmentService = new MemberInstallmentService();

    /**
     * Standard constructor
     */
    public MemberInstallment() {
        this.setToken(generateToken());
    }

    /**
     * Gets MemberInstallment object by specific key/value pair
     *
     * @param key   Field to search in
     * @param value Value to search for
     * @return Installment
     */
    public MemberInstallment get(String key, String value) {
        return Ebean.find(MemberInstallment.class).where().eq(key, value).findUnique();
    }

    /**
     * Saves current object into persistence database
     */
    public void save() {
        Ebean.save(this);
    }

    /**
     * Sets current member's installment as paid
     */
    public void setAsPaid() {
        this.setPaid(true);
        this.save();
    }

    /**
     * Removes member installment from DB
     *
     * @param token Unique member installment token
     * @return boolean
     */
    public boolean remove(String token) {
        MemberInstallment memberInstallment = Ebean.find(MemberInstallment.class).where().eq("token", token).findUnique();
        if (memberInstallment != null) {
            Ebean.delete(memberInstallment);
            return true;
        }

        return false;
    }

    /**
     * Generates unique member installment token
     *
     * @return String
     */
    public String generateToken() {
        return MD5.getMD5((new Date()).toString());
    }

    public String toString() {
        String amountDue = MoneyFormat.setMoney(memberInstallmentService.getAmountDue(getToken()));
        return this.getInstallment().getSubscription().toString() + " - " + getInstallment().getFormattedDueDate() + " (" + amountDue + ")";
    }


    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Installment getInstallment() {
        return installment;
    }

    public void setInstallment(Installment installment) {
        this.installment = installment;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
