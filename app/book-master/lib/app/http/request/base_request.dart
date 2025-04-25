import 'package:daily_task/app/constans/api_path.dart';
import 'package:daily_task/app/constans/strings.dart';
import 'package:daily_task/app/http/dao/login_dao.dart';
import 'package:daily_task/app/http/request/pink_base_request.dart';
import 'package:get_storage/get_storage.dart';

abstract class BaseRequest extends PinkBaseRequest {
  @override
  String authority() {
    return "${ApiPath.BASE_URL}";
  }

  @override
  String url() {
    //是否需要登录
    var token = GetStorage().read(StringConst.Authorization);
    if (needLogin() || (token != null && token != "")) {
      print(LoginDao.getBoardingAuth());
      addHeader(StringConst.Authorization, LoginDao.getBoardingAuth()
          // PinkConstants.Authorization, "Bearer " + LoginDao.getBoardingPass()
          );
    }
    return super.url();
  }
}
