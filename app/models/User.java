package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by chandan on 28/10/16.
 */
@Entity
public class User extends Model {


  @Constraints.Required
  @Id
  public String email;
  public String password;
  public String phone;
  public String imei;

  public String validate() {
    if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
      return "Invalid email or password";
    }
    return null;
  }

  private static Finder<String, User> find = new Finder<>(User.class);

  public boolean authenticate() {
    User user = User.find.byId(this.email);
    return (user != null && user.password.equals(this.password));
  }

  public boolean isNewUser() {
    return !(phone == null || imei == null) && !(phone.isEmpty() || imei.isEmpty()) && User.find.byId(this.email) == null;
  }

  @Override
  public String toString() {
    return "User{" +
      "email='" + email + '\'' +
      ", password='" + password + '\'' +
      ", phone='" + phone + '\'' +
      ", imei='" + imei + '\'' +
      '}';
  }
}
