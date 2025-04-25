import 'package:daily_task/app/features/books/controllers/books_controller.dart';
import 'package:daily_task/app/features/search/controllers/search_controller.dart';
import 'package:get/get.dart';

class BooksBinding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<BooksController>(
          () => BooksController(),
    );
  }
}
