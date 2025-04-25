import 'package:daily_task/app/constans/api_path.dart';
import 'package:daily_task/app/constans/app_constants.dart';

import 'base_request.dart';
import 'pink_base_request.dart';

class UploadRequest extends BaseRequest {
  @override
  HttpMethod httpMethod() {
    return HttpMethod.POST;
  }

  @override
  bool needLogin() {
    return true;
  }

  @override
  String path() {
    return "/oss/upload";
  }
}
