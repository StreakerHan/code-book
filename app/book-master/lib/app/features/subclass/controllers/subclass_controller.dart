import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/http/dao/book_category_dao.dart';
import 'package:daily_task/app/http/dao/book_dao.dart';
import 'package:daily_task/app/model/book_category_list_model.dart';
import 'package:daily_task/app/model/book_list_model.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';

class SubclassController extends GetxController {

  var category = BookCategoryModel().obs;

  var categoryList = <BookCategoryModel>[].obs;

  getList() async {
    try {
      BookCategoryListModel result = await BookCategoryDao.get('2',category.value.id!, page: 1, size: 100);
      categoryList.value = result.data!;
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
    category.value = (Get.arguments as Map)["category"];
    print(category);
    getList();
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {
  }
}
