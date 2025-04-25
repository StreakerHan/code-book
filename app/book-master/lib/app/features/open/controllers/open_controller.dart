import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';

class OpenController extends GetxController {
  var currentIndex = 0.obs;
  static int initialPage = 0;
  final PageController controller = PageController(initialPage: initialPage);
  late List<Widget> pages;
  bool hasBuild = false;

  @override
  void onInit() {
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {
    controller.dispose();
  }
}
