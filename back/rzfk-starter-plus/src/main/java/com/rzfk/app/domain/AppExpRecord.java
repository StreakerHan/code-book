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
 * 用户经验记录对象 app_exp_record
 *
 * @author streaker
 * @date 2022-08-13
 */
@Data
@Accessors(chain = true)
@TableName("app_exp_record")
public class AppExpRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 操作前经验
     */
    private String preExp;
    /**
     * 变化经验
     */
    private String changeExp;
    /**
     * 操作后经验
     */
    private String afterExp;
    /**
     * 类型
     */
    private String type;
    /**
     *
     */
    private Date createTime;
}
