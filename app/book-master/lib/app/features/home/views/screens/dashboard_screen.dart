library dashboard;

import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:cached_network_image/cached_network_image.dart';
import 'package:connectivity/connectivity.dart';
import 'package:custom_refresh_indicator/custom_refresh_indicator.dart';
import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/constans/strings.dart';
import 'package:daily_task/app/features/home/views/components/banner_card.dart';
import 'package:daily_task/app/features/home/views/components/bottom_navbar.dart';
import 'package:daily_task/app/features/home/views/components/categories.dart';
import 'package:daily_task/app/features/home/views/components/header_weekly_task.dart';
import 'package:daily_task/app/features/home/views/components/rank_list.dart';
import 'package:daily_task/app/features/home/views/components/upgrade_pro_section.dart';
import 'package:daily_task/app/http/dao/book_dao.dart';
import 'package:daily_task/app/http/dao/carousel_list_dao.dart';
import 'package:daily_task/app/model/book_list_model.dart';
import 'package:daily_task/app/model/carousel_list_model.dart';
import 'package:daily_task/app/model/rank_model.dart';
import 'package:daily_task/app/shared_components/card_task.dart';
import 'package:daily_task/app/shared_components/header_text.dart';
import 'package:daily_task/app/shared_components/list_task_assigned.dart';
import 'package:daily_task/app/shared_components/list_task_date.dart';
import 'package:daily_task/app/shared_components/responsive_builder.dart';
import 'package:daily_task/app/shared_components/search_field.dart';
import 'package:daily_task/app/shared_components/selection_button.dart';
import 'package:daily_task/app/shared_components/simple_selection_button.dart';
import 'package:daily_task/app/shared_components/simple_user_profile.dart';
import 'package:daily_task/app/shared_components/task_progress.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:daily_task/app/shared_components/user_profile.dart';
import 'package:daily_task/app/utils/event_bus.dart';
import 'package:daily_task/app/utils/update.dart';
import 'package:eva_icons_flutter/eva_icons_flutter.dart';
import 'package:external_app_launcher/external_app_launcher.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:flutter_update_dialog/flutter_update_dialog.dart';
import 'package:get/get.dart';
import 'package:daily_task/app/utils/helpers/app_helpers.dart';
import 'package:get_storage/get_storage.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:intl/intl.dart';
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:r_upgrade/r_upgrade.dart';
import 'package:url_launcher/url_launcher.dart';

// binding
part '../../bindings/dashboard_binding.dart';

// controller
part '../../controllers/dashboard_controller.dart';

// model

// component

part '../components/main_menu.dart';

part '../components/task_menu.dart';

part '../components/member.dart';

part '../components/task_in_progress.dart';

part '../components/weekly_task.dart';

part '../components/task_group.dart';

