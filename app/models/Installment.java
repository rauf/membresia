package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;
import models.Subscription;
import play.data.format.*;
import play.data.validation.Constraints;
import services.MD5;

@Entity
@Table(name = "installment")
public class Installment extends Model {

    @Id
    private Long id;

    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date dueDate = new Date();

    @Constraints.Required
    @Column(columnDefinition="Decimal(10,2) default '0.00'")
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

    @ManyToOne(cascade = CascadeType.ALL)
    public Subscription subscription;

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

        return this.getSubscription().toString() + " - " + this.getDueDate().toString();
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
