import 'package:daily_task/app/config/core/pink_net.dart';
import 'package:daily_task/app/http/request/carousel_list_request.dart';
import 'package:daily_task/app/model/carousel_list_model.dart';

class CarouselListDao {
  static get(String dynamic, {int page = 1, size = 10}) async {
    CarouselListRequest request = CarouselListRequest();
    // request.add("topId", dynamic).add("pageNumber", page).add("pageSize", size);
    var result = await PinkNet.getInstance().fire(request);
    // print("1231231"+result);
    return CarouselList.fromJson(result);
  }
}
