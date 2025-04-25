import 'package:cached_network_image/cached_network_image.dart';
import 'package:daily_task/app/constans/app_constants.dart';
import 'package:daily_task/app/constans/colors.dart';
import 'package:daily_task/app/model/book_list_model.dart';
import 'package:daily_task/app/utils/helpers/app_helpers.dart';
import 'package:dotted_border/dotted_border.dart';
import 'package:eva_icons_flutter/eva_icons_flutter.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:timeago/timeago.dart' as timeago;

class ListTaskAssignedData {
  final String image;
  final String label;
  final String jobDesk;
  final DateTime? editDate;
  final String assignTo;
  final String tag;

  const ListTaskAssignedData({
    required this.image,
    required this.label,
    required this.jobDesk,
    this.editDate,
    required this.assignTo,
    required this.tag,
  });
}

class ListTaskAssigned extends StatelessWidget {
  const ListTaskAssigned({
    required this.data,
    Key? key,
  }) : super(key: key);

  final BookModel data;

  @override
  Widget build(BuildContext context) {
    return _buildListViewItem();
  }

  Widget _buildListViewItem() {
    String imageUrl = data.img!;
    return InkWell(
      onTap: () {
        Get.toNamed("bookInfo", arguments: {
          "bookInfo": data
        });
      },
      child: Container(
        padding: EdgeInsets.fromLTRB(kSpacing, 12, kSpacing, 12),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Image.network(
              imageUrl,
              height: 99,
              width: 77,
              fit: BoxFit.cover,
            ),
            SizedBox(
              width: 12,
            ),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Text(
                    data.name!,
                    style: TextStyle(color: AppColors.black, fontSize: 14),
                    maxLines: 1,
                  ),
                  SizedBox(height: 6),
                  Text(
                    data.descb!,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                    style:
                        const TextStyle(color: Color(0xFF6A6C7A), fontSize: 12),
                  ),
                  SizedBox(
                    height: 9,
                  ),
                  Row(
                    mainAxisSize: MainAxisSize.max,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Expanded(
                          child: Text(
                        data.typeName!,
                        style: const TextStyle(
                          color: Color(0xFF9A9AA7),
                          fontSize: 14,
                        ),
                      )),
                      data.tags != null &&
                              data.tags!.split(",").toList().length > 0
                          ? tagView(data.tags!.split(",").toList()[0])
                          : tagView('限免'),
                      data.tags != null &&
                              data.tags!.split(",").toList().length > 1
                          ? SizedBox(
                              width: 4,
                            )
                          : SizedBox(),
                      data.tags != null &&
                              data.tags!.split(",").toList().length > 1
                          ? tagView(data.tags!.split(",").toList()[1])
                          : SizedBox(),
                    ],
                  ),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }

  Widget tagView(String tag) {
    return Container(
      padding: EdgeInsets.fromLTRB(5, 0, 5, 0),
      alignment: Alignment.center,
      child: Text(
        tag,
        style: TextStyle(color: Color(0xFF6A6C7A), fontSize: 11),
      ),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.all(Radius.circular(3)),
          border: Border.all(width: 0.5, color: Colors.black12)),
    );
  }
}
