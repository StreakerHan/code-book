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
 * 全国区划对象 app_region
 *
 * @author streaker
 * @date 2022-08-10
 */
@Data
@Accessors(chain = true)
@TableName("app_region")
public class AppRegion extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 删除标志 true/false 删除/未删除
     */
    private Integer deleteFlag;
    /**
     * 区域编码
     */
    private String adCode;
    /**
     * 区域中心点经纬度
     */
    private String center;
    /**
     * 城市代码
     */
    private String cityCode;
    /**
     * 行政区划级别
     */
    private String level;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private Long orderNum;
    /**
     * 父ID
     */
    private Long parentId;
    /**
     * 行政地区路径
     */
    private String path;

}
