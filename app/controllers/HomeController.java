package controllers;

import play.mvc.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import views.html.*;
import models.CaseStudy;
import models.Employees;
import models.Gratitude_Card;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render());
    }

    public Result valuation() {
    	//Employees emp = Employees.find.byId(1);
    	Gratitude_Card gc = Gratitude_Card.find.byId(1);
    	CaseStudy cs = CaseStudy.find.byId(1);
    	return ok(valuation.render(gc,cs));
    }
    public Result bbs(){
    	List<Gratitude_Card> gc = Gratitude_Card.find.all();

    	gc= new SelectGC(new HashMap<String, String[]>()).sortDate(gc);

    	return ok(bbs.render(gc, "", new HashMap<String, String[]>()));
    }
    public Result bbsPost(){
    	List<Gratitude_Card> gc ;

    	Map<String, String[]> params =request().body().asFormUrlEncoded();
    	SelectGC sel = new SelectGC(params);
    	gc = sel.find();

    	return ok(bbs.render(gc, params.get("sender_department")[0],params));
    }
    public Result test(){
    	//Employees emp = Employees.find.byId(1);
    	List<Gratitude_Card> gc =
    			Gratitude_Card.find.where().orderBy("date DESC").findList();
    	CaseStudy cs = CaseStudy.find.byId(1);
    	return ok(test.render(gc,cs));
    }
    public Result trans(){
    	//Employees emp = Employees.find.byId(1);
    	Gratitude_Card gc = Gratitude_Card.find.byId(1);
    	CaseStudy cs = CaseStudy.find.byId(1);
    	return ok(trans.render());
    }
    public Result login(){
    	return ok(login.render());
    }

    public Result typical(){
    	List<Gratitude_Card> gc = Gratitude_Card.find.all();

    	gc= new SelectGC(new HashMap<String, String[]>()).sortDate(gc);

    	return ok(typical.render(gc, "", new HashMap<String, String[]>()));
    }
    public Result typicalPost(){
    	List<Gratitude_Card> gc ;

    	Map<String, String[]> params =request().body().asFormUrlEncoded();
    	SelectGC sel = new SelectGC(params);
    	gc = sel.find();

    	return ok(typical.render(gc, params.get("sender_department")[0],params));
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
    	Map<String,String[]> params = request().body().asFormUrlEncoded();
    	Gratitude_Card newCard = new Gratitude_Card();
    	newCard.card_title = params.get("card_title")[0];
    	newCard.find.all();
    	return redirect(routes.HomeController.test());
    }

}

