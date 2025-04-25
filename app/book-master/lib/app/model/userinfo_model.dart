class Userinfo {
  int? code;
  String? msg;
  UserinfoData? data;

  Userinfo({this.code, this.msg, this.data});

  Userinfo.fromJson(Map<String, dynamic> json) {
    code = json['code'];
    msg = json['msg'];
    data = json['data'] != null ? UserinfoData?.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['code'] = code;
    data['msg'] = msg;
    if (data != null) {
      data['data'] = this.data?.toJson();
    }
    return data;
  }
}

class UserinfoData {
  String? id;
  String? nickname;
  String? password;
  dynamic name;
  dynamic socialUid;
  dynamic socialToken;
  dynamic mobile;
  dynamic email;
  dynamic clientId;
  dynamic pushToken;
  String? sex;
  dynamic source;
  dynamic socialSource;
  dynamic avatar;
  String? province;
  String? city;
  String? area;
  dynamic address;
  String? lastLoginTime;
  String? birth;
  String? inviteCode;
  String? isSign;
  String? status;
  String? exp;
  dynamic idcard;
  dynamic sign;
  dynamic team;
  String? point;
  String? createTime;

  UserinfoData(
      {this.id,
        this.nickname,
        this.password,
        this.name,
        this.socialUid,
        this.socialToken,
        this.mobile,
        this.email,
        this.clientId,
        this.pushToken,
        this.sex,
        this.source,
        this.socialSource,
        this.avatar,
        this.province,
        this.city,
        this.area,
        this.address,
        this.lastLoginTime,
        this.birth,
        this.inviteCode,
        this.isSign,
        this.status,
        this.exp,
        this.idcard,
        this.sign,
        this.team,
        this.point,
      this.createTime});

  UserinfoData.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nickname = json['nickname'];
    password = json['password'];
    name = json['name'];
    socialUid = json['socialUid'];
    socialToken = json['socialToken'];
    mobile = json['mobile'];
    email = json['email'];
    clientId = json['clientId'];
    pushToken = json['pushToken'];
    sex = json['sex'];
    source = json['source'];
    socialSource = json['socialSource'];
    avatar = json['avatar'];
    province = json['province'];
    city = json['city'];
    area = json['area'];
    address = json['address'];
    lastLoginTime = json['lastLoginTime'];
    birth = json['birth'];
    inviteCode = json['inviteCode'];
    isSign = json['isSign'];
    status = json['status'];
    exp = json['exp'];
    idcard = json['idcard'];
    sign = json['sign'];
    point = json['point'];
    createTime = json['createTime'];
    team = json['team'];
  }

  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['nickname'] = nickname;
    data['password'] = password;
    data['name'] = name;
    data['socialUid'] = socialUid;
    data['socialToken'] = socialToken;
    data['mobile'] = mobile;
    data['email'] = email;
    data['clientId'] = clientId;
    data['pushToken'] = pushToken;
    data['sex'] = sex;
    data['source'] = source;
    data['socialSource'] = socialSource;
    data['avatar'] = avatar;
    data['province'] = province;
    data['city'] = city;
    data['area'] = area;
    data['address'] = address;
    data['lastLoginTime'] = lastLoginTime;
    data['birth'] = birth;
    data['inviteCode'] = inviteCode;
    data['isSign'] = isSign;
    data['status'] = status;
    data['exp'] = exp;
    data['idcard'] = idcard;
    data['sign'] = sign;
    data['point'] = point;
    data['createTime'] = createTime;
    data['team'] = team;
    return data;
  }
}
