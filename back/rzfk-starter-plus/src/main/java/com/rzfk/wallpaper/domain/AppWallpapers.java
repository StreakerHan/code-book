package com.rzfk.wallpaper.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.rzfk.common.core.domain.BaseEntity;

/**
 * 壁纸对象 app_wallpapers
 *
 * @author streaker
 * @date 2022-11-03
 */
@Data
@Accessors(chain = true)
@TableName("app_wallpapers")
public class AppWallpapers extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 图片链接
     */
    private String url;
    /**
     * 图片宽度
     */
    private String width;
    /**
     * 图片高度
     */
    private String height;
    /**
     * 图片标题
     */
    private String title;
    /**
     * 下载次数
     */
    private String downloads;
    /**
     * 图片类型
     */
    private String type;
    /**
     * 主题id
     */
    private String topic;
    @TableField(exist = false)
    private String topicName;

}
