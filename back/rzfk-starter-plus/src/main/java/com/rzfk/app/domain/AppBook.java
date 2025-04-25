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
 * 书籍信息对象 app_book
 *
 * @author streaker
 * @date 2023-02-03
 */
@Data
@Accessors(chain = true)
@TableName("app_book")
public class AppBook extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 书名
     */
    private String name;
    /**
     * 封面
     */
    private String img;
    /**
     * 链接
     */
    private String href;
    /**
     * 阅读链接
     */
    private String readHref;
    /**
     * 标签
     */
    private String tags;
    /**
     * 简介
     */
    private String descb;

    private Long type;

    private Date createTime;

    @TableField(exist = false)
    private String typeName;

    private String chapters;

}
