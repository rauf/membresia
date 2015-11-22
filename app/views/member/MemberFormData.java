package views.member;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.ValidationError;
import play.i18n.Messages;
import models.Subscription;

public class MemberFormData {

    public String name = "";
    public String lastName = "";
    public String email = "";
    public String password = "";
    public String address = "";
    public String city = "";
    public String state = "";
    public String country = "";
    public String cp = "";
    public String phone = "";
    public String nif = "";
    public List<String> subscriptions = new ArrayList<>();

    public MemberFormData() {
    }

    public MemberFormData(
            String name,
            String lastName,
            String email,
            String password,
            String address,
            String city,
            String state,
            String country,
            String cp,
            String phone,
            String nif,
            List<Subscription> subscriptions
    ) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.cp = cp;
        this.phone = phone;
        this.nif = nif;
        for (Subscription subscription : subscriptions) {
            this.subscriptions.add(subscription.title);
        }
    }

    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if (name == null || name.length() == 0) {
            errors.add(new ValidationError("name", Messages.get("member.form.validation.name")));
        }

        if (lastName == null || lastName.length() == 0) {
            errors.add(new ValidationError("lastName", Messages.get("member.form.validation.lastName")));
        }

        if (email == null || email.length() == 0) {
            errors.add(new ValidationError("email", Messages.get("member.form.validation.email")));
        }

//        if (password == null || password.length() == 0) {
//            errors.add(new ValidationError("password", Messages.get("member.form.validation.password")));
//        } else if (password.length() < 5) {
//            errors.add(new ValidationError("password", Messages.get("member.form.validation.password.length")));
//        }

        if (address == null || address.length() == 0) {
            errors.add(new ValidationError("address", Messages.get("member.form.validation.address")));
        }

        if (city == null || city.length() == 0) {
            errors.add(new ValidationError("city", Messages.get("member.form.validation.city")));
        }

        if (state == null || state.length() == 0) {
            errors.add(new ValidationError("state", Messages.get("member.form.validation.state")));
        }

        if (country == null || country.length() == 0) {
            errors.add(new ValidationError("country", Messages.get("member.form.validation.country")));
        }

        if (cp == null || cp.length() == 0) {
            errors.add(new ValidationError("cp", Messages.get("member.form.validation.cp")));
        }

        if (phone == null || phone.length() == 0) {
            errors.add(new ValidationError("phone", Messages.get("member.form.validation.phone")));
        }

        if (nif == null || nif.length() == 0) {
            errors.add(new ValidationError("name", Messages.get("member.form.validation.nif")));
        }

//        if (subscriptions.size() > 0) {
//            for (String subscription : subscriptions) {
//                if (Subscription.findByToken(subscription) == null) {
//                    errors.add(new ValidationError("subscriptions", Messages.get("member.form.validation.subscription", subscription) + "."));
//                }
//            }
//        }

        if (errors.size() > 0)
            return errors;

        return null;
    }
}
