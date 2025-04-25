import 'package:animated_snack_bar/animated_snack_bar.dart';


/// 警告通知
void showWarnToast(String text) {
  AnimatedSnackBar.material(
    text,
    type: AnimatedSnackBarType.warning,
  );
}

/// 正常通知
void showToast(String text) {
  AnimatedSnackBar.material(
    text,
    type: AnimatedSnackBarType.success,
  );
}
