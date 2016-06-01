package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

import models.CaseStudy;
import models.Category;
import models.Employees;
import models.Gratitude_Card;

/**
 * params 検索に使用するパラメーターを格納(検索前に必ずnewで初期化すること)
 * name 感謝カード各項目の論理名称を格納
 */
public class SelectEmp {

	private Map<String, String[]> params;
	public static final Map<String, String> name = new HashMap<String, String>(){
		{
			put("emp_cd", "社員ID");
			put("employees", "社員名");
			put("department", "所属");
			put("count","受信件数");
			put("syosai", "詳細");
			put("start_date", "");
			put("end_date", "");
		}
	};

	SelectEmp(Map<String, String[]> params){
		this.params = params;
	}

	public List<Employees> find(){
		List<Gratitude_Card> emp;
		ExpressionList<Employees> empWhere;


		/*Query<> cat = Category.find.where().eq("category_name",params.get("category")[0]).findList();
    	if(!"カテゴリ".equals(params.get("category")[0])){
    		gc = Gratitude_Card.find.where().eq("category_id",
    				cat.get(0)
    			).findList();
    	}else{
    		gc = Gratitude_Card.find.all();
    	}*/
		// com...EbeanList を戻り値に持つメソッドを連結する
		//gc= Gratitude_Card.find.all();

		//gc = sortDate(Gratitude_Card.find.all());
		empWhere=makeWhere();
		empWhere=empWhere.eq("del_flag" , 0);
		empWhere=findDepartment(empWhere);
		empWhere=findEmployees(empWhere);

		return empWhere.findList();
	}
	public List<Employees> findS(){
		List<Gratitude_Card> emp;
		ExpressionList<Employees> empWhere;


		/*Query<> cat = Category.find.where().eq("category_name",params.get("category")[0]).findList();
    	if(!"カテゴリ".equals(params.get("category")[0])){
    		gc = Gratitude_Card.find.where().eq("category_id",
    				cat.get(0)
    			).findList();
    	}else{
    		gc = Gratitude_Card.find.all();
    	}*/
		// com...EbeanList を戻り値に持つメソッドを連結する
		//gc= Gratitude_Card.find.all();

		//gc = sortDate(Gratitude_Card.find.all());
		empWhere=makeWhere();
		empWhere=empWhere.eq("del_flag" , 0);

		return empWhere.findList();
	}


	private ExpressionList<Employees> makeWhere(){
		ExpressionList<Employees> empWhere = Employees.find.where();
		empWhere=
				Ebean
				.find(Employees.class).fetch("department_id").where();
		return empWhere;
	}


	public List<Gratitude_Card> sortDate(ExpressionList<Gratitude_Card> gc){

		return gc.orderBy("date DESC").findList();
	}



	private ExpressionList<Employees> findDepartment(ExpressionList<Employees> emp){
		String buf;
		try{
			if(params.get("department") == null){
				return emp;
			}else{
				buf=params.get("department")[0];
			}
			if(name.get("department").equals(buf)){
				return emp;
			}else{
				emp.eq("department_id.department_name" ,buf);


				return emp;
			}

		}catch(Exception e){
			return emp;
		}




	}
	private ExpressionList<Employees> findEmployees(ExpressionList<Employees> emp){

		String buf;
		if(params.get("employees") == null){
			return  emp;
		}else{
			buf=params.get("employees")[0];
		}
		if(name.get("employees").equals(buf)){
			return emp;
		}else{
			emp=emp.eq("name" ,buf);


			return emp;
		}

	}

	private ExpressionList<Gratitude_Card> startDate(ExpressionList<Gratitude_Card> gc){
		String buf;
		ExpressionList<Gratitude_Card> st;
		if(!params.containsKey("start_date")){
			return gc;
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = f.parse(params.get("start_date")[0]);

			 st= gc.ge("date", d);

		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			//e.printStackTrace();
			return gc;
		}

		return st;
	}
	private ExpressionList<Gratitude_Card> endDate(ExpressionList<Gratitude_Card> gc){
		String buf;
		ExpressionList<Gratitude_Card> st;
		if(!params.containsKey("start_date")){
			return gc;
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = f.parse(params.get("end_date")[0]);
			Calendar c =Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DATE,1);
			d= c.getTime();

			 st= gc.lt("date", d);

		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			//e.printStackTrace();
			return gc;
		}

		return st;
	}

	private ExpressionList<Gratitude_Card> findCS(ExpressionList<Gratitude_Card> gc){
		if(params.containsKey("pickup")){
			String pickup = params.get("pickup")[0];
			if(pickup.equals(name.get("selected"))){
				gc= gc.eq("cs", CaseStudy.selCase());
			}else if(pickup.equals(name.get("removed"))){
				gc= gc.ne("cs", CaseStudy.selCase());
			}
		}

		return gc;
	}


 	public void controlCS(){
		Gratitude_Card gc;

		int iD;
		if(params.containsKey("sel_pickup")){
			iD=Integer.valueOf(params.get("sel_pickup")[0]);
			gc=Gratitude_Card.find.byId(iD);
			CaseStudy.setGC(gc);
		}else if(params.containsKey("del_pickup")){
			iD=Integer.valueOf(params.get("del_pickup")[0]);
			gc=Gratitude_Card.find.byId(iD);
			CaseStudy.delGC(gc);
		}
	}
 	public static Map<String, String> trans(Map<String, String[]> pos){
 		Map<String,String> post = new HashMap<>();

 		for(String str : name.keySet()){
 			if(pos.containsKey(str) && (pos.get(str).length != 0)){
 				post.put(str, pos.get(str)[0]);
 			}else{
 				post.put(str , name.get(str));
 			}
 		}


 		return post;
 	}


}