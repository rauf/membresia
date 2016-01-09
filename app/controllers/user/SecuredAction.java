package controllers.user;

import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import static play.mvc.Controller.session;

/**
 * Class for security check on action access
 */
public class SecuredAction extends Action.Simple {

    /**
     * Checks for auth session and redirects user if not authenticated.
     *
     * @param ctx Http context
     * @return F.Promise<Result>
     * @throws Throwable
     */
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        String token = session("X-AUTH-TOKEN");
        if (token != null) {

            return delegate.call(ctx);
        }
        Result unauthorized = Results.redirect(controllers.user.routes.UserController.login());

        return F.Promise.pure(unauthorized);
    }
}
