package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 书籍分类视图对象 app_book_category
 *
 * @author streaker
 * @date 2023-02-03
 */
@Data
@ApiModel("书籍分类视图对象")
public class AppBookCategoryVo {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@ApiModelProperty("主键")
	private Long id;

    /**
     * 分类名称
     */
	@Excel(name = "分类名称")
	@ApiModelProperty("分类名称")
	private String name;

    /**
     * 父id
     */
	@Excel(name = "父id")
	@ApiModelProperty("父id")
	private Long parentId;

    /**
     * 封面图
     */
	@Excel(name = "封面图")
	@ApiModelProperty("封面图")
	private String img;

    /**
     * 描述
     */
	@Excel(name = "描述")
	@ApiModelProperty("描述")
	private String descb;

    /**
     * 链接
     */
	@Excel(name = "链接")
	@ApiModelProperty("链接")
	private String href;

    /**
     * 等级
     */
	@Excel(name = "等级")
	@ApiModelProperty("等级")
	private String level;


}
