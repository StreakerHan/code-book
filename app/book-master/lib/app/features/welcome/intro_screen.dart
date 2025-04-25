import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';
import 'package:intro_slider/intro_slider.dart';

class IntroScreenDefault extends StatefulWidget {
  const IntroScreenDefault({Key? key}) : super(key: key);

  @override
  IntroScreenDefaultState createState() => IntroScreenDefaultState();
}

class IntroScreenDefaultState extends State<IntroScreenDefault> {
  List<ContentConfig> listContentConfig = [];

  @override
  void initState() {
    super.initState();


    listContentConfig.add(
      const ContentConfig(
        title: "4k+精品IT教程资料",
        description: "随时随地的查阅你想要的教程资料。",
        pathImage: "assets/images/comhar_standing_desk_1.jpg",
        backgroundColor: Color(0xfff5a623),
      ),
    );
    listContentConfig.add(
      const ContentConfig(
        title: "1w+开发人员",
        description: "更多的IT从业者选择从这查阅相关资料。",
        pathImage: "assets/images/comhar_standing_desk_1.jpg",
        backgroundColor: Color(0xff203152),
      ),
    );
    listContentConfig.add(
      const ContentConfig(
        title: "猿书网",
        description: "【猿书网】的目标是成为最大的IT行业资料网。",
        pathImage: "assets/images/comhar_standing_desk_1.jpg",
        backgroundColor: Color(0xff9932CC),
      ),
    );
  }

  void onDonePress() {
    GetStorage().write("FirstOpen", "1");
    Get.offNamed("index");
  }

  @override
  Widget build(BuildContext context) {
    return IntroSlider(
      key: UniqueKey(),
      listContentConfig: listContentConfig,
      onDonePress: onDonePress,
      renderDoneBtn: renderDoneBtn(),
      renderPrevBtn: renderPrevBtn(),
      renderNextBtn: renderNextBtn(),
      renderSkipBtn: renderSkipBtn(),
    );
  }

  Widget renderDoneBtn() {
    return Row(
      children: [Text("开始使用"), Icon(Icons.done)],
    );
  }
  Widget renderPrevBtn() {
    return Row(
      children: [Text("上一页"), Icon(Icons.keyboard_arrow_left)],
    );
  }
  Widget renderNextBtn() {
    return Row(
      children: [Text("下一页"), Icon(Icons.arrow_right)],
    );
  }
  Widget renderSkipBtn() {
    return Row(
      children: [SizedBox(width: 14),Text("跳过"),Icon(Icons.highlight_off)],
    );
  }
}
