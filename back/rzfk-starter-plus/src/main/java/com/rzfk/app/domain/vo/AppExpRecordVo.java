package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户经验记录视图对象 app_exp_record
 *
 * @author streaker
 * @date 2022-08-13
 */
@Data
@ApiModel("用户经验记录视图对象")
public class AppExpRecordVo {

	private static final long serialVersionUID = 1L;

	/** $pkColumn.columnComment */
	@ApiModelProperty("$pkColumn.columnComment")
	private Long id;

    /**
     * 用户主键
     */
	@Excel(name = "用户主键")
	@ApiModelProperty("用户主键")
	private Long userId;

    /**
     * 操作前经验
     */
	@Excel(name = "操作前经验")
	@ApiModelProperty("操作前经验")
	private String preExp;

    /**
     * 变化经验
     */
	@Excel(name = "变化经验")
	@ApiModelProperty("变化经验")
	private String changeExp;

    /**
     * 操作后经验
     */
	@Excel(name = "操作后经验")
	@ApiModelProperty("操作后经验")
	private String afterExp;

    /**
     * 类型
     */
	@Excel(name = "类型")
	@ApiModelProperty("类型")
	private String type;


}
