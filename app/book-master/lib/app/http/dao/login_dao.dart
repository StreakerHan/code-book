import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/config/core/pink_net.dart';
import 'package:daily_task/app/constans/strings.dart';
import 'package:daily_task/app/http/request/base_request.dart';
import 'package:daily_task/app/http/request/forget_pwd_request.dart';
import 'package:daily_task/app/http/request/login_request.dart';
import 'package:daily_task/app/http/request/registration_request.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:get_storage/get_storage.dart';

class LoginDao {
  static login(String email, String password) {
    return _send(email, password);
  }

  static registration(String userName, String email, String validateCode,
      String password, String rePassword) {
    return _send(email, password,
        rePassword: rePassword, validateCode: validateCode, userName: userName);
  }

  static forgetPwd(String email, String validateCode, String password,
      String rePassword) async {
    ForgetPwdRequest request = ForgetPwdRequest();
    request
        .add("email", email)
        .add("validate_code", validateCode)
        .add("password", password)
        .add("re_password", rePassword);
    var result = await PinkNet.getInstance().fire(request);
    return result;
  }

  static _send(String email, String password,
      {rePassword, validateCode, userName}) async {
    BaseRequest request;
    if (rePassword != null || validateCode != null) {
      request = RegistrationRequest();
      request
          .add("email", email)
          .add("validate_code", validateCode)
          .add("username", userName)
          .add("password", password)
          .add("re_password", rePassword);
    } else {
      request = LoginRequest();
      request.add("username", email).add("password", password);
    }
    try {
      var result = await PinkNet.getInstance().fire(request);
      // showToast(result['msg']);
      // GetStorage().write(PinkConstants.Authorization, result['data']['token']);
      return result;
    } on NeedAuth catch (e) {
      showToast(e.message);
    } on NeedLogin catch (e) {
      showToast(e.message);
    } on PinkNetError catch (e) {
      showToast(e.message);
    }
    // print('loginresult9999999login');
    // print(result);
    // print('resulttoken');
    // print(result['data']['token']);
    // if (result["code"] == 200) {
    //   //保存登录令牌
    //   GetStorage().write(PinkConstants.Authorization, result['data']['token']);
    // }
  }

  static getBoardingPass() {
    return GetStorage().read(StringConst.FirstOpen);
  }

  static getBoardingAuth() {
    return GetStorage().read(StringConst.Authorization);
  }
}
