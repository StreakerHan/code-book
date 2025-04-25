import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/constans/sizes.dart';
import 'package:daily_task/app/constans/styles.dart';
import 'package:daily_task/app/features/userEmail/views/components/login_button.dart';
import 'package:daily_task/app/features/userEmail/views/components/login_input.dart';
import 'package:daily_task/app/utils/event_bus.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controllers/user_email_controller.dart';

class UserEmailView extends GetView<UserEmailController> {

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
          "修改邮箱",
          style: Styles.customTitleTextStyle(
            color: AppColors.headingText,
            fontWeight: FontWeight.w600,
            fontSize: Sizes.TEXT_SIZE_20,
          ),
        ),
      ),
      body: Container(
        color: Color.fromRGBO(242, 242, 242, 1),
        child: Column(
          children: [
            _content(context),
          ],
        ),
      ),
    );
  }

  Widget _content(context) {
    return Expanded(
        child: Container(
            margin: EdgeInsets.only(top: kSpacing),
            color: Colors.white,
            child: MediaQuery.removePadding(
              removeTop: true,
              removeBottom: true,
              context: context,
              child: ListView(
                children: [
                  LoginInput(
                      colors: Colors.black,
                      title: "邮箱",
                      hint: "请输入邮箱地址",
                      autofocus: false,
                      onChanged: (text) {
                        controller.newEmail.value = text;
                        controller.checkInput();
                      },
                      focusChanged: (text) {}),
                  Padding(
                    padding: EdgeInsets.only(top: 20, left: 20, right: 20),
                    child: Obx(() => LoginButton(
                          "确认修改",
                          color: Colors.black54,
                          enable: controller.loginEnable.value,
                          onPressed: controller.checkParams,
                        )),
                  )
                ],
              ),
            )));
  }
}
