import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/http/dao/book_dao.dart';
import 'package:daily_task/app/model/book_list_model.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';

class SearchController extends GetxController {
  var currentIndex = 0.obs;
  static int initialPage = 0;
  final PageController controller = PageController(initialPage: initialPage);
  late List<Widget> pages;
  bool hasBuild = false;

  TextEditingController textEditingController = TextEditingController();
  var bookData = <BookModel>[].obs;

  getBookList() async {
    try {
      BookListModel result = await BookDao.getList(textEditingController.text,"");
      bookData.value = result.rows!;
      return true;
    } on NeedAuth catch (e) {
      showWarnToast(e.message);
      return false;
    } on PinkNetError catch (e) {
      showWarnToast(e.message);
      return false;
    }
  }

  @override
  void onInit() {
    getBookList();
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {
    controller.dispose();
  }
}
