part of dashboard;

class DashboardController extends GetxController {
  final scafoldKey = GlobalKey<ScaffoldState>();

  //声明，后面需要销毁
  StreamSubscription? event;

  var avatar = "".obs;

  var name = "".obs;

  //更新
  UpdateDialog? dialog;
  var Update = false.obs;
  var serverAndroidVersion = "";
  var serverAndroidUrl = "";
  var serverAndroidMsg = "";

  var bannerList = <Top>[].obs;
  var bottomList = <Top>[].obs;
  var noticeList = <Notice>[].obs;

  //推荐
  var weeklyTask = <BookModel>[].obs;

  final member = ["Flutter开源项目", "Vue开源项目"];

  final dataTask = const TaskProgressData(totalTask: 5, totalCompleted: 1);



  void onPressedProfil() {}

  void onSelectedMainMenu(int index, SelectionButtonData value) {}

  void onSelectedTaskMenu(int index, String label) {}

  void searchTask(String value) {
    Get.toNamed("search");
  }

  void onPressedTask(int index, ListTaskAssignedData data) {
    Get.toNamed("bookInfo");
  }

  void onPressedAssignTask(int index, ListTaskAssignedData data) {}

  void onPressedMemberTask(int index, ListTaskAssignedData data) {}

  void onPressedCalendar() {}

  void onPressedTaskGroup(int index, ListTaskDateData data) {}

  void openDrawer() {
    if (scafoldKey.currentState != null) {
      scafoldKey.currentState!.openDrawer();
    }
  }

  late StreamSubscription<ConnectivityResult> subscription;

  ///网络监听
  initNetworkListen() {
    subscription = Connectivity()
        .onConnectivityChanged
        .listen((ConnectivityResult result) {
      // WIFI网络
      if (result == ConnectivityResult.wifi) {
        // 移动网络
        checkUpdate();
        createBannerAd();
        loadData();
      } else if (result == ConnectivityResult.mobile) {
        // 没有网络
        checkUpdate();
        createBannerAd();
        loadData();
      } else {}
    });
    createBannerAd();
    checkUpdate();
    loadData();
  }

  void onUpdate() async {
    if (Platform.isAndroid) {
      Update.value = false;
      await RUpgrade.upgrade(serverAndroidUrl,
          fileName: 'app-release.apk', isAutoRequestInstall: true);
    } else {
      launch("https://apps.apple.com/cn/app/id1673616107");
    }
  }

  void customStyle() {
    if (dialog != null && dialog!.isShowing()) {
      return;
    }
    dialog = UpdateDialog.showUpdate(Get.context as BuildContext,
        width: 250,
        title: "是否升级到" + serverAndroidVersion + "版本？",
        updateContent: serverAndroidMsg,
        titleTextSize: 14,
        contentTextSize: 12,
        buttonTextSize: 12,
        topImage: Image.asset('assets/images/update_bg_app_top.png'),
        extraHeight: 5,
        radius: 8,
        themeColor: const Color(0xFFFFAC5D),
        progressBackgroundColor: const Color(0x5AFFAC5D),
        isForce: true,
        updateButtonText: '升级',
        ignoreButtonText: '忽略此版本',
        enableIgnore: true, onIgnore: () {
      Update.value = false;
      GetStorage().write(
          "update_time", DateTime.now().millisecondsSinceEpoch + 86400000);
      dialog!.dismiss();
    }, onUpdate: onUpdate);
  }

  void checkUpdate() async {
    Map res = await UpdateUtil.getUpgrade();
    print(res["update"]);
    print(GetStorage().read("update_time"));
    print(DateTime.now().millisecondsSinceEpoch);
    if (res["update"] == true) {
      Update.value = true;
      serverAndroidVersion = res["version"];
      serverAndroidMsg = res["msg"];
      serverAndroidUrl = res["url"];
    }
    if ((Update.value && GetStorage().read("update_time") == null) ||
        (Update.value &&
            GetStorage().read("update_time") <
                DateTime.now().millisecondsSinceEpoch)) {
      customStyle();
    }
  }

  getList() async {
    try {
      CarouselList result = await CarouselListDao.get('1', page: 1, size: 10);
      print('---------------');
      bannerList.value = result.data!.top!;
      noticeList.value = result.data!.notice!;
      bottomList.value = result.data!.bottom!;
      return true;
    } on NeedAuth catch (e) {
      showWarnToast(e.message);
      return false;
    } on PinkNetError catch (e) {
      showWarnToast(e.message);
      return false;
    }
  }

  loadData() async {
    getList();
    getRecommendList();
  }

  getRecommendList() async {
    try {
      BookListModel result = await BookDao.getRecommend();
      weeklyTask.value = result.rows!;
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
          : "ca-app-pub-2043245855119152/8209488854",
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
    avatar.value = GetStorage().read("avatar");
    name.value = GetStorage().read("name");
    checkUpdate();
    createBannerAd();
    getList();
    getRecommendList();
    //通知监听
    event = EventBusUtils.getInstance()?.on().listen((event) {
      print(event);
      if (event["type"] == "avatar") {
        avatar.value = event["data"];
      }
      if (event["type"] == "name") {
        name.value = event["data"];
      }
    });
    super.onInit();
  }

  @override
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {}
}
