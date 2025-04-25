import 'package:daily_task/app/config/core/pink_net.dart';
import 'package:daily_task/app/http/request/user_remove_request.dart';
import 'package:daily_task/app/model/userinfo_model.dart';

class UserRemoveDao {
  static get() async {
    UserRemoveRequest request = UserRemoveRequest();
    var result = await PinkNet.getInstance().fire(request);
    return Userinfo.fromJson(result);
  }
}
