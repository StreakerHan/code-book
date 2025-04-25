import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/constans/sizes.dart';
import 'package:daily_task/app/constans/styles.dart';
import 'package:daily_task/app/features/userUsername/views/components/user_textarea.dart';
import 'package:daily_task/app/http/dao/user_update_dao.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:daily_task/app/utils/event_bus.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controllers/user_username_controller.dart';

class UserUsernameView extends GetView<UserUsernameController> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.white,
          elevation: 0.0,
          leading: InkWell(
            onTap: () {
              Map user = {"type" : "user", "data" : "refresh"};
              EventBusUtils.getInstance()?.fire(user);
              Get.back();
            },
            child: Image.asset(
              ImageRasterPath.arrowBackIcon,
              color: AppColors.headingText,
            ),
          ),
          centerTitle: true,
          title: Text(
            "修改用户名称",
            style: Styles.customTitleTextStyle(
              color: AppColors.headingText,
              fontWeight: FontWeight.w600,
              fontSize: Sizes.TEXT_SIZE_20,
            ),
          ),
          actions: <Widget>[
            InkWell(
                onTap: () async {
                  try {
                    var result = await UserUpdateDao.update(
                        "nickname", controller.userInfo.value.nickname!);
                    if (result["code"] == 200) {
                      showToast("修改成功");
                      Map user = {"type" : "user", "data" : "refresh"};
                      EventBusUtils.getInstance()?.fire(user);
                      Get.back();
                    } else {
                      showToast("修改失败");
                    }
                  } on NeedLogin catch (e) {
                    showWarnToast(e.message);
                  } on NeedAuth catch (e) {
                    showWarnToast(e.message);
                  }
                },
                child: Padding(
                  padding: EdgeInsets.only(right: 22,top: 15),
                  child: Text(
                    "保存",
                    style: TextStyle(color: Colors.black54, fontSize: 14),
                  ),
                ))
          ],
        ),
        body: Container(
          color: Color.fromRGBO(242, 242, 242, 1),
          child: Column(
            children: [
              _content(),
            ],
          ),
        ));
  }

  _content() {
    return UserTextarea(
        controller: TextEditingController.fromValue(TextEditingValue(
            text: controller.userInfo.value.nickname!, //判断keyword是否为空
            // 保持光标在最后
            selection: TextSelection.fromPosition(TextPosition(
                affinity: TextAffinity.downstream,
                offset: controller.userInfo.value.nickname!.length)))),
        onChanged: (value) {
          controller.userInfo.value.nickname = value;
        },
        size: 12);
  }
}
