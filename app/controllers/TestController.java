package controllers;

import models.CaseStudy;
import models.Employees;
import models.Gratitude_Card;
import models.Category;
import play.mvc.*;
import java.util.List;
import java.util.ArrayList;

import views.html.*;
import play.data.Form;
import play.data.FormFactory;
import javax.inject.Inject;
import java.util.Map;
import java.util.HashMap;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class TestController extends Controller {



    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

	public List<Gratitude_Card> reGC(){
		List<Gratitude_Card> gc = new ArrayList<Gratitude_Card>();
		return gc;
	}


    public Result test2(){
    	List<Gratitude_Card> gc = Gratitude_Card.find.all();
    	Map map = new HashMap<String, String[]>();
    	String[] str= {"送信部署"};
    	gc= Gratitude_Card.find.all();

    	return ok(test2.render(gc, "", map));
    }
    public Result test2Post(){
    	List<Gratitude_Card> gc ;

    	Map<String, String[]> params =request().body().asFormUrlEncoded();
    	SelectGC sel = new SelectGC(params);
    	sel.controlCS();
    	gc = sel.find();

    	return ok(test2.render(gc, params.get("start_date")[0],params));
    }


    public Result makeJS(){
    	return ok(views.js.template.ichiran.render());
    }
    public Result syousai(){
    	Map<String, String[]> params =request().body().asFormUrlEncoded();
    	int iD = Integer.valueOf(params.get("CardNumber")[0]);
    	Gratitude_Card gc = Gratitude_Card.find.byId(iD);
    	return ok(syousai.render(gc,"" +iD));
    }

    public Result syousaiGet(){

    	return ok(syousaiNot.render());
    }
}
