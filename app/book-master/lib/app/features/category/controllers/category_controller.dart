import 'dart:io';

import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/http/dao/book_category_dao.dart';
import 'package:daily_task/app/model/book_category_list_model.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:get/get.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';

class CategoryController extends GetxController {

  var categoryList = <BookCategoryModel>[].obs;

  getList() async {
    try {
      BookCategoryListModel result = await BookCategoryDao.get('1',"", page: 1, size: 100);
      categoryList.value = result.data!;
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
          : "ca-app-pub-2043245855119152/8968036757",
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
    getList();
    createBannerAd();
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {}
}