package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * app用户登录记录视图对象 app_login_record
 *
 * @author streaker
 * @date 2022-08-10
 */
@Data
@ApiModel("app用户登录记录视图对象")
public class AppLoginRecordVo {

	private static final long serialVersionUID = 1L;

	/** $pkColumn.columnComment */
	@ApiModelProperty("$pkColumn.columnComment")
	private Long id;

    /**
     * 用户id
     */
	@Excel(name = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 登录时间
     */
	@Excel(name = "登录时间" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("登录时间")
	private Date loginTime;

    /**
     * 登录ip
     */
	@Excel(name = "登录ip")
	@ApiModelProperty("登录ip")
	private String loginIp;


}
