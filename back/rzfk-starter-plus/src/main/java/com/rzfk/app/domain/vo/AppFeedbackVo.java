package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 意见反馈视图对象 app_feedback
 *
 * @author streaker
 * @date 2022-08-18
 */
@Data
@ApiModel("意见反馈视图对象")
public class AppFeedbackVo {

	private static final long serialVersionUID = 1L;

	/** ID */
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 创建时间
     */
	@Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("创建时间")
	private Date createTime;

    /**
     * 反馈内容
     */
	@Excel(name = "反馈内容")
	@ApiModelProperty("反馈内容")
	private String context;

    /**
     * 图片
     */
	@Excel(name = "图片")
	@ApiModelProperty("图片")
	private String images;

    /**
     * 手机号
     */
	@Excel(name = "手机号")
	@ApiModelProperty("手机号")
	private String mobile;

    /**
     * 类型
     */
	@Excel(name = "类型")
	@ApiModelProperty("类型")
	private String type;

    /**
     * 用户主键
     */
	@Excel(name = "用户主键")
	@ApiModelProperty("用户主键")
	private Long userId;


}
