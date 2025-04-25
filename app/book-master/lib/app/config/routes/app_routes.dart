part of 'app_pages.dart';

/// used to switch pages
class Routes {
  Routes._();

  static const dashboard = _Paths.dashboard;
  static const category = _Paths.category;
  static const index = _Paths.index;
  static const signup = _Paths.signup;
  static const login = _Paths.login;
  static const welcome = _Paths.welcome;
  static const search = _Paths.search;
  static const bookInfo = _Paths.bookInfo;
  static const chapters = _Paths.chapters;
  static const bookContent = _Paths.bookContent;
  static const subclass = _Paths.subclass;
  static const books = _Paths.books;
  static const userInfo = _Paths.userInfo;
  static const userEmail = _Paths.userEmail;
  static const userUsername = _Paths.userUsername;
  static const userPwd = _Paths.userPwd;
  static const aboutApp = _Paths.aboutApp;
  static const collect = _Paths.collect;
  static const record = _Paths.record;
}

/// contains a list of route names.
// made separately to make it easier to manage route naming
class _Paths {
  static const dashboard = '/home';
  static const category = '/category';
  static const index = '/index';
  static const signup = '/signup';
  static const login = '/login';
  static const welcome = '/welcome';
  static const search = '/search';
  static const bookInfo = '/bookInfo';
  static const chapters = '/chapters';
  static const bookContent = '/bookContent';
  static const subclass = '/subclass';
  static const books = '/books';
  static const userInfo = '/userInfo';
  static const userEmail = '/userEmail';
  static const userUsername = '/userUsername';
  static const userPwd = '/userPwd';
  static const aboutApp = '/aboutApp';
  static const collect = '/collect';
  static const record = '/record';

// Example :
// static const index = '/';
// static const splash = '/splash';
// static const product = '/product';
}
