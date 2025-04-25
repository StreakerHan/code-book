import 'package:cached_network_image/cached_network_image.dart';
import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/model/rank_model.dart';
import 'package:dotted_border/dotted_border.dart';
import 'package:eva_icons_flutter/eva_icons_flutter.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:intl/intl.dart';
import 'package:daily_task/app/utils/helpers/app_helpers.dart';

class CardRank extends StatelessWidget {
  const CardRank({
    required this.data,
    required this.primary,
    required this.onPrimary,
    required this.title,
    Key? key,
  }) : super(key: key);

  final List<RankModel> data;
  final Color primary;
  final Color onPrimary;
  final String title;

  @override
  Widget build(BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(20),
      child: Material(
        child: InkWell(
          onTap: () {
            Get.toNamed("bookInfo");
          },
          child: Container(
            width: 300,
            height: 300,
            decoration: BoxDecoration(
              gradient: LinearGradient(
                colors: [primary, primary.withOpacity(.7)],
                begin: AlignmentDirectional.topCenter,
                end: AlignmentDirectional.bottomCenter,
              ),
            ),
            child: _BackgroundDecoration(
              child: Padding(
                padding: const EdgeInsets.only(left: 10, top: 10),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    SizedBox(
                      height: 280,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                           Text(
                             title,
                             style: const TextStyle(
                                 fontSize: 16, fontWeight: FontWeight.w800,color: Colors.white),
                           ),
                          Expanded(
                            child:ListView.builder(
                              itemCount: data.length,
                              itemBuilder: (context, index) {
                                return  ListTile(
                                  onTap: (){
                                    Get.toNamed("bookInfo");
                                  },
                                  hoverColor: Colors.transparent,
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(20),
                                  ),
                                  leading: Container(
                                    width: 40,
                                    height: 50,
                                    decoration: BoxDecoration(
                                      borderRadius: BorderRadius.circular(10),
                                      color: Colors.blueGrey.withOpacity(.1),
                                    ),
                                    child: CachedNetworkImage(imageUrl: data[index].image,fit: BoxFit.fill,),
                                  ),
                                  title: Text(
                                    data[index].name,
                                    style: const TextStyle(fontWeight: FontWeight.w400,color: Colors.white,fontSize: 14),
                                    maxLines: 1,
                                    overflow: TextOverflow.ellipsis,
                                  ),
                                  subtitle: Text(
                                    data[index].view,
                                    maxLines: 1,
                                    style: const TextStyle(color: Colors.white70,fontSize: 11),
                                    overflow: TextOverflow.ellipsis,
                                  ),
                                  trailing: const Icon(Icons.menu_book,color: Colors.white,size: 15,),
                                );
                              },
                            ),
                          )
                        ],
                      ),
                    ),
                    // const Spacer(flex: 1),
                    // const SizedBox(height: 10),
                    // const Spacer(flex: 2),
                    // _doneButton(),
                  ],
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class _IconLabel extends StatelessWidget {
  const _IconLabel({
    required this.color,
    required this.iconData,
    required this.label,
    Key? key,
  }) : super(key: key);

  final Color color;
  final IconData iconData;
  final String label;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Icon(
          iconData,
          color: color,
          size: 18,
        ),
        const SizedBox(width: 5),
        Text(
          label,
          style: TextStyle(
            fontSize: 12,
            color: color.withOpacity(.8),
          ),
        )
      ],
    );
  }
}

class _BackgroundDecoration extends StatelessWidget {
  const _BackgroundDecoration({required this.child, Key? key})
      : super(key: key);

  final Widget child;

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Align(
          alignment: Alignment.topRight,
          child: Transform.translate(
            offset: const Offset(25, -25),
            child: CircleAvatar(
              radius: 50,
              backgroundColor: Colors.white.withOpacity(.1),
            ),
          ),
        ),
        Align(
          alignment: Alignment.bottomLeft,
          child: Transform.translate(
            offset: const Offset(-70, 70),
            child: CircleAvatar(
              radius: 100,
              backgroundColor: Colors.white.withOpacity(.1),
            ),
          ),
        ),
        child,
      ],
    );
  }
}
