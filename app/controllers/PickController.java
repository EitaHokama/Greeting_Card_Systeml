package controllers;

import models.CaseStudy;
import models.Category;
import models.Department;
import models.Employees;
import models.Gratitude_Card;
import models.Login;
import play.mvc.*;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

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
public class PickController extends Controller {
@Inject
private FormFactory formFactory;
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    public Result pickup(){
    	//部署でページの表示を制限
    	//Employees.find.byId(Integer.valueOf(session("empId"))).parmissions
    	if(("経営陣".equals(Employees.find.byId(Integer.valueOf(session("empId"))).permissions) ||
    			("人事".equals(Employees.find.byId(Integer.valueOf(session("empId"))).permissions)))){
    	List<Gratitude_Card> gc = Gratitude_Card.find.all();
    	HashMap map = new HashMap<String, String[]>();


    	gc= Gratitude_Card.find.all();
    	SelectGC sel = new SelectGC(map);

    	gc = sel.findP();
    	return ok(pickup.render(gc, "", map));
    	}

    	return ok(parerr.render());
    }
    public Result pickupPost(){
    	List<Gratitude_Card> gc ;

    	Map<String, String[]> params =request().body().asFormUrlEncoded();
    	SelectGC sel = new SelectGC(params);

    	gc = sel.findP();

    	return ok(pickup.render(gc, params.get("start_date")[0],params));
    }


}
