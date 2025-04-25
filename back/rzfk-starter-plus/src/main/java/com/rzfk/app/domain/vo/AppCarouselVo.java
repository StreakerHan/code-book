package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 轮播内容设置视图对象 app_carousel
 *
 * @author streaker
 * @date 2022-08-14
 */
@Data
@ApiModel("轮播内容设置视图对象")
public class AppCarouselVo {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@ApiModelProperty("主键")
	private Long id;

    /**
     * 封面
     */
	@Excel(name = "封面")
	@ApiModelProperty("封面")
	private String img;

    /**
     * 标题
     */
	@Excel(name = "标题")
	@ApiModelProperty("标题")
	private String title;

    /**
     * 类型
     */
	@Excel(name = "类型")
	@ApiModelProperty("类型")
	private String type;

    /**
     * 跳转路径
     */
	@Excel(name = "跳转路径")
	@ApiModelProperty("跳转路径")
	private String url;

    /**
     * 状态
     */
	@Excel(name = "状态")
	@ApiModelProperty("状态")
	private String status;

    /**
     * 创建时间
     */
	@Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
