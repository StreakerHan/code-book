import 'package:daily_task/app/config/core/pink_net.dart';
import 'package:daily_task/app/http/request/book_request.dart';
import 'package:daily_task/app/model/book_list_model.dart';

import '../../model/book_info_model.dart';

class BookDao {
  static getRecommend() async {
    BookRecommendRequest request = BookRecommendRequest();
    var result = await PinkNet.getInstance().fire(request);
    return BookListModel.fromJson(result);
  }

  static getList(String name, String type, {int page = 1, size = 1000}) async {
    BookListRequest request = BookListRequest();
    request
        .add("name", name)
        .add("type", type)
        .add("pageNum", page)
        .add("pageSize", size);
    var result = await PinkNet.getInstance().fire(request);
    return BookListModel.fromJson(result);
  }

  static getInfo(String id) async {
    BookInfoRequest request = BookInfoRequest();
    request.add("id", id);
    var result = await PinkNet.getInstance().fire(request);
    return BookInfo.fromJson(result);
  }

  static getInfoToken(String id) async {
    BookInfoTokenRequest request = BookInfoTokenRequest();
    request.add("id", id);
    var result = await PinkNet.getInstance().fire(request);
    return BookInfo.fromJson(result);
  }

  static collect(String id) async {
    BookCollectRequest request = BookCollectRequest();
    request.add("id", id);
    var result = await PinkNet.getInstance().fire(request);
    return BookInfo.fromJson(result);
  }

  static collectList() async {
    MyBookCollectRequest request = MyBookCollectRequest();
    var result = await PinkNet.getInstance().fire(request);
    return BookListModel.fromJson(result);
  }

  static viewList() async {
    MyBookViewRequest request = MyBookViewRequest();
    var result = await PinkNet.getInstance().fire(request);
    return BookListModel.fromJson(result);
  }
}
