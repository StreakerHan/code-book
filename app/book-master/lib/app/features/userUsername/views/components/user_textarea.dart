import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:flutter/material.dart';


class UserTextarea extends StatelessWidget {
  final TextEditingController controller;
  final ValueChanged<String>? onChanged;
  final int size;
  const UserTextarea(
      {Key? key, required this.controller, this.onChanged, required this.size})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      margin: EdgeInsets.only(top: 10),
      padding: EdgeInsets.only(
          top: kSpacing,
          bottom: kSpacing,
          left: kSpacing,
          right: kSpacing),
      child: TextField(
        maxLines: 1,
        maxLength: size,
        cursorColor: AppColors.primary,
        cursorHeight: 20,
        onChanged: onChanged,
        style: TextStyle(fontSize: 14, color: Colors.black),
        decoration: InputDecoration(
          isCollapsed: true,
          //内容内边距，影响高度
          border: OutlineInputBorder(
            ///用来配置边框的样式
            borderSide: BorderSide.none,
          ),
        ),
        controller: controller,
      ),
    );
  }
}
