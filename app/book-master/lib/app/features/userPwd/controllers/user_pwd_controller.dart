import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/http/dao/user_update_dao.dart';
import 'package:daily_task/app/model/userinfo_model.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:daily_task/app/utils/string_util.dart';
import 'package:get/get.dart';

class UserPwdController extends GetxController {
  //TODO: Implement UserPwdController
  var profileMo = UserinfoData().obs;
  var protect = false.obs;
  var loginEnable = false.obs;
  late String oldPassword;
  // late String validateCode;
  late String newPassword;
  late String reNewPassword;

  void checkInput() {
    bool enable;
    if (isNotEmpty(oldPassword) &&
        // isNotEmpty(validateCode) &&
        isNotEmpty(newPassword) &&
        isNotEmpty(reNewPassword)) {
      enable = true;
    } else {
      enable = false;
    }
    this.loginEnable.value = enable;
  }

  void checkParams() {
    String? tips;
    if (newPassword != reNewPassword) {
      tips = "二次密码不一致";
      showWarnToast(tips);
      return;
    }
    send();
  }

  void send() async {
    try {
      var result = await UserUpdateDao.updatePassword(oldPassword,
          newPassword, reNewPassword);
      if (result["code"] == 200) {
        showToast("修改成功");
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
