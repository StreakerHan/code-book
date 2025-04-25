package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * app版本管理视图对象 app_version
 *
 * @author streaker
 * @date 2022-08-20
 */
@Data
@ApiModel("app版本管理视图对象")
public class AppVersionVo {
	private static final long serialVersionUID = 1L;

	/** ID */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 更新内容
	 */
	@Excel(name = "更新内容")
	@ApiModelProperty("更新内容")
	private String content;

	/**
	 * 下载地址
	 */
	@Excel(name = "下载地址")
	@ApiModelProperty("下载地址")
	private String downloadUrl;

	/**
	 * 是否强制更新
	 */
	@Excel(name = "是否强制更新")
	@ApiModelProperty("是否强制更新")
	private String forceUpdate;

	/**
	 * 类型
	 */
	@Excel(name = "类型")
	@ApiModelProperty("类型")
	private String type;

	/**
	 * 版本号
	 */
	@Excel(name = "版本号")
	@ApiModelProperty("版本号")
	private String version;

	/**
	 * 版本名称
	 */
	@Excel(name = "版本名称")
	@ApiModelProperty("版本名称")
	private String versionName;

	/**
	 * 版本更新时间
	 */
	@Excel(name = "版本更新时间" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("版本更新时间")
	private Date versionUpdateDate;

}
