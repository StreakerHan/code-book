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
 * 短信发送记录业务对象 app_sms_record
 *
 * @author streaker
 * @date 2022-08-20
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("短信发送记录业务对象")
public class AppSmsRecordBo extends BaseEntity {

    private Long id;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", required = true)
    @NotBlank(message = "手机号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;

    /**
     * 发送时间
     */
    @ApiModelProperty(value = "发送时间")
    private Date createTime;

    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址", required = true)
    @NotBlank(message = "ip地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ip;

    /**
     * 发送内容
     */
    @ApiModelProperty(value = "发送内容", required = true)
    @NotBlank(message = "发送内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;


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
