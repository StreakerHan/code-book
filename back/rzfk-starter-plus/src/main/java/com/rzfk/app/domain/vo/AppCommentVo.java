package com.rzfk.app.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 文章评论视图对象 app_comment
 *
 * @author streaker
 * @date 2022-08-17
 */
@Data
@ApiModel("文章评论视图对象")
public class AppCommentVo {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@ApiModelProperty("主键")
	private Long id;

    /**
     * 用户主键
     */
	@Excel(name = "用户主键")
	@ApiModelProperty("用户主键")
	private Long userId;

    /**
     * 文章主键
     */
	@Excel(name = "文章主键")
	@ApiModelProperty("文章主键")
	private Long postId;

    /**
     * 创建时间
     */
	@Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("创建时间")
	private Date createTime;

    /**
     * 评论内容
     */
	@Excel(name = "评论内容")
	@ApiModelProperty("评论内容")
	private String content;

	private String image;

	private String title;

	private String image1;

	private String title1;


}
