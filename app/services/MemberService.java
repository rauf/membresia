package services;

import models.Member;
import play.data.Form;
import services.contract.MemberServiceInterface;

import java.util.List;

/**
 * Middleware class for controller model interaction and other member related business logic
 */
public class MemberService implements MemberServiceInterface {

    public Form<MemberFormData> setFormData(MemberFormData memberData) {
        return Form.form(MemberFormData.class).fill(memberData);
    }

    public MemberFormData setMemberData(String token) {
        return new MemberFormData(getModel().getMemberByToken(token));
    }

    public List<Member> getMemberList() {
        return getModel().getMemberList();
    }

    public Member save(Form<MemberFormData> formData) {
        Member member = (formData.get().getId() != null) ? getModel().getMemberById(formData.get().getId()) : getModel();
        member.setData(formData.get());
        member.save();
        return member;
    }

    public boolean remove(String token) {
        return getModel().remove(token);
    }

    public Member getMember(String token) {
        return getModel().getMemberByToken(token);
    }

    private Member getModel() {
        return new Member();
    }
}
