package controllers;

import models.CaseStudy;
import models.Category;
import models.Department;
import models.Employees;
import models.Gratitude_Card;
import play.mvc.*;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import javax.inject.Inject;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

import views.html.*;
import java.text.SimpleDateFormat;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints.Required;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
@Security.Authenticated(Secured.class)
public class CreationController extends Controller {
@Inject
private FormFactory formFactory;
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	public Result creation() {
		///DEPARTMENT DT = DEPARTMENT.find.byId(1);
		//CATEGORY CG = CATEGORY.find.byId(1);
		//return ok(test.render(gc,cs));
		return ok(creation.render("",""));
	}
	public Result creationPost(){
		Map<String,String[]> params = request().body().asFormUrlEncoded();
		Gratitude_Card gc =new Gratitude_Card();

		gc.sender_id= Employees.find.byId((Integer.parseInt(session("empId"))));//セッションからidを求めること
		gc.date = new Date();
		boolean category_error=true;
		Category cs = new Category();

		try {
			cs =Category.find.where().eq("category_name", params.get("カテゴリ")[0]).findUnique();
			if(cs !=null){
				category_error=false;
				gc.category_id=cs;
			}
		} catch (Exception e) {
			// TODO: handle exception

		}
		if(category_error){
			return badRequest(creation.render("カテゴリ", "カテゴリを入力してください。"));
		}
		Department dep = new Department();
		boolean dep_error=true;
		try{
			dep =Department.find.where().eq("department_name", params.get("sel_bunruiA")[0]).findUnique();
			if(dep !=null){
				dep_error=false;
			}

		} catch (Exception e){

		}
		if(dep_error){
			return  badRequest(creation.render("sel_bunruiA", "部署を入力してください。"));
		}
		Employees emp = new Employees();
		boolean emp_error=true;
		try{
			emp =Employees.find.where().eq("del_flag",0).eq("department_id", dep).eq("name", params.get("sel_bunruiB")[0]).findUnique();
			if(emp.employees_id.intValue() == gc.sender_id.employees_id.intValue()){
				return  badRequest(creation.render("sel_bunruiA", "自分に感謝カードを贈ることはできません。"));
			}
			if(emp !=null){
				emp_error=false;
				gc.receiver_id=emp;
			}


		} catch (Exception e){

		}
		if(emp_error){
			return  badRequest(creation.render("sel_bunruiA", "名前を入力してください。"));
		}
		boolean title_error=true;
		try{
			String title = params.get("title")[0];
			if(title.length()<2){
				return badRequest(creation.render("title","タイトルが短すぎます(2文字以上)"));

			}else if(title.length()>=30){
				return badRequest(creation.render("title","タイトルが長すぎます(30文字以下)"));

			}else{
				title_error=false;
				gc.card_title=title;
			}
		} catch (Exception e){

		}
		if(title_error){
			return badRequest(creation.render("title","タイトルを入力してください。"));
		}
		boolean content_error=true;
		try{
			String content = params.get("kanso")[0];
			if(content.length()<2){
				return badRequest(creation.render("title","内容が短すぎます(2文字以上)"));

			}else if(content.length()>=300){
				return badRequest(creation.render("title","内容が長すぎます(300文字以下)"));

			}else{
				content_error=false;
				gc.card_content=content;
			}
		} catch(Exception e){

		}
		if(content_error){
			return badRequest(creation.render("title","内容を入力してください。"));
		}

		String sql = " SELECT MAX(card_id) as max FROM GRATITUDE_CARD ";

		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql)
		        .findList();
		gc.card_id=sqlRows.get(0).getInteger("max")+1;
		gc.save();

		return ok(creation.render("success", "感謝カードを送りました。"));

	}

}
