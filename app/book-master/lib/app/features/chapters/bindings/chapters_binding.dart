import 'package:daily_task/app/features/chapters/controller/chapters_controller.dart';
import 'package:get/get.dart';


class ChaptersBinding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<ChaptersController>(
          () => ChaptersController(),
    );
  }
}
