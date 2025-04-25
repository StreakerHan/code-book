import 'package:daily_task/app/features/bookContent/controllers/bookContent_controller.dart';
import 'package:get/get.dart';


class BookContentBinding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<BookContentController>(
          () => BookContentController(),
    );
  }
}
