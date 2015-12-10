package services.contract;

import models.Installment;
import models.Member;

public interface MemberInstallmentServiceInterface {

    public void createMemberInstallment(Member member, Installment installment);
}