class DashboardScreen extends GetView<DashboardController> {
  // const DashboardScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Get.put(DashboardController());
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.white,
          elevation: 0.0,
          leading: Padding(
              padding: const EdgeInsets.only(left: kSpacing / 2),
              child: Obx(
                () => IconButton(
                  onPressed: () => controller.openDrawer(),
                  icon: controller.avatar.value !=null
                      ? CircleAvatar(
                          backgroundImage:
                              NetworkImage(controller.avatar.value))
                      : const Icon(
                          Icons.person,
                          color: Colors.black45,
                          size: 32,
                        ),
                ),
              )),
          title: Column(
            children: [
              Row(
                children: [
                  Expanded(
                      child: GestureDetector(
                    onTap: () {
                      Get.toNamed("search");
                    },
                    child: Container(
                      margin: EdgeInsets.fromLTRB(0, 0, 0, 0),
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.all(Radius.circular(5)),
                        color: AppColors.homeGrey,
                      ),
                      padding: EdgeInsets.fromLTRB(10, 10, 10, 10),
                      child: Row(
                        mainAxisSize: MainAxisSize.max,
                        children: <Widget>[
                          Image.asset(
                            "assets/icons/icon_home_search.png",
                            width: 15,
                            height: 15,
                          ),
                          Text(
                            "   搜索资料",
                            style: TextStyle(
                              color: AppColors.homeGreyText,
                              fontSize: 15,
                            ),
                          ),
                        ],
                      ),
                    ),
                  )),
                ],
              ),
            ],
          ),
        ),
        key: controller.scafoldKey,
        drawer: ResponsiveBuilder.isDesktop(context)
            ? null
            : Drawer(
                child: SafeArea(
                  child: SingleChildScrollView(child: _buildSidebar(context)),
                ),
              ),
        body: CustomRefreshIndicator(
          builder: MaterialIndicatorDelegate(
            builder: (context, controller) {
              return Icon(
                Icons.replay_circle_filled,
                color: HexColor("#37BBF7"),
                size: 30,
              );
            },
          ),

          /// A function that is called when the user drags the refresh indicator.
          onRefresh: () => controller.loadData(),
          child: SafeArea(
            child: ResponsiveBuilder(
              mobileBuilder: (context, constraints) {
                return SingleChildScrollView(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      _buildTaskContent(
                        onPressedMenu: () => controller.openDrawer(),
                      ),
                      const SizedBox(height: 15),
                      // _buildCalendarContent(),
                    ],
                  ),
                );
              },
              tabletBuilder: (context, constraints) {
                return Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Flexible(
                      flex: constraints.maxWidth > 800 ? 8 : 7,
                      child: SingleChildScrollView(
                        controller: ScrollController(),
                        child: _buildTaskContent(
                          onPressedMenu: () => controller.openDrawer(),
                        ),
                      ),
                    ),
                    SizedBox(
                      height: MediaQuery.of(context).size.height,
                      child: const VerticalDivider(),
                    ),
                    // Flexible(
                    //   flex: 4,
                    //   child: SingleChildScrollView(
                    //     controller: ScrollController(),
                    //     child: _buildCalendarContent(),
                    //   ),
                    // ),
                  ],
                );
              },
              desktopBuilder: (context, constraints) {
                return Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Flexible(
                      flex: constraints.maxWidth > 1350 ? 3 : 4,
                      child: SingleChildScrollView(
                        controller: ScrollController(),
                        child: _buildSidebar(context),
                      ),
                    ),
                    Flexible(
                      flex: constraints.maxWidth > 1350 ? 10 : 9,
                      child: SingleChildScrollView(
                        controller: ScrollController(),
                        child: _buildTaskContent(),
                      ),
                    ),
                    SizedBox(
                      height: MediaQuery.of(context).size.height,
                      child: const VerticalDivider(),
                    ),
                    // Flexible(
                    //   flex: 4,
                    //   child: SingleChildScrollView(
                    //     controller: ScrollController(),
                    //     child: _buildCalendarContent(),
                    //   ),
                    // ),
                  ],
                );
              },
            ),
          ),
        ));
  }

  Widget _buildSidebar(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 10),
          child: Obx(()=>UserProfile(
            image: controller.avatar.value!=null?controller.avatar.value:"https://cdn.nlark.com/yuque/0/2023/png/21561452/1676705011188-e962ca3d-56f9-4ba9-80d2-66cf2e37ab84.png",
            name: controller.name.value!="" &&controller.name.value!=null?controller.name.value:"请登录",
            onPressed: controller.onPressedProfil,
          )),
        ),
        // const SizedBox(height: 15),
        // Padding(
        //   padding: const EdgeInsets.symmetric(horizontal: 10),
        //   child: _MainMenu(onSelected: controller.onSelectedMainMenu),
        // ),
        const Divider(
          indent: 20,
          thickness: 1,
          endIndent: 20,
          height: 60,
        ),
        _Member(member: controller.member),
        const SizedBox(height: kSpacing / 2),
        _TaskMenu(
          onSelected: controller.onSelectedTaskMenu,
        ),
        const SizedBox(height: kSpacing / 2),
        const Divider(
          indent: 20,
          thickness: 1,
          endIndent: 20,
          height: 10,
        ),
        Padding(
          padding: const EdgeInsets.all(kSpacing / 2),
          child: TextButton(
            style: ButtonStyle(
              // backgroundColor: MaterialStateProperty.all(Color(0xFFF2F6F5)),
              padding: MaterialStateProperty.all(EdgeInsets.all(10)),
              shape: MaterialStateProperty.all(
                RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(20),
                ),
              ),
            ),
            onPressed: () {
              GetStorage().remove(StringConst.Authorization);
              GetStorage().write("name","");
              GetStorage().write("avatar","");
              // 调用 eventBus.fir 发送事件信息
              //发出通知
              //退出登录，清除用户头像
              Map avatar = {"type": "avatar", "data": ""};
              Map name = {"type": "name", "data": ""};
              EventBusUtils.getInstance()?.fire(avatar);
              EventBusUtils.getInstance()?.fire(name);
              Get.toNamed("login");
            },
            child: Row(
              children: [
                SizedBox(
                  child: SvgPicture.asset(
                    "assets/icons/Log out.svg",
                    width: 20,
                    color: AppColors.kPrimaryColor,
                  ),
                ),
                SizedBox(
                  width: kSpacing,
                ),
                Obx(()=>Text(
                  controller.name.value!=""?"退出登录":"点击登录",
                  style: TextStyle(color: Colors.black54),
                )),
                Spacer(),
                Icon(
                  Icons.arrow_forward_ios_rounded,
                  size: 18,
                  color: Colors.black45,
                ),
              ],
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildTaskContent({Function()? onPressedMenu}) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: kSpacing / 2),
      child: Column(
        children: [
          const SizedBox(height: kSpacing / 2),
          _cardSwiper(),
          const SizedBox(height: kSpacing / 2),
          // const UpgradeProSection(),
          // const SizedBox(height: kSpacing / 2),
          // Container(
          //   color: Colors.white12,
          //   child: Row(
          //     children: [
          //       Expanded(
          //         child: Container(
          //           padding: const EdgeInsets.only(left: 2 * kSpacing),
          //           child: const Text(
          //             "已完成阅读：",
          //             style: const TextStyle(
          //                 fontSize: 13,
          //                 fontWeight: FontWeight.w500,
          //                 color: Colors.black45),
          //           ),
          //         ),
          //       ),
          //       const SizedBox(width: kSpacing / 2),
          //       SizedBox(
          //           width: 200,
          //           child: Container(
          //             padding: const EdgeInsets.only(right: 2 * kSpacing),
          //             child: TaskProgress(data: controller.dataTask),
          //           )),
          //     ],
          //   ),
          // ),
          Container(
              width: double.infinity,
              margin: EdgeInsets.fromLTRB(kSpacing, 0, kSpacing, 0),
              padding: EdgeInsets.fromLTRB(18, 10, 18, 10),
              decoration: BoxDecoration(
                  color: Color(0XFFEBF9F6),
                  borderRadius: BorderRadius.all(Radius.circular(100))),
              child: Obx(
                () => Text(
                  controller.noticeList.isEmpty
                      ? ""
                      : controller.noticeList[0].title!,
                  style: TextStyle(color: Color(0xFF9A9AA7), fontSize: 12),
                ),
              )),
          // const SizedBox(height: kSpacing / 2),
          // Categories(),
          // const SizedBox(height: kSpacing / 2),
          // _TaskInProgress(data: controller.taskInProgress),
          const SizedBox(height: kSpacing / 2),
          const HeaderWeeklyTask(),
          const SizedBox(height: kSpacing / 4),
          _WeeklyTask(
            data: controller.weeklyTask,
          ),
          const SizedBox(height: kSpacing / 2),
          _banner(),
          // Row(
          //   children: [
          //     Container(
          //       padding: EdgeInsets.only(left: 10),
          //       child: const Text(
          //         "热门榜单",
          //         style: TextStyle(
          //           fontSize: 17,
          //           fontWeight: FontWeight.w800,
          //         ),
          //       ),
          //     ),
          //     const Spacer(),
          //     // const SizedBox(width: 10),
          //     // _buildAddNewButton(),
          //   ],
          // ),
          // const SizedBox(height: kSpacing / 2),
          // RankList(data: controller.rankData, data1: controller.rankData1)
        ],
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

  Widget _cardSwiper() {
    return Container(
        constraints: BoxConstraints(minHeight: 130),
        height: 130,
        child: BannerCard(
          controller.bannerList,
        ));
  }
}
