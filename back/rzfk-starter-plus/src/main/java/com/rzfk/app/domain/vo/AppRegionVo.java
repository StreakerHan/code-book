package com.rzfk.app.domain.vo;

import com.rzfk.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 全国区划视图对象 app_region
 *
 * @author streaker
 * @date 2022-08-10
 */
@Data
@ApiModel("全国区划视图对象")
public class AppRegionVo {

	private static final long serialVersionUID = 1L;

	/** ID */
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 删除标志 true/false 删除/未删除
     */
	@Excel(name = "删除标志 true/false 删除/未删除")
	@ApiModelProperty("删除标志 true/false 删除/未删除")
	private Integer deleteFlag;

    /**
     * 区域编码
     */
	@Excel(name = "区域编码")
	@ApiModelProperty("区域编码")
	private String adCode;

    /**
     * 区域中心点经纬度
     */
	@Excel(name = "区域中心点经纬度")
	@ApiModelProperty("区域中心点经纬度")
	private String center;

    /**
     * 城市代码
     */
	@Excel(name = "城市代码")
	@ApiModelProperty("城市代码")
	private String cityCode;

    /**
     * 行政区划级别
     */
	@Excel(name = "行政区划级别")
	@ApiModelProperty("行政区划级别")
	private String level;

    /**
     * 名称
     */
	@Excel(name = "名称")
	@ApiModelProperty("名称")
	private String name;

    /**
     * 排序
     */
	@Excel(name = "排序")
	@ApiModelProperty("排序")
	private Long orderNum;

    /**
     * 父ID
     */
	@Excel(name = "父ID")
	@ApiModelProperty("父ID")
	private Long parentId;

    /**
     * 行政地区路径
     */
	@Excel(name = "行政地区路径")
	@ApiModelProperty("行政地区路径")
	private String path;


}
