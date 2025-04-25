package com.rzfk.app.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 书籍信息视图对象 app_book
 *
 * @author streaker
 * @date 2023-02-03
 */
@Data
@ApiModel("书籍信息视图对象")
public class AppBookVo {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@ApiModelProperty("主键")
	private Long id;

    /**
     * 书名
     */
	@Excel(name = "书名")
	@ApiModelProperty("书名")
	private String name;

    /**
     * 封面
     */
	@Excel(name = "封面")
	@ApiModelProperty("封面")
	private String img;

    /**
     * 链接
     */
	@Excel(name = "链接")
	@ApiModelProperty("链接")
	private String href;

    /**
     * 阅读链接
     */
	@Excel(name = "阅读链接")
	@ApiModelProperty("阅读链接")
	private String readHref;

    /**
     * 标签
     */
	@Excel(name = "标签")
	@ApiModelProperty("标签")
	private String tags;

    /**
     * 简介
     */
	@Excel(name = "简介")
	@ApiModelProperty("简介")
	private String descb;
	/**
	 * 分类
	 */
	@Excel(name = "分类")
	@ApiModelProperty("分类")
	private Long type;

    /**
     * 创建时间
     */
	@Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("创建时间")
	private Date createTime;

	@TableField(exist = false)
	private String typeName;

	private String chapters;

	@TableField(exist = false)
	private String collect;


}
