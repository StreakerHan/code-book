package com.rzfk.framework.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Locale;
import java.util.TimeZone;

/**
 * 程序注解配置
 *
 * @author Lion Li
 */
@Configuration
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
public class ApplicationConfig {
    /**
     * 时区配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return builder -> {
        	builder.locale(Locale.CHINA);
        	builder.timeZone(TimeZone.getDefault());
        	builder.serializerByType(Long.class,ToStringSerializer.instance);
        	builder.serializerByType(Long.TYPE,ToStringSerializer.instance);
        	// 通过modules会关闭查找，需要自行添加组件的序列化
		};
    }
}
