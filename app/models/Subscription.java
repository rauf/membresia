package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;

import play.data.format.*;
import play.data.validation.*;
import services.MemberFormData;

@Entity
@Table(name = "subscrition")
public class Subscription extends Model {

    @Id
    private Long id;

    @Constraints.Required
    public String title;

    @Constraints.Required
    public String description;

    @Constraints.Required
    public Float amount;

    @Constraints.Required
    public String periocity;

    @Constraints.Required
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    public Date dueDatePeriod;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date updated_at = new Date();

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    public List<Installment> installments = new ArrayList<Installment>();

    @ManyToMany(mappedBy = "subscriptions", cascade = CascadeType.ALL)
    public List<Member> members = new ArrayList<Member>();

    public static Subscription findByToken(String token) {
        return (Subscription) Ebean.find(Subscription.class).where().eq("token", token);
    }

    public static Map<String, Boolean> makeSubscriptionMap(MemberFormData member) {
        List<Subscription> allSubscriptions = new ArrayList<>();
        Map<String, Boolean> subscriptionMap = new HashMap<String, Boolean>();
        for (Subscription subscription : allSubscriptions) {
            subscriptionMap.put(subscription.title, (member != null && member.getSubscriptions().contains(subscription.title)));
        }
        return subscriptionMap;
    }

    @Override
    public String toString() {
        return String.format("[Subscription %s]", this.title);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getPeriocity() {
        return periocity;
    }

    public void setPeriocity(String periocity) {
        this.periocity = periocity;
    }

    public Date getDueDatePeriod() {
        return dueDatePeriod;
    }

    public void setDueDatePeriod(Date dueDatePeriod) {
        this.dueDatePeriod = dueDatePeriod;
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

    public List<Installment> getInstallments() {
        return installments;
    }

    public void setInstallments(List<Installment> installments) {
        this.installments = installments;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
