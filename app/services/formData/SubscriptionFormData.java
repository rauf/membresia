package services.formData;

import models.Member;
import models.Subscription;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import services.SubscriptionService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
//    protected Date endDate;
    protected String token;
    protected List<String> members = new ArrayList<>();
    protected Integer mode;

    protected Subscription subscription = new Subscription();
    protected SubscriptionService subscriptionService = new SubscriptionService();


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
//        this.endDate = subscription.getEndDate();
        this.token = subscription.getToken();
        for (Member member : subscription.getMembers()) {
            this.members.add(member.toString());
        }
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

//        Calendar endDateCal = Calendar.getInstance();
//        endDateCal.setTime(endDate);

        Calendar today = Calendar.getInstance();

        if (title == null) {
            errors.add(new ValidationError("title", Messages.get("subscription.form.validation.title")));
        }

        if (amount == null) {
            errors.add(new ValidationError("amount", Messages.get("subscription.form.validation.amount")));
        }

        if (periodicity == null) {
            errors.add(new ValidationError("periodicity", Messages.get("subscription.form.validation.periodicity")));
        }

        if (dueDatePeriod == null) {
            errors.add(new ValidationError("dueDatePeriod", Messages.get("subscription.form.validation.dueDatePeriod")));
        } else {
            if (dueDatePeriodCal.before(today)) {
                errors.add(new ValidationError("dueDatePeriod", Messages.get("subscription.form.validation.dueDatePeriod.before")));
            }

        }
//
//        if (endDateCal.before(today) || endDateCal.before(dueDatePeriod)) {
//            errors.add(new ValidationError("endDate", Messages.get("subscription.form.validation.endDate.before")));
//        }

        if (errors.size() > 0)

            return errors;

        return null;
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

//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }

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

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
