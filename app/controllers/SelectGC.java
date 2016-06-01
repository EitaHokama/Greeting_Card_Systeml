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
public class SelectGC {

	private Map<String, String[]> params;
	public static final Map<String, String> name = new HashMap<String, String>(){
		{
			put("sender_department", "送信部署");
			put("sender", "送信者");
			put("receiver_department", "受信部署");
			put("receiver","受信者");
			put("date", "日付");
			put("title", "タイトル");
			put("category", "カテゴリ");
			put("date_range","日付範囲");
			put("pickup","すべて");
			put("selected", "選択済");
			put("removed", "未選択");
			put("start_date","");
			put("end_date","");
		}
	};

	SelectGC(Map<String, String[]> params){
		this.params = params;
	}

	public List<Gratitude_Card> find(){
		List<Gratitude_Card> gc;
		ExpressionList<Gratitude_Card> gcWhere;


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
		gcWhere=makeWhere();
		gcWhere=findCategory(gcWhere);
		gcWhere=findDepartment(gcWhere,"send");//send or rec
		gcWhere=findDepartment(gcWhere,"rec");
		gcWhere=findEmployees(gcWhere,"send");
		gcWhere=findEmployees(gcWhere,"rec");
		gcWhere=startDate(gcWhere);
		gcWhere=endDate(gcWhere);
		gcWhere=findCS(gcWhere);

		gc=sortDate(gcWhere);

		return gc;
	}
	public List<Gratitude_Card> findRec(Employees emp){
		List<Gratitude_Card> gc;
		ExpressionList<Gratitude_Card> gcWhere;

		gcWhere=makeWhere();
		gcWhere=findCategory(gcWhere);
		gcWhere=findDepartment(gcWhere,"send");//send or rec
		gcWhere=findEmployees(gcWhere,"send");
		gcWhere=gcWhere.eq("receiver_id.name" , emp.name);
		gcWhere=startDate(gcWhere);
		gcWhere=endDate(gcWhere);
		//gcWhere=findCS(gcWhere);
		gc=sortDate(gcWhere);

		return gc;
	}
	public List<Gratitude_Card> findSend(Employees emp){
		List<Gratitude_Card> gc;
		ExpressionList<Gratitude_Card> gcWhere;

		gcWhere=makeWhere();
		gcWhere=findCategory(gcWhere);
		gcWhere=findDepartment(gcWhere,"rec");//send or rec
		gcWhere=findEmployees(gcWhere,"rec");
		gcWhere=gcWhere.eq("sender_id.name" , emp.name);
		gcWhere=startDate(gcWhere);
		gcWhere=endDate(gcWhere);
		//gcWhere=findCS(gcWhere);
		gc=sortDate(gcWhere);

		return gc;
	}
	public List<Gratitude_Card> findBBS(){
		List<Gratitude_Card> gc;
		ExpressionList<Gratitude_Card> gcWhere;

		gcWhere=makeWhere();
		gcWhere=findCategory(gcWhere);
		gcWhere=findDepartment(gcWhere,"send");//send or rec
		gcWhere=findDepartment(gcWhere,"rec");
		gcWhere=findEmployees(gcWhere,"send");
		gcWhere=findEmployees(gcWhere,"rec");
		gcWhere=startDate(gcWhere);
		gcWhere=endDate(gcWhere);

		gc=sortDate(gcWhere);

		return gc;
	}

	private ExpressionList<Gratitude_Card> makeWhere(){
		ExpressionList<Gratitude_Card> gcWhere = Gratitude_Card.find.where();
		gcWhere=
				Ebean
				.find(Gratitude_Card.class)
					.fetch("sender_id")
						.fetch("sender_id.department_id")
					.fetch("receiver_id")
						.fetch("receiver_id.department_id")
					.fetch("category_id")
					.fetch("cs")
				.where();
		return gcWhere;
	}


	public List<Gratitude_Card> sortDate(ExpressionList<Gratitude_Card> gc){

		return gc.orderBy("date DESC").findList();
	}


