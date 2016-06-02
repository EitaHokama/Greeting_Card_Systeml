package controllers;

import play.mvc.*;
import javax.inject.Inject;
import models.Login;
import models.AdLogin;
import play.data.Form;
import play.data.FormFactory;
import views.html.authentication.*;
import java.util.List;
import models.Employees;
import models.Administrators;

public class AuthController extends Controller{
	@Inject
	private FormFactory formFactory;

	//下記一般用ログインコード
	public Result index(){
		if(session("login") != null){
			return ok("あなたは既に " + session("login") + " としてログインしています");
		}
		List<Employees> emp = Employees.find.all();
		return ok(index.
				render(formFactory.form(Login.class)));
	}

	public Result authenticate(){

		Form<Login> form = formFactory.form(Login.class).bindFromRequest();

		if(form.hasErrors()){
			return badRequest(index.render(form));
		}else{
			Login login = form.get();

			//ユーザーネームを参照しperに権限データを入れ、depIdには部署名を入れ、empIdは社員IDを入れている。
			String per = Employees.find.where().eq("name", login.username).findUnique().permissions;
			String depid = Employees.find.where().eq("name", login.username).findUnique().department_id.department_name;
			String empid = String.valueOf(Employees.find.where().eq("name", login.username).findUnique().employees_id);

			session("login",login.username);
			session("depId",depid);
			session("empId",empid);

			if(per == null){
			MailController mail = new MailController();
			return mail.receive();
			}

			if(per.equals("人事")){
			HomeController zin = new HomeController();
			return zin.bbs();
			}

			PickController kei = new PickController();
			return kei.pickup();
		}
	}

	public Result logout(){
		session().clear();
		return redirect(routes.AuthController.index());
	}

	//下記管理者用ログインコード
	public Result adindex(){
		if(session("login") != null){
			return ok("あなたは既に " + session("login") + " としてログインしています");
		}
		List<Administrators> admin = Administrators.find.all();
		return ok(adindex.render(formFactory.form(AdLogin.class)));

	}

	public Result adauthenticate(){

		Form<AdLogin> form = formFactory.form(AdLogin.class).bindFromRequest();

		if(form.hasErrors()){
			return badRequest(adindex.render(form));
		}else{
			AdLogin login = form.get();
			session("login",login.username);

			HomeController kanri = new HomeController();
			return kanri.valuation();
		}

	}
	public Result adlogout(){
		session().clear();
		return redirect(routes.AuthController.adindex());
	}
}
