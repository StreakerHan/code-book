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
 * 积分记录对象 app_points_record
 *
 * @author streaker
 * @date 2022-08-13
 */
@Data
@Accessors(chain = true)
@TableName("app_points_record")
public class AppPointsRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 消费之前积分
     */
    private String beforePoint;
    /**
     * 内容
     */
    private String content;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 当前积分
     */
    private String point;
    /**
     * 消费积分类型
     */
    private String pointType;
    /**
     * 消费积分
     */
    private String variablePoint;
    /**
     * 创建时间
     */
    private Date createTime;

    private String changeType;
}
