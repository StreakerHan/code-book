import 'package:daily_task/app/model/book_list_model.dart';
import 'package:flutter/material.dart';

class CarouselList {
  int? code;
  String? msg;
  CarouselListData? data;

  CarouselList({this.code, this.msg, this.data});

  CarouselList.fromJson(Map<String, dynamic> json) {
    code = json['code'];
    msg = json['msg'];
    data = json['data'] != null ? CarouselListData?.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['code'] = code;
    data['msg'] = msg;
    if (this.data != null) {
      data['data'] = this.data?.toJson();
    }
    return data;
  }
}

class CarouselListData {
  List<Top>? top;
  List<Top>? bottom;
  List<Notice>? notice;
  List<BookModel>? source;

  CarouselListData({this.top, this.bottom, this.notice});

  CarouselListData.fromJson(Map<String, dynamic> json) {
    if (json['top'] != null) {
      top = <Top>[];
      json['top'].forEach((v) {
        top?.add(Top.fromJson(v));
      });
    }
    if (json['bottom'] != null) {
      bottom = <Top>[];
      json['bottom'].forEach((v) {
        bottom?.add(Top.fromJson(v));
      });
    }
    if (json['notice'] != null) {
      notice = <Notice>[];
      json['notice'].forEach((v) {
        notice?.add(Notice.fromJson(v));
      });
    }
    if (json['source'] != null) {
      source = <BookModel>[];
      json['source'].forEach((v) {
        source?.add(BookModel.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    if (top != null) {
      data['top'] = top?.map((v) => v.toJson()).toList();
    }
    if (bottom != null) {
      data['bottom'] = bottom?.map((v) => v.toJson()).toList();
    }
    if (notice != null) {
      data['notice'] = notice?.map((v) => v.toJson()).toList();
    }
    if (source != null) {
      data['source'] = source?.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Top {
  String? id;
  String? img;
  String? title;
  String? type;
  dynamic url;
  String? status;
  String? createTime;
  dynamic searchValue;
  dynamic params;

  Top(
      {this.id,
        this.img,
        this.title,
        this.type,
        this.url,
        this.status,
        this.createTime,
        this.searchValue,
      this.params});

  Top.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    img = json['img'];
    title = json['title'];
    type = json['type'];
    url = json['url'];
    status = json['status'];
    createTime = json['createTime'];
    searchValue = json['searchValue'];
    params = json['params'];
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['img'] = img;
    data['title'] = title;
    data['type'] = type;
    data['url'] = url;
    data['status'] = status;
    data['createTime'] = createTime;
    data['searchValue'] = searchValue;
    data['params'] = params;
    return data;
  }
}

class Notice {
  String? id;
  String? img;
  String? title;
  String? type;
  dynamic url;
  String? status;
  String? createTime;
  dynamic searchValue;
  dynamic params;

  Notice(
      {this.id,
        this.img,
        this.title,
        this.type,
        this.url,
        this.status,
        this.createTime,
        this.searchValue,
        this.params});

  Notice.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    img = json['img'];
    title = json['title'];
    type = json['type'];
    url = json['url'];
    status = json['status'];
    createTime = json['createTime'];
    searchValue = json['searchValue'];
    params = json['params'];
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['img'] = img;
    data['title'] = title;
    data['type'] = type;
    data['url'] = url;
    data['status'] = status;
    data['createTime'] = createTime;
    data['searchValue'] = searchValue;
    data['params'] = params;
    return data;
  }
}
// "id": "1573954776566177794",
// "name": "日本站正赛",
// "typeId": "1560441534464655361",
// "seq": "16",
// "type": "video",
// "url": "https://guanzhi-video-new.oss-cn-beijing.aliyuncs.com/products/21546/21546.mp4",
// "link": "链接: https://pan.baidu.com/s/18OTKGZ1RuSK0Qk_fniwuzw 提取码: 0wtq  --来自百度网盘超级会员v2的分享",
// "img": "https://motohub.oss-cn-qingdao.aliyuncs.com/e2669ccd8e2b441380cf75d8e2367326.png",
// "status": "1"
class Source {
  String? id;
  String? img;
  String? name;
  String? type;
  String? seq;
  dynamic url;
  dynamic link;
  String? status;
  String? typeId;
  String? typeName;

  Source({this.id,
    this.img,
    this.name,
    this.type,
    this.seq,
    this.url,
    this.link,
    this.status,
    this.typeId,
    this.typeName});

  Source.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    img = json['img'];
    name = json['name'];
    type = json['type'];
    url = json['url'];
    status = json['status'];
    link = json['link'];
    typeId = json['typeId'];
    seq = json['seq'];
    typeName = json['typeName'];
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['img'] = img;
    data['name'] = name;
    data['type'] = type;
    data['url'] = url;
    data['status'] = status;
    data['link'] = link;
    data['typeId'] = typeId;
    data['seq'] = seq;
    data['typeName'] = typeName;
    return data;
  }
}
