import 'package:daily_task/app/config/core/pink_net.dart';
import 'package:daily_task/app/http/request/book_chapters_request.dart';
import 'package:daily_task/app/model/book_chapters_model.dart';

class BookChaptersDao {
  static get(String bookId) async {
    BookChaptersRequest request = BookChaptersRequest();
    request
        .add("bookId", bookId);
    var result = await PinkNet.getInstance().fire(request);
    return BookChaptersListModel.fromJson(result);
  }
}
