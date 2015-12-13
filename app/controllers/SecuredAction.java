package controllers;

import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import static play.mvc.Controller.session;


public class SecuredAction extends Action.Simple {

    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        String token = session("X-AUTH-TOKEN");
        if (token != null) {
            return delegate.call(ctx);
        }
        Result unauthorized = Results.redirect(routes.UserController.login());
        return F.Promise.pure(unauthorized);
    }
}
