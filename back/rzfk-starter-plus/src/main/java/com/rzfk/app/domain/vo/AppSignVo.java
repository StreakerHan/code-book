package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 用户签到记录视图对象 app_sign
 *
 * @author streaker
 * @date 2022-08-13
 */
@Data
@ApiModel("用户签到记录视图对象")
public class AppSignVo {

	private static final long serialVersionUID = 1L;

	/** ID */
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 会员用户ID
     */
	@Excel(name = "会员用户ID")
	@ApiModelProperty("会员用户ID")
	private Long memberId;

    /**
     * 会员用户名
     */
	@Excel(name = "会员用户名")
	@ApiModelProperty("会员用户名")
	private String memberName;

    /**
     * 连续签到天数
     */
	@Excel(name = "连续签到天数")
	@ApiModelProperty("连续签到天数")
	private String signDay;

    /**
     * 签到日 
     */
	@Excel(name = "签到日 ")
	@ApiModelProperty("签到日 ")
	private String day;
	/**
	 * 创建时间
	 */
	@Excel(name = "创建时间 ")
	@ApiModelProperty("创建时间 ")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
