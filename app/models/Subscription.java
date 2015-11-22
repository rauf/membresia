package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;

import play.data.format.*;
import play.data.validation.*;
import views.member.MemberFormData;

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
            subscriptionMap.put(subscription.title, (member != null && member.subscriptions.contains(subscription.title)));
        }
        return subscriptionMap;
    }

    @Override
    public String toString() {
        return String.format("[Subscription %s]", this.title);
    }
}
