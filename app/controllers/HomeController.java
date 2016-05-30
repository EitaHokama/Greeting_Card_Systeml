package controllers;

import models.CaseStudy;
import models.Employees;
import models.Gratitude_Card;
import play.mvc.*;
import java.util.Map;
import views.html.*;

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
        return ok(index.render("Your new application is ready."));
    }

    public Result test(){
    	//Employees emp = Employees.find.byId(1);
    	Gratitude_Card gc = Gratitude_Card.find.byId(1);
    	CaseStudy cs = CaseStudy.find.byId(1);
    	return ok(test.render(gc,cs));
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
    /*public Result tasks() {
        List<Task> taskList = Task.find.all();
        return ok(tasks.render(taskList));*/


    public Result createkanri() {
        // java.util.Map をインポートしておく
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Employees newEmp = new Employees();
        newEmp.name = params.get("name")[0]; // <input type="text" name="name" /> に入力された値
        newEmp.save();
        return redirect(routes.HomeController.kanri());
    }

    }

















