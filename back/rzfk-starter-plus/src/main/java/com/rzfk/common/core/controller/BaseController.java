package com.rzfk.common.core.controller;

import cn.hutool.core.util.StrUtil;
import com.rzfk.common.core.domain.R;
import com.rzfk.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * web层通用数据处理
 *
 * @author ruoyi
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected R<Void> toAjax(int rows)
    {
        return rows > 0 ? R.success() : R.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected R<Void> toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 返回成功
     */
    public R<Void> success()
    {
        return R.success();
    }

    /**
     * 返回失败消息
     */
    public R<Void> error()
    {
        return R.error();
    }

    /**
     * 返回成功消息
     */
    public R<Void> success(String message)
    {
        return R.success(message);
    }

    /**
     * 返回失败消息
     */
    public R<Void> error(String message)
    {
        return R.error(message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StrUtil.format("redirect:{}", url);
    }
}
