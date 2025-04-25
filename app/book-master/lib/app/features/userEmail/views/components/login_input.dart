import 'package:daily_task/app/constans/colors.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

///登录输入框 中自定义widget
class LoginInput extends StatefulWidget {
  final String title;
  final String hint;
  final bool isForgetPassword;
  final ValueChanged<String>? onChanged;
  final ValueChanged<bool>? focusChanged;
  final bool lineStretch;
  final bool obscureText;
  final bool autofocus;
  final TextInputType? keyBoardType;
  final Color colors;

  const LoginInput(
      {Key? key,
      required this.title,
      this.hint = "",
      this.isForgetPassword = false,
      this.onChanged,
      this.focusChanged,
      this.lineStretch = false,
      this.obscureText = false,
      this.autofocus = true,
      this.keyBoardType, required this.colors})
      : super(key: key);

  @override
  _LoginInputState createState() => _LoginInputState();
}

class _LoginInputState extends State<LoginInput> {
  final _focusNode = FocusNode();
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    //是否获取光标的监听
    _focusNode.addListener(() {
      if (widget.focusChanged != null) {
        widget.focusChanged!(_focusNode.hasFocus);
      }
    });
  }

  @override
  void dispose() {
    _focusNode.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          children: [
            Container(
              padding: EdgeInsets.only(left: 15),
              width: 100,
              child: Text(
                widget.title,
                style: TextStyle(fontSize: 20,color: widget.colors,fontWeight: FontWeight.w400),
              ),
            ),
            _input(),
            widget.isForgetPassword
                ? Container(
                    padding: EdgeInsets.only(
                        left: 50, right: 50),
                    child: TextButton(
                      onPressed: () {
                        Get.toNamed("/forget-pwd");
                      },
                      child: Text(
                        "忘记密码?",
                        style: TextStyle(
                          fontSize: 42, color: widget.colors
                        ),
                      ),
                      style: ButtonStyle(
                        //定义文本的样式 这里设置的颜色是不起作用的
                        textStyle: MaterialStateProperty.all(TextStyle(
                            fontSize: 38, color: widget.colors)),
                        //更优美的方式来设置
                        foregroundColor: MaterialStateProperty.resolveWith(
                          (states) {
                            if (states.contains(MaterialState.focused) &&
                                !states.contains(MaterialState.pressed)) {
                              //获取焦点时的颜色
                              return widget.colors;
                            } else if (states.contains(MaterialState.pressed)) {
                              //按下时的颜色
                              return widget.colors;
                            }
                            //默认状态使用灰色
                            return AppColors.primary;
                          },
                        ),
                        //背景颜色
                        backgroundColor:
                            MaterialStateProperty.resolveWith((states) {
                          //设置按下时的背景颜色
                          if (states.contains(MaterialState.pressed)) {
                            return Colors.transparent;
                          }
                          //默认不使用背景颜色
                          return Colors.transparent;
                        }),
                        //设置水波纹颜色
                        overlayColor:
                            MaterialStateProperty.all(Colors.transparent),
                        //设置阴影  不适用于这里的TextButton
                        elevation: MaterialStateProperty.all(0),
                        //设置按钮内边距
                        padding: MaterialStateProperty.all(EdgeInsets.all(4)),
                        //设置按钮的大小
                        minimumSize: MaterialStateProperty.all(Size(20, 20)),

                        //外边框装饰 会覆盖 side 配置的样式
                        shape: MaterialStateProperty.all(StadiumBorder()),
                      ),
                    ),
                  )
                : Container()
          ],
        ),
        Padding(
          padding: EdgeInsets.only(left: !widget.lineStretch ? 15 : 0),
          child: Divider(
            height: 1,
            thickness: 0.5,
          ),
        )
      ],
    );
  }

  _input() {
    return Expanded(
      child: TextField(
        focusNode: _focusNode,
        onChanged: widget.onChanged,
        obscureText: widget.obscureText,
        keyboardType: widget.keyBoardType,
        autofocus: !widget.autofocus,
        cursorColor: AppColors.primary,
        style: TextStyle(fontSize:20, fontWeight: FontWeight.w300,color: widget.colors),
        //输入框样式
        decoration: InputDecoration(
            contentPadding: EdgeInsets.only(left: 20, right: 20),
            border: InputBorder.none,
            hintText: widget.hint,
            hintStyle: TextStyle(fontSize: 15,color: widget.colors)),
      ),
    );
  }
}
