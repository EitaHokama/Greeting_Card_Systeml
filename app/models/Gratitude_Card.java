package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;
import com.avaje.ebean.common.BeanList;

import play.data.validation.Constraints.Required;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Gratitude_Card extends Model {
	@Id
	//@ManyToMany
	public Integer card_id;
	@ManyToOne
	@Required
	public Employees sender_id;
	@ManyToOne
	@Required
	public Employees receiver_id;
	@Required
	public Date date;
	@Required
	public String card_title;
	@Required
	public String card_content;
	@ManyToOne
	@Required
	public Category category_id;

	@ManyToMany
	public List<CaseStudy> cs = new BeanList<>();


	public static Find<Integer,Gratitude_Card> find = new Find<Integer,Gratitude_Card>(){};

	public List<String> split(){

		List<String> spr = Arrays.asList(card_content.split("\n",0));

		return spr;
	}
}
