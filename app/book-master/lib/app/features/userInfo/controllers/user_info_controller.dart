import 'dart:async';

import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/http/dao/profile_dao.dart';
import 'package:daily_task/app/http/dao/user_update_dao.dart';
import 'package:daily_task/app/model/userinfo_model.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:daily_task/app/utils/event_bus.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';

class UserInfoController extends GetxController {
  //TODO: Implement UserInfoController
  ScrollController scrollController = ScrollController();
  var profileMo = Userinfo().obs;
  var userInfo = UserinfoData().obs;

  //声明，后面需要销毁
  StreamSubscription? event;

  Future<void> updateInfo(String slug, String value) async {
    try {
      var result = await UserUpdateDao.update(slug, value);
      if (result["code"] == 200) {
        loadData();
        showToast("修改成功");
      } else {
        showToast("修改失败");
      }
    } on NeedLogin catch (e) {
      showWarnToast(e.message);
    } on NeedAuth catch (e) {
      showWarnToast(e.message);
    }
  }

  Future<void> loadData() async {
    try {
      Userinfo result = await ProfileDao.get();
      if (result.code == 401) {
        showToast("当前未登录，请登录！");
      } else {
        print("______________________________" + result.msg.toString());
        profileMo.value = result;
        userInfo.value = profileMo.value.data!;
      }
    } on NeedAuth catch (e) {
      showToast(e.message);
    } on NeedLogin catch (e) {
      showToast(e.message);
    } on PinkNetError catch (e) {
      showToast(e.message);
    }
  }

  @override
  void onInit() {
    loadData();
    profileMo.value = (Get.arguments as Map)["profileMo"];
    userInfo.value = profileMo.value.data!;

    //通知监听
    event = EventBusUtils.getInstance()?.on().listen((event) {
      print(event);
      if (event["type"] == "user") {
        if(event['data'] == "refresh"){
          loadData();
        }
      }
    });
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {
    scrollController.dispose();
  }
}
