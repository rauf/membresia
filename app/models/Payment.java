package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;
import views.formData.PaymentFormData;
import services.MemberInstallmentService;
import services.MoneyFormat;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.inject.Inject;
import javax.persistence.*;

/**
 * Model class for Payment entity
 */
@Entity
@Table(name = "payment")
public class Payment extends Model {

    @Id
    protected Long id;

    @Constraints.Required
    protected Double amount;

    @Constraints.Required
    @Column(unique = true)
    protected String token;

    @Transient
    protected String memberInstallmentToken;

    @Constraints.Required
    protected Integer status = 0;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date created_at = new Date();

    @ManyToOne(cascade = CascadeType.ALL)
    protected Installment installment;

    @ManyToOne(cascade = CascadeType.ALL)
    protected MemberInstallment memberInstallment;

    private MemberInstallmentService memberInstallmentService = new MemberInstallmentService();

    /**
     * Generic constructor
     */
    public Payment() {

    }

    /**
     * Fills the object instance with data from form through a Form object
     *
     * @param formData Data from payment form as a PaymentFormData object
     */
    public void setData(PaymentFormData formData) {
        this.setAmount(formData.getAmount());
        this.setToken(formData.getToken());
        this.setMemberInstallmentToken(formData.getMemberInstallmentToken());

        MemberInstallment memberInstallment = new MemberInstallment();
        this.setMemberInstallment(memberInstallment.get("token", memberInstallmentToken));
        this.setInstallment(this.memberInstallment.getInstallment());
    }

    /**
     * Gets MemberInstallment object by specific key/value pair
     *
     * @param key   Field to search in
     * @param value Value to search for
     * @return Installment
     */
    public Payment get(String key, String value) {
        return Ebean.find(Payment.class).where().eq(key, value).findUnique();
    }

    /**
     * Saves current object into persistence database
     */
    public void save() {
        Ebean.save(this);
        if (memberInstallmentService.getAmountDue(this.getMemberInstallment().getToken()) == 0.0) {
            memberInstallmentService.setPaid(getMemberInstallment());
        }
    }

    /**
     * Returns a string with formatted payment date for printing purposes
     *
     * @return String
     */
    public String getFormattedPaymentDate() {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

        return format1.format(this.getCreated_at().getTime());
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
        return getFormattedPaymentDate() + " (" + getFormattedAmount() + ")";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Installment getInstallment() {
        return installment;
    }

    public void setInstallment(Installment installment) {
        this.installment = installment;
    }

    public MemberInstallment getMemberInstallment() {
        return memberInstallment;
    }

    public void setMemberInstallment(MemberInstallment memberInstallment) {
        this.memberInstallment = memberInstallment;
    }

    public void setMemberInstallmentToken(String memberInstallmentToken) {
        this.memberInstallmentToken = memberInstallmentToken;
    }
}
