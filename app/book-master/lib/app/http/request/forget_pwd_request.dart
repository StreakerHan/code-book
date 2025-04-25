import 'package:daily_task/app/constans/api_path.dart';

import 'base_request.dart';
import 'pink_base_request.dart';

class ForgetPwdRequest extends BaseRequest {
  @override
  HttpMethod httpMethod() {
    return HttpMethod.POST;
  }

  @override
  bool needLogin() {
    return false;
  }

  @override
  String path() {
    return "${ApiPath.BASE_URL}/forgetPwd";
  }
}
