class BookChaptersListModel {
  String? total;
  List<BookChapterModel>? rows;
  int? code;
  String? msg;

  BookChaptersListModel({this.total, this.rows, this.code, this.msg});

  BookChaptersListModel.fromJson(Map<String, dynamic> json) {
    total = json['total'];
    if (json['rows'] != null) {
      rows = <BookChapterModel>[];
      json['rows'].forEach((v) {
        rows?.add(BookChapterModel.fromJson(v));
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

class BookChapterModel {
  String? id;
  dynamic bookId;
  String? catalogueName;
  String? seq;
  dynamic href;
  dynamic content;
  String? level;
  String? parentId;

  BookChapterModel(
      {this.id,
      this.bookId,
      this.catalogueName,
      this.seq,
      this.href,
      this.content,
      this.level,
      this.parentId});

  BookChapterModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    bookId = json['bookId'];
    catalogueName = json['catalogueName'];
    seq = json['seq'];
    href = json['href'];
    content = json['content'];
    level = json['level'];
    parentId = json['parentId'];
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['bookId'] = bookId;
    data['catalogueName'] = catalogueName;
    data['seq'] = seq;
    data['href'] = href;
    data['content'] = content;
    data['level'] = level;
    data['parentId'] = parentId;
    return data;
  }
}
