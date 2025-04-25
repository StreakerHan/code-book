import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/constans/sizes.dart';
import 'package:daily_task/app/constans/styles.dart';
import 'package:daily_task/app/features/userEmail/views/components/login_button.dart';
import 'package:daily_task/app/features/userEmail/views/components/login_input.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controllers/user_pwd_controller.dart';

class UserPwdView extends GetView<UserPwdController> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0.0,
        leading: InkWell(
          onTap: () => Get.back(),
          child: Image.asset(
            ImageRasterPath.arrowBackIcon,
            color: AppColors.headingText,
          ),
        ),
        centerTitle: true,
        title: Text(
          "修改密码",
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
          child: MediaQuery.removePadding(
              removeTop: true,
              removeBottom: true,
              context: context,
              child: ListView(
                children: [
                  LoginInput(
                      colors: Colors.black54,
                      title: "旧密码",
                      hint: "请输入旧密码",
                      obscureText: true,
                      onChanged: (text) {
                        controller.oldPassword = text;
                        controller.checkInput();
                      },
                      focusChanged: (text) {
                        controller.protect.value = text;
                      }),
                  LoginInput(
                      colors: Colors.black54,
                      title: "新密码",
                      hint: "请输入新密码",
                      obscureText: true,
                      onChanged: (text) {
                        controller.newPassword = text;
                        controller.checkInput();
                      },
                      focusChanged: (text) {
                        controller.protect.value = text;
                      }),
                  LoginInput(
                      colors: Colors.black54,
                      title: "确认密码",
                      hint: "请再次输入新密码",
                      obscureText: true,
                      onChanged: (text) {
                        controller.reNewPassword = text;
                        controller.checkInput();
                      },
                      focusChanged: (text) {
                        controller.protect.value = text;
                      }),
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
              ))),
    );
  }
}
