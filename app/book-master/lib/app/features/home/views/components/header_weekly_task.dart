
import 'package:daily_task/app/features/home/views/screens/dashboard_screen.dart';
import 'package:eva_icons_flutter/eva_icons_flutter.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class HeaderWeeklyTask extends StatelessWidget {
  const HeaderWeeklyTask({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    return Row(
      children: [
         Container(
           padding: const EdgeInsets.only(left: 10),
          child: const Text(
            "推荐阅读",
            style: TextStyle(
              fontSize: 17,
              fontWeight: FontWeight.w800,
            ),
          ),
        ),
        const Spacer(),
        _buildArchive(),
      ],
    );
  }

  Widget _buildArchive() {
    return ElevatedButton.icon(
      icon: const Icon(
        EvaIcons.refresh,
        size: 16,
      ),
      onPressed: () {
        Get.find<DashboardController>().getRecommendList();
      },
      style: ElevatedButton.styleFrom(
        primary: Colors.grey[100],
        onPrimary: Colors.grey[850],
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(20),
        ),
        elevation: 0,
      ),
      label: const Text("换一换"),
    );
  }
}
