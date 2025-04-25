import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/shared_components/custom_elevated_button.dart';
import 'package:daily_task/app/shared_components/custom_textfield.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kartal/kartal.dart' hide MediaQueryExtension hide ContextExtension;
import 'package:url_launcher/url_launcher.dart';

import '../controllers/login_controller.dart';

class LoginView extends GetView<LoginController> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _body(context),
    );
  }

  SizedBox _body(BuildContext context) {
    return SizedBox(
      height: context.height * 1,
      width: context.width * 1,
      child: SingleChildScrollView(
        child: Column(
          children: [
            context.emptySizedHeightBoxLow3x,
            topImage(context),
            context.emptySizedHeightBoxLow3x,
            topText(context),
            context.emptySizedHeightBoxLow3x,
            CustomTextField(
              height: context.height * 0.07,
              width: context.width * 0.8,
              hinttext: "用户名称",
              prefixIcon: const Icon(
                Icons.person,
                color: AppColors.kPrimaryColor,
              ),
              onChanged: (text) {
                controller.email = text;
                controller.checkInput();
              },
            ),
            context.emptySizedHeightBoxLow,
            CustomTextField(
              height: context.height * 0.07,
              width: context.width * 0.8,
              hinttext: "密码",
              obscureText: true,
              prefixIcon: const Icon(
                Icons.lock,
                color: AppColors.kPrimaryColor,
              ),
              onChanged: (text) {
                controller.password = text;
                controller.checkInput();
              },
              suffixIcon: const Icon(Icons.vpn_key,size: 15,),
            ),
            midText(context),
            context.emptySizedHeightBoxLow,
            CustomElevatedButton(
              child: Text(
                "登 录",
                style: const TextStyle(color: Colors.white,fontSize: 20),
              ),
              borderRadius: 20,
              color: AppColors.kPrimaryColor,
              height: context.height * 0.07,
              width: context.width * 0.6,
              onPressed: (){
                controller.send();
              },
            ),
            context.emptySizedHeightBoxLow3x,
            // const Text(AppText.or),
            // context.emptySizedHeightBoxLow3x,
            // const Text(AppText.loginwith),
            context.emptySizedHeightBoxLow,
            // socialIcon(context),
            bottomText(context),
            TextButton(
              child: const Text(
                "返回首页",
                style: TextStyle(color: AppColors.kPrimaryColor),
              ),
              onPressed: () {
                Get.offAllNamed("index");
              },
            )
          ],
        ),
      ),
    );
  }

  Container topImage(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(top:30),
      child: SizedBox(
        height: context.height * 0.2,
        child: Image.asset('assets/images/loginill.png'),
      ),
    );
  }

  Text topText(BuildContext context) {
    return Text(
      "登 录",
      style: context.textTheme.headline5!.copyWith(fontWeight: FontWeight.bold),
    );
  }

  SizedBox midText(BuildContext context) {
    return SizedBox(
      height: context.height * 0.1,
      width: context.width * 0.95,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          Row(
            children: [
              Container(
                padding: EdgeInsets.all(10),
                child: RichText(
                  text: TextSpan(children: [
                    TextSpan(
                        text: '注册或登录帐户，表示您同意我们的',
                        style: TextStyle(fontSize: 12,color: Colors.black)
                      // AppTextStyles.body2_12_Regular.color_font_color_tertiary,
                    ),
                    TextSpan(
                        text: '《服务协议》',
                        style: TextStyle(color: Colors.blue, fontSize: 12),
                        // style: AppTextStyles.body2_12_Regular.color_primary_color1,
                        recognizer: TapGestureRecognizer()
                          ..onTap = () {
                            _launchURL("http://www.streaker.cn/yhxy.html");
                            // WebViewApp();
                            // 查看 服务条款
                          }),
                    TextSpan(
                      text: ' 和 ',
                      style:  TextStyle(fontSize: 12,color: Colors.black)
                    ),
                    TextSpan(
                        text: '《隐私政策》',
                        style: TextStyle(color: Colors.blue, fontSize: 12),
                        // style: AppTextStyles.body2_12_Regular.color_primary_color1,
                        recognizer: TapGestureRecognizer()
                          ..onTap = () {
                            // 查看 服务条款
                            _launchURL("http://www.streaker.cn/ysxy.html");
                          }),
                  ]),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  SizedBox socialIcon(BuildContext context) {
    return SizedBox(
      height: context.height * 0.1,
      width: context.width * 0.8,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          IconButton(
              onPressed: null, icon: Image.asset('assets/images/google.png')),
          IconButton(
              onPressed: null, icon: Image.asset('assets/images/apple.png')),
          IconButton(
              onPressed: null, icon: Image.asset('assets/images/face.png')),
        ],
      ),
    );
  }

  SizedBox bottomText(BuildContext context) {
    return SizedBox(
      width: context.width * 0.7,
      height: context.height * 0.08,
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Text(
            "还没有账号？"
          ),
          TextButton(
            child: const Text(
              "去注册",
              style: TextStyle(color: AppColors.kPrimaryColor),
            ),
            onPressed: () {
              Get.offNamed("signup");
            },
          )
        ],
      ),
    );
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
