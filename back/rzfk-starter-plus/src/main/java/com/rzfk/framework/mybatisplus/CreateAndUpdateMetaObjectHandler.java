package com.rzfk.framework.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.rzfk.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * MP注入处理器
 * @author Lion Li
 * @date 2021/4/25
 */
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {
	private final static String createTime ="createTime";
	private final static String createBy ="createBy";
	private final static String createIdBy = "createIdBy";
	private final static String updateTime ="updateTime";
	private final static String updateBy ="updateBy";
	private final static String updateIdBy = "updateIdBy";
	@Override
	public void insertFill(MetaObject metaObject) {
		//根据属性名字设置要填充的值
		if (metaObject.hasGetter(createTime)) {
			if (metaObject.getValue(createTime) == null) {
				this.setFieldValByName(createTime, new Date(), metaObject);
			}
		}
		if (metaObject.hasGetter(createBy)) {
			if (metaObject.getValue(createBy) == null) {
				this.setFieldValByName(createBy, SecurityUtils.getUsername(), metaObject);
			}
		}
		if (metaObject.hasGetter(createIdBy)) {
			if (metaObject.getValue(createIdBy) == null) {
				this.setFieldValByName(createIdBy, SecurityUtils.getLoginUser().getUser().getUserId().toString(), metaObject);
			}
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		if (metaObject.hasGetter(updateBy)) {
			if (metaObject.getValue(updateBy) == null) {
				this.setFieldValByName(updateBy, SecurityUtils.getUsername(), metaObject);
			}
		}
		if (metaObject.hasGetter(updateTime)) {
			if (metaObject.getValue(updateTime) == null) {
				this.setFieldValByName(updateTime, new Date(), metaObject);
			}
		}
		if (metaObject.hasGetter(updateIdBy)) {
			if (metaObject.getValue(updateIdBy) == null) {
				this.setFieldValByName(updateIdBy, SecurityUtils.getLoginUser().getUser().getUserId().toString(), metaObject);
			}
		}
	}

}
