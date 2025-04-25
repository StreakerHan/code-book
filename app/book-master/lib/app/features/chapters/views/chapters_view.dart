import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/features/chapters/controller/chapters_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:get/get.dart';
import 'package:scroll_to_index/scroll_to_index.dart';

///@author longshaohua
///小说目录章节页

class ChaptersView extends GetView<ChaptersController> {
  @override
  Widget build(BuildContext context) {
    Get.put(ChaptersController);
    return Scaffold(
        appBar: AppBar(
          elevation: 0.0,
          titleSpacing: 0,
          centerTitle: true,
          automaticallyImplyLeading: false,
          title: titleView(),
          backgroundColor: AppColors.white,
        ),
        body: Obx(
          () =>
              // DynamicTreeView(
              //   data: controller.bookChaptersData,
              //   config: Config(
              //       parentTextStyle:
              //           TextStyle(color: Colors.black, fontWeight: FontWeight.w600),
              //       parentPaddingEdgeInsets:
              //           EdgeInsets.only(left: 16, top: 0, bottom: 0)),
              //   onTap: (m) {
              //     print("onChildTap -> $m");
              //   },
              //   width: 220.0,
              // )

              ListView.separated(
            itemCount: controller.bookChapters.length,
            controller: controller.autoScrollController,
            itemBuilder: (context, index) {
              return itemView(index);
            },
            separatorBuilder: (context, index) {
              return Padding(
                padding: EdgeInsets.fromLTRB(15, 0, 0, 0),
                child: Divider(height: 1, color: AppColors.dividerDarkColor),
              );
            },
          ),
        ));
  }

  Widget titleView() {
    return Container(
      constraints: BoxConstraints.expand(height: 30),
      child: Stack(
        alignment: Alignment.center,
        children: <Widget>[
          Positioned(
            left: 0,
            child: Material(
              color: Colors.transparent,
              child: InkWell(
                onTap: () {
                  Get.back();
                },
                child: Padding(
                  padding: EdgeInsets.fromLTRB(15, 0, 15, 0),
                  child: Image.asset(
                    'assets/icons/icon_title_back.png',
                    width: 20,
                    height: 18,
                  ),
                ),
              ),
            ),
          ),
          GestureDetector(
            onTap: () {},
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Text(
                  "目 录",
                  style: TextStyle(
                      fontSize: 18,
                      color: AppColors.textBlack6,
                      fontWeight: FontWeight.w600),
                  overflow: TextOverflow.ellipsis,
                ),
                SizedBox(
                  width: 4,
                ),
              ],
            ),
          )
        ],
      ),
    );
  }

  Widget itemView(int index) {
    return AutoScrollTag(
        key: ValueKey(index),
        controller: controller.autoScrollController,
        index: index,
        child: Material(
          color: Colors.transparent,
          child: InkWell(
            onTap: () {
              Get.toNamed("bookContent", arguments: {
                "chapterIndex": index.toString(),
                "bookInfo": controller.bookInfo.value
              });
            },
            child: Padding(
              padding: EdgeInsets.fromLTRB(15, 16, 15, 16),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisSize: MainAxisSize.max,
                children: <Widget>[
                  SizedBox(
                      width: 10.0 *
                          double.parse(controller.bookChapters[index].level!)),
                  Padding(
                    padding: EdgeInsets.fromLTRB(0, 4, 0, 0),
                    child: Text(
                      controller.bookChapters[index].seq! + "、",
                      style:
                          TextStyle(fontSize: 12, color: AppColors.textBlack3),
                    ),
                  ),
                  Expanded(
                    child: Text(
                      controller.bookChapters[index].catalogueName!,
                      style:
                          TextStyle(fontSize: 15, color: AppColors.textBlack3),
                    ),
                  ),
                  // true
                  //     ? Image.asset(
                  //         "assets/icons/icon_chapters_vip.png",
                  //         width: 16,
                  //         height: 16,
                  //       )
                  //     : Container(),
                ],
              ),
            ),
          ),
        ));
  }
}
