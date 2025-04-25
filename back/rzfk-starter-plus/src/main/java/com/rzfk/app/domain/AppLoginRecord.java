package com.rzfk.app.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rzfk.common.core.domain.BaseEntity;

/**
 * app用户登录记录对象 app_login_record
 *
 * @author streaker
 * @date 2022-08-10
 */
@Data
@Accessors(chain = true)
@TableName("app_login_record")
public class AppLoginRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 登录ip
     */
    private String loginIp;

}
