package com.rzfk;

import com.alibaba.fastjson.JSONArray;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 启动程序
 *
 * @author ruoyi
 */

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableScheduling
public class Application
{
    public static void main(String[] args)
    {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(Application.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  rzfk-starter-plus启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
