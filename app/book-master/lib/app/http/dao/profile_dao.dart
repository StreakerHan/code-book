import 'package:daily_task/app/config/core/pink_net.dart';
import 'package:daily_task/app/http/request/profile_request.dart';
import 'package:daily_task/app/model/userinfo_model.dart';

class ProfileDao {
  static get() async {
    ProfileRequest request = ProfileRequest();
    var result = await PinkNet.getInstance().fire(request);
    return Userinfo.fromJson(result);
  }
}
