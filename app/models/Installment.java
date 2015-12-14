package models;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.Constraints;
import services.MD5;
import services.MoneyFormat;

@Entity
@Table(name = "installment")
public class Installment extends Model {

    @Id
    private Long id;

    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date dueDate = new Date();

    @Constraints.Required
    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    public Double amount;

    @Constraints.Required
    @Column(unique = true)
    protected String token;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date updated_at = new Date();

    @OneToMany(mappedBy = "installment", cascade = CascadeType.ALL)
    public List<Payment> payments = new ArrayList<Payment>();

    @OneToMany(mappedBy = "installment", cascade = CascadeType.ALL)
    public List<MemberInstallment> memberInstallments = new ArrayList<MemberInstallment>();

    @ManyToOne(cascade = CascadeType.ALL)
    public Subscription subscription;

    public Installment(Subscription subscription) {
        this.setSubscription(subscription);
        this.setToken(generateToken());
    }

    public Installment(Date dueDate, Double amount, Subscription subscription) {
        this.setDueDate(dueDate);
        this.setAmount(amount);
        this.setSubscription(subscription);
        this.setToken(generateToken());
    }

    /**
     * Saves current object into persistence database
     */
    public void save() {

        Ebean.save(this);
    }

    /**
     * Gets Installment object from hash token
     *
     * @param token Unique Installment identifier hash
     * @return Installment
     */
    public Installment getInstallmentByToken(String token) {

        return Ebean.find(Installment.class).where().eq("token", token).findUnique();
    }

    /**
     * Removes installment from DB
     *
     * @param token Unique installment token
     * @return boolean
     */
    public boolean remove(String token) {

        Installment installment = getInstallmentByToken(token);
        if (installment != null) {
            Ebean.delete(installment);
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
     * Create admin user string
     *
     * @return String
     */
    public String toString() {
        return this.getSubscription().toString() + " - " + getFormattedDueDate() + " (" + getFormattedAmount() + ")";
    }

    public String getFormattedDueDate() {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String formatted = format1.format(this.getDueDate().getTime());

        return formatted;
    }

    public String getFormattedAmount() {
        return MoneyFormat.setMoney(getAmount());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<MemberInstallment> getMemberInstallments() {
        return memberInstallments;
    }

    public void setMemberInstallments(List<MemberInstallment> memberInstallments) {
        this.memberInstallments = memberInstallments;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}
