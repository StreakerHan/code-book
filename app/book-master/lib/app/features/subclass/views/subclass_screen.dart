import 'package:auto_route/auto_route.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/constans/sizes.dart';
import 'package:daily_task/app/constans/styles.dart';
import 'package:daily_task/app/features/search/controllers/search_controller.dart';
import 'package:daily_task/app/features/subclass/controllers/subclass_controller.dart';
import 'package:daily_task/app/model/book_category_list_model.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class SubclassScreen extends GetView<SubclassController> {
  TextStyle subTitleTextStyle = Styles.customNormalTextStyle(
    color: AppColors.accentText,
    fontSize: Sizes.TEXT_SIZE_14,
  );

  TextStyle listTitleTextStyle = Styles.customTitleTextStyle(
    color: AppColors.headingText,
    fontWeight: FontWeight.w600,
    fontSize: Sizes.TEXT_SIZE_18,
  );

  @override
  Widget build(BuildContext context) {
    Get.put(SubclassController());
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0.0,
        leading: InkWell(
          onTap: () => Get.back(),
          child: Image.asset(
            ImageRasterPath.arrowBackIcon,
            color: AppColors.headingText,
          ),
        ),
        centerTitle: true,
        title: Obx(() => Text(
              controller.category.value.name!,
              style: Styles.customTitleTextStyle(
                color: AppColors.headingText,
                fontWeight: FontWeight.w600,
                fontSize: Sizes.TEXT_SIZE_20,
              ),
            )),
      ),
      body: Obx(()=>Container(
          child:ListView.builder(
              physics: AlwaysScrollableScrollPhysics(),
              // padding: EdgeInsets.only(top: setHeight(32), bottom: setHeight(32)),
              itemCount: controller.categoryList.length,
              // controller: scrollController,
              itemBuilder: (BuildContext context, int index) {
                return Container(
                    height: 85,
                    // color: Colors.blue,
                    // margin: EdgeInsets.only(bottom: setHeight(24)),
                    child: ListTile(
                      leading: CachedNetworkImage(
                        width: 100,
                        // height: 120,
                        imageUrl: controller.categoryList[index].img != null
                            ? controller.categoryList[index].img.toString()
                            : controller.categoryList[index].img.toString(),
                        fit: BoxFit.fitHeight,
                      ),
                      title: Text(
                        controller.categoryList[index].name.toString(),
                        overflow: TextOverflow.ellipsis,
                        maxLines: 2,
                        style: TextStyle(fontSize: 12, color: Colors.black),
                      ),
                      subtitle:  Text(
                        controller.categoryList[index].descb.toString(),
                        maxLines: 2,
                        style: subTitleTextStyle,
                      ),
                      trailing: Icon(Icons.arrow_right, size: 20),
                      dense: true,
                      selected: true,
                      onTap: () {
                        Get.toNamed("/books", arguments: {"category": controller.categoryList[index]});
                      },
                    ));
              })
      )),
    );
  }

 
}
