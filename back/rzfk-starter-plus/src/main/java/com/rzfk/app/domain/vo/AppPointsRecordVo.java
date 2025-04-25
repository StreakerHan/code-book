package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 积分记录视图对象 app_points_record
 *
 * @author streaker
 * @date 2022-08-13
 */
@Data
@ApiModel("积分记录视图对象")
public class AppPointsRecordVo {

	private static final long serialVersionUID = 1L;

	/** ID */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 创建时间
	 */
	@Excel(name = "创建时间")
	@ApiModelProperty("创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

    /**
     * 消费之前积分
     */
	@Excel(name = "消费之前积分")
	@ApiModelProperty("消费之前积分")
	private String beforePoint;

    /**
     * 内容
     */
	@Excel(name = "内容")
	@ApiModelProperty("内容")
	private String content;

    /**
     * 会员ID
     */
	@Excel(name = "会员ID")
	@ApiModelProperty("会员ID")
	private Long memberId;

    /**
     * 会员名称
     */
	@Excel(name = "会员名称")
	@ApiModelProperty("会员名称")
	private String memberName;

    /**
     * 当前积分
     */
	@Excel(name = "当前积分")
	@ApiModelProperty("当前积分")
	private String point;

    /**
     * 消费积分类型
     */
	@Excel(name = "消费积分类型")
	@ApiModelProperty("消费积分类型")
	private String pointType;

    /**
     * 消费积分
     */
	@Excel(name = "消费积分")
	@ApiModelProperty("消费积分")
	private String variablePoint;

	@Excel(name = "变化类型")
	@ApiModelProperty("变化类型")
	private String changeType;
}
