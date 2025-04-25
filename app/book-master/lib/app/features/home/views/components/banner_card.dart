import 'package:cached_network_image/cached_network_image.dart';
import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/model/carousel_list_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_card_swipper/flutter_card_swiper.dart';
import 'package:get/get.dart';

class BannerCard extends StatelessWidget {
  final List<Top>? bannerList;
  final double bannerHeight;
  final EdgeInsetsGeometry? padding;

  const BannerCard(this.bannerList,
      {Key? key, this.bannerHeight = 200, this.padding})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      constraints: BoxConstraints(minHeight: 200),
      padding: EdgeInsets.only(left: kSpacing/4, right: kSpacing/4),
      // height: bannerHeight,
      child: _banner(),
    );
  }

  _banner() {
    var right = 10 + (padding?.horizontal ?? 0) / 2;
    return Obx(()=>Swiper(
      itemCount: bannerList!.length,
      autoplay: true,
      key: UniqueKey(),
      itemBuilder: (BuildContext context, int index) {
        return _image(bannerList![index].img!);
      },
      //自定义指示器
      pagination: SwiperPagination(
          alignment: Alignment.bottomRight,
          // margin: EdgeInsets.only(right: right, bottom: 5),
          builder: DotSwiperPaginationBuilder(
              color: Colors.white60, size: 6, activeSize: 12)),
    ));
  }

  _image(String bannerList) {
    return Container(
      // color: Colors.red,
      // padding: EdgeInsets.only(left: kSpacing),
      child: ClipRRect(
        borderRadius: BorderRadius.all(Radius.circular(6)),
        child: InkWell(
          onTap: () {
          },
          child:
              CachedNetworkImage(height: bannerHeight, imageUrl: bannerList,fit: BoxFit.cover,),

        ),
      ),
    );
  }
}
