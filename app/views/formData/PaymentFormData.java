package views.formData;


import play.data.validation.ValidationError;
import play.i18n.Messages;
import lib.MD5;
import services.subscription.MemberInstallmentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that handles Payment form submission and validation and relates submitted data to the model
 */
public class PaymentFormData {

    protected Double amount;
    protected String token;
    protected String memberInstallmentToken;
    protected String memberToken;


    /**
     * Creates a new PaymentFormData instance from a payment create action
     */
    public PaymentFormData() {
        this.token = generateToken();
    }

    /**
     * Server side validation method
     *
     * @return List<ValidationError>
     */
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();
        MemberInstallmentService memberInstallmentService = new MemberInstallmentService();
        Double amountDue = memberInstallmentService.getAmountDue(memberInstallmentToken);


        if (memberInstallmentToken == null) {
            errors.add(new ValidationError("memberInstallmentToken", Messages.get("payment.form.validation.memberInstallmentToken")));
        }

        if (amount == null || amount == 0) {
            errors.add(new ValidationError("amount", Messages.get("payment.form.validation.amount")));
        }

        if (amountDue < amount) {
            errors.add(new ValidationError("amount", Messages.get("payment.form.validation.excess", amountDue)));

        }

        if (errors.size() > 0)

            return errors;

        return null;
    }

    /**
     * Generates unique object token
     *
     * @return String
     */
    public String generateToken() {
        return MD5.getMD5((new Date()).toString());
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

    public String getMemberInstallmentToken() {
        return memberInstallmentToken;
    }

    public void setMemberInstallmentToken(String memberInstallmentToken) {
        this.memberInstallmentToken = memberInstallmentToken;
    }

    public void setMemberToken(String memberToken) {
        this.memberToken = memberToken;
    }

    public String getMemberToken() {
        return memberToken;
    }
}
