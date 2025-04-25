import 'package:daily_task/app/config/routes/app_pages.dart';
import 'package:daily_task/app/constans/strings.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_navigation/src/routes/get_route.dart';
import 'package:get/get_navigation/src/routes/route_middleware.dart';
import 'package:get_storage/get_storage.dart';

class OpenMiddleware extends GetMiddleware {
  @override
  int? priority = 2;

  bool get isFirstOpen =>
      getBoardingPass() != "1";

  @override
  RouteSettings? redirect(String? route) {
    print("是否首次打开");
    print(GetStorage().read("FirstOpen"));
    print(isFirstOpen);
    if (isFirstOpen) {
      return RouteSettings(name: Routes.welcome);
    }
    return super.redirect(route);
  }


  @override
  GetPage? onPageCalled(GetPage? page) {
    return super.onPageCalled(page);
  }

  static getBoardingPass() {
    return GetStorage().read(StringConst.FirstOpen);
  }
  static getBoardingAuth() {
    return GetStorage().read(StringConst.Authorization);
  }
}
