package models;

import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.Constraints;
import services.MD5;
import services.MoneyFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Model class for installment entity
 */
@Entity
@Table(name = "installment")
public class Installment extends Model {

    @Id
    protected Long id;

    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date dueDate = new Date();

    @Constraints.Required
    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    protected Double amount;

    @Constraints.Required
    @Column(unique = true)
    protected String token;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    protected Date updated_at = new Date();

    @OneToMany(mappedBy = "installment", cascade = CascadeType.ALL)
    protected List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "installment", cascade = CascadeType.ALL)
    protected List<MemberInstallment> memberInstallments = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    protected Subscription subscription;

    /**
     * Constructs an installment from a given subscription
     *
     * @param subscription Subscription object to create the installment from
     */
    public Installment(Subscription subscription) {
        this.setSubscription(subscription);
        this.setToken(generateToken());
    }

    /**
     * Constructs an installmente from a given subscription, amount and due date
     *
     * @param dueDate      Date the installemnt is due
     * @param amount       Amount due
     * @param subscription Subscription object to create the installment from
     */
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
     * Gets Installment object by specific key/value pair
     *
     * @param key   Field to search in
     * @param value Value to search for
     * @return Installment
     */
    public Installment get(String key, String value) {
        return Ebean.find(Installment.class).where().eq(key, value).findUnique();
    }

    /**
     * Removes installment from DB
     *
     * @param token Unique installment token
     * @return boolean
     */
    public boolean remove(String token) {
        Installment installment = get("token", token);
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
     * Returns a string with formatted due date for printing purposes
     *
     * @return String
     */
    public String getFormattedDueDate() {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

        return format1.format(this.getDueDate().getTime());
    }

    /**
     * Returns a string with formatted amount due for printing purposes
     *
     * @return String
     */
    public String getFormattedAmount() {
        return MoneyFormat.setMoney(getAmount());
    }

    /**
     * Calculates percentage of collected payments
     *
     * @return int
     */
    public int getProgress() {
        double totalPayments = 0.0;
        for (Payment payment : getPayments()) {
            totalPayments += payment.getAmount();
        }
        double totalDue = getAmount() * getSubscription().getMembers().size();

        return (int) (totalPayments / totalDue * 100);
    }

    public String toString() {
        return this.getSubscription().toString() + " - " + getFormattedDueDate() + " (" + getFormattedAmount() + ")";
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public List<Payment> getPayments() {
        return payments;
    }
}