	private ExpressionList<Gratitude_Card> findCategory(ExpressionList<Gratitude_Card> gc){
		String buf;
		if(params.get("category") == null){
			buf = name.get("category");
		}else{
			buf=params.get("category")[0];
		}
		if(name.get("category").equals(buf)){
			return gc;
		}else{
			List<Category> cat
			= Category.find.where().
			eq("category_name", buf).findList();
			return gc.eq("category_id", cat.get(0));
		}



	}

	private ExpressionList<Gratitude_Card> findDepartment(ExpressionList<Gratitude_Card> gc,String mode){
		String modeSQL;
		if("send".equals(mode)){
			mode="sender_department";
			modeSQL= "sender_id";
			//return gc.eq("card_title","カード1");
		}else if("rec".equals(mode)){
			mode="receiver_department";
			modeSQL= "receiver_id";
		}else{//モードが検出できなければそのままスルー
			return gc.eq("card_title","カード1");
		}
		String buf;
		if(params.get(mode) == null){
			buf = name.get(mode);
		}else{
			buf=params.get(mode)[0];
		}
		if(name.get(mode).equals(buf)){
			return gc;
		}else{
			gc.eq(modeSQL+".department_id.department_name" ,buf);


			return gc;
		}



	}
	private ExpressionList<Gratitude_Card> findEmployees(ExpressionList<Gratitude_Card> gc,String mode){
		String modeSQL;
		if("send".equals(mode)){
			mode="sender";
			modeSQL= "sender_id";
			//return gc.eq("card_title","カード1");
		}else if("rec".equals(mode)){
			mode="receiver";
			modeSQL= "receiver_id";
		}else{//モードが検出できなければそのままスルー
			return gc;
		}
		String buf;
		if(params.get(mode) == null){
			buf = name.get(mode);
		}else{
			buf=params.get(mode)[0];
		}
		if(name.get(mode).equals(buf)){
			return gc;
		}else{
			gc.eq(modeSQL+".name" ,buf);


			return gc;
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
 	public static HashMap lastMonth(HashMap map){
 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 		Date d = new Date();
 		Calendar c=Calendar.getInstance();
 		c.setTime(d);
 		c.add(Calendar.MONTH, -1);
 		d= c.getTime();
 		String[] str =new String[1];
 		String[] str2 =new String[1];

 		str[0]=sdf.format(d);

 		str2[0]="";
 		map.remove("start_date");
 		map.put("start_date", str);

 		map.put("end_date", str2);
		return map;
 	}

	public static HashMap thisYear(HashMap map) {
		// TODO 自動生成されたメソッド・スタブ
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 		Date d = new Date();
 		Calendar c=Calendar.getInstance();
 		c.setTime(d);
 		Calendar cr=Calendar.getInstance();
 		cr.set(c.get(Calendar.YEAR), 0, 1, 0, 0, 0);
 		d= cr.getTime();
 		String[] str =new String[1];
 		String[] str2 =new String[1];

 		str[0]=sdf.format(d);
 		cr.add(Calendar.YEAR, 1);
 		cr.add(Calendar.DAY_OF_MONTH, -1);
 		d=cr.getTime();

 		str2[0]=sdf.format(d);
 		map.remove("start_date");
 		map.put("start_date", str);

 		map.put("end_date", str2);
		return map;

	}

	public static HashMap ThisMonth(HashMap map) {
		// TODO 自動生成されたメソッド・スタブ
 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 		Date d = new Date();
 		Calendar c=Calendar.getInstance();
 		c.setTime(d);
 		Calendar cr=Calendar.getInstance();
 		cr.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
 		d= cr.getTime();
 		String[] str =new String[1];
 		String[] str2 =new String[1];

 		str[0]=sdf.format(d);
 		cr.add(Calendar.MONTH, 1);
 		cr.add(Calendar.DAY_OF_MONTH, -1);
 		d=cr.getTime();

 		str2[0]=sdf.format(d);
 		map.remove("start_date");
 		map.put("start_date", str);

 		map.put("end_date", str2);
		return map;
	}

}