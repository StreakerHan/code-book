package com.rzfk.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 验证码 配置属性
 *
 * @author Lion Li
 */
@Data
@Component
@ConfigurationProperties(prefix = "xss")
public class XssProperties {

    /**
     * 过滤开关
     */
    private String enabled;

    /**
     * 排除链接（多个用逗号分隔）
     */
    private String excludes;

    /**
     * 匹配链接
     */
    private String urlPatterns;

}
