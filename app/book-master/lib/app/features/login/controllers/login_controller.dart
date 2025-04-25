import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/constans/strings.dart';
import 'package:daily_task/app/http/dao/login_dao.dart';
import 'package:daily_task/app/shared_components/custom_snack_bar.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:daily_task/app/utils/event_bus.dart';
import 'package:daily_task/app/utils/string_util.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';

class LoginController extends GetxController {
  //TODO: Implement LoginController
  var protect = false.obs;
  var loginEnable = false.obs;
  late String email = "";
  late String password = "";
  final passwordController = TextEditingController();

  void checkInput() {
    var enable;
    if (isNotEmpty(email) && isNotEmpty(password)) {
      enable = true;
    } else {
      enable = false;
    }
    loginEnable.value = enable;
  }

  void send() async {
    try {
      var result = await LoginDao.login(email, password);
      if (result["code"] == 200) {
        GetStorage().write(StringConst.Authorization, result['data']['token']);
        GetStorage().write("name", result['data']['user']['nickname']);
        GetStorage().write("avatar", result['data']['user']['avatar']);
        CustomSnackBar.showCustomSnackBar(title: (result['msg']), message: "开启你的阅读之旅！");
        Map avatar = {"type" : "avatar", "data" : result['data']['user']['avatar']};
        EventBusUtils.getInstance()?.fire(avatar);
        // 登录用户账号
        Get.offAllNamed("/index");
      } else {
        CustomSnackBar.showCustomErrorSnackBar(title: (result['msg']), message: '请检查用户名和密码是否正确！');
      }
    } on NeedAuth catch (e) {
      showWarnToast(e.toString());
    }
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
