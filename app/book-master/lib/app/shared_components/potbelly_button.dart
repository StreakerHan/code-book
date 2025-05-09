import 'package:daily_task/app/constans/decorations.dart';
import 'package:daily_task/app/constans/sizes.dart';
import 'package:daily_task/app/constans/styles.dart';
import 'package:flutter/material.dart';

class PotbellyButton extends StatelessWidget {
  final double buttonWidth;
  final double buttonHeight;
  final BoxDecoration decoration;
  final String buttonText;
  final TextStyle? buttonTextStyle;
  final GestureTapCallback? onTap;

  PotbellyButton(
    this.buttonText, {
    this.buttonWidth = Sizes.WIDTH_300,
    this.buttonHeight = Sizes.HEIGHT_60,
    this.decoration = Decorations.primaryButtonDecoration,
    this.buttonTextStyle = Styles.normalTextStyle,
    this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: onTap,
      child: Container(
        width: buttonWidth,
        height: buttonHeight,
//        margin: EdgeInsets.only(bottom: 57),
        decoration: decoration,
        child: Column(
          // mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Container(
              padding: EdgeInsets.only(top: 15),
              child: Text(
                buttonText,
                textAlign: TextAlign.center,
                style: buttonTextStyle,
              ),
            )
          ],
        ),
      ),
    );
  }
}
