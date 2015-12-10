package models;

import play.Logger;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;

import java.util.*;
import javax.persistence.*;

import services.formData.PaymentFormData;

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

    /**
     * Generic constructor
     */
    public Payment() {
        super();
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
     * Saves current object into persistence database
     */
    public void save() {
        Ebean.save(this);
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

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
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

    public String getMemberInstallmentToken() {
        return memberInstallmentToken;
    }

    public void setMemberInstallmentToken(String memberInstallmentToken) {
        this.memberInstallmentToken = memberInstallmentToken;
    }
}
