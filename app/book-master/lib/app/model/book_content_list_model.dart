class BookContentList {
  int? code;
  String? msg;
  List<BookContentModel>? data;

  BookContentList({this.code, this.msg, this.data});

  BookContentList.fromJson(Map<String, dynamic> json) {
    code = json['code'];
    msg = json['msg'];
    if (json['data'] != null) {
      data = <BookContentModel>[];
      json['data'].forEach((v) {
        data?.add(BookContentModel.fromJson(v));
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

class BookContentModel {
  String? id;
  String? bookId;
  String? catalogueName;
  String? seq;
  String? href;
  dynamic content;
  String? parentId;
  String? level;

  BookContentModel(
      {this.id,
      this.bookId,
      this.catalogueName,
      this.seq,
      this.href,
      this.content,
      this.parentId,
      this.level});

  BookContentModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    bookId = json['bookId'];
    catalogueName = json['catalogueName'];
    seq = json['seq'];
    href = json['href'];
    content = json['content'];
    parentId = json['parentId'];
    level = json['level'];
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['bookId'] = bookId;
    data['catalogueName'] = catalogueName;
    data['seq'] = seq;
    data['href'] = href;
    data['content'] = content;
    data['parentId'] = parentId;
    data['level'] = level;
    return data;
  }
}
