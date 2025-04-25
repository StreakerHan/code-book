package com.rzfk.app.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.rzfk.common.core.domain.BaseEntity;

/**
 * 短信发送记录对象 app_sms_record
 *
 * @author streaker
 * @date 2022-08-20
 */
@Data
@Accessors(chain = true)
@TableName("app_sms_record")
public class AppSmsRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 发送内容
     */
    private String content;

    private Date createTime;

}
