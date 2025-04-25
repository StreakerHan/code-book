import 'dart:io';

import 'package:adaptive_dialog/adaptive_dialog.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/constans/sizes.dart';
import 'package:daily_task/app/constans/styles.dart';
import 'package:daily_task/app/http/dao/upload_dao.dart';
import 'package:daily_task/app/shared_components/custom_snack_bar.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:daily_task/app/utils/event_bus.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:photo_manager/photo_manager.dart';
import 'package:wechat_assets_picker/wechat_assets_picker.dart';

import '../controllers/user_info_controller.dart';
import 'components/image_picker.dart';
import 'components/upload_mo.dart';

class UserInfoView extends GetView<UserInfoController> {
  @override
  Widget build(BuildContext context) {
    Get.put(UserInfoController());
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
            "个人信息",
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
        ));
  }

  _content(context) {
    return Expanded(
      child: Container(
        margin: EdgeInsets.only(top: 10),
        child: Column(
          children: [
            Divider(
              height: 0.5,
            ),
            settingItemButton(
              () async {
                var type =
                    await showModalActionSheet(context: context, actions: [
                  SheetAction(label: "从相册选择", key: 0),
                  SheetAction(label: "拍照选择", key: 1),
                ]);
                File? file;
                if (type == 0) {
                  List<AssetEntity>? fileList =
                      await getImagePicker(context, maxAssets: 1);
                  file = await fileList![0].file;
                } else if (type == 1) {
                  AssetEntity? fileList = await getImageCamera(context);
                  file = await fileList!.file;
                }
                if (file == null) return;
                UplaodMo url = await (UploadDao()).uploadImg(file);
                controller.userInfo.update((val) {
                  val!.avatar = url.data;
                });
                controller.updateInfo(
                    "avatar", controller.userInfo.value.avatar);
              },
              "头像",
              Obx(() => ClipRRect(
                    borderRadius: BorderRadius.circular(60),
                    child: cachedImage(controller.userInfo.value.avatar,
                        width: 60, height: 60),
                  )),
            ),
            settingItemButton(
              () {
                Get.toNamed("userUsername",
                    arguments: {"userInfo": controller.userInfo.value});
              },
              "用户名称",
              Obx(() => Text(
                    controller.userInfo.value.nickname!,
                    style: TextStyle(fontSize: 12, color: Colors.grey),
                  )),
            ),
            settingItemButton(
              () async {
                var text = (await showModalActionSheet(
                    context: context,
                    actions: [
                      SheetAction(label: "男", key: "1"),
                      SheetAction(label: "女", key: "0")
                    ]))!;
                controller.userInfo.update((val) {
                  val!.sex = text;
                });
                controller.updateInfo(
                    "sex", controller.userInfo.value.sex.toString());
              },
              "性别",
              Obx(() => Text(
                    controller.userInfo.value.sex == "0" ? "女" : "男",
                    style: TextStyle(fontSize: 12, color: Colors.grey),
                  )),
            ),
            settingItemButton(
              () async {
                var text = await showDatePicker(
                    cancelText: "关闭",
                    confirmText: "确定",
                    context: context,
                    initialDate: DateTime(
                        int.parse(controller.userInfo.value.birth == null
                            ? "2000"
                            : controller.userInfo.value.birth
                                .toString()
                                .substring(0, 4)),
                        int.parse(controller.userInfo.value.birth == null
                            ? "01"
                            : controller.userInfo.value.birth
                                .toString()
                                .substring(5, 7)),
                        int.parse(controller.userInfo.value.birth == null
                            ? "01"
                            : controller.userInfo.value.birth
                                .toString()
                                .substring(8, 10))),
                    firstDate: DateTime(1900),
                    lastDate: DateTime(2100));
                if (text != null) {
                  controller.userInfo.update((val) {
                    val!.birth = text.toString();
                  });
                  controller.updateInfo(
                      "birth",
                      controller.userInfo.value.birth
                          .toString()
                          .substring(0, 10));
                }
              },
              "出生年月",
              Obx(() => Text(
                    controller.userInfo.value.birth != null &&
                            controller.userInfo.value.birth != ""
                        ? controller.userInfo.value.birth
                            .toString()
                            .substring(0, 10)
                        : "",
                    style: TextStyle(fontSize: 12, color: Colors.grey),
                  )),
            ),
            settingItemButton(() {
              Get.toNamed("userPwd",
                  arguments: {"userInfo": controller.userInfo.value});
            }, "修改密码", Container()),
            settingItemButton(
              () {
                Get.toNamed("userEmail",
                    arguments: {"userInfo": controller.userInfo.value});
              },
              "邮箱",
              Obx(() => Text(
                    controller.userInfo.value.email ?? "",
                    style: TextStyle(fontSize: 12, color: Colors.grey),
                  )),
            ),
            SizedBox(
              height: kSpacing / 2,
            ),
            Divider(
              height: 0.5,
            ),
            // settingItemButton(() {
            //   ClipboardTool.setDataToast(
            //       controller.userInfo.value.id!);
            // },
            //     "UID",
            //     Text(
            //       "${controller.userInfo.value.id!}",
            //       style: TextStyle(fontSize: 12, color: Colors.grey),
            //     ),
            //     isShowIcon: false),
            // settingItemButton(() {
            //   Get.toNamed("/user-qrcode",
            //       arguments: {"userInfo": controller.userInfo.value});
            // }, "二维码名片", Container()),
            settingItemButton(
              () {
                Clipboard.setData(
                    ClipboardData(text: controller.userInfo.value.inviteCode));
                CustomSnackBar.showCustomSnackBar(title: "复制成功！", message: "快去发送给好朋友吧！");
              },
              "我的邀请码",
              Obx(() => Text(
                    controller.userInfo.value.inviteCode!,
                    style: TextStyle(fontSize: 12, color: Colors.grey),
                  )),
            ),
          ],
        ),
      ),
    );
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
                    style: TextStyle(fontSize: 12),
                  ),
                  Row(
                    children: [
                      text,
                      isShowIcon
                          ? SizedBox(width: kSpacing / 4)
                          : Container(height: 60),
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

  /// 带缓存的image
  Widget cachedImage(String url,
      {double width = 300,
      double height = 250,
      String img = 'assets/icon/default.png'}) {
    return CachedNetworkImage(
        height: height,
        width: width,
        fit: BoxFit.cover,
        placeholder: (BuildContext context, String url) => Image.asset(
              img,
              height: height,
              width: width,
              fit: BoxFit.cover,
            ),
        errorWidget: (
          BuildContext context,
          String url,
          dynamic error,
        ) =>
            Image.asset(
              img,
              height: height,
              width: width,
              fit: BoxFit.cover,
            ),
        imageUrl:
            // url.startsWith('https://') || url.startsWith('http://')
            //     ? url
            //     : PinkConstants.ossDomain + '/' + url);
            url != null && url != ""
                ? url
                : 'https://cdn.nlark.com/yuque/0/2022/png/21561452/1669961162759-3c7f205a-a3a1-458d-a4a0-5084d9fa948b.png?x-oss-process=image%2Fresize%2Cw_750%2Climit_0');
  }
}
