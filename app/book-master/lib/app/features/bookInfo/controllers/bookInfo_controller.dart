import 'dart:io';

import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/http/dao/book_dao.dart';
import 'package:daily_task/app/model/book_info_model.dart';
import 'package:daily_task/app/model/book_list_model.dart';
import 'package:daily_task/app/shared_components/custom_snack_bar.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';

import '../../../constans/strings.dart';

class BookInfoController extends GetxController {
  var currentIndex = 0.obs;
  static int initialPage = 0;
  final PageController controller = PageController(initialPage: initialPage);
  late List<Widget> pages;
  bool hasBuild = false;

  ScrollController scrollController = new ScrollController();


  //判断是否加入书架
  var isAddBookshelf = "".obs;
  var bookInfo = BookModel().obs;

  //收藏/取消收藏
  collect() async{
    if (GetStorage().read(StringConst.Authorization) != null &&
        GetStorage().read(StringConst.Authorization) != "") {
      try {
        BookInfo result =
        await BookDao.collect(bookInfo.value.id!);
        print(result.data1);
        if(result.code == 200){
          if(isAddBookshelf.value == '1'){
            isAddBookshelf.value = '0';
            CustomSnackBar.showCustomErrorSnackBar(title: ("已从我的收藏夹移除！"), message: '期待再次阅读。');
          }else{
            isAddBookshelf.value = '1';
            CustomSnackBar.showCustomSnackBar(title: ("已加入我的收藏夹！"), message: '快去阅读吧。');
          }
        }
        return true;
      } on NeedAuth catch (e) {
        showWarnToast(e.message);
        return false;
      } on PinkNetError catch (e) {
        showWarnToast(e.message);
        return false;
      }
    }else{
      CustomSnackBar.showCustomErrorSnackBar(title: ("请先登录！"), message: '登录后才能收藏。');
    }
  }

  getBookInfoToken() async {
    try {
      BookInfo result =
      await BookDao.getInfoToken(bookInfo.value.id!);
      isAddBookshelf.value = result.data1!.collect!;
      return true;
    } on NeedAuth catch (e) {
      showWarnToast(e.message);
      return false;
    } on PinkNetError catch (e) {
      showWarnToast(e.message);
      return false;
    }
  }

  BannerAd? banner;

  void createBannerAd() {
    banner = BannerAd(
      adUnitId: Platform.isAndroid
          ? 'ca-app-pub-3487107522093822/7952646325'
          : "ca-app-pub-2043245855119152/6829228077",
      size: AdSize.banner,
      request: AdRequest(),
      listener: BannerAdListener(
        // Called when an ad is successfully received.
        onAdLoaded: (Ad ad) => print('${ad.runtimeType} loaded.'),
        // Called when an ad request failed.
        onAdFailedToLoad: (Ad ad, LoadAdError error) {
          print('${ad.runtimeType} failed to load: $error');
        },
        // Called when an ad opens an overlay that covers the screen.
        onAdOpened: (Ad ad) => print('${ad.runtimeType} opened.'),
        // Called when an ad removes an overlay that covers the screen.
        onAdClosed: (Ad ad) {
          print('${ad.runtimeType} closed');
          ad.dispose();
          createBannerAd();
          print('${ad.runtimeType} reloaded');
        },
        // Called when an ad is in the process of leaving the application.
        // onApplicationExit: (Ad ad) => print('Left application.'),
      ),
    )..load();
  }

  @override
  void onInit() {
    createBannerAd();
    bookInfo.value = (Get.arguments as Map)["bookInfo"];
    //获取详情
    if (GetStorage().read(StringConst.Authorization) != null &&
        GetStorage().read(StringConst.Authorization) != "") {
      getBookInfoToken();
    }else{
      isAddBookshelf.value = '0';
    }
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
