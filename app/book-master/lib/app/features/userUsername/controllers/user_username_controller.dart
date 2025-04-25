import 'package:daily_task/app/model/userinfo_model.dart';
import 'package:get/get.dart';

class UserUsernameController extends GetxController {
  //TODO: Implement UserUsernameController
  // var profileMo = Userinfo().obs;
  var userInfo = UserinfoData().obs;

  @override
  void onInit() {
    userInfo.value = (Get.arguments as Map)["userInfo"];
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {}
}
