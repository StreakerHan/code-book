package com.rzfk.app.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.rzfk.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.rzfk.common.core.domain.TreeEntity;

/**
 * 书籍分类对象 app_book_category
 *
 * @author streaker
 * @date 2023-02-03
 */
@Data
@Accessors(chain = true)
@TableName("app_book_category")
public class AppBookCategory extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 封面图
     */
    private String img;
    /**
     * 描述
     */
    private String descb;
    /**
     * 链接
     */
    private String href;
    /**
     * 等级
     */
    private String level;

    private Long parentId;

}
