import 'package:daily_task/app/config/core/pink_net.dart';
import 'package:daily_task/app/http/request/book_category_request.dart';
import 'package:daily_task/app/model/book_category_list_model.dart';

class BookCategoryDao {
  static get(String level, String parentId, {int page = 1, size = 100}) async {
    BookCategoryRequest request = BookCategoryRequest();
    request
        .add("level", level)
        .add("parentId", parentId)
        .add("pageNum", page)
        .add("pageSize", size);
    var result = await PinkNet.getInstance().fire(request);
    return BookCategoryListModel.fromJson(result);
  }
}
