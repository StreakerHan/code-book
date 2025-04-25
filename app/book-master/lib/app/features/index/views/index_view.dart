import 'dart:io';

import 'package:daily_task/app/features/category/views/screens/category_screen.dart';
import 'package:daily_task/app/features/home/views/screens/dashboard_screen.dart';
import 'package:daily_task/app/features/user/views/user_view.dart';
import 'package:eva_icons_flutter/eva_icons_flutter.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controllers/index_controller.dart';

class IndexView extends GetView<IndexController> {
  @override
  Widget build(BuildContext context) {
    controller.pages = [
      DashboardScreen(),
      CategoriesScreen(),
      // DynamicView(),
      // ContactView(),
      UserView(),
    ];
    return Scaffold(
      body: PageView(
        controller: controller.controller,
        children: controller.pages,
        onPageChanged: (index) => _onJumpTo(index),
        physics: NeverScrollableScrollPhysics(),
      ),
      bottomNavigationBar: Container(
        child: Obx(() => BottomNavigationBar(
              backgroundColor: Colors.white,
              currentIndex: controller.currentIndex.value,
              onTap: (index) => _onJumpTo(index),
              selectedItemColor: Theme.of(context).primaryColor,
              unselectedItemColor: Theme.of(context).primaryColor.withOpacity(.5),
              type: BottomNavigationBarType.fixed,
              showUnselectedLabels: false,
              selectedFontSize: 12,
              items: const [
                BottomNavigationBarItem(
                  activeIcon: Icon(EvaIcons.home),
                  icon: Icon(EvaIcons.homeOutline),
                  label: "首页",
                ),
                BottomNavigationBarItem(
                  activeIcon: Icon(EvaIcons.bookOpen),
                  icon: Icon(EvaIcons.book),
                  label: "分类",
                ),
                // BottomNavigationBarItem(
                //   activeIcon: Icon(EvaIcons.codeDownload),
                //   icon: Icon(EvaIcons.code),
                //   label: "开源",
                // ),
                BottomNavigationBarItem(
                  activeIcon: Icon(EvaIcons.personOutline),
                  icon: Icon(EvaIcons.person),
                  label: "我的",
                ),
              ],
            )),
      ),
    );
  }
  _onJumpTo(int index) {
    controller.controller.jumpToPage(index);
    controller.currentIndex.value = index;
  }
}
