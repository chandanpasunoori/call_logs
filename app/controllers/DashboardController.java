package controllers;

import models.Feed;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by chandan on 28/10/16.
 */
public class DashboardController extends Controller {
  private final Configuration conf;

  @Inject
  public DashboardController(Configuration conf) {

    this.conf = conf;
  }

  public Result dashboard() {
    return ok(views.html.dashboard.render());
  }

  public Result getFeeds(Integer start, Integer end) {
    List<Feed> feeds = Feed.getFeedsByPhone(start, end, "9550275292");
    return ok(toJson(feeds));
  }

  public Result viewFeed(String feedId) {
    return ok(views.html.model.render(feedId));
  }

  public Result addSampleData(Integer no, String phone, String imei) {
    for (Integer i = 0; i < no; i++) {
      Feed feed = new Feed();
      feed.imei = imei;
      feed.phone = phone;
      feed.file = conf.getString("documents_store") + "/" + phone + "/" + "call_log_" + i + ".log";
      feed.save();
    }
    return ok();
  }
}
