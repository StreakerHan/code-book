import 'package:daily_task/app/config/core/dio_adapter.dart';
import 'package:daily_task/app/config/core/pink_error.dart';
import 'package:daily_task/app/config/core/pink_net_adapter.dart';
import 'package:daily_task/app/http/request/pink_base_request.dart';
import 'package:daily_task/app/shared_components/toast.dart';
import 'package:dio/dio.dart';

class PinkNet {
  PinkNet._();

  static PinkNet? _instance;

  static PinkNet getInstance() {
    _instance ??= PinkNet._();
    return _instance!;
  }

  Future fire(PinkBaseRequest request) async {
    print('****************************request************************');
    late PinkNetResponse response = new PinkNetResponse(request: request);
    print(request.path());
    print(request.params);
    var error;
    try {
      response = await send(request);
    } on PinkNetError catch (e) {
      error = e;
      response = e.data;
    } catch (e) {
      error = e;
    }
    if (response == null) {
      print(error);
    }
    var result = response.data;
    print(result);
    var status = result['code'];
    print(status);
    print(request.url());
    print(result["msg"]);
    switch (status) {
      case 200:
        return result;
      case 401:
        return NeedLogin();
      case 403:
        return NeedAuth(result.toString(), data: result);
      case 500:
        return result;
      default:
        throw PinkNetError(status!, result['msg'].toString(), data: result);
    }
  }

  ProgressCallback sendProgress = (int count, int total) {};

  Future<dynamic> send<T>(PinkBaseRequest request) async {
    PinkNetAdapter adapter = DioAdapter();
    if (request.file != null) {
      adapter.sendProgress = sendProgress;
    }
    return adapter.send(request);
  }

  void printLog(log) {}
}
