package views.formData;

import models.SelectOptionItem;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import models.Member;
import models.Subscription;
import services.MemberInstallmentService;
import services.MemberService;
import services.MoneyFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that handles Members form submission and validation and relates submitted data to the model
 * as well as other form related actions.
 */
public class MemberFormData {

    protected Long id;
    protected String memberId;
    protected String name;
    protected String lastName;
    protected String email;
    protected String address;
    protected String city;
    protected String state;
    protected String country;
    protected String cp;
    protected String phone;
    protected String nif;
    protected String token;
    protected Integer mode;
    protected List<String> subscriptions = new ArrayList<>();

    protected Member member = new Member();
    protected MemberService memberService = new MemberService();


    /**
     * Creates a new MemberFormData instance for member create action
     */
    public MemberFormData() {
        this.memberId = this.member.generateMemberId();
        this.token = this.member.generateToken();
    }

    /**
     * Created a new MemberFormData instance for member editing action
     *
     * @param member Member model object
     */
    public MemberFormData(Member member) {
        this.id = member.getId();
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.lastName = member.getLastName();
        this.email = member.getEmail();
        this.address = member.getAddress();
        this.city = member.getCity();
        this.state = member.getState();
        this.country = member.getCountry();
        this.cp = member.getCp();
        this.phone = member.getPhone();
        this.nif = member.getNif();
        this.token = member.getToken();
        this.subscriptions.addAll(member.getSubscriptions().stream().map(Subscription::getToken).collect(Collectors.toList()));
    }

    /**
     * Server side validation method
     *
     * @return List<ValidationError>
     */
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

        if (memberService.isMemberEmailUsed(email, token)) {
            errors.add(new ValidationError("email", Messages.get("member.form.validation.emailUsed")));
        }

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

//        if (subscriptions == null || subscriptions.size() == 0) {
//            errors.add(new ValidationError("subscriptions", Messages.get("member.form.validation.subscriptions")));
//        }

        if (errors.size() > 0)

            return errors;

        return null;
    }

    /**
     * Creates a list of member installments for given installment to use as a pull down menu
     *
     * @param member Member to show the installments for
     * @return
     */
    public Map<SelectOptionItem, Boolean> makeMemberInstallmentMap(Member member) {
        MemberInstallmentService memberInstallmentService = new MemberInstallmentService();
        Map<SelectOptionItem, Boolean> memberInstallmentMap = new HashMap<>();
        member.getMemberInstallments().stream().filter(memberInstallment -> !memberInstallment.getPaid()).forEach(memberInstallment -> {
            Double amountDue = memberInstallmentService.getAmountDue(memberInstallment.getToken());
            String due = MoneyFormat.setMoney(amountDue);
            SelectOptionItem selectOptionItem = new SelectOptionItem(memberInstallment.getInstallment().toString() + " | " + due, memberInstallment.getToken());
            memberInstallmentMap.put(selectOptionItem, false);
        });

        return memberInstallmentMap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public List<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
