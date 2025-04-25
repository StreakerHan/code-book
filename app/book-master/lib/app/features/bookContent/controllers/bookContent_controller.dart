import 'dart:async';
import 'dart:io';

import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/constans/strings.dart';
import 'package:daily_task/app/http/dao/book_chapters_dao.dart';
import 'package:daily_task/app/http/dao/book_content_dao.dart';
import 'package:daily_task/app/model/book_chapters_model.dart';
import 'package:daily_task/app/model/book_content_list_model.dart';
import 'package:daily_task/app/model/book_list_model.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_easyloading/flutter_easyloading.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';
import 'package:scroll_to_index/scroll_to_index.dart';

class BookContentController extends GetxController {
  var currentIndex = 0.obs;
  static int initialPage = 0;
  final PageController controller = PageController(initialPage: initialPage);
  late List<Widget> pages;
  bool hasBuild = false;
  late ScrollController scrollController = new ScrollController();
  bool isNighttime = false;
  bool isAddBookshelf = false;
  late GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  RxDouble addBookshelfWidth = 95.0.obs;
  RxDouble bottomHeight = 200.0.obs;
  RxDouble sImagePadding = 20.0.obs;

  RxDouble height = 0.0.obs;

  RxDouble bottomPadding = 200.0.obs;
  RxDouble imagePadding = 20.0.obs;
  RxDouble addBookshelfPadding = 95.0.obs;

  RewardedInterstitialAd? rewardedInterstitialAd;

  var chapterIndex = "".obs;

  var bookInfo = BookModel().obs;

  var bookContentList = <BookChapterModel>[].obs;

  var bookChapters = <BookChapterModel>[].obs;

  var autoScrollController = AutoScrollController();

  getBookContentHref(String href) async {
    try {
      BookChaptersListModel result =
          await BookContentDao.get1(bookInfo.value.id!, href);
      bookContentList.value = result.rows!;
      EasyLoading.showSuccess('加载成功!');
      return true;
    } on NeedAuth catch (e) {
      showWarnToast(e.message);
      return false;
    } on PinkNetError catch (e) {
      showWarnToast(e.message);
      return false;
    }
  }

  getBookContentList() async {
    try {
      BookChaptersListModel result =
          await BookContentDao.get(chapterIndex.value, bookInfo.value.id!, "");
      bookContentList.value = result.rows!;
      EasyLoading.showSuccess('加载成功!');
      return true;
    } on NeedAuth catch (e) {
      showWarnToast(e.message);
      return false;
    } on PinkNetError catch (e) {
      showWarnToast(e.message);
      return false;
    }
  }

  getBookContentTokenList() async {
    try {
      BookChaptersListModel result =
          await BookContentDao.get2(chapterIndex.value, bookInfo.value.id!, "");
      bookContentList.value = result.rows!;
      EasyLoading.showSuccess('加载成功!');
      return true;
    } on NeedAuth catch (e) {
      showWarnToast(e.message);
      return false;
    } on PinkNetError catch (e) {
      showWarnToast(e.message);
      return false;
    }
  }

  getBookChapterList() async {
    try {
      BookChaptersListModel result =
          await BookChaptersDao.get(bookInfo.value.id!);
      bookChapters.value = result.rows!;
      return true;
    } on NeedAuth catch (e) {
      showWarnToast(e.message);
      return false;
    } on PinkNetError catch (e) {
      showWarnToast(e.message);
      return false;
    }
  }

  void createRewardedInterstitialAd() {
    RewardedInterstitialAd.load(
      adUnitId: Platform.isAndroid
          ? 'ca-app-pub-3487107522093822/7952646325'
          : "ca-app-pub-2043245855119152/1576901391",
      request: AdRequest(),
      rewardedInterstitialAdLoadCallback: RewardedInterstitialAdLoadCallback(
        onAdLoaded: (RewardedInterstitialAd ad) {
          print('$ad loaded.');
          // Keep a reference to the ad so you can show it later.
          rewardedInterstitialAd = ad;
        },
        onAdFailedToLoad: (LoadAdError error) {
          print('RewardedInterstitialAd failed to load: $error');
        },
      ),
    );
  }
  late Timer timer;

  @override
  void onInit() {
    createRewardedInterstitialAd();
    EasyLoading.show(status: '正在加载资源...');
    chapterIndex.value = (Get.arguments as Map)["chapterIndex"];
    bookInfo.value = (Get.arguments as Map)["bookInfo"];
    if (GetStorage().read(StringConst.Authorization) != null &&
        GetStorage().read(StringConst.Authorization) != "") {
      getBookContentTokenList();
    }else{
      getBookContentList();
    }
    getBookChapterList();
    timer = Timer.periodic(Duration(milliseconds: 120000), (timer) {//Time每1秒执行一次下面代码
      rewardedInterstitialAd?.show(onUserEarnedReward: (AdWithoutView ad, RewardItem reward) {  });
    });
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {
    controller.dispose();
    rewardedInterstitialAd?.dispose();
  }
}
