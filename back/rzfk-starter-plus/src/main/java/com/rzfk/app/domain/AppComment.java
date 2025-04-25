package com.rzfk.app.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.rzfk.common.core.domain.BaseEntity;

/**
 * 文章评论对象 app_comment
 *
 * @author streaker
 * @date 2022-08-17
 */
@Data
@Accessors(chain = true)
@TableName("app_comment")
public class AppComment extends BaseEntity {

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
     * 文章主键
     */
    private Long postId;
    /**
     * 评论内容
     */
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private String nickname;

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private String image;

    @TableField(exist = false)
    private String title;

    @TableField(exist = false)
    private String image1;

    @TableField(exist = false)
    private String title1;
}
