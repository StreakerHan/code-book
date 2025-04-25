
import 'package:daily_task/app/config/core/pink_net.dart';
import 'package:daily_task/app/http/request/user_update_request.dart';

class UserUpdateDao {
  static update(String slug, String value) async {
    UserInfoUpdateRequest request = UserInfoUpdateRequest();
    // request.add("slug", slug);
    request.add(slug, value);
    var result = await PinkNet.getInstance().fire(request);
    return result;
  }

  static updatePassword(String oldPassword, String newPassword, String reNewPassword) async {
    UserPasswordUpdateRequest request = UserPasswordUpdateRequest();
    request.add("password", oldPassword);
    request.add("newPassword", newPassword);
    request.add("re_new_password", reNewPassword);
    var result = await PinkNet.getInstance().fire(request);
    return result;
  }

  static updateEmail(
    String newEmail,
    String validateCode,
  ) async {
    UserEmailUpdateRequest request = UserEmailUpdateRequest();
    request.add("new_email", newEmail);
    request.add("validate_code", validateCode);
    var result = await PinkNet.getInstance().fire(request);
    return result;
  }
}
