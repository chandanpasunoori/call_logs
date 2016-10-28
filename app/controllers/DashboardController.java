package controllers;

import models.Feed;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
    String is_logged = session("is_logged");
    if (is_logged != null && Objects.equals(is_logged, "true")) {
      return ok(views.html.dashboard.render());
    } else {
      return redirect(routes.LoginController.loginGet());
    }
  }

  public Result getFeeds(Integer start, Integer end) {
    String is_logged = session("is_logged");
    if (is_logged != null && Objects.equals(is_logged, "true")) {
      String phone = session("phone");
      if (phone != null) {
        List<Feed> feeds = Feed.getFeedsByPhone(start, end, phone);
        return ok(toJson(feeds));
      }
    }
    return ok(toJson(Collections.singletonList("")));
  }

  public Result viewFeed(String feedId) {
    String phone = session("phone");
    Path path = Paths.get(conf.getString("documents_store"), phone, feedId + ".log");
    if (Files.exists(path)) {
      try {
        List<String> strings = Files.readAllLines(path);
        return ok(views.html.model.render(strings));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return ok(views.html.model.render(Arrays.asList("", "")));
  }

  public Result addSampleData(Integer no, String phone, String email, String imei) {
    for (Integer i = 0; i < no; i++) {
      Feed feed = new Feed();
      feed.id = UUID.randomUUID();
      feed.imei = imei;
      feed.email = email;
      feed.phone = phone;
      feed.file = Paths.get(conf.getString("documents_store"), phone, feed.id.toString() + ".log").toString();
      feed.save();
      Path directory = Paths.get(conf.getString("documents_store"), phone);
      Path file_name = Paths.get(directory.toString(), feed.id.toString() + ".log");
      try {
        Files.createDirectories(directory);
        Files.createFile(file_name);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return ok();
  }
}
