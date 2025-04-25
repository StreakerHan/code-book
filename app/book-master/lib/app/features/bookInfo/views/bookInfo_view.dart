import 'dart:async';

import 'package:auto_route/auto_route.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/constans/decorations.dart';
import 'package:daily_task/app/constans/sizes.dart';
import 'package:daily_task/app/features/bookInfo/controllers/bookInfo_controller.dart';
import 'package:daily_task/app/shared_components/potbelly_button.dart';
import 'package:daily_task/app/shared_components/static_rating_bar.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';
import 'package:hexcolor/hexcolor.dart';

///@author longshaohua
///详情页

class BookInfoView extends GetView<BookInfoController> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: HexColor("#605166"),
          title: Text("详情"),
          elevation: 0.0,
          // actions: <Widget>[
          //   InkWell(
          //       onTap: () {},
          //       child: Container(
          //         padding: EdgeInsets.only(right: 20),
          //         child: Image.asset(
          //           'assets/icons/icon_share.png',
          //           color: AppColors.white,
          //           width: 20,
          //           height: 20,
          //         ),
          //       ))
          // ],
        ),
        backgroundColor: AppColors.white,
        body: Container(
          child: Column(children: <Widget>[
            Expanded(
              child: childLayout(),
            ),
            PotbellyButton(
              '开 始 阅 读',
              onTap: () {
                Get.toNamed("bookContent", arguments: {
                  "chapterIndex": "0",
                  "bookInfo": controller.bookInfo.value
                });
              },
              buttonHeight: 85,
              buttonWidth: MediaQuery.of(context).size.width,
              decoration: Decorations.customHalfCurvedButtonDecoration(
                topleftRadius: Sizes.RADIUS_24,
                topRightRadius: Sizes.RADIUS_24,
              ),
            ),
          ]),
        ));
  }

  Widget childLayout() {
    return Stack(
      alignment: Alignment.topLeft,
      children: <Widget>[
        contentView(),
        // titleView(),
      ],
    );
  }

  Widget titleView() {
    return Container(
      color: Color.fromARGB(0, 255, 255, 255),
      constraints: BoxConstraints.expand(height: 48),
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
                      color: Color.fromARGB(255, 255, 255, 255),
                      width: 20,
                      height: 48,
                    ),
                  ),
                ),
              )),
          const Text(
            "资料名",
            style: TextStyle(fontSize: 18, color: Color.fromARGB(0, 0, 0, 0)),
            overflow: TextOverflow.ellipsis,
          ),
          Positioned(
            right: 0,
            child: Material(
              color: Colors.transparent,
              child: InkWell(
                onTap: () {},
                child: Padding(
                  padding: EdgeInsets.fromLTRB(15, 0, 15, 0),
                  child: Image.asset(
                    'assets/icons/icon_share.png',
                    color: Color.fromARGB(255, 255, 255, 255),
                    width: 18,
                    height: 48,
                  ),
                ),
              ),
            ),
          ),
          Positioned(
            bottom: 0,
            left: 0,
            right: 0,
            child: Offstage(
              offstage: true,
              child: Divider(height: 1, color: AppColors.dividerDarkColor),
            ),
          ),
        ],
      ),
    );
  }

  Widget contentView() {
    return SingleChildScrollView(
      controller: controller.scrollController,
      scrollDirection: Axis.vertical,
      child: Column(
        children: <Widget>[
          coverView(),
          bodyView(),
          Container(
            height: 14,
            color: AppColors.dividerColor,
          ),
          Padding(
              padding: EdgeInsets.fromLTRB(15, 20, 15, 20),
              child: Text(
                controller.bookInfo.value.descb!,
                style: TextStyle(fontSize: 14, color: AppColors.black),
              )),
          Container(
            height: 14,
            color: AppColors.dividerColor,
          ),
          // Padding(
          //   padding: EdgeInsets.fromLTRB(15, 12, 15, 12),
          //   child: Row(
          //     crossAxisAlignment: CrossAxisAlignment.center,
          //     children: <Widget>[
          //       Text(
          //         "最新书评",
          //         style: TextStyle(fontSize: 15, color: AppColors.textBlack3),
          //       ),
          //       Expanded(
          //         child: Container(),
          //       ),
          //       Padding(
          //         padding: EdgeInsets.fromLTRB(0, 1, 3, 0),
          //         child: Image.asset(
          //           'assets/icons/icon_info_edit.png',
          //           width: 16,
          //           height: 16,
          //         ),
          //       ),
          //       Text(
          //         "写书评",
          //         style: TextStyle(fontSize: 18, color: Color(0xFF33C3A5)),
          //       )
          //     ],
          //   ),
          // ),
          // commentList(),
          // Container(
          //   child: Text(
          //     "查看更多评论（268）",
          //     style: TextStyle(color: AppColors.textPrimaryColor, fontSize: 18),
          //   ),
          //   padding: EdgeInsets.fromLTRB(0, 12, 0, 12),
          // ),
          Container(
            alignment: Alignment.center,
            color: AppColors.white,
            child: Text(
              "上架时间：" + controller.bookInfo.value.createTime!,
              style: TextStyle(color: AppColors.textBlack9, fontSize: 12),
            ),
            padding: EdgeInsets.fromLTRB(0, 14, 0, 68),
          ),
          Container(
            height: 14,
          ),
          _banner()
        ],
      ),
    );
  }

  ///封面view
  Widget coverView() {
    return Container(
      color: AppColors.infoBgColor,
      padding: EdgeInsets.fromLTRB(15, 10, 15, 20),
      child: Row(
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          CachedNetworkImage(
            imageUrl: controller.bookInfo.value.img!,
            height: 137,
            width: 100,
            fit: BoxFit.cover,
          ),
          SizedBox(
            width: 14,
          ),
          Expanded(
            child: Column(
              mainAxisSize: MainAxisSize.max,
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text(
                  controller.bookInfo.value.name!,
                  maxLines: 2,
                  style: TextStyle(fontSize: 18, color: AppColors.white),
                  overflow: TextOverflow.ellipsis,
                ),
                SizedBox(
                  height: 10,
                ),
                Text(
                  controller.bookInfo.value.descb!,
                  maxLines: 2,
                  overflow: TextOverflow.ellipsis,
                  style: TextStyle(fontSize: 12, color: AppColors.white),
                ),
                SizedBox(
                  height: 41,
                ),
                Row(
                  mainAxisSize: MainAxisSize.max,
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Container(
                      width: 250,
                      padding: EdgeInsets.fromLTRB(0, 0, 11, 0),
                      child: Text(
                        controller.bookInfo.value.tags!,
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                        style: const TextStyle(
                            fontSize: 13, color: AppColors.kFoodyBiteYellow),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          )
        ],
      ),
    );
  }

  Widget bodyView() {
    return Row(
      mainAxisSize: MainAxisSize.max,
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        Obx(()=>bodyChildView(
            controller.isAddBookshelf.value == "1"
                ? 'assets/icons/icon_details_bookshelf_add.png'
                : 'assets/icons/icon_details_bookshelf.png',
            controller.isAddBookshelf.value == "1" ? "已在收藏夹" : "加入收藏夹",
            0)),
        bodyChildView('assets/icons/icon_details_chapter.png',
            controller.bookInfo.value.chapters.toString() + "章", 1),
        // bodyChildView('assets/icons/icon_details_reward.png', "支持作品", 2),
        // bodyChildView('assets/icons/icon_details_download.png', "批量下载", 3),
      ],
    );
  }

  Widget bodyChildView(String img, String content, int tap) {
    return Expanded(
      flex: 1,
      child: GestureDetector(
        onTap: () {
          if (tap == 0) {
            print("collect");
            controller.collect();
          }
          if (tap == 1) {
            Get.toNamed("chapters",
                arguments: {"bookInfo": controller.bookInfo.value});
          }
        },
        child: Container(
          alignment: Alignment.center,
          padding: EdgeInsets.fromLTRB(0, 12, 0, 12),
          child: Column(
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Image.asset(
                img,
                width: 34,
                height: 34,
                fit: BoxFit.contain,
              ),
              Text(
                content,
                style: TextStyle(color: AppColors.textBlack3, fontSize: 15),
              ),
            ],
          ),
        ),
      ),
    );
  }

  //评论列表
  Widget commentList() {
    return Padding(
      padding: EdgeInsets.fromLTRB(15, 0, 15, 0),
      child: Column(
        children: <Widget>[
          itemView("嘻嘻", "求更新，不够看", 4.5, "9", true),
          itemView("书友805699513", "不错不错。", 5, "8", false),
          itemView("书友007", "没看先点赞", 5, "5", true),
          itemView("书友00888", "好文章不错，就是更新太慢了。", 3, "1", false),
          itemView("书友00666", "打卡", 5, "9", true),
        ],
      ),
    );
  }

  Widget itemView(
      String name, String content, double rate, String likeNum, bool image) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Row(
          children: <Widget>[
            new ClipOval(
              child: new SizedBox(
                width: 32,
                height: 32,
                child: new Image.asset("assets/icons/icon_default_avatar.png"),
              ),
            ),
            Padding(
              padding: EdgeInsets.fromLTRB(10, 0, 0, 0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Text(name,
                      style:
                          TextStyle(color: AppColors.textBlack6, fontSize: 14)),
                  Padding(
                    padding: EdgeInsets.fromLTRB(0, 5, 0, 0),
                    child: new StaticRatingBar(
                      size: 10,
                      rate: rate,
                      count: 2,
                    ),
                  )
                ],
              ),
            )
          ],
        ),
        Padding(
          padding: EdgeInsets.fromLTRB(0, 14, 0, 14),
          child: Text(
            content,
            style: TextStyle(color: AppColors.textBlack3, fontSize: 14),
          ),
        ),
        Row(
          children: <Widget>[
            Text(
              "2019.05.09",
              style: TextStyle(color: AppColors.textBlack9, fontSize: 12),
            ),
            Expanded(
              child: Container(),
            ),
            GestureDetector(
              child: image
                  ? Image.asset(
                      "assets/icons/icon_like_true.png",
                      width: 18,
                      height: 18,
                    )
                  : Image.asset(
                      "assets/icons/icon_like_false.png",
                      width: 18,
                      height: 18,
                    ),
              onTap: () {},
            ),
            Padding(
              padding: EdgeInsets.fromLTRB(2, 0, 20, 0),
              child: Text(
                likeNum,
                style: TextStyle(color: AppColors.textBlack9, fontSize: 12),
              ),
            ),
            Image.asset(
              "assets/icons/icon_comment.png",
              width: 18,
              height: 18,
            ),
          ],
        ),
        SizedBox(
          height: 18,
        )
      ],
    );
  }

  String getWordCount(int wordCount) {
    if (wordCount > 10000) {
      return (wordCount / 10000).toStringAsFixed(1) + "万字";
    }
    return wordCount.toString() + "字";
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
