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
 * 轮播内容设置对象 app_carousel
 *
 * @author streaker
 * @date 2022-08-14
 */
@Data
@Accessors(chain = true)
@TableName("app_carousel")
public class AppCarousel extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 封面
     */
    private String img;
    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private String type;
    /**
     * 跳转路径
     */
    private String url;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;

}
