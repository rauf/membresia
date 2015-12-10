package services;

import models.Installment;
import models.Member;
import models.MemberInstallment;
import services.contract.MemberInstallmentServiceInterface;

public class MemberInstallmentService implements MemberInstallmentServiceInterface {

    public void createMemberInstallment(Member member, Installment installment) {
        MemberInstallment memberInstallment = getModel();
        memberInstallment.setMember(member);
        memberInstallment.setInstallment(installment);
        memberInstallment.save();
    }

    private MemberInstallment getModel() {
        return new MemberInstallment();
    }
}
