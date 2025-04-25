import 'package:dynamic_treeview/dynamic_treeview.dart';

class DataModel implements BaseData {
  String? id;
  String? parentId;
  String? name;
  String? seq;

  ///Any extra data you want to get when tapped on children
  Map<String, dynamic>? extras;
  DataModel({this.id, this.parentId, this.name,this.seq, this.extras});
  @override
  String getId() {
    return id.toString();
  }

  @override
  Map<String, dynamic> getExtraData() {
    return extras!;
  }

  @override
  String getParentId() {
    return parentId.toString();
  }

  @override
  String getTitle() {
    return name.toString();
  }

  String getSeq(){
    return seq.toString();
  }
}
