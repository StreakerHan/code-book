package com.rzfk.wallpaper.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 壁纸视图对象 app_wallpapers
 *
 * @author streaker
 * @date 2022-11-03
 */
@Data
@ApiModel("壁纸视图对象")
public class AppWallpapersVo {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@ApiModelProperty("主键")
	private Long id;

    /**
     * 图片链接
     */
	@Excel(name = "图片链接")
	@ApiModelProperty("图片链接")
	private String url;

    /**
     * 图片宽度
     */
	@Excel(name = "图片宽度")
	@ApiModelProperty("图片宽度")
	private String width;

    /**
     * 图片高度
     */
	@Excel(name = "图片高度")
	@ApiModelProperty("图片高度")
	private String height;

    /**
     * 图片标题
     */
	@Excel(name = "图片标题")
	@ApiModelProperty("图片标题")
	private String title;

    /**
     * 下载次数
     */
	@Excel(name = "下载次数")
	@ApiModelProperty("下载次数")
	private String downloads;

    /**
     * 图片类型
     */
	@Excel(name = "图片类型")
	@ApiModelProperty("图片类型")
	private String type;

	/**
	 * 主题
	 */
	@Excel(name = "主题")
	@ApiModelProperty("主题")
	private String topic;

	@TableField(exist = false)
	private String topicName;


}
