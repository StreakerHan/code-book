import 'dart:ffi';

import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/features/index/controllers/index_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:text_scroll/text_scroll.dart';

// ignore: must_be_immutable
class Categories extends StatelessWidget {
  IndexController controller = Get.find<IndexController>();
  List<Map<String, dynamic>> categories = [
    {
      "icon": "assets/icons/Flash Icon.svg",
      "text": "Flash DealFlash DealFlash Deal"
    },
    {"icon": "assets/icons/Bill Icon.svg", "text": "Bill"},
    {"icon": "assets/icons/Game Icon.svg", "text": "Game"},
    {"icon": "assets/icons/Gift Icon.svg", "text": "Daily Gift"},
    {"icon": "assets/icons/ellipsis.svg", "text": "More"},
  ];

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: kSpacing),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          ...List.generate(
            categories.length,
            (index) => CategoryCard(
              icon: categories[index]['icon'],
              text: categories[index]['text'],
              press: () {
                print(index);
                if (index == 4) {
                  controller.controller.jumpToPage(1);
                }
              },
            ),
          ),
        ],
      ),
    );
  }
}

class CategoryCard extends StatelessWidget {
  const CategoryCard({
    Key? key,
    required this.icon,
    required this.text,
    required this.press,
  }) : super(key: key);

  final String icon, text;
  final GestureTapCallback press;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: press,
      child: SizedBox(
        width: 55,
        child: Column(
          children: [
            AspectRatio(
              aspectRatio: 1,
              child: Container(
                padding: EdgeInsets.symmetric(horizontal: 15),
                decoration: BoxDecoration(
                  color: Color(0xFFDFECFF),
                  borderRadius: BorderRadius.circular(10),
                ),
                child: SvgPicture.asset(
                  icon,
                  color: AppColors.secondaryElement.withOpacity(.8),
                ),
              ),
            ),
            SizedBox(
              height: 5,
            ),
            TextScroll(
              text,
              textAlign: TextAlign.center,
              velocity: Velocity(pixelsPerSecond: Offset(30, 0)),
              pauseBetween: Duration(milliseconds: 1000),
              mode: TextScrollMode.endless,
              // maxLines: 1,
              style: const TextStyle(
                fontSize: 11,
                letterSpacing: 1,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
