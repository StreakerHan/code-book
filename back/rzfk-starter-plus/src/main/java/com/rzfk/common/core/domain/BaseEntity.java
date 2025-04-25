package com.rzfk.common.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity基类
 *
 * @author ruoyi
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;


//    /**
//     * 搜索值
//     */
//    @ApiModelProperty(value = "搜索值")
//    @TableField(exist = false)
//    private String searchValue;
//
//    /**
//     * 请求参数
//     */
//    @ApiModelProperty(value = "请求参数")
//    @TableField(exist = false)
//    private Map<String, Object> params = new HashMap<>();

}
