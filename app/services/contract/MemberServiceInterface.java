package services.contract;

import play.data.Form;
import models.Member;
import services.formData.MemberFormData;
import services.Pager;

import java.util.List;

/**
 * Middleware interface class for controller model interaction and other member related business logic
 */
public interface MemberServiceInterface {

    /**
     * Sets a MemberFormData object from model data
     *
     * @param token Unique member token identifier
     * @return MemberFormData
     */
    MemberFormData setMemberData(String token);

    /**
     * Generates a form object from a MemberFormData object populated from model
     *
     * @param memberData Data from model object
     * @return Form
     */
    Form<MemberFormData> setFormData(MemberFormData memberData);


    /**
     * Gets member paginated list from DB
     *
     * @param pager Pager object for query pagination
     * @return List
     */
    List<Member> getMemberList(Pager pager);

    /**
     * Get a specific member by token
     *
     * @param token Unique member identifier
     * @return Member
     */
    Member getMember(String token);

    /**
     * Saves form data into persistence object
     *
     * @param formData Member data from UI form
     * @return Member
     */
    Member save(Form<MemberFormData> formData);

    /**
     * Removes a specific member by token
     *
     * @param token Unique member identifier
     * @return boolean
     */
    boolean remove(String token);

    /**
     * Check if more that two members could be using the same email account
     *
     * @param email Member email to check upon
     * @param token Current member token to skip when verifying email
     * @return boolean
     */
    boolean isMemberEmailUsed(String email, String token);
}
