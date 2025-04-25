package com.rzfk.framework.aspectj;

import com.rzfk.common.annotation.DevReplace;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Profile("dev")
@Aspect
@Component
public class DevReplaceAspect {
	@Value("${sys.file.path}")
	private String filePath;
	private static final Logger log = LoggerFactory.getLogger(DevReplaceAspect.class);

	// 配置织入点
	@Pointcut("@annotation(com.rzfk.common.annotation.DevReplace)")
	public void devPointCut() {
	}

	@Around("devPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		return handleAround(joinPoint);
	}

	protected Object handleAround(final ProceedingJoinPoint joinPoint) throws Throwable{
		//获取方法参数值数组
		Object[] args = joinPoint.getArgs();
		// 获得注解
		DevReplace devReplace = getAnnotationLog(joinPoint);
		if (devReplace != null) {
			// 获取配置文件
			if("sys.file.path".equals(args[0])){
				return filePath;
			}
		}
		return joinPoint.proceed(args);
	}

	/**
	 * 是否存在注解，如果存在就获取
	 */
	private DevReplace getAnnotationLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(DevReplace.class);
		}
		return null;
	}
}
