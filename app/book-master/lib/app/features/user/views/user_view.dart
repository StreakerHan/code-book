import 'dart:math';

import 'package:cached_network_image/cached_network_image.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/features/user/controllers/user_controller.dart';
import 'package:daily_task/app/features/user/views/about_panda_page.dart';
import 'package:daily_task/app/shared_components/custom_snack_bar.dart';
import 'package:daily_task/app/utils/event_bus.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';
import 'package:hexcolor/hexcolor.dart';

import '../../../constans/strings.dart';

class UserView extends GetView<UserController> {
  @override
  Widget build(BuildContext context) {
    Get.put(UserController());
    return Scaffold(
      appBar: PreferredSize(
          child: AppBar(
            backgroundColor: AppColors.meBgColor,
            elevation: 0.0,
          ),
          preferredSize: Size.fromHeight(10)),
      body: SafeArea(
        child: SingleChildScrollView(
          child: Column(
            children: <Widget>[
              _headView(),
              Stack(
                children: <Widget>[
                  Container(
                    width: double.infinity,
                    height: 100,
                    color: AppColors.meBgColor,
                  ),
                  Padding(
                    padding: EdgeInsets.fromLTRB(15, 0, 15, 0),
                    child: Image.asset(
                      "assets/icons/icon_me_vip_bg.png",
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.fromLTRB(30, 15, 30, 15),
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: <Widget>[
                        Image.asset(
                          "assets/icons/icon_me_vip.png",
                          width: 18,
                          height: 18,
                        ),
                        SizedBox(
                          width: 5,
                        ),
                        Text(
                          "欢迎使用猿书网",
                          style: TextStyle(
                              color: AppColors.meTextColor, fontSize: 14),
                        ),
                        Expanded(child: Container()),
                        GestureDetector(
                          onTap: () => {Get.offNamed("index")},
                          child: Text(
                            "万份资料免费读",
                            style: TextStyle(
                                color: AppColors.meTextColor, fontSize: 14),
                          ),
                        ),
                        SizedBox(
                          width: 5,
                        ),
                        Image.asset(
                          "assets/icons/icon_me_vip_right_arrow.png",
                          width: 16,
                          height: 16,
                          color: AppColors.meTextColor,
                        ),
                      ],
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.fromLTRB(0, 50, 0, 0),
                    color: AppColors.white,
                    child: Column(
                      children: <Widget>[
                        _childView(
                          "assets/icons/icon_me_account.png",
                          "账户信息",
                          "修改个人资料",
                          true,
                        ),
                        // _childView(
                        //   "assets/icons/icon_me_task.png",
                        //   "我的任务",
                        //   "绑定手机送礼券",
                        //   false,
                        // ),
                        // _childView(
                        //   "assets/icons/icon_me_game.png",
                        //   "我的游戏",
                        //   "",
                        //   true,
                        // ),
                        // Container(
                        //   height: 12,
                        //   color: AppColors.dividerColor,
                        // ),
                        // _childView(
                        //   "assets/icons/icon_me_gift.png",
                        //   "兑换中心",
                        //   "",
                        //   true,
                        // ),
                        // _childView(
                        //   "assets/icons/icon_me_message.png",
                        //   "我的消息",
                        //   "88",
                        //   false,
                        // ),
                        // _childView(
                        //   "assets/icons/icon_me_comment.png",
                        //   "我的评论",
                        //   "购买、充值记录",
                        //   true,
                        // ),
                        // Container(
                        //   height: 12,
                        //   color: AppColors.dividerColor,
                        // ),
                        _childView(
                          "assets/icons/icon_me_cloud.png",
                          "我的收藏夹",
                          "收藏好书",
                          true,
                        ),
                        // _childView(
                        //   "assets/icons/icon_me_download.png",
                        //   "我的下载",
                        //   "",
                        //   true,
                        // ),
                        _childView(
                          "assets/icons/icon_me_read_record.png",
                          "阅读记录",
                          "",
                          true,
                        ),
                        // Container(
                        //   height: 12,
                        //   color: AppColors.dividerColor,
                        // ),
                        // _childView(
                        //   "assets/icons/icon_me_help.png",
                        //   "帮助与反馈",
                        //   "",
                        //   true,
                        // ),
                        _childView(
                          "assets/icons/icon_me_panda.png",
                          "关于猿书网",
                          "",
                          true,
                        ),
                        Container(
                          height: 12,
                          color: AppColors.dividerColor,
                        ),
                        _childView(
                          "assets/icons/logout.png",
                          GetStorage().read(StringConst.Authorization) != null
                              ? "退出登录"
                              : "点击登录",
                          "",
                          true,
                        ),
                      ],
                    ),
                  )
                ],
              ),
              SizedBox(
                height: 20,
              ),
              _banner()
            ],
          ),
        ),
      ),
    );
  }

  Widget _headView() {
    return Container(
      height: 100,
      color: AppColors.meBgColor,
      child: Column(
        children: <Widget>[
          Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Obx(() => Row(
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.fromLTRB(15, 10, 15, 10),
                        child: ClipOval(
                          child: SizedBox(
                            width: 56,
                            height: 56,
                            child: CachedNetworkImage(
                              imageUrl: controller.avatar.value != ""
                                  ? controller.avatar.value
                                  : "https://cdn.nlark.com/yuque/0/2023/png/21561452/1676705011188-e962ca3d-56f9-4ba9-80d2-66cf2e37ab84.png",
                              fit: BoxFit.cover,
                            ),
                          ),
                        ),
                      ),
                      Container(
                        alignment: Alignment.center,
                        // padding: EdgeInsets.all(10.0),
                        child: GestureDetector(
                          child: Text(
                            controller.name.value != "" &&
                                    controller.name.value != null
                                ? controller.name.value
                                : "请登录",
                            style: TextStyle(
                              fontSize: 18,
                              color: AppColors.white,
                            ),
                          ),
                          onTap: () {
                            if (GetStorage().read("name") == "") {
                              Get.toNamed("login");
                            }
                          },
                        ),
                      ),
                    ],
                  )),
              Expanded(
                child: Container(),
              ),
              // InkWell(
              //   onTap: () {},
              //   child: Padding(
              //     padding: EdgeInsets.fromLTRB(15, 7, 15, 0),
              //     child: Image.asset(
              //       'assets/icons/icon_me_setting.png',
              //       width: 23,
              //       height: 48,
              //     ),
              //   ),
              // ),
            ],
          ),
          // Row(
          //     mainAxisAlignment: MainAxisAlignment.spaceBetween,
          //     children: <Widget>[
          //       SizedBox(
          //         width: 10,
          //       ),
          //       Column(
          //         mainAxisAlignment: MainAxisAlignment.end,
          //         children: <Widget>[
          //           Text(
          //             "0",
          //             style: TextStyle(
          //               fontSize: 14,
          //               color: AppColors.white,
          //             ),
          //           ),
          //           SizedBox(
          //             height: 5,
          //           ),
          //           Text(
          //             "我的收藏夹",
          //             style: TextStyle(
          //               fontSize: 14,
          //               color: AppColors.white,
          //             ),
          //           ),
          //         ],
          //       ),
          //       Container(
          //         margin: EdgeInsets.fromLTRB(0, 0, 0, 0),
          //         color: Color(0x50FFFFFF),
          //         width: 1,
          //         height: 23,
          //         child: Text(""),
          //       ),
          //       Column(
          //         mainAxisAlignment: MainAxisAlignment.end,
          //         children: <Widget>[
          //           Text(
          //             "0",
          //             style: TextStyle(
          //               fontSize: 14,
          //               color: AppColors.white,
          //             ),
          //           ),
          //           SizedBox(
          //             height: 5,
          //           ),
          //           Text(
          //             "积分",
          //             style: TextStyle(
          //               fontSize: 14,
          //               color: AppColors.white,
          //             ),
          //           ),
          //         ],
          //       ),
          //       Container(
          //         margin: EdgeInsets.fromLTRB(0, 0, 0, 0),
          //         color: Color(0x50FFFFFF),
          //         width: 1,
          //         height: 23,
          //         child: Text(""),
          //       ),
          //       Column(
          //         mainAxisAlignment: MainAxisAlignment.end,
          //         children: <Widget>[
          //           Text(
          //             "签到",
          //             style: TextStyle(
          //               fontSize: 14,
          //               color: AppColors.white,
          //             ),
          //           ),
          //           SizedBox(
          //             height: 5,
          //           ),
          //           Text(
          //             "赢现金",
          //             style: TextStyle(
          //               fontSize: 12,
          //               color: HexColor("#FEEABD"),
          //             ),
          //           ),
          //         ],
          //       ),
          //       SizedBox(
          //         width: 10,
          //       ),
          //     ],
          //   ),
          // SizedBox(
          //   height: 30,
          // ),
        ],
      ),
    );
  }

  Widget _childView(String image, String content, String message, bool isGray) {
    return Material(
      color: Colors.transparent,
      child: InkWell(
        onTap: () {
          if (content == "账户信息") {
            if (GetStorage().read(StringConst.Authorization) != null &&
                GetStorage().read(StringConst.Authorization) != "") {
              Get.toNamed("/userInfo",
                      arguments: {"profileMo": controller.profileMo.value})
                  ?.then((value) => controller.loadData());
            } else {
              CustomSnackBar.showCustomErrorSnackBar(
                  title: "请先登录", message: message);
            }
          }
          if (content == "我的收藏夹") {
            if (GetStorage().read(StringConst.Authorization) != null &&
                GetStorage().read(StringConst.Authorization) != "") {
              Get.toNamed("/collect");
            } else {
              CustomSnackBar.showCustomErrorSnackBar(
                  title: "请先登录", message: message);
            }
          }
          if (content == "阅读记录") {
            if (GetStorage().read(StringConst.Authorization) != null &&
                GetStorage().read(StringConst.Authorization) != "") {
              Get.toNamed("/record");
            } else {
              CustomSnackBar.showCustomErrorSnackBar(
                  title: "请先登录", message: message);
            }
          }
          if (content == "退出登录" || content == "点击登录") {
            GetStorage().remove(StringConst.Authorization);
            GetStorage().write("name", "");
            GetStorage().write("avatar", "");
            // 调用 eventBus.fir 发送事件信息
            //发出通知
            //退出登录，清除用户头像
            Map avatar = {"type": "avatar", "data": ""};
            Map name = {"type": "name", "data": ""};
            EventBusUtils.getInstance()?.fire(avatar);
            EventBusUtils.getInstance()?.fire(name);
            Get.toNamed("login");
          }
          if (content == "关于猿书网") {
            Get.toNamed("aboutApp");
          }
        },
        child: Padding(
          padding: EdgeInsets.fromLTRB(15, 15, 15, 15),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Image.asset(
                image,
                width: 23,
                height: 23,
              ),
              SizedBox(
                width: 10,
              ),
              Text(
                content,
                style: TextStyle(
                  color: AppColors.textBlack3,
                  fontSize: 16,
                ),
              ),
              Expanded(child: Container()),
              Text(
                message,
                style: TextStyle(
                  color:
                      isGray ? AppColors.textBlack9 : AppColors.meRedTextColor,
                  fontSize: 13,
                ),
              ),
              SizedBox(
                width: 5,
              ),
              Image.asset(
                "assets/icons/icon_me_arrow.png",
                width: 14,
                height: 14,
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _banner() {
    return Container(
      width: double.infinity,
      height: 100,
      child: AdWidget(
        ad: controller.banner!,
      ),
    );
  }
}
