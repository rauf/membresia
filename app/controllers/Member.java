package controllers;

import play.mvc.*;
import play.data.Form;
import play.i18n.Messages;
import views.member.MemberFormData;
import models.Subscription;


public class Member extends Controller {

    public Result index() {
        return ok(views.html.member.index.render("ok"));
    }

    public Result create() {
        MemberFormData memberData = new MemberFormData();
        Form<MemberFormData> formData;
        formData = Form.form(MemberFormData.class).fill(memberData);
        return ok(views.html.member.form.render(
                Messages.get("member.form.global.new.title"),
                formData,
                Subscription.makeSubscriptionMap(memberData)
        ));
    }

    public Result show(String token) {

        return ok(views.html.member.index.render("ok"));
    }


    public Result save() {

        // Get the submitted form data from the request object, and run validation.
        Form<MemberFormData> formData = Form.form(MemberFormData.class).bindFromRequest();

        if (formData.hasErrors()) {
            // Don't call formData.get() when there are errors, pass 'null' to helpers instead.
            flash("error", "Please correct errors above.");
            return badRequest(views.html.member.form.render(
                    Messages.get("member.form.global.new.title"),
                    formData,
                    Subscription.makeSubscriptionMap(null)
            ));
        } else {
            // Convert the formData into a Student model instance.
            models.Member member = models.Member.makeInstance(formData.get());
            flash("success", "Member instance created/edited: " + member);
            return ok(views.html.member.form.render(
                    Messages.get("member.form.global.new.title"),
                    formData,
                    Subscription.makeSubscriptionMap(formData.get())
            ));
        }
    }

    public Result edit(String token) {

        return ok(views.html.member.index.render("ok"));
    }

    public Result update() {

        return ok(views.html.member.index.render("ok"));
    }

    public Result remove(String token) {

        return ok(views.html.member.index.render("ok"));
    }

    public Result makePayment(String token) {

        return ok(views.html.member.index.render("ok"));
    }
}
