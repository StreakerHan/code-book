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
 * 意见反馈对象 app_feedback
 *
 * @author streaker
 * @date 2022-08-18
 */
@Data
@Accessors(chain = true)
@TableName("app_feedback")
public class AppFeedback extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 反馈内容
     */
    private String context;
    /**
     * 图片
     */
    private String images;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 类型
     */
    private String type;
    /**
     * 用户主键
     */
    private Long userId;

}
