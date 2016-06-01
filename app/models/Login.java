package models;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.Required;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class Login{
	public String username;
	public String password;

	public String validate(){
		if(authenticate(username,password)){
			return null;
		}
		return "Invalid username and password";
	}
	private Boolean authenticate(String username,String password){
		return Employees.authenticate(username, password);

	}
}
