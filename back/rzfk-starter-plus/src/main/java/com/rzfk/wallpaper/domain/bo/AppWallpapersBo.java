package com.rzfk.wallpaper.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rzfk.common.core.validate.AddGroup;
import com.rzfk.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.rzfk.common.core.domain.BaseEntity;

/**
 * 壁纸业务对象 app_wallpapers
 *
 * @author streaker
 * @date 2022-11-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("壁纸业务对象")
public class AppWallpapersBo extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 图片链接
     */
    @ApiModelProperty(value = "图片链接", required = true)
    private String url;

    /**
     * 图片宽度
     */
    @ApiModelProperty(value = "图片宽度", required = true)
    private String width;

    /**
     * 图片高度
     */
    @ApiModelProperty(value = "图片高度", required = true)
    private String height;

    /**
     * 图片标题
     */
    @ApiModelProperty(value = "图片标题", required = true)
    private String title;

    /**
     * 下载次数
     */
    @ApiModelProperty(value = "下载次数", required = true)
    private String downloads;

    /**
     * 图片类型
     */
    @ApiModelProperty(value = "图片类型", required = true)
    private String type;


    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer pageNum;

    /**
     * 排序列
     */
    @ApiModelProperty("排序列")
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向", example = "asc,desc")
    private String isAsc;

    private String topic;

    @TableField(exist = false)
    private String topicName;

}
