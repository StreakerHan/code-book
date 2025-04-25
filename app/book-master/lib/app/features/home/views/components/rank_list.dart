import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/model/rank_model.dart';
import 'package:daily_task/app/shared_components/card_rank.dart';
import 'package:daily_task/app/shared_components/card_task.dart';
import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';

class RankList extends StatelessWidget {
  const RankList({
    required this.data,
    required this.data1,
    Key? key,
  }) : super(key: key);

  final List<RankModel> data;
  final List<RankModel> data1;

  @override
  Widget build(BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(kBorderRadius * 2),
      child: SizedBox(
          height: 300,
          child: ListView(
            shrinkWrap: true,
            scrollDirection: Axis.horizontal,
            physics: const BouncingScrollPhysics(),
            children: [
              CardRank(
                data: data,
                primary: _getSequenceColor(0),
                onPrimary: Colors.white,
                title: "阅 读 榜",
              ),
              const SizedBox(width: 10),
              CardRank(
                data: data1,
                primary: _getSequenceColor(1),
                onPrimary: Colors.white,
                title: "收 藏 榜",
              ),
            ],
          )),
    );
  }

  Color _getSequenceColor(int index) {
    int val = index % 4;
    if (val == 3) {
      return HexColor("#FF6D3A");
    } else if (val == 2) {
      return HexColor("#696969");
    } else if (val == 1) {
      return HexColor("#94392B");
    } else {
      return HexColor("#749C99");
    }
  }

  Widget _rankInfo() {
    return Container();
  }
}
