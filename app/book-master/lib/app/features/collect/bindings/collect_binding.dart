import 'package:get/get.dart';

import '../controllers/collect_controller.dart';

class CollectBinding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<CollectController>(
          () => CollectController(),
    );
  }
}
