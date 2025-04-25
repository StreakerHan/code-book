
import 'package:daily_task/app/constans/styles.dart';
import 'package:daily_task/app/shared_components/responsive_builder.dart';
import 'package:flutter/material.dart';

class UpgradeProSection extends StatelessWidget {
  const UpgradeProSection({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: Styles.defaultYellowColor,
        borderRadius: Styles.defaultBorderRadius,
      ),
      padding: EdgeInsets.symmetric(horizontal: Styles.defaultPadding),
      child: Row(
        children: [
          Expanded(
            flex: 2,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                FittedBox(
                  fit: BoxFit.fitWidth,
                  child: RichText(
                    text: TextSpan(
                      style: const TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.black,
                        fontSize: 18,
                      ),
                      children: [
                        const TextSpan(
                          text: "Upgrade your account to ",
                        ),
                        TextSpan(
                          text: "PRO+",
                          style: TextStyle(
                            color: Styles.defaultRedColor,
                          ),
                        )
                      ],
                    ),
                  ),
                ),
                Visibility(
                  visible: !ResponsiveBuilder.isMobile(context),
                  child: Flexible(
                    child: Padding(
                      padding: const EdgeInsets.only(top: 10.0),
                      child: RichText(
                        text: TextSpan(
                          style: const TextStyle(
                            color: Colors.black,
                          ),
                          children: [
                            const TextSpan(text: "With a "),
                            TextSpan(
                              text: "PRO+ ",
                              style: TextStyle(
                                color: Styles.defaultRedColor,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            const TextSpan(
                              text:
                              "account you get many additional and convenient features to control your finances.",
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
          Expanded(
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Align(
                alignment: Alignment.centerRight,
                child: Image.asset("assets/images/astranaut.png",width: 30,height: 30,),
              ),
            ),
          ),
          Expanded(
            child: Align(
              alignment: Alignment.center,
              child: Container(
                decoration: const BoxDecoration(
                  color: Colors.white,
                  shape: BoxShape.circle,
                ),
                height: 40,
                width: 40,
                child: IconButton(
                  icon: const Icon(Icons.chevron_right),
                  onPressed: () {},
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}