import 'package:cached_network_image/cached_network_image.dart';
import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/features/books/controllers/books_controller.dart';
import 'package:daily_task/app/features/collect/controllers/collect_controller.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../../constans/sizes.dart';
import '../../../constans/styles.dart';

class CollectView extends GetView<CollectController> {

  @override
  Widget build(BuildContext context) {
    Get.put(CollectController());
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0.0,
        centerTitle: true,
        leading: InkWell(
          onTap: () => Get.back(),
          child: Image.asset(
            ImageRasterPath.arrowBackIcon,
            color: AppColors.headingText,
          ),
        ),
        title: Text(
          "我的收藏夹",
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
      body: SafeArea(
          child: Column(
            children: <Widget>[
              // titleView(),
              Divider(height: 1, color: AppColors.dividerDarkColor),
              Expanded(
                child: contentView(),
              ),
            ],
          )),
    );
  }

  Widget titleView() {
    return Container(
      alignment: Alignment.center,
      constraints: BoxConstraints.expand(height: 48),
      child: Flex(
        direction: Axis.horizontal,
        children: <Widget>[
          Material(
            color: Colors.transparent,
            child: InkWell(
              onTap: () {
                Get.back();
              },
              child: Padding(
                padding: EdgeInsets.fromLTRB(15, 0, 15, 0),
                child: Image.asset(
                  'assets/icons/icon_title_back.png',
                  color: AppColors.black,
                  width: 20,
                  height: 48,
                ),
              ),
            ),
          ),
          Expanded(
              child: Container(
                height: 36,
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.all(Radius.circular(5)),
                  color: AppColors.homeGrey,
                ),
                padding: EdgeInsets.fromLTRB(7, 5, 0, 0),
                child: TextField(
                  controller: controller.textEditingController,
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.search,
                  textAlign: TextAlign.start,
                  onSubmitted: (s) {
                    // getData(_controller.text);
                  },
                  decoration: InputDecoration(
                      isDense: true,
                      icon: Image.asset(
                        "assets/icons/icon_home_search.png",
                        width: 15,
                        height: 15,
                      ),
                      hintText: "输入关键字搜索",
                      contentPadding: const EdgeInsets.symmetric(vertical: 5),
                      border: InputBorder.none),
                  style: TextStyle(
                    color: AppColors.textBlack6,
                    fontSize: 13,
                  ),
                ),
              )),
          MaterialButton(
            minWidth: 10,
            onPressed: () {
              controller.getBookList();
            },
            child: Text(
              "搜索",
              style: TextStyle(fontSize: 14, color: AppColors.textPrimaryColor),
            ),
            height: 48,
          )
        ],
      ),
    );
  }

  Widget contentView() {
    return Obx(()=>ListView.builder(
      itemBuilder: (context, index) => _buildListViewItem(index),
      padding: EdgeInsets.fromLTRB(15, 0, 15, 0),
      itemCount: controller.bookData.length,
    ));
  }

  Widget _buildListViewItem(int position) {
    return InkWell(
      onTap: () {
        Get.toNamed("bookInfo", arguments: {
          "bookInfo": controller.bookData[position]
        });
      },
      child: Container(
        padding: EdgeInsets.fromLTRB(20, 12, 20, 12),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            CachedNetworkImage(
              imageUrl:controller.bookData[position].img!,
              height: 99,
              width: 77,
              fit: BoxFit.cover,
            ),
            SizedBox(
              width: 12,
            ),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Text(
                    controller.bookData[position].name!,
                    maxLines: 2,
                    style: TextStyle(color: AppColors.textBlack3, fontSize: 14),
                  ),
                  SizedBox(height: 6),
                  Text(
                    controller.bookData[position].descb!,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                    style: TextStyle(color: AppColors.textBlack6, fontSize: 12),
                  ),
                  SizedBox(
                    height: 5,
                  ),
                  Row(
                    mainAxisSize: MainAxisSize.max,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Expanded(
                          child: Text(
                            controller.bookData[position].typeName!,
                            style: TextStyle(
                              color: AppColors.textBlack9,
                              fontSize: 14,
                            ),
                          )),
                      controller.bookData[position].tags!.split(",").toList() !=
                          null &&
                          controller.bookData[position].tags!
                              .split(",")
                              .toList()
                              .length >
                              0
                          ? tagView(controller.bookData[position].tags!
                          .split(",")
                          .toList()[0])
                          : tagView('限免'),
                      controller.bookData[position].tags!.split(",").toList() !=
                          null &&
                          controller.bookData[position].tags!
                              .split(",")
                              .toList()
                              .length >
                              1
                          ? SizedBox(
                        width: 4,
                      )
                          : SizedBox(),
                      controller.bookData[position].tags!.split(",").toList() !=
                          null &&
                          controller.bookData[position].tags!
                              .split(",")
                              .toList()
                              .length >
                              1
                          ? tagView(controller.bookData[position].tags!
                          .split(",")
                          .toList()[1])
                          : SizedBox(),
                    ],
                  ),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }

  Widget tagView(String tag) {
    return Container(
      padding: EdgeInsets.fromLTRB(5, 0, 5, 0),
      alignment: Alignment.center,
      child: Text(
        tag,
        style: TextStyle(color: Color(0xFF6A6C7A), fontSize: 11),
      ),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.all(Radius.circular(3)),
          border: Border.all(width: 0.5, color: Colors.black12)),
    );
  }

  String getWordCount(int wordCount) {
    if (wordCount > 10000) {
      return (wordCount / 10000).toStringAsFixed(1) + "万字";
    }
    return wordCount.toString() + "字";
  }
}
