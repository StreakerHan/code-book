package com.rzfk.app.domain.vo;

import com.rzfk.app.domain.AppComment;
import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * app文章视图对象 app_post
 *
 * @author streaker
 * @date 2022-08-14
 */
@Data
@ApiModel("app文章视图对象")
public class AppPostVo {

	private static final long serialVersionUID = 1L;

	/** $pkColumn.columnComment */
	@ApiModelProperty("$pkColumn.columnComment")
	private Long id;

    /**
     * 标题
     */
	@Excel(name = "标题")
	@ApiModelProperty("标题")
	private String title;

    /**
     * 发布时间
     */
	@Excel(name = "发布时间" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("发布时间")
	private Date createTime;

    /**
     * 内容
     */
	@Excel(name = "内容")
	@ApiModelProperty("内容")
	private String content;

    /**
     * 封面
     */
	@Excel(name = "封面")
	@ApiModelProperty("封面")
	private String img;

    /**
     * 类型
     */
	@Excel(name = "类型")
	@ApiModelProperty("类型")
	private String type;

    /**
     * 状态
     */
	@Excel(name = "状态")
	@ApiModelProperty("状态")
	private String status;

    /**
     * 视频
     */
	@Excel(name = "视频")
	@ApiModelProperty("视频")
	private String video;

    /**
     * 链接
     */
	@Excel(name = "链接")
	@ApiModelProperty("链接")
	private String link;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = true)
	private String description;

	/**
	 * 图片
	 */
	@ApiModelProperty(value = "图片", required = true)
	private String image;

	private String author;

	private Long createBy;

	//浏览量
	private String view;
	//点赞数
	private String like;
	//评论数
	private String comment;
	//收藏数
	private String collect;

	//当前登录用户是否点赞
	private String isLike;

	//当前登录用户是否收藏
	private String isCollect;

	private List<AppComment> comments;
}
