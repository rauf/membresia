package views.formData;

import models.user.Member;
import models.subscription.Periodicity;
import models.formData.SelectOptionItem;
import models.subscription.Subscription;
import play.data.validation.ValidationError;
import play.i18n.Messages;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that handles Members form submission and validation and relates submitted data to the model
 */
public class SubscriptionFormData {

    protected Long id;
    protected String subscriptionId;
    protected String title;
    protected String description;
    protected Double amount;
    protected String periodicity;
    protected Date dueDatePeriod;
    protected String token;
    protected List<String> members = new ArrayList<>();
    protected Integer mode;

    protected Subscription subscription = new Subscription();

    /**
     * Creates a new SubscriptionFormData instance for subscription create action
     */
    public SubscriptionFormData() {
        this.subscriptionId = this.subscription.generateSubscriptionId();
        this.token = this.subscription.generateToken();
    }

    /**
     * Created a new SubscriptionFormData instance for subscription editing action
     *
     * @param subscription subscription model
     */
    public SubscriptionFormData(Subscription subscription) {
        this.id = subscription.getId();
        this.subscriptionId = subscription.getSubscriptionId();
        this.title = subscription.getTitle();
        this.description = subscription.getDescription();
        this.amount = subscription.getAmount();
        this.periodicity = subscription.getPeriodicity();
        this.dueDatePeriod = subscription.getDueDatePeriod();
        this.token = subscription.getToken();
        this.members.addAll(subscription.getMembers().stream().map(Member::toString).collect(Collectors.toList()));
    }

    /**
     * Server side validation method
     *
     * @return List<ValidationError>
     */
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        Calendar dueDatePeriodCal = Calendar.getInstance();
        dueDatePeriodCal.setTime(dueDatePeriod);

        Calendar today = Calendar.getInstance();

        if (title == null || title.length() == 0) {
            errors.add(new ValidationError("title", Messages.get("subscription.form.validation.title")));
        }

        if (amount == null) {
            errors.add(new ValidationError("amount", Messages.get("subscription.form.validation.amount")));
        }

        if (periodicity == null || periodicity.length() == 0) {
            errors.add(new ValidationError("periodicity", Messages.get("subscription.form.validation.periodicity")));
        }

        if (dueDatePeriod == null) {
            errors.add(new ValidationError("dueDatePeriod", Messages.get("subscription.form.validation.dueDatePeriod")));
        } else {
            if (dueDatePeriodCal.before(today)) {
                errors.add(new ValidationError("dueDatePeriod", Messages.get("subscription.form.validation.dueDatePeriod.before")));
            }
        }

        if (errors.size() > 0)

            return errors;

        return null;
    }

    /**
     * Generates a list of available subscriptions to subscribe the member to and to use as a pull down menu
     *
     * @param memberFormData Member form data to define selected select menu items
     * @return Map
     */
    public Map<SelectOptionItem, Boolean> makeSubscriptionMap(MemberFormData memberFormData) {
        Subscription subscription = new Subscription();
        List<Subscription> allSubscriptions = subscription.getList();
        Map<SelectOptionItem, Boolean> subscriptionMap = new HashMap<>();
        for (Subscription subscriptionItem : allSubscriptions) {
            SelectOptionItem selectOptionItem = new SelectOptionItem(subscriptionItem.toString(), subscriptionItem.getToken());
            subscriptionMap.put(selectOptionItem, (memberFormData != null && memberFormData.getSubscriptions().contains(subscriptionItem.getToken())));
        }

        return subscriptionMap;
    }

    /**
     * Creates a list of available subscription periods to use as a pull down menu options
     *
     * @param subscriptionFormData Subscription data used to define selected items on menu
     * @return Map
     */
    public Map<SelectOptionItem, Boolean> makePeriodicityMap(SubscriptionFormData subscriptionFormData) {
        EnumSet<Periodicity> periods = EnumSet.allOf(Periodicity.class);
        Map<SelectOptionItem, Boolean> periodicityMap = new HashMap<>();
        for (Periodicity period : periods) {
            SelectOptionItem selectOptionItem = new SelectOptionItem(period.toString(), period.name());
            periodicityMap.put(selectOptionItem, subscriptionFormData != null && ((subscriptionFormData.getPeriodicity() != null) && subscriptionFormData.getPeriodicity().equals(period.name())));
        }
        return periodicityMap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public Date getDueDatePeriod() {
        return dueDatePeriod;
    }

    public void setDueDatePeriod(Date dueDatePeriod) {
        this.dueDatePeriod = dueDatePeriod;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}
