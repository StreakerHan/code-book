import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/http/dao/login_dao.dart';
import 'package:daily_task/app/shared_components/custom_snack_bar.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:daily_task/app/utils/string_util.dart';
import 'package:get/get.dart';

class RegController extends GetxController {
  //TODO: Implement RegController
  var protect = false.obs;
  var loginEnable = false.obs;
  var userName = "".obs;
  var email= "".obs;
  var validateCode= "".obs;
  var password= "".obs;
  var rePassword= "".obs;

  void checkInput() {
    bool enable;
    if (isNotEmpty(userName.value) &&
        isNotEmpty(password.value) &&
        isNotEmpty(rePassword.value)) {
      enable = true;
    } else {
      enable = false;
    }
    this.loginEnable.value = enable;
  }

  void send() async {
    try {
      print("注册开始");
      var result = await LoginDao.registration(
          userName.value, email.value, validateCode.value, password.value, rePassword.value);
      if (result["code"] == 200) {
        CustomSnackBar.showCustomSnackBar(title: "注册成功，请登录",message: "欢迎您成为【猿书网】的读者。");
        Get.offAllNamed("/login");
      } else {
        showWarnToast(result['msg']);
      }
    } on NeedLogin catch (e) {}
  }

  void checkParams() {
    // String? tips;
    // if (password.toString().length < 6) {
    //   tips = "密码长度不能少于6位";
    //   showWarnToast(tips);
    //   return;
    // }
    // if (password != rePassword) {
    //   tips = "两次密码不一致";
    //   showWarnToast(tips);
    //   return;
    // }
    // send();
  }

  @override
  void onInit() {
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {}
}
