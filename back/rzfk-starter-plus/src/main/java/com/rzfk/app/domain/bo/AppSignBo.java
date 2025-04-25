package com.rzfk.app.domain.bo;

import com.rzfk.common.annotation.Excel;
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
 * 用户签到记录业务对象 app_sign
 *
 * @author streaker
 * @date 2022-08-13
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户签到记录业务对象")
public class AppSignBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 会员用户ID
     */
    @ApiModelProperty(value = "会员用户ID", required = true)
    @NotNull(message = "会员用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long memberId;

    /**
     * 会员用户名
     */
    @ApiModelProperty(value = "会员用户名", required = true)
    @NotBlank(message = "会员用户名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    @NotBlank(message = "创建时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date createTime;

    /**
     * 连续签到天数
     */
    @ApiModelProperty(value = "连续签到天数", required = true)
    @NotNull(message = "连续签到天数不能为空", groups = { AddGroup.class, EditGroup.class })
    private String signDay;

    /**
     * 签到日 
     */
    @ApiModelProperty(value = "签到日 ", required = true)
    @NotNull(message = "签到日 不能为空", groups = { AddGroup.class, EditGroup.class })
    private String day;

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
