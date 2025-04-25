package com.rzfk.app.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.rzfk.common.core.domain.BaseEntity;

/**
 * app版本管理对象 app_version
 *
 * @author streaker
 * @date 2022-08-20
 */
@Data
@Accessors(chain = true)
@TableName("app_version")
public class AppVersion extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 更新内容
     */
    private String content;
    /**
     * 下载地址
     */
    private String downloadUrl;
    /**
     * 是否强制更新
     */
    private String forceUpdate;
    /**
     * 类型
     */
    private String type;
    /**
     * 版本号
     */
    private String version;
    /**
     * 版本名称
     */
    private String versionName;
    /**
     * 版本更新时间
     */
    private Date versionUpdateDate;
}
