package controllers;

import models.CaseStudy;

import models.Employees;
import models.Gratitude_Card;
import play.mvc.*;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.inject.Inject;
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
public class HomeController extends Controller {
@Inject
private FormFactory formFactory;
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render());
    }

  /*  public Result bbs() {
    	//Employees emp = Employees.find.byId(1);
    	Gratitude_Card gc = Gratitude_Card.find.byId(1);
    	CaseStudy cs = CaseStudy.find.byId(1);
    	return ok(bbs.render(gc,cs));
    }*/
    public Result bbs(){
    	List<Gratitude_Card> gc = Gratitude_Card.find.all();
    	HashMap map = new HashMap<String, String[]>();

    	SelectGC.lastMonth(map);
    	SelectGC sel = new SelectGC(map);

    	gc = sel.findBBS();

    	return ok(bbs.render(gc, "", map));
    }
    public Result bbsPost(){
    	List<Gratitude_Card> gc ;

    	Map<String, String[]> params =request().body().asFormUrlEncoded();
    	SelectGC sel = new SelectGC(params);
    	gc = sel.findBBS();

    	return ok(bbs.render(gc, "",params));
    }
    public Result valuation() {
    	//Employees emp = Employees.find.byId(1);
    	List<Employees> emp = Employees.find.all();

    	return ok(valuation.render(emp,"",new HashMap<String,String[]>()));
    }
    public Result valuationPost() {
    	//Employees emp = Employees.find.byId(1);

    	List<Employees> emp = Employees.find.all();
    	return ok(valuation.render(emp,"",new HashMap<String,String[]>()));
    }

    public Result test(){
    	//Employees emp = Employees.find.byId(1);
    	List<Gratitude_Card> gc =
    			Gratitude_Card.find.where().orderBy("date DESC").findList();
    	CaseStudy cs = CaseStudy.find.byId(1);
    	return ok(test.render(gc,cs));
    }

    public Result login(){
    	return ok(login.render());
    }
    /*public Result typical(){
    	//Employees emp = Employees.find.byId(1);
    	Gratitude_Card gc = Gratitude_Card.find.byId(1);
    	CaseStudy cs = CaseStudy.find.byId(1);
    	return ok(typical.render(gc,cs));
    }*/
    public Result typical(){
    	List<Gratitude_Card> gc = Gratitude_Card.find.all();
    	Map map = new HashMap<String, String[]>();


    	gc= Gratitude_Card.find.all();
    	SelectGC sel = new SelectGC(map);
    	gc = sel.find();

    	return ok(typical.render(gc, "", map));
    }
    public Result typicalPost(){
    	List<Gratitude_Card> gc ;

    	Map<String, String[]> params =request().body().asFormUrlEncoded();
    	SelectGC sel = new SelectGC(params);
    	sel.controlCS();
    	gc = sel.find();

    	return ok(typical.render(gc, params.get("start_date")[0],params));
    }

    public Result creation() {
    	///DEPARTMENT DT = DEPARTMENT.find.byId(1);
    	//CATEGORY CG = CATEGORY.find.byId(1);
    	//return ok(test.render(gc,cs));
    	return ok(creation.render());
    }

    public Result kanri() {
        return ok(kanri.render());

    }
    public Result valuation_detailed() {
        return ok(valuation_detailed.render());
    }

    public Result createTest(){
    	Employees emp = formFactory.form(Employees.class).bindFromRequest().get();
    	emp.save();
    	return redirect(routes.HomeController.test());
    }

}

