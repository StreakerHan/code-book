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
 * 资源信息视图对象 app_source_info
 *
 * @author streaker
 * @date 2022-08-19
 */
@Data
@ApiModel("资源信息视图对象")
public class AppSourceInfoVo {

	private static final long serialVersionUID = 1L;

	/** $pkColumn.columnComment */
	@ApiModelProperty("$pkColumn.columnComment")
	private Long id;

    /**
     * 资源名称
     */
	@Excel(name = "资源名称")
	@ApiModelProperty("资源名称")
	private String name;

    /**
     * 类型id
     */
	@Excel(name = "类型id")
	@ApiModelProperty("类型id")
	private Long typeId;

    /**
     * 排序
     */
	@Excel(name = "排序")
	@ApiModelProperty("排序")
	private String seq;

    /**
     * 类型
     */
	@Excel(name = "类型")
	@ApiModelProperty("类型")
	private String type;

    /**
     * 链接
     */
	@Excel(name = "链接")
	@ApiModelProperty("链接")
	private String url;

    /**
     * 百度云
     */
	@Excel(name = "百度云")
	@ApiModelProperty("百度云")
	private String link;

	/**
	 * 状态
	 */
	@Excel(name = "状态")
	@ApiModelProperty("状态")
	private String status;

	/**
	 * 封面
	 */
	@Excel(name = "封面")
	@ApiModelProperty("封面")
	private String img;

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

	//分类名称
	private String typeName;

	private List<AppComment> comments;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 发布时间
	 */
	private String uploadTime;

	/**
	 * 内容
	 */
	private String content;
}
