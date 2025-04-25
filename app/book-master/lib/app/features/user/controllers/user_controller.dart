import 'dart:async';
import 'dart:io';

import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/http/dao/profile_dao.dart';
import 'package:daily_task/app/model/userinfo_model.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:daily_task/app/utils/event_bus.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';

class UserController extends GetxController {

  //声明，后面需要销毁
  StreamSubscription? event;

  var avatar = "".obs;

  var name = "".obs;

  final profileMo = Userinfo().obs;

  BannerAd? banner;

  void createBannerAd() {
    banner = BannerAd(
      adUnitId: Platform.isAndroid
          ? 'ca-app-pub-3487107522093822/7952646325'
          : "ca-app-pub-2043245855119152/7654955087",
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

  Future<void> loadData() async {
    try {
      Userinfo result = await ProfileDao.get();
      if(result.code == 401){
        showToast("当前未登录，请登录！");
      }else{
        print("______________________________"+result.msg.toString());
        profileMo.value = result;
        GetStorage().write("name", profileMo.value.data?.nickname);
        name.value = GetStorage().read("name");
        GetStorage().write("avatar", profileMo.value.data?.avatar);
        avatar.value = GetStorage().read("avatar");
      }
    } on NeedAuth catch (e) {
      showToast(e.message);
    } on NeedLogin catch (e) {
      showToast(e.message);
    } on PinkNetError catch (e) {
      showToast(e.message);
    }
  }

  @override
  void onInit() {
    createBannerAd();
    loadData();
    avatar.value = GetStorage().read("avatar");
    name.value = GetStorage().read("name");
    //通知监听
    event = EventBusUtils.getInstance()?.on().listen((event) {
      print(event);
      if (event["type"] == "avatar") {
        avatar.value = event["data"];
      }
      if (event["type"] == "name") {
        name.value = event["data"];
      }
      if (event["type"] == "user") {
        if(event['data'] == "refresh"){
          loadData();
        }
      }
    });
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {
  }
}
