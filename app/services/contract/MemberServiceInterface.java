package services.contract;

import models.Member;
import play.data.Form;
import services.AdminUserFormData;
import services.MemberFormData;

import java.util.List;

/**
 * Middleware interface class for controller model interaction and other member related business logic
 */
public interface MemberServiceInterface {

    Form<MemberFormData> setFormData(MemberFormData memberData);

    MemberFormData setMemberData(String token);

    List<Member> getMemberList();

    Member getMember(String token);

    Member save(Form<MemberFormData> formData);

    boolean remove(String token);
}
