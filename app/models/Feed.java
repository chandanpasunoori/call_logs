package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;
import play.data.format.Formats;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by chandan on 28/10/16.
 */
@Entity
public class Feed extends Model {
  private static Finder<Long, Feed> find = new Finder<>(Feed.class);

  @Id
  public UUID id;
  public String phone;
  public String email;
  public String imei;
  public String file;
  @Formats.DateTime(pattern = "yyyyMMdd")
  public Date added = new Date();

  public static List<Feed> getFeedsByPhone(Integer start, Integer end, String phone) {
    PagedList<Feed> pagedList = Feed.find.where().eq("phone", phone).order().desc("added").findPagedList(start, end);
    return pagedList.getList();
  }

  public static List<Feed> getFeedsByEmail(Integer start, Integer end, String email) {
    PagedList<Feed> pagedList = Feed.find.where().eq("email", email).order().desc("added").findPagedList(start, end);
    return pagedList.getList();
  }
}
