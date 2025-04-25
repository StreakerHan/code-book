package com.rzfk.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.rzfk.common.core.redis.RedisCache;
import com.rzfk.common.utils.spring.SpringUtils;
import com.rzfk.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Slf4j
public class ZipFileUtil {
    public static String DATA_FILE = "data.json";
    public static String KEY_TYPE = "type";
    public static String KEY_DATA = "data";
    public static String KEY_IMGS = "imgs";
    public static String KEY_FILE_PATH = "sys.file.path";
    public static String KEY_BASE_URL = "sys.base.url";
    public static ISysConfigService configService;
    static {
		configService = SpringUtils.getBean(ISysConfigService.class);
	}
    public static byte[] genZip(String type, JSONObject data, List<String> fileKeys) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        JSONObject json = new JSONObject();
        json.set(KEY_TYPE,type);
        if(CollUtil.isNotEmpty(fileKeys)){
            for(String key: fileKeys){
                String url = data.getStr(key);
                String fileName = IdUtil.fastUUID()+".jpg";
                zip.putNextEntry(new ZipEntry(fileName));
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                HttpUtil.download(url,out,false);
                zip.write(out.toByteArray());
                zip.closeEntry();
                data.set(key,fileName);
            }
            json.set(KEY_IMGS,StrUtil.join(",",fileKeys.toArray()));
        }
        json.set(KEY_DATA,data);
        log.debug("data:[{}]",json.toString());
        // data.json
        zip.putNextEntry(new ZipEntry(DATA_FILE));
        zip.write(json.toString().getBytes(Charset.defaultCharset()));
        zip.closeEntry();
        zip.close();
        return outputStream.toByteArray();
    }
    public static JSONObject readZip(ZipFile zf) throws Exception{
        JSONObject result = new JSONObject();
        List<String> list = ZipUtil.listFileNames(zf,"");
        log.debug("文件列表：{}",JSONUtil.toJsonStr(list));
        for(String s: list){
            if(s.endsWith(".json")){
                ZipEntry ze = zf.getEntry(s);
                InputStream in = zf.getInputStream(ze);
                JSONObject json = JSONUtil.parseObj(readFileStr(in));
                result.set(StrUtil.subBefore(s,".",true),json);
            }
        }
        String imgs = result.getJSONObject(KEY_DATA).getStr(KEY_IMGS);
        if(StrUtil.isNotEmpty(imgs)){
            String[] arr = imgs.split(StrUtil.COMMA);
            //上传对象存储
            for(int i=0,l=arr.length;i<l;i++){
                String fileName = result.getJSONObject(KEY_DATA).getJSONObject(KEY_DATA).getStr(arr[i]);
                ZipEntry ze = zf.getEntry(fileName);
                InputStream in = zf.getInputStream(ze);
                FileUtil.writeFromStream(in,configService.selectConfigByKey(KEY_FILE_PATH)+fileName);
                String url = configService.selectConfigByKey(KEY_BASE_URL)+fileName;
                // 上传更新路径
                result.getJSONObject(KEY_DATA).getJSONObject(KEY_DATA).set(arr[i],url);
            }
        }
        return result;
    }
    public static String readFileStr(InputStream in) throws Exception{
        BufferedReader br = IoUtil.getReader(in, Charset.defaultCharset());
        String line;
        StringBuffer result = new StringBuffer();
        while ((line = br.readLine()) != null) {
            result.append(line+"\n");
        }
        return result.toString();
    }
}
