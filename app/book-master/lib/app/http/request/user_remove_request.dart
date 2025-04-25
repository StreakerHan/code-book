import 'base_request.dart';
import 'pink_base_request.dart';

class UserRemoveRequest extends BaseRequest {
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
    return "/app/appUser/logoff";
  }
}
