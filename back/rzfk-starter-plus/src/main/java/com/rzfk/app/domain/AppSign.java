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
 * 用户签到记录对象 app_sign
 *
 * @author streaker
 * @date 2022-08-13
 */
@Data
@Accessors(chain = true)
@TableName("app_sign")
public class AppSign extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 会员用户ID
     */
    private Long memberId;
    /**
     * 会员用户名
     */
    private String memberName;
    /**
     * 连续签到天数
     */
    private String signDay;
    /**
     * 签到日 
     */
    private String day;
    /**
     * 创建时间
     */
    private Date createTime;

}
