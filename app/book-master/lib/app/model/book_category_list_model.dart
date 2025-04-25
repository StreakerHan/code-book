class BookCategoryListModel {
  int? code;
  String? msg;
  List<BookCategoryModel>? data;

  BookCategoryListModel({this.code, this.msg, this.data});

  BookCategoryListModel.fromJson(Map<String, dynamic> json) {
    code = json['code'];
    msg = json['msg'];
    if (json['data'] != null) {
      data = <BookCategoryModel>[];
      json['data'].forEach((v) {
        data?.add(BookCategoryModel.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final datas = <String, dynamic>{};
    datas['code'] = code;
    datas['msg'] = msg;
    if (data != null) {
      datas['data'] = data?.map((v) => v.toJson()).toList();
    }
    return datas;
  }
}

class BookCategoryModel {
  String? id;
  String? name;
  String? parentId;
  dynamic img;
  dynamic descb;
  String? href;
  String? level;

  BookCategoryModel(
      {this.id,
      this.name,
      this.parentId,
      this.img,
      this.descb,
      this.href,
      this.level});

  BookCategoryModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    parentId = json['parentId'];
    img = json['img'];
    descb = json['descb'];
    href = json['href'];
    level = json['level'];
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['parentId'] = parentId;
    data['img'] = img;
    data['descb'] = descb;
    data['href'] = href;
    data['level'] = level;
    return data;
  }
}
