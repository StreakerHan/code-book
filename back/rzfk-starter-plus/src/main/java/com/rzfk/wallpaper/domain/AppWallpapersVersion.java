package com.rzfk.wallpaper.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rzfk.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * app版本管理对象 app_version
 *
 * @author streaker
 * @date 2022-08-20
 */
@Data
@Accessors(chain = true)
@TableName("app_wallpapers_version")
public class AppWallpapersVersion extends BaseEntity {

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
