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
 * 书籍内容对象 app_book_content
 *
 * @author streaker
 * @date 2023-02-03
 */
@Data
@Accessors(chain = true)
@TableName("app_book_content")
public class AppBookContent extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 书籍主键
     */
    private Long bookId;
    /**
     * 目录名称
     */
    private String catalogueName;
    /**
     * 目录顺序
     */
    private String seq;
    /**
     * 内容链接
     */
    private String href;
    /**
     * 内容
     */
    private String content;
    /**
     * 级别
     */
    private String level;

    private Long parentId;

}
