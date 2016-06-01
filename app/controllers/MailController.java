package controllers;

import models.CaseStudy;

import models.Employees;
import models.Gratitude_Card;
import play.mvc.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import views.html.*;
import java.text.SimpleDateFormat;
import play.data.Form;
import play.data.validation.Constraints.Required;
import views.html.mail.*;
/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class MailController extends Controller {
    public Result receive(){
    	List<Gratitude_Card> gc ;

    	Map<String, String[]> params =request().body().asFormUrlEncoded();
    	SelectGC sel = new SelectGC(params);
    	gc = sel.findRec(Employees.find.byId(Integer.parseInt(session("empId"))));//Employeesクラスを代入・ログインユーザーのIDで絞込み

    	return ok(receive.render(gc, "",params));
    }
    public Result receiveGet(){
    	List<Gratitude_Card> gc ;
    	Map<String, String[]> params =new HashMap<String, String[]>();

    	SelectGC sel = new SelectGC(params);
    	gc = sel.findRec(Employees.find.byId(Integer.parseInt(session("empId"))));//Employeesクラスを代入・ログインユーザーのIDで絞込み

    	return ok(receive.render(gc, "",params));
    }
    public Result trans(){
    	List<Gratitude_Card> gc ;

    	Map<String, String[]> params =request().body().asFormUrlEncoded();
    	SelectGC sel = new SelectGC(params);
    	gc = sel.findSend(Employees.find.byId(Integer.parseInt(session("empId"))));//Employeesクラスを代入・ログインユーザーのIDで絞込み

    	return ok(trans.render(gc, "",params));
    }
    public Result transGet(){
    	List<Gratitude_Card> gc ;
    	Map<String, String[]> params =new HashMap<String, String[]>();

    	SelectGC sel = new SelectGC(params);
    	gc = sel.findSend(Employees.find.byId(Integer.parseInt(session("empId"))));//Employeesクラスを代入・ログインユーザーのIDで絞込み

    	return ok(trans.render(gc, "",params));
    }
}
