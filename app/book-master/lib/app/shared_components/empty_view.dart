import 'package:daily_task/app/constans/colors.dart';
import 'package:flutter/material.dart';

class EmptyView extends StatelessWidget {
  const EmptyView({Key? key}) : super(key: key);


  @override
  Widget build(BuildContext context) {
    return Container(
      color: AppColors.homeGrey,
      width: double.infinity,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Image.asset(
            "assets/images/empty.png",
            width: 150,
            height: 150,
          ),
          SizedBox(
            height: 14,
          ),
          Text(
            "暂无相关书籍信息",
            style: TextStyle(fontSize: 12, color: AppColors.textBlack9),
          ),
          SizedBox(
            height: 25,
          ),

        ],
      ),
    );
  }
}