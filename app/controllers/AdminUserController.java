package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.i18n.Messages;
import play.api.libs.mailer.MailerClient;
import models.AdminUser;
import play.mvc.With;
import services.formData.AdminUserFormData;
import services.AdminUserService;
import services.Pager;

import javax.inject.Inject;
import java.util.List;
import views.html.*;


/**
 * Controller for MemberController component
 */

@With(SecuredAction.class)
public class AdminUserController extends Controller {

    private AdminUserService adminUserService = new AdminUserService();

    private AdminUserFormData adminUserData;

    private Form<AdminUserFormData> formData;

    private Pager pager;

    private Integer currentPage = 1;

    private final MailerClient mailer;

    @Inject
    public AdminUserController(MailerClient mailer) {
        this.mailer = mailer;
    }


    /**
     * Shows admin user list
     *
     * @return Result
     */
    public Result index(Integer currentPage) {
        this.currentPage = currentPage;
        pager = new Pager(this.currentPage);
        List<AdminUser> adminUsers = adminUserService.getAdminUserList(pager);

        return ok(views.html.adminUser.index.render(Messages.get("adminUser.list.global.title"), adminUsers, pager));
    }

    /**
     * Renders admin user form in creation mode
     *
     * @return Result
     */
    public Result create() {
        adminUserData = new AdminUserFormData();
        formData = adminUserService.setFormData(adminUserData);
        adminUserData.setMode(0);

        return ok(views.html.adminUser.form.render(Messages.get("adminUser.form.global.new.title"), formData));
    }

    /**
     * Renders admin user form in editing mode by user token
     *
     * @param token Unique admin user token identifier
     * @return Result
     */
    public Result edit(String token) {
        adminUserData = new AdminUserFormData();
        adminUserData = adminUserService.setAdminUserData(token);
        adminUserData.setMode(1);
        formData = adminUserService.setFormData(adminUserData);

        return ok(views.html.adminUser.form.render(Messages.get("adminUser.form.global.new.title"), formData));
    }

    /**
     * Saves admin user form data after create or edit
     *
     * @return Result
     */
    public Result save() {
        adminUserData = new AdminUserFormData();
        formData = Form.form(AdminUserFormData.class).bindFromRequest();

        if (formData.hasErrors()) {
            flash("error", Messages.get("app.global.validation.message"));

            return badRequest(views.html.adminUser.form.render(Messages.get("adminUser.form.global.new.title"), formData));
        } else {
            AdminUser user = adminUserService.save(formData);

            if (formData.get().getMode() == 0)
                adminUserService.sendNewAccountMail(mailer, user);

            flash("success", Messages.get("adminUser.form.save.message.notification", user));
            pager = new Pager(this.currentPage);
            List<AdminUser> users = adminUserService.getAdminUserList(pager);

            return ok(views.html.adminUser.index.render(Messages.get("adminUser.list.global.title"), users, pager));
        }
    }

    /**
     * Show admin user details by user token
     *
     * @param token Unique user token identifier
     * @return Result
     */
    public Result show(String token) {
        AdminUser member = adminUserService.getAdminUser(token);

        return ok(views.html.adminUser.show.render(member));
    }

    /**
     * Removes a specific admin user by member token
     *
     * @param token Unique user token identifier
     * @return Result
     */
    public Result remove(String token) {
        if (adminUserService.remove(token)) {
            flash("success", Messages.get("member.form.remove.message.success"));
        } else {
            flash("error", Messages.get("member.form.remove.message.error"));
        }
        pager = new Pager(1);
        List<AdminUser> members = adminUserService.getAdminUserList(pager);

        return ok(views.html.adminUser.index.render(Messages.get("adminUser.list.global.title"), members, pager));
    }
}
