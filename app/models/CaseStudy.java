package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;
import com.avaje.ebean.common.BeanList;

import play.data.validation.Constraints.Required;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class CaseStudy extends Model {
	@Id
	public Integer case_id;
	@Required
	public Integer year;
	@Required
	public Integer month;

	@ManyToMany(mappedBy="cs")
    public List<Gratitude_Card> gc = new BeanList<>();

    public static Find<Integer,CaseStudy> find = new Find<Integer,CaseStudy>(){};

    public static CaseStudy selCase(int year , int month){

    	CaseStudy cs = CaseStudy.find.where().eq("year", year).eq("month", month).findUnique();
    	if(cs==null){
    		cs = new CaseStudy();
    		cs.year = year;
    		cs.month = month;
    		cs.save();
    	}
    	return cs;
    }
    public static CaseStudy selCase(){
    	Calendar c = Calendar.getInstance();
    	c.setTime(new Date());
    	int year = c.get(Calendar.YEAR);
    	int month = c.get(Calendar.MONTH);
    	month++;
    	CaseStudy cs = selCase(year, month);

    	return cs;
    }
    public static CaseStudy lastCase(){
    	Calendar c = Calendar.getInstance();
    	c.setTime(new Date());
    	c.add(Calendar.MONTH, -1);
    	int year = c.get(Calendar.YEAR);
    	int month = c.get(Calendar.MONTH);
    	month++;
    	CaseStudy cs = selCase(year, month);

    	return cs;
    }

    public static void setGC(Gratitude_Card gc){
    	CaseStudy cs = selCase();
    	if(!cs.gc.contains(gc)){//gcが含まれていなければ
    		cs.gc.add(gc);

    		cs.save();

    	}
    	return;
    }
    public static void delGC(Gratitude_Card gc){
    	CaseStudy cs = selCase();
    	while(cs.gc.remove(gc));
    	cs.save();
    	return;
    }
}
