import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/features/chapters/views/components/chapters_data.dart';
import 'package:daily_task/app/http/dao/book_chapters_dao.dart';
import 'package:daily_task/app/model/book_chapters_model.dart';
import 'package:daily_task/app/model/book_list_model.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:get/get.dart';
import 'package:scroll_to_index/scroll_to_index.dart';

class ChaptersController extends GetxController {
  var bookInfo = BookModel().obs;

  var bookChapters = <BookChapterModel>[].obs;

  var bookChaptersData = <DataModel>[].obs;

  var autoScrollController = AutoScrollController();

  getBookChapterList() async {
    try {
      BookChaptersListModel result =
          await BookChaptersDao.get(bookInfo.value.id!);
      bookChapters.value = result.rows!;
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
    bookInfo.value = (Get.arguments as Map)["bookInfo"];
    getBookChapterList();
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {
    super.onClose();
  }
}
