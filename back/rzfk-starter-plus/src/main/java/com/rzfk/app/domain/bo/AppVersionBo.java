package com.rzfk.app.domain.bo;

import com.rzfk.common.core.validate.AddGroup;
import com.rzfk.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.rzfk.common.core.domain.BaseEntity;

/**
 * app版本管理业务对象 app_version
 *
 * @author streaker
 * @date 2022-08-20
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("app版本管理业务对象")
public class AppVersionBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 更新内容
     */
    @ApiModelProperty(value = "更新内容", required = true)
    @NotBlank(message = "更新内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 下载地址
     */
    @ApiModelProperty(value = "下载地址", required = true)
    @NotBlank(message = "下载地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String downloadUrl;

    /**
     * 是否强制更新
     */
    @ApiModelProperty(value = "是否强制更新", required = true)
    @NotBlank(message = "是否强制更新不能为空", groups = { AddGroup.class, EditGroup.class })
    private String forceUpdate;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    @NotBlank(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    private String version;

    /**
     * 版本名称
     */
    @ApiModelProperty(value = "版本名称", required = true)
    @NotBlank(message = "版本名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String versionName;

    /**
     * 版本更新时间
     */
    @ApiModelProperty(value = "版本更新时间", required = true)
    private Date versionUpdateDate;


    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer pageNum;

    /**
     * 排序列
     */
    @ApiModelProperty("排序列")
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向", example = "asc,desc")
    private String isAsc;

}
