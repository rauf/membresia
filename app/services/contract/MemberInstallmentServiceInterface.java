package services.contract;

import models.Installment;
import models.Member;
import models.MemberInstallment;

public interface MemberInstallmentServiceInterface {

    /**
     * Gets a member installment by requested value and key
     *
     * @param key   Field to search for
     * @param value Value to look for in provided field
     * @return MemberInstallmebt
     */
    MemberInstallment get(String key, String value);

    /**
     * Generates member installments from a member subscription list and  due installments
     *
     * @param member Member to create installments for
     */
    void setMemberInstallments(Member member);

    /**
     * Create a new member installment from a subscription instalment
     *
     * @param member      Member to create instalment for
     * @param installment Installment to use for member installment generation
     */
    void createMemberInstallment(Member member, Installment installment);

    /**
     * Gets total amount paid by member on a specific installment
     *
     * @param token Member installment token
     * @return Double
     */
    Double getTotalPaid(String token);

    /**
     * Gets member debt on a specific installment
     *
     * @param token Member installment token
     * @return Double
     */
    Double getAmountDue(String token);

    /**
     * Set a member installment as paid
     *
     * @param memberInstallment Member installmebt to set as paid
     */
    void setPaid(MemberInstallment memberInstallment);
}
