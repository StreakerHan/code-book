import 'package:daily_task/app/features/aboutApp/bindings/about_app_binding.dart';
import 'package:daily_task/app/features/aboutApp/views/about_app_view.dart';
import 'package:daily_task/app/features/bookContent/bindings/bookContent_binding.dart';
import 'package:daily_task/app/features/bookContent/views/bookContent_view.dart';
import 'package:daily_task/app/features/bookInfo/bindings/BookInfo_binding.dart';
import 'package:daily_task/app/features/bookInfo/views/bookInfo_view.dart';
import 'package:daily_task/app/features/books/bindings/books_binding.dart';
import 'package:daily_task/app/features/books/views/books_view.dart';
import 'package:daily_task/app/features/category/bindings/category_binding.dart';
import 'package:daily_task/app/features/category/views/screens/category_screen.dart';
import 'package:daily_task/app/features/chapters/bindings/chapters_binding.dart';
import 'package:daily_task/app/features/chapters/views/chapters_view.dart';
import 'package:daily_task/app/features/collect/bindings/collect_binding.dart';
import 'package:daily_task/app/features/collect/views/collect_view.dart';
import 'package:daily_task/app/features/index/bindings/index_binding.dart';
import 'package:daily_task/app/features/index/views/index_view.dart';
import 'package:daily_task/app/features/login/bindings/login_binding.dart';
import 'package:daily_task/app/features/login/views/login_view.dart';
import 'package:daily_task/app/features/record/bindings/record_binding.dart';
import 'package:daily_task/app/features/record/views/record_view.dart';
import 'package:daily_task/app/features/search/bindings/search_binding.dart';
import 'package:daily_task/app/features/search/views/search_view.dart';
import 'package:daily_task/app/features/signup/bindings/reg_binding.dart';
import 'package:daily_task/app/features/signup/views/reg_view.dart';
import 'package:daily_task/app/features/subclass/bindings/subclass_binding.dart';
import 'package:daily_task/app/features/subclass/views/subclass_screen.dart';
import 'package:daily_task/app/features/user/bindings/user_binding.dart';
import 'package:daily_task/app/features/userEmail/bindings/user_email_binding.dart';
import 'package:daily_task/app/features/userEmail/views/user_email_view.dart';
import 'package:daily_task/app/features/userInfo/bindings/user_info_binding.dart';
import 'package:daily_task/app/features/userInfo/views/user_info_view.dart';
import 'package:daily_task/app/features/userPwd/bindings/user_pwd_binding.dart';
import 'package:daily_task/app/features/userPwd/views/user_pwd_view.dart';
import 'package:daily_task/app/features/userUsername/bindings/user_username_binding.dart';
import 'package:daily_task/app/features/userUsername/views/user_username_view.dart';
import 'package:daily_task/app/features/welcome/intro_screen.dart';
import 'package:daily_task/app/utils/OpenMiddleware.dart';

import '../../features/home/views/screens/dashboard_screen.dart';
import 'package:get/get.dart';

part 'app_routes.dart';

/// contains all configuration pages
class AppPages {

  AppPages._();

  /// when the app is opened, this page will be the first to be shown
  static const initial = Routes.index;

  static const category = Routes.category;

  static const signup = Routes.signup;

  static const login = Routes.login;

  static const welcome = Routes.welcome;

  static const search = Routes.search;

  static const bookInfo = Routes.bookInfo;

  static const chapters = Routes.chapters;

  static const bookContent = Routes.bookContent;

  static const subclass = Routes.subclass;

  static const books = Routes.books;

  static const userInfo = Routes.userInfo;

  static const userEmail = Routes.userEmail;

  static const userUsername = Routes.userUsername;

  static const userPwd = Routes.userPwd;

  static const aboutApp = Routes.aboutApp;

  static const collect = Routes.collect;

  static const record = Routes.record;

  static final routes = [
    GetPage(
        name: _Paths.index,
        page: () => IndexView(),
        binding: IndexBinding(),
        transition: Transition.fadeIn,
        middlewares: [OpenMiddleware()]),
    GetPage(
      name: _Paths.dashboard,
      page: () => DashboardScreen(),
      binding: DashboardBinding(),
    ),
    GetPage(
      name: _Paths.category,
      page: () => CategoriesScreen(),
      binding: CategoryBinding(),
    ),
    GetPage(
      name: _Paths.signup,
      page: () => RegView(),
      binding: RegBinding(),
    ),
    GetPage(
      name: _Paths.login,
      page: () => LoginView(),
      binding: LoginBinding(),
    ),
    GetPage(
      name: _Paths.login,
      page: () => LoginView(),
      binding: LoginBinding(),
    ),
    GetPage(
      name: _Paths.welcome,
      page: () => IntroScreenDefault(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.search,
      page: () => SearchView(),
      binding: SearchBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.bookInfo,
      page: () => BookInfoView(),
      binding: BookInfoBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.chapters,
      page: () => ChaptersView(),
      binding: ChaptersBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.bookContent,
      page: () => BookContentView(),
      binding: BookContentBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.subclass,
      page: () => SubclassScreen(),
      binding: SubclassBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.books,
      page: () => BooksView(),
      binding: BooksBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.userInfo,
      page: () => UserInfoView(),
      binding: UserInfoBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.userEmail,
      page: () => UserEmailView(),
      binding: UserEmailBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.userUsername,
      page: () => UserUsernameView(),
      binding: UserUsernameBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.userPwd,
      page: () => UserPwdView(),
      binding: UserPwdBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.aboutApp,
      page: () => AboutAppView(),
      binding: AboutAppBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.collect,
      page: () => CollectView(),
      binding: CollectBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
    GetPage(
      name: _Paths.record,
      page: () => RecordView(),
      binding: RecordBinding(),
      transition: Transition.fadeIn,
      // middlewares: [OpenMiddleware()]
    ),
  ];
}
