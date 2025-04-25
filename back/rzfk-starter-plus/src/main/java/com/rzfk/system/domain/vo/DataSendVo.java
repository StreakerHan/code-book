package com.rzfk.system.domain.vo;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class DataSendVo {
    // 类型 case 等
    private String type;
    // 唯一键
    private String id;
    // data
    private JSONObject data;
    // json中文件url对应的key
    private List<String> fileKeys;
}
