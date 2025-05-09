import 'package:daily_task/app/constans/colors.dart';
import 'package:flutter/animation.dart';
import 'package:photo_manager/photo_manager.dart';
import 'package:wechat_assets_picker/wechat_assets_picker.dart';
import 'package:wechat_camera_picker/wechat_camera_picker.dart';


Future<List<AssetEntity>?> getImagePicker(context,
    {required int maxAssets,
    RequestType requestType = RequestType.image}) async {
  final List<AssetEntity>? fileList = await AssetPicker.pickAssets(context,
      maxAssets: maxAssets,
      requestType: requestType,
      themeColor: AppColors.primary,
      routeCurve: Curves.fastLinearToSlowEaseIn);
  return fileList;
}

Future<AssetEntity?> getImageCamera(context) async {
  final AssetEntity? fileList = await CameraPicker.pickFromCamera(context,
      theme: CameraPicker.themeData(AppColors.primary));
  return fileList;
}
