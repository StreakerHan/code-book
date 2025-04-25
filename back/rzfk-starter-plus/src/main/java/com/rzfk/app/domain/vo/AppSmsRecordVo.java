package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 短信发送记录视图对象 app_sms_record
 *
 * @author streaker
 * @date 2022-08-20
 */
@Data
@ApiModel("短信发送记录视图对象")
public class AppSmsRecordVo {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@ApiModelProperty("主键")
	private Long id;

    /**
     * 手机号码
     */
	@Excel(name = "手机号码")
	@ApiModelProperty("手机号码")
	private String phone;

    /**
     * 发送时间
     */
	@Excel(name = "发送时间" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("发送时间")
	private Date createTime;

    /**
     * ip地址
     */
	@Excel(name = "ip地址")
	@ApiModelProperty("ip地址")
	private String ip;

    /**
     * 发送内容
     */
	@Excel(name = "发送内容")
	@ApiModelProperty("发送内容")
	private String content;


}
