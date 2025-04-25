package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * App资源互动记录视图对象 app_interact_record
 *
 * @author streaker
 * @date 2022-08-15
 */
@Data
@ApiModel("App资源互动记录视图对象")
public class AppInteractRecordVo {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@ApiModelProperty("主键")
	private Long id;

    /**
     * 用户主键
     */
	@Excel(name = "用户主键")
	@ApiModelProperty("用户主键")
	private Long userId;

    /**
     * 创建时间
     */
	@Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("创建时间")
	private Date createTime;

    /**
     * 资源类型
     */
	@Excel(name = "资源类型")
	@ApiModelProperty("资源类型")
	private String type;

    /**
     * 资源id
     */
	@Excel(name = "资源id")
	@ApiModelProperty("资源id")
	private Long sourceId;

    /**
     * 互动类型（view，like，collect）
     */
	@Excel(name = "互动类型" , readConverterExp = "v=iew，like，collect")
	@ApiModelProperty("互动类型（view，like，collect）")
	private String interactType;

	private String image;

	private String image1;

	private String title;

	private String title1;

	private String view;

	private String postType;
}
