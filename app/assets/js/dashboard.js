/**
 * Created by chandan on 28/10/16.
 */
$(document).ready(function () {
  var win = $(window);
  // Each time the user scrolls

  var start = 0;
  var end = 20;
  load_more();
  win.scroll(function () {
    // End of the document reached?
    if ($(document).height() - win.height() == win.scrollTop()) {
      load_more();
    }
  });

  function load_more() {
    $('#loading').show();
    $.ajax({
      url: '/getfeeds/' + start + "/" + end,
      dataType: 'html',
      success: function (data) {

        var feeds = JSON.parse(data);

        feeds.forEach(function (feed) {
          console.log(JSON.stringify(feed));
          $('#feeds').append(
            '<tr>' +
            '<td>' + feed.phone + '</td>' +
            '<td>' + feed.imei + '</td>' +
            '<td>' + feed.file + '</td>' +
            '<td>' + new Date(feed.added) + '</td>' +
            '<td><a data-toggle="modal" data-target="#file_model" data-remote="/viewFeed/' + feed.id + '" id="' + feed.id + '">Open</a></td>' +
            '</tr>'
          );
        });
        $('#loading').hide();
      }
    });
    start = end;
    end += 20;
  }

});