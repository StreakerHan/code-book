class BookInfo {
  int? code;
  String? msg;
  Data? data1;

  BookInfo({this.code, this.msg, this.data1});

  BookInfo.fromJson(Map<String, dynamic> json) {
    code = json['code'];
    msg = json['msg'];
    data1 = json['data'] != null ? Data?.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['code'] = code;
    data['msg'] = msg;
    if (data != null) {
      data['data'] = data1?.toJson();
    }
    return data;
  }
}

class Data {
  String? id;
  String? name;
  String? img;
  String? href;
  String? readHref;
  String? tags;
  String? descb;
  String? type;
  String? createTime;
  dynamic typeName;
  String? chapters;
  String? collect;

  Data(
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
      this.collect});

  Data.fromJson(Map<String, dynamic> json) {
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
    collect = json['collect'];
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
    data['collect'] = collect;
    return data;
  }
}
