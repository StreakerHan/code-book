class BookListModel {
  String? total;
  List<BookModel>? rows;
  int? code;
  String? msg;

  BookListModel({this.total, this.rows, this.code, this.msg});

  BookListModel.fromJson(Map<String, dynamic> json) {
    total = json['total'];
    if (json['rows'] != null) {
      rows = <BookModel>[];
      json['rows'].forEach((v) {
        rows?.add(BookModel.fromJson(v));
      });
    }
    code = json['code'];
    msg = json['msg'];
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['total'] = total;
    if (rows != null) {
      data['rows'] = rows?.map((v) => v.toJson()).toList();
    }
    data['code'] = code;
    data['msg'] = msg;
    return data;
  }
}

class BookModel {
  String? id;
  String? name;
  String? img;
  String? href;
  String? readHref;
  String? tags;
  String? descb;
  String? type;
  String? createTime;
  String? typeName;
  String? chapters;
  String? view;

  BookModel(
      {this.id,
      this.name,
      this.img,
      this.href,
      this.readHref,
      this.tags,
      this.descb,
      this.type,
      this.createTime,
      this.typeName,
      this.chapters,
      this.view});

  BookModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    img = json['img'];
    href = json['href'];
    readHref = json['readHref'];
    tags = json['tags'];
    descb = json['descb'];
    type = json['type'];
    createTime = json['createTime'];
    typeName = json['typeName'];
    chapters = json['chapters'];
    view = json['view'];
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['img'] = img;
    data['href'] = href;
    data['readHref'] = readHref;
    data['tags'] = tags;
    data['descb'] = descb;
    data['type'] = type;
    data['createTime'] = createTime;
    data['typeName'] = typeName;
    data['chapters'] = chapters;
    data['view'] = view;
    return data;
  }
}
