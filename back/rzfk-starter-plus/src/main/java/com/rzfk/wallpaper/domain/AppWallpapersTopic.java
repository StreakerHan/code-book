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
 * 壁纸主题对象 app_wallpapers_topic
 *
 * @author streaker
 * @date 2022-11-03
 */
@Data
@Accessors(chain = true)
@TableName("app_wallpapers_topic")
public class AppWallpapersTopic extends BaseEntity {

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

}
