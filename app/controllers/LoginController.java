
package controllers;

import akka.actor.ActorSystem;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.db.DBApi;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.ExecutionContextExecutor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by chandan on 28/10/16.
 */
@Singleton
public class LoginController extends Controller {
  private final ActorSystem actorSystem;
  private final ExecutionContextExecutor exec;
  private final DBApi dbApi;
  private final FormFactory formFactory;

  @Inject
  public LoginController(ActorSystem actorSystem, ExecutionContextExecutor exec, DBApi dbApi, FormFactory formFactory) {
    this.actorSystem = actorSystem;
    this.exec = exec;
    this.dbApi = dbApi;
    this.formFactory = formFactory;
  }

  public Result registerGet() {
    Form<User> userForm = formFactory.form(User.class);
    return ok(views.html.register.render(userForm));
  }

  public Result registerPost() {
    Form<User> registerForm = formFactory.form(User.class).bindFromRequest();
    if (registerForm.hasErrors()) {
      return badRequest(views.html.register.render(registerForm));
    } else {
      User user = registerForm.get();
      if (user.isNewUser()) {
        user.save();
        return redirect(routes.DashboardController.dashboard());
      } else {
        return badRequest(views.html.register.render(registerForm));
      }
    }
  }


  public Result loginGet() {
    Form<User> userForm = formFactory.form(User.class);
    return ok(views.html.login.render(userForm));
  }

  public Result loginPost() {
    Form<User> userForm = formFactory.form(User.class).bindFromRequest();
    if (userForm.hasErrors()) {
      return badRequest(views.html.login.render(userForm));
    } else {
      User user = userForm.get();
      if (user.authenticate()) {
        return redirect(routes.DashboardController.dashboard());
      } else {
        return badRequest(views.html.login.render(userForm));
      }
    }
  }
}
