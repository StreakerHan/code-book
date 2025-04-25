import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/constans/gradients.dart';
import 'package:flutter/material.dart';

import 'sizes.dart';

class Decorations {

  static const BoxDecoration primaryButtonDecoration = BoxDecoration(
    color: AppColors.secondaryElement,
    boxShadow: [
      Shadows.secondaryShadow,
    ],
    borderRadius: BorderRadius.all(Radius.circular(Sizes.RADIUS_8)),
  );

  static const BoxDecoration categoryButtonDecoration = BoxDecoration(
      gradient: Gradients.secondaryGradient2,
      boxShadow: [
        Shadows.secondaryShadow,
      ],
    borderRadius: BorderRadius.all(
      Radius.circular(Sizes.RADIUS_8),
    )
  );

  static const BoxDecoration halfButtonDecoration = BoxDecoration(
    color: AppColors.secondaryElement,
    boxShadow: [
      Shadows.secondaryShadow,
    ],
    borderRadius: BorderRadius.only(topLeft: Radius.circular(24.0)),
  );

  static BoxDecoration customDecoration({
    Color color = AppColors.secondaryElement,
    List<BoxShadow> boxShadow = const [Shadows.secondaryShadow],
    double borderRadius = Sizes.RADIUS_12,
  }) {
    return BoxDecoration(
      color: color,
      boxShadow: boxShadow,
      borderRadius: BorderRadius.all(Radius.circular(borderRadius)),
    );
  }

  static BoxDecoration customHalfCurvedButtonDecoration({
    Color color = AppColors.homeTabText,
    List<BoxShadow> boxShadow = const [Shadows.secondaryShadow],
    double topleftRadius = 0,
    double topRightRadius = 0,
    double bottomleftRadius = 0,
    double bottomRightRadius = 0,
  }) {
    return BoxDecoration(
      color: color,
      boxShadow: boxShadow,
      borderRadius: BorderRadius.only(
        topLeft: Radius.circular(topleftRadius),
        topRight: Radius.circular(topRightRadius),
        bottomLeft: Radius.circular(bottomleftRadius),
        bottomRight: Radius.circular(bottomRightRadius),
      ),
//      border: Border(
//        right: BorderSide(width: 4.0, color: AppColors.primaryColor),
////        bottom: BorderSide(width: 16.0, color: AppColors.primaryColor),
//      ),
    );
  }

  static const BoxDecoration regularDecoration = BoxDecoration(
    color: Color.fromARGB(255, 255, 255, 255),
  );

  static BoxDecoration customRegularDecoration({
    Color color = const Color.fromARGB(255, 255, 255, 255),
  }) {
    return BoxDecoration(color: color);
  }

  static const BoxDecoration horizontalBarDecoration = BoxDecoration(
    color: Color.fromARGB(255, 248, 249, 255),
  );
}

class Shadows {
  static const BoxShadow primaryShadow = BoxShadow(
    color: Color.fromARGB(41, 0, 0, 0),
    offset: Offset(0, 1),
    blurRadius: 2,
  );

  static const BoxShadow secondaryShadow = BoxShadow(
    color: Color.fromARGB(13, 0, 0, 0),
    offset: Offset(0, 3.33333),
    blurRadius: 33,
  );


}
