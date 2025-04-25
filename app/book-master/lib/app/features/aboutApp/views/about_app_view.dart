import 'package:adaptive_dialog/adaptive_dialog.dart';
import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/constans/styles.dart';
import 'package:daily_task/app/utils/clipboard_tool.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../../constans/sizes.dart';
import '../controllers/about_app_controller.dart';

class AboutAppView extends GetView<AboutAppController> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar:AppBar(
        backgroundColor: Colors.white,
        elevation: 0.0,
        leading: InkWell(
          onTap: () {
            Get.back();
          },
          child: Image.asset(
            ImageRasterPath.arrowBackIcon,
            color: AppColors.headingText,
          ),
        ),
        centerTitle: true,
        title: Text(
          "关于猿书网",
          style: Styles.customTitleTextStyle(
            color: AppColors.headingText,
            fontWeight: FontWeight.w600,
            fontSize: Sizes.TEXT_SIZE_20,
          ),
        ),
      ),
      body: Container(
        color: Colors.grey[100],
        child: Column(
          children: [
            SizedBox(height: kSpacing,),
            ..._content(context)
          ],
        ),
      ),
    );
  }

  List<Widget> _content(BuildContext context) {
    return [
      Container(
        width: 100,
        margin: EdgeInsets.all(20),
        child: Image.asset("assets/images/4646842.png"),
      ),
      settingItemButton(() {
        ClipboardTool.setDataToast(controller.version.value);
      },
          "App版本",
          Obx(() => Text(
                controller.version.value,
                style: TextStyle(fontSize: 18, color: Colors.grey),
              )),
          isShowIcon: false),
      settingItemButton(() {
        ClipboardTool.setDataToast(controller.appName.value);
      },
          "App名称",
          Text(
            "猿书网",
            style: TextStyle(fontSize: 18, color: Colors.grey),
          ),
          isShowIcon: false),
      settingItemButton(() async {
        final result = await showOkCancelAlertDialog(
          context: context,
          title: '提示',
          message: '确认删除关于此账号的一切信息？',
          cancelLabel: '取消',
          okLabel: '确认',
          fullyCapitalizedForMaterial: false,
        );
        print(result.name.toString());
        if (result.name.toString() == "ok") {
          controller.remove();
        }
      },
          "注销账号",
          Text(""),
          isShowIcon: true),
    ];
  }

  Widget settingItemButton(GestureTapCallback onTap, String title, Widget text,
      {isShowIcon = true}) {
    return Column(
      children: [
        Material(
          color: Colors.white,
          child: InkWell(
            onTap: onTap,
            child: Container(
              padding: EdgeInsets.only(left: 15, right: 10, top: 8, bottom: 8),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    title,
                    style: TextStyle(fontSize: 15),
                  ),
                  Row(
                    children: [
                      text,
                      isShowIcon
                          ? SizedBox(width: 3,)
                          : Container(
                        height: 40,
                      ),
                      isShowIcon
                          ? Icon(
                        Icons.keyboard_arrow_right,
                        color: Colors.grey,
                      )
                          : Container()
                    ],
                  )
                ],
              ),
            ),
          ),
        ),
        Divider(
          height: 0.5,
        ),
      ],
    );
  }
}
