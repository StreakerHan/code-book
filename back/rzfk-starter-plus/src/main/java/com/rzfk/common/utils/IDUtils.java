package com.rzfk.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.util.UUID;

/**
 * ID 获取帮助类
 */
public class IDUtils {
    private static Snowflake worker ;
    static{
        worker = IdUtil.getSnowflake(1,1);
    }
    public static Long getId(){
        return worker.nextId();
    }
}
