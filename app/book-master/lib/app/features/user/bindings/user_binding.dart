import 'package:daily_task/app/features/user/controllers/user_controller.dart';
import 'package:get/get.dart';

class UserBinding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<UserController>(
          () => UserController(),
    );
  }
}
