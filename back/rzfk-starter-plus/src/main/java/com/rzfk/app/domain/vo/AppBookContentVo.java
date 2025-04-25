package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 书籍内容视图对象 app_book_content
 *
 * @author streaker
 * @date 2023-02-03
 */
@Data
@ApiModel("书籍内容视图对象")
public class AppBookContentVo {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@ApiModelProperty("主键")
	private Long id;

    /**
     * 书籍主键
     */
	@Excel(name = "书籍主键")
	@ApiModelProperty("书籍主键")
	private Long bookId;

    /**
     * 目录名称
     */
	@Excel(name = "目录名称")
	@ApiModelProperty("目录名称")
	private String catalogueName;

    /**
     * 目录顺序
     */
	@Excel(name = "目录顺序")
	@ApiModelProperty("目录顺序")
	private String seq;

    /**
     * 内容链接
     */
	@Excel(name = "内容链接")
	@ApiModelProperty("内容链接")
	private String href;

    /**
     * 内容
     */
	@Excel(name = "内容")
	@ApiModelProperty("内容")
	private String content;

    /**
     * 父目录主键
     */
	@Excel(name = "父目录主键")
	@ApiModelProperty("父目录主键")
	private Long parentId;

    /**
     * 级别
     */
	@Excel(name = "级别")
	@ApiModelProperty("级别")
	private String level;


}
