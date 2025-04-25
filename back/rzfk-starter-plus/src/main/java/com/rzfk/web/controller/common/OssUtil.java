package com.rzfk.web.controller.common;

import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.pig4cloud.plugin.oss.OssProperties;
import com.pig4cloud.plugin.oss.service.OssTemplate;
import com.rzfk.common.utils.spring.SpringUtils;
import com.rzfk.framework.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author jpx
 * @date 2021/6/11 16:40
 **/
@Slf4j
public class OssUtil {
	private static OssTemplate template = SpringUtils.getBean(OssTemplate.class);
	private static OssProperties properties = SpringUtils.getBean(OssProperties.class);
	private static ServerConfig serverConfig = SpringUtils.getBean(ServerConfig.class);

	public static String upload(String path, byte[] bytes) throws Exception {
		return upload(path,bytes,true);
	}
	public static String upload(String path, byte[] bytes,boolean withBaseUrl) throws Exception {
		return upload(properties.getBucketName(),path,bytes,withBaseUrl);
	}
	public static String upload(String path, InputStream in) throws Exception {
		return upload(path,in,true);
	}
	public static String upload(String path, InputStream in,boolean withBaseUrl) throws Exception {
		return upload(properties.getBucketName(),path,in,withBaseUrl);
	}
	/**
	 * 上传接口
	 *
	 * @param bucket
	 * @param path
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String upload(String bucket, String path, InputStream in,boolean withBaseUrl) throws Exception {
		/**
		 * 使用ByteArrayInputStream 避免minio报错
		 * 见 https://github.com/aws/aws-sdk-java/issues/427#issuecomment-273550783
		 */
		byte[] bytes = IOUtils.toByteArray(in);
		return upload(bucket,path,bytes,withBaseUrl);
	}

	/**
	 * 上传接口
	 * @param bucket
	 * @param path
	 * @param bytes
	 * @return 访问地址
	 * @throws Exception
	 */
	public static String upload(String bucket,String path,byte[] bytes,boolean withBaseUrl) throws Exception {
		template.putObject(bucket, path, new ByteArrayInputStream(bytes));
//		String rtnVal = StrUtil.SLASH + "oss" + StrUtil.SLASH + bucket + StrUtil.SLASH + path;
		String rtnVal = path;
		if(withBaseUrl){
			rtnVal = serverConfig.baseUrl + rtnVal;
		}
		return rtnVal;
	}
	/**
	 * 删除接口
	 *
	 * @param bucket
	 * @param path
	 * @return
	 */
	public static boolean delete(String bucket, String path) {
		try {
			template.removeObject(bucket, path);
			return true;
		} catch (Exception e) {
			log.error("文件删除失败：{}", e);
			return false;
		}
	}

	/**
	 * 文件下载 兼容多层目录
	 *
	 * @param bucket
	 * @param path
	 * @return
	 */
	public static InputStream get(String bucket, String path) throws Exception{
		try (S3Object s3Object = template.getObject(bucket, path)) {
			return s3Object.getObjectContent();
		} catch (Exception e) {
			log.error("文件读取异常: {}", e.getLocalizedMessage());
			throw e;
		}
	}
}
