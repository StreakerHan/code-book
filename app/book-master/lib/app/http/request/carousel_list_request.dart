
import 'base_request.dart';
import 'pink_base_request.dart';

class CarouselListRequest extends BaseRequest {
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
    return "/app/appCarousel/getIndexList";
  }
}
