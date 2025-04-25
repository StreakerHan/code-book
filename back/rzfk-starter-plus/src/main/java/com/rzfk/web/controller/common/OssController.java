package com.rzfk.web.controller.common;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.pig4cloud.plugin.oss.OssProperties;
import com.pig4cloud.plugin.oss.service.OssTemplate;
import com.rzfk.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * @author jpx
 * @date 2021/5/26 17:30
 **/
@RestController
@RequestMapping("/oss")
@Slf4j
public class OssController {
	private static String FILE_PATH_STR = "filePath";
	private static String USE_ORGIN_NAME = "useOrginName";
	private static String BUCKET_NAME_STR = "bucketName";
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	@Autowired
	private OssProperties properties;
	@Autowired
	private OssTemplate ossTemplate;

	/**
	 * 上传文件
	 * 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(bucketName, filename)
	 */
	@PostMapping("/upload")
	public R upload(@RequestParam Map<String, Object> param, MultipartFile file, HttpServletRequest request) throws Exception {
		String path = MapUtil.getStr(param, FILE_PATH_STR, "");
		if (StrUtil.isNotBlank(path)) {
			path += StrUtil.SLASH;
		}
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		String fileName = MapUtil.getStr(param, USE_ORGIN_NAME) == "1" ? file.getOriginalFilename() : IdUtil.simpleUUID() + StrUtil.DOT + suffix;
		String bucket = StrUtil.isEmpty(MapUtil.getStr(param, BUCKET_NAME_STR)) ? properties.getBucketName() : MapUtil.getStr(param, BUCKET_NAME_STR);
		String url = OssUtil.upload(bucket, path + fileName, file.getBytes(),true);
		return R.success("上传成功", url);
	}

	@DeleteMapping("/{bucket}/**")
	public R delete(@PathVariable String bucket, HttpServletRequest request, HttpServletResponse response) {
		String uri = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		String path = antPathMatcher.extractPathWithinPattern(pattern, uri);
		if (OssUtil.delete(bucket, path)) {
			return R.success();
		} else {
			return R.error();
		}
	}

	/**
	 * 文件下载 兼容多层目录
	 *
	 * @param bucket
	 * @param request
	 * @param response
	 */
	@GetMapping("/{bucket}/**")
	public void file(@PathVariable String bucket, HttpServletRequest request, HttpServletResponse response) {
		String uri = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		String path = antPathMatcher.extractPathWithinPattern(pattern, uri);

		try (S3Object s3Object = ossTemplate.getObject(bucket, path)) {
			response.setContentType("application/octet-stream; charset=UTF-8");
			IoUtil.copy(s3Object.getObjectContent(), response.getOutputStream());
		} catch (Exception e) {
			log.error("文件读取异常: {}", e.getLocalizedMessage());
		}
	}
}
