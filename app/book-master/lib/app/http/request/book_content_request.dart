import 'base_request.dart';
import 'pink_base_request.dart';

/// 不带token
class BookContentRequest extends BaseRequest {
  @override
  HttpMethod httpMethod() {
    return HttpMethod.GET;
  }

  @override
  bool needLogin() {
    return false;
  }

  @override
  String path() {
    return "/app/bookContent/chaptersContent";
  }
}
/// 带token
class BookContentTokenRequest extends BaseRequest {
  @override
  HttpMethod httpMethod() {
    return HttpMethod.GET;
  }

  @override
  bool needLogin() {
    return true;
  }

  @override
  String path() {
    return "/app/bookContent/chaptersContentWithToken";
  }
}
/// 根据href获取内容
class BookContentHrefRequest extends BaseRequest {
  @override
  HttpMethod httpMethod() {
    return HttpMethod.GET;
  }

  @override
  bool needLogin() {
    return false;
  }

  @override
  String path() {
    return "/app/bookContent/chaptersContentHref";
  }
}

