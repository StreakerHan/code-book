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
 * App资源互动记录对象 app_interact_record
 *
 * @author streaker
 * @date 2022-08-15
 */
@Data
@Accessors(chain = true)
@TableName("app_interact_record")
public class AppInteractRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 资源类型
     */
    private String type;
    /**
     * 资源id
     */
    private Long sourceId;
    /**
     * 互动类型（view，like，collect）
     */
    private String interactType;

    private Date createTime;

    @TableField(exist = false)
    private String image;

    @TableField(exist = false)
    private String title;

    @TableField(exist = false)
    private String image1;

    @TableField(exist = false)
    private String title1;

    @TableField(exist = false)
    private String view;

    @TableField(exist = false)
    private String postType;
}
