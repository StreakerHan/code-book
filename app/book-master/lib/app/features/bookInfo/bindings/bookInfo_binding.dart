import 'package:daily_task/app/features/bookInfo/controllers/bookInfo_controller.dart';
import 'package:get/get.dart';

class BookInfoBinding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut(() => BookInfoController());
  }
}
