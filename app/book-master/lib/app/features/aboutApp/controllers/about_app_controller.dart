import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/constans/strings.dart';
import 'package:daily_task/app/http/dao/user_remove_dao.dart';
import 'package:daily_task/app/model/userinfo_model.dart';
import 'package:daily_task/app/shared_components/custom_snack_bar.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:daily_task/app/utils/event_bus.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';
import 'package:package_info/package_info.dart';

class AboutAppController extends GetxController {
  //TODO: Implement AboutAppController
  var version = "".obs;
  var appName = "".obs;
  var buildNumber = "".obs;
  var packageName = "".obs;

  @override
  Future<void> onInit() async {
    PackageInfo packageInfo = await PackageInfo.fromPlatform();
    version.value = packageInfo.version;
    appName.value = packageInfo.appName;
    buildNumber.value = packageInfo.buildNumber;
    packageName.value = packageInfo.packageName;
    super.onInit();
  }

  Future<void> remove() async {
    try {
      Userinfo result = await UserRemoveDao.get();
      if(result.code == 200){
        GetStorage().remove(StringConst.Authorization);
        GetStorage().write("name","");
        GetStorage().write("avatar","");
        // 调用 eventBus.fir 发送事件信息
        //发出通知
        //退出登录，清除用户头像
        Map avatar = {"type": "avatar", "data": ""};
        Map name = {"type": "name", "data": ""};
        Map user = {"type": "user", "data": "refresh"};
        EventBusUtils.getInstance()?.fire(avatar);
        EventBusUtils.getInstance()?.fire(name);
        CustomSnackBar.showCustomSnackBar(title: "账号已注销", message: "您在本APP的数据已经全部清除。");
        // 登出用户账号
        Future.delayed(Duration(milliseconds: 1000), () {
          Get.offAllNamed("/index");
        });
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
  void onReady() {
    super.onReady();
  }

  @override
  void onClose() {}
}
