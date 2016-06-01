package models;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.Required;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Administrators extends Model {
	@Id

	public Integer admin_id;
	@Required
	public String login_id;
	@Required
	public String pass;


	public static Find<Integer,Administrators> find = new Find<Integer,Administrators>(){};

	public static Boolean authenticate(String username, String password) {
        Administrators admin = Administrators.find.where().eq("login_id", username).findUnique();
        return (admin != null && admin.pass.equals(password));
    }
}
