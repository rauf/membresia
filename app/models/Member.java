package models;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import org.apache.commons.codec.digest.Md5Crypt;
import play.data.format.Formats;
import play.data.validation.*;
import services.MD5;
import views.member.MemberFormData;

import java.util.*;

@Entity
@Table(name = "member")
public class Member extends Model {

    @Id
    private Long id;

    @Constraints.Required
    @Column(unique = true)
    public String email;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String lastName;

    @Constraints.Required
    @Column(unique = true)
    public String token;

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

    public Member() {
        super();
    }

    public Member(
            long id,
            String address,
            String cp,
            String city,
            String state,
            String country,
            String nif,
            String phone) {
        super();

        this.setId(id);
        this.address = address;
        this.cp = cp;
        this.city = city;
        this.state = state;
        this.country = country;
        this.nif = nif;
        this.phone = phone;
        this.token = MD5.getMD5((new Date()).toString());
    }

    public boolean hasSubscription(String subscriptionName) {
        for (Subscription subscription : this.subscriptions) {
            if (subscriptionName.equals(subscription.getTitle()))
                return true;
        }
        return false;
    }

    public void addSubscription(Subscription subscription) {
        this.subscriptions.add(subscription);
    }

    public static Member makeInstance(MemberFormData formData) {
        Member member = new Member();
        member.name = formData.name;
        member.lastName = formData.lastName;
        member.email = formData.email;
        member.address = formData.address;
        member.city = formData.city;
        member.state = formData.state;
        member.cp = formData.cp;
        member.country = formData.country;
        member.nif = formData.nif;
        member.phone = formData.phone;
        member.token = formData.token;
        for (String subscription : formData.subscriptions) {
            member.subscriptions.add(Subscription.findByToken(subscription));
        }
        member.save();
        return member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
