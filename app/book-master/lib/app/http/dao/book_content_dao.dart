import 'package:daily_task/app/config/core/pink_net.dart';
import 'package:daily_task/app/http/request/book_content_request.dart';
import 'package:daily_task/app/http/request/book_request.dart';
import 'package:daily_task/app/model/book_chapters_model.dart';

class BookContentDao {
  //无token
  static get(String index, String bookId, String href) async {
    BookContentRequest request = BookContentRequest();
    request.add("bookId", bookId).add("index", index).add("href", href);
    var result = await PinkNet.getInstance().fire(request);
    return BookChaptersListModel.fromJson(result);
  }

  //根据href获取内容
  static get1(String bookId, String href) async {
    BookContentHrefRequest request = BookContentHrefRequest();
    request.add("bookId", bookId).add("href", href);
    var result = await PinkNet.getInstance().fire(request);
    return BookChaptersListModel.fromJson(result);
  }

  //有token
  static get2(String index, String bookId, String href) async {
    BookContentTokenRequest request = BookContentTokenRequest();
    request.add("bookId", bookId).add("index", index).add("href", href);
    var result = await PinkNet.getInstance().fire(request);
    return BookChaptersListModel.fromJson(result);
  }
}
