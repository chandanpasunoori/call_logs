/**
 * Created by chandan on 28/10/16.
 */
$(document).ready(function () {
  var win = $(window);
  // Each time the user scrolls

  var page = 0;
  var size = 50;
  var feedno = 1;
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
      url: '/getfeeds/' + page++ + "/" + size,
      dataType: 'html',
      success: function (data) {

        var feeds = JSON.parse(data);

        feeds.forEach(function (feed) {
          // console.log(JSON.stringify(feed));
          $('#feeds').append(
            '<tr>' +
            '<td>' + feedno++ + '</td>' +
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
  }

});