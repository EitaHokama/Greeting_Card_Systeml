package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;

@Entity
 public class Test extends Model {

    @Id
    public Integer id;

    public String name;
}