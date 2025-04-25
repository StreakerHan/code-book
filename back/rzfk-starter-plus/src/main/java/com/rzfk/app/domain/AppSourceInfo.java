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
 * 资源信息对象 app_source_info
 *
 * @author streaker
 * @date 2022-08-19
 */
@Data
@Accessors(chain = true)
@TableName("app_source_info")
public class AppSourceInfo extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 类型id
     */
    private Long typeId;
    /**
     * 排序
     */
    private String seq;
    /**
     * 类型
     */
    private String type;
    /**
     * 链接
     */
    private String url;
    /**
     * 百度云
     */
    private String link;
    /**
     * 封面
     */
    private String img;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 发布时间
     */
    private String uploadTime;
    /**
     * 内容
     */
    private String content;
    /**
     * 分类名称
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 浏览次数
     */
    @TableField(exist = false)
    private String view;
}
