package com.rzfk.app.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.rzfk.common.core.domain.BaseEntity;

/**
 * app文章对象 app_post
 *
 * @author streaker
 * @date 2022-08-14
 */
@Data
@Accessors(chain = true)
@TableName("app_post")
public class AppPost extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 封面
     */
    private String img;
    /**
     * 类型
     */
    private String type;
    /**
     * 状态
     */
    private String status;
    /**
     * 视频
     */
    private String video;
    /**
     * 链接
     */
    private String link;

    private Date createTime;

    private Long createBy;

    @TableField(exist = false)
    private String view;

    @TableField(exist = false)
    private String like;

    @TableField(exist = false)
    private String collect;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片
     */
    private String image;

}
