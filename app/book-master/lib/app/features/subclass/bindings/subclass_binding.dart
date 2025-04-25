import 'package:daily_task/app/features/subclass/controllers/subclass_controller.dart';
import 'package:get/get.dart';

class SubclassBinding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<SubclassController>(
          () => SubclassController(),
    );
  }
}
