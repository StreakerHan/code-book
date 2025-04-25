import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/http/dao/user_update_dao.dart';
import 'package:daily_task/app/model/userinfo_model.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:daily_task/app/utils/event_bus.dart';
import 'package:daily_task/app/utils/string_util.dart';
import 'package:get/get.dart';

class UserEmailController extends GetxController {
  //TODO: Implement UserEmailController
  var profileMo = UserinfoData().obs;
  var protect = false.obs;
  var loginEnable = false.obs;
  var newEmail = "".obs;
  late String validateCode;

  void checkInput() {
    bool enable;
    if (isNotEmpty(newEmail.value)) {
      enable = true;
    } else {
      enable = false;
    }
    this.loginEnable.value = enable;
  }

  void checkParams() {
    send();
  }

  void send() async {
    try {
      var result =
          await UserUpdateDao.update("email", newEmail.value);
      if (result["code"] == 200) {
        showToast("修改成功");
        Map user = {"type" : "user", "data" : "refresh"};
        EventBusUtils.getInstance()?.fire(user);
        Get.back();
      } else {
        showWarnToast(result['msg']);
      }
    } on NeedLogin catch (e) {}
  }

  @override
  void onInit() {
    profileMo.value = (Get.arguments as Map)["userInfo"];
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {}
}
