import 'package:daily_task/app/constans/strings.dart';
import 'package:daily_task/app/http/dao/login_dao.dart';

/// all endpoint api
class ApiPath {
  // Example :
  static String BASE_URL = "192.168.3.205:8088";
  //
  static header() {
    Map<String, dynamic> header = {};
    header[StringConst.Authorization] = LoginDao.getBoardingAuth();
    return header;
  }

}
