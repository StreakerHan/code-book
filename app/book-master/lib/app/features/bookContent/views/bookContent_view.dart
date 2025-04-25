import 'dart:async';

import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/features/bookContent/controllers/bookContent_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:flutter_markdown/flutter_markdown.dart';
import 'package:get/get.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:scroll_to_index/scroll_to_index.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:url_launcher/url_launcher.dart';

///@author longshaohua
///小说内容浏览页

class BookContentView extends GetView<BookContentController> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: AppColors.white,
          title: Obx(() => Text(
                controller.bookContentList.length != 0 &&
                        controller.bookContentList[0].catalogueName != null
                    ? controller.bookContentList[0].catalogueName.toString()
                    : "",
                style: TextStyle(color: AppColors.black),
              )),
          elevation: 0.0,
          leading: InkWell(
            onTap: () {
              Get.back();
            },
            child: Image.asset(
              ImageRasterPath.arrowBackIcon,
              color: AppColors.headingText,
            ),
          ),
        ),
        key: controller.scaffoldKey,
        backgroundColor: Colors.white,
        //侧滑菜单显示章节
        endDrawer: Drawer(
          child: Column(
            mainAxisSize: MainAxisSize.max,
            children: <Widget>[
              Container(
                height: 50,
                color: AppColors.white,
                child: Container(),
              ),

              /// 章节目录 list
              Expanded(
                child: Obx(() => ListView.separated(
                      itemCount: controller.bookChapters.length,
                      controller: controller.autoScrollController,
                      itemBuilder: (context, index) {
                        return itemView(index);
                      },
                      separatorBuilder: (context, index) {
                        return Padding(
                          padding: EdgeInsets.fromLTRB(14, 0, 0, 0),
                          child: Divider(
                              height: 1, color: AppColors.dividerDarkColor),
                        );
                      },
                    )),
              )
            ],
          ),
        ),
        body: Obx(
          () => Stack(
            children: <Widget>[
              GestureDetector(
                  onTap: () {
                    print("点击");
                  },
                  child: SingleChildScrollView(
                    controller: controller.scrollController,
                    padding: EdgeInsets.fromLTRB(
                      16,
                      0,
                      9,
                      0,
                    ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: <Widget>[
                        const SizedBox(
                          height: 16,
                        ),
                        Html(
                          data: controller.bookContentList.isNotEmpty &&
                                  controller.bookContentList[0].content != null
                              ? controller.bookContentList[0].content.toString()
                              : "",
                          tagsList: Html.tags..addAll(["bird", "flutter"]),
                          style: {
                            "code":Style(
                              backgroundColor:
                              AppColors.whiteShade_50,
                              // height: 0.5,
                            ),
                            "table": Style(
                              backgroundColor:
                                  Color.fromARGB(0x50, 0xee, 0xee, 0xee),
                            ),
                            "tr": Style(
                              border: Border(
                                  bottom: BorderSide(color: Colors.grey)),
                            ),
                            "th": Style(
                              padding: EdgeInsets.all(6),
                              backgroundColor: Colors.grey,
                            ),
                            "td": Style(
                              padding: EdgeInsets.all(6),
                              alignment: Alignment.topLeft,
                            ),
                            'h5': Style(
                                maxLines: 2,
                                textOverflow: TextOverflow.ellipsis),
                          },
                          customRender: {
                            "table": (context, child) {
                              return SingleChildScrollView(
                                scrollDirection: Axis.horizontal,
                                child: (context.tree as TableLayoutElement)
                                    .toWidget(context),
                              );
                            },
                            "bird": (RenderContext context, Widget child) {
                              return TextSpan(text: "🐦");
                            },
                            "flutter": (RenderContext context, Widget child) {
                              return FlutterLogo(
                                style: (context.tree.element!
                                            .attributes['horizontal'] !=
                                        null)
                                    ? FlutterLogoStyle.horizontal
                                    : FlutterLogoStyle.markOnly,
                                textColor: context.style.color!,
                                size: context.style.fontSize!.size! * 5,
                              );
                            },
                          },
                          onLinkTap: (url, _, __, ___) {
                            if(url.toString().contains("http")){
                              _launchURL(url.toString());
                            }else{
                              controller.getBookContentHref(url.toString());
                            }
                            print("Opening $url...");
                          },
                        ),
                        const SizedBox(
                          height: 12,
                        ),

                        /// 章节切换
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceAround,
                          children: <Widget>[
                            MaterialButton(
                              minWidth: 125,
                              textColor: AppColors.kFoodyBiteYellow,
                              shape: RoundedRectangleBorder(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(125)),
                                side: BorderSide(
                                    color: AppColors.textPrimaryColor,
                                    width: 1),
                              ),
                              onPressed: () {
                                controller.scaffoldKey.currentState!
                                    .openEndDrawer();
                              },
                              child: Text("显示目录"),
                            ),
                            MaterialButton(
                              minWidth: 125,
                              textColor: AppColors.textPrimaryColor,
                              shape: RoundedRectangleBorder(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(125)),
                                side: BorderSide(
                                    color: AppColors.textPrimaryColor,
                                    width: 1),
                              ),
                              onPressed: () {
                                controller.chapterIndex.value =
                                    (int.parse(controller.chapterIndex.value) -
                                            1)
                                        .toString();
                                controller.getBookContentList();
                              },
                              child: Text("上一章"),
                            ),
                            MaterialButton(
                              minWidth: 125,
                              textColor: AppColors.textPrimaryColor,
                              shape: RoundedRectangleBorder(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(125)),
                                side: BorderSide(
                                    color: AppColors.textPrimaryColor,
                                    width: 1),
                              ),
                              onPressed: () {
                                controller.chapterIndex.value =
                                    (int.parse(controller.chapterIndex.value) +
                                            1)
                                        .toString();
                                controller.getBookContentList();
                              },
                              child: Text("下一章"),
                            ),
                          ],
                        ),
                        SizedBox(
                          height: 16,
                        ),
                      ],
                    ),
                  )),

              // settingView(context),
            ],
          ),
        ));
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
              controller.chapterIndex.value =
                  index
                      .toString();
              controller.getBookContentList();
              controller.scaffoldKey.currentState!
                  .closeEndDrawer();
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

  //设置状态栏文字颜色
  void setStemStyle() async {
    await Future.delayed(const Duration(milliseconds: 500), () {
      SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle.light);
    });
  }

  /// 判断是否加入书架

  Future<bool> isAddBookshelf() async {
    var bookList = null;
    if (bookList != null) {
      return true;
    } else {
      return false;
    }
  }

  _launchURL(String a ) async {
    String url = a;
    if (await canLaunch(url)) {
      await launch(url);
    } else {
      throw 'Could not launch $url';
    }
  }
}
