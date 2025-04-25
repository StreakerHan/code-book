import 'package:auto_route/auto_route.dart';
import 'package:daily_task/app/constans/data.dart';
import 'package:daily_task/app/constans/sizes.dart';
import 'package:daily_task/app/constans/spaces.dart';
import 'package:daily_task/app/constans/styles.dart';
import 'package:daily_task/app/features/category/controllers/category_controller.dart';
import 'package:daily_task/app/features/category/views/components/category_card.dart';
import 'package:daily_task/app/features/home/views/components/banner_card.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';

import '../../../../constans/app_constants.dart';
import '../../../../constans/colors.dart';

class CategoriesScreen extends GetView<CategoryController> {
  const CategoriesScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var textTheme = Theme.of(context).textTheme;
    Get.put(CategoryController());
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0.0,
        centerTitle: true,
        title: Text(
          "分类",
          style: Styles.customTitleTextStyle(
            color: AppColors.headingText,
            fontWeight: FontWeight.w600,
            fontSize: Sizes.TEXT_SIZE_20,
          ),
        ),
        // actions: <Widget>[
        //   InkWell(
        //     onTap: () {},
        //     child: Image.asset(
        //       ImageRasterPath.searchIcon,
        //       color: AppColors.headingText,
        //     ),
        //   )
        // ],
      ),
      body: Container(
          margin: EdgeInsets.symmetric(
              horizontal: Sizes.MARGIN_16, vertical: Sizes.MARGIN_16),
          child: Obx(
            () => ListView.separated(
              itemCount: controller.categoryList.length,
              separatorBuilder: (context, index) {
                return SpaceH12();
              },
              itemBuilder: (context, index) {
                return Column(
                  children: [
                    Container(
                      child: FoodyBiteCategoryCard(
                        onTap: () => {
                          Get.toNamed("subclass", arguments: {
                            "category": controller.categoryList[index]
                          })
                        },
                        width: MediaQuery.of(context).size.width,
                        imagePath: categoryListImagePaths[index],
                        gradient: gradients[index],
                        category: controller.categoryList[index].name!,
                        hasHandle: true,
                        opacity: 0.85,
                      ),
                    ),
                    SizedBox(
                      height: 10,
                    ),
                  ],
                );
              },
            ),
          )),
    );
  }

  Widget _banner() {
    return Container(
      width: double.infinity,
      height: 100,
      child: AdWidget(
        ad: controller.banner!,
      ),
    );
  }
}
