package com.rzfk.common.utils;

import com.aliyun.fileform.models.FileField;
import com.aliyun.openplatform20191219.models.AuthorizeFileUploadRequest;
import com.aliyun.openplatform20191219.models.AuthorizeFileUploadResponse;
import com.aliyun.oss.models.PostObjectRequest;
import com.aliyun.tea.TeaConverter;
import com.aliyun.tea.TeaPair;
import com.aliyun.teautil.models.RuntimeOptions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.ocr.model.v20191230.RecognizeLicensePlateRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeLicensePlateResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author StreakerHan
 * @version 1.0
 * @date 2021/5/4 16:52
 * <p>
 * 阿里云车牌号识别
 */

public class ViapiFileUtilAdvance {


    public static String getShUrl(InputStream file) throws IOException {
        String accessKey = "****";    //您的AccessKeyID
        String accessKeySecret = "*****";   //您的AccessKeySecret
        String regionId = "cn-shanghai";
        try (InputStream inputStream = file) {
            ViapiFileUtilAdvance fileUtils = ViapiFileUtilAdvance.getInstance(accessKey, accessKeySecret, regionId);
            String ossTempFileUrl = fileUtils.upload(inputStream);
            return ossTempFileUrl;
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
    }

    public static String getCarNoByUrl(String imgUrl) throws Exception {
        //压缩图片大小至2M
        byte[] imgBytes = getByteByPic(imgUrl);
        byte[] resultImg = compressUnderSize(imgBytes, 3072 * 1024);
        InputStream sbs = new ByteArrayInputStream(resultImg);
        imgUrl = ViapiFileUtilAdvance.getShUrl(sbs);
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "****", "****");
        IAcsClient client = new DefaultAcsClient(profile);
        RecognizeLicensePlateRequest request = new RecognizeLicensePlateRequest();
        request.setRegionId("cn-shanghai");
        request.setImageURL(imgUrl);
        try {
            RecognizeLicensePlateResponse response = client.getAcsResponse(request);
            System.out.println("****************************************************************** " + response.getData().getPlates().get(0).getPlateNumber());
            return new Gson().toJson(response.getData().getPlates().get(0));
        } catch (ServerException e) {
            e.printStackTrace();
            return e.toString();
        } catch (ClientException e) {
            return e.getErrMsg();
        }
    }

    public static String getCarNoByInputStream(InputStream inputStream) throws Exception {
        String imgUrl = ViapiFileUtilAdvance.getShUrl(inputStream);
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "****", "*****");
        IAcsClient client = new DefaultAcsClient(profile);
        RecognizeLicensePlateRequest request = new RecognizeLicensePlateRequest();
        request.setRegionId("cn-shanghai");
        request.setImageURL(imgUrl);
        try {
            RecognizeLicensePlateResponse response = client.getAcsResponse(request);
            System.out.println("****************************************************************** " + response.getData().getPlates().get(0).getPlateNumber());
            return new Gson().toJson(response.getData().getPlates().get(0));
        } catch (ServerException e) {
            e.printStackTrace();
            return e.toString();
        } catch (ClientException e) {
            return e.getErrMsg();
        }
    }

    public static InputStream buildInputStream(String filePath) throws IOException {
        if (StringUtils.startsWithAny(filePath, new CharSequence[]{"http://", "https://"})) {
            filePath = URLDecoder.decode(filePath, "UTF-8");
            URL url = new URL(filePath);
            URLConnection urlConnection = url.openConnection();
            return urlConnection.getInputStream();
        } else {
            File key1 = new File(filePath);
            return new FileInputStream(key1);
        }
    }

    public static synchronized ViapiFileUtilAdvance getInstance(String accessKeyId, String accessKeySecret, String regionId) throws Exception {
        String mapKey = String.format("%s:%s:%s", regionId, accessKeyId, accessKeySecret);
        ViapiFileUtilAdvance fileUtils = _map.get(mapKey);
        if (fileUtils == null) {
            _map.putIfAbsent(mapKey, new ViapiFileUtilAdvance(accessKeyId, accessKeySecret, regionId));
            fileUtils = _map.get(mapKey);
        }
        return fileUtils;
    }


    private ViapiFileUtilAdvance(String accessKeyId, String accessKeySecret, String regionId) throws Exception {
        Map<String, Object> cm = new HashMap<>();
        cm.put("regionId", regionId);
        cm.put("autoretry", "true");
        cm.put("accessKeyId", accessKeyId);
        cm.put("accessKeySecret", accessKeySecret);
        cm.put("connectTimeout", 15 * 1000);
        cm.put("readTimeout", 30 * 1000);
        cm.put("maxIdleConns", 200);
        cm.put("type", "access_key");
        cm.put("endpoint", "openplatform.aliyuncs.com");
        _runtime = RuntimeOptions.build(cm);
        com.aliyun.tearpc.models.Config authConfig = com.aliyun.tearpc.models.Config.build(cm);
        _authClient = new com.aliyun.openplatform20191219.Client(authConfig);
        //ossEndpointType = "internal";//aliyun-vpc
        _ossConfig = com.aliyun.oss.models.Config.build(TeaConverter.buildMap(
                new TeaPair("accessKeySecret", accessKeySecret),
                new TeaPair("type", "access_key"),
                new TeaPair("protocol", _authClient._protocol),
                new TeaPair("regionId", _authClient._regionId)
        ));
    }

    public String upload(InputStream inputStream) throws Exception {
        AuthorizeFileUploadRequest authRequest = AuthorizeFileUploadRequest.build(TeaConverter.buildMap(
                new TeaPair("product", "imageseg"),
                new TeaPair("regionId", _authClient._regionId)
        ));
        AuthorizeFileUploadResponse authResponse = _authClient.authorizeFileUploadWithOptions(authRequest, _runtime);
        com.aliyun.oss.models.Config ossConfig = new com.aliyun.oss.models.Config();
        com.aliyun.openapiutil.Client.convert(_ossConfig, ossConfig);
        ossConfig.accessKeyId = authResponse.accessKeyId;
        ossConfig.endpoint = com.aliyun.openapiutil.Client.getEndpoint(authResponse.endpoint, authResponse.useAccelerate, ossEndpointType);
        com.aliyun.oss.Client ossClient = new com.aliyun.oss.Client(ossConfig);
        FileField fileObj = FileField.build(TeaConverter.buildMap(
                new TeaPair("filename", authResponse.objectKey),
                new TeaPair("content", inputStream),
                new TeaPair("contentType", "")
        ));
        PostObjectRequest.PostObjectRequestHeader ossHeader = PostObjectRequest.PostObjectRequestHeader.build(TeaConverter.buildMap(
                new TeaPair("accessKeyId", authResponse.accessKeyId),
                new TeaPair("policy", authResponse.encodedPolicy),
                new TeaPair("signature", authResponse.signature),
                new TeaPair("key", authResponse.objectKey),
                new TeaPair("file", fileObj),
                new TeaPair("successActionStatus", "201")
        ));
        PostObjectRequest uploadRequest = PostObjectRequest.build(TeaConverter.buildMap(
                new TeaPair("bucketName", authResponse.bucket),
                new TeaPair("header", ossHeader)
        ));
        com.aliyun.ossutil.models.RuntimeOptions ossRuntime = new com.aliyun.ossutil.models.RuntimeOptions();
        com.aliyun.openapiutil.Client.convert(_runtime, ossRuntime);
        ossClient.postObject(uploadRequest, ossRuntime);
        String imageURL = "http://" + authResponse.bucket + "." + authResponse.endpoint + "/" + authResponse.objectKey + "";
        return imageURL;
    }

    RuntimeOptions _runtime;
    com.aliyun.openplatform20191219.Client _authClient;
    com.aliyun.oss.models.Config _ossConfig;
    String ossEndpointType = null;
    static Map<String, ViapiFileUtilAdvance> _map = new ConcurrentHashMap<>();


    public static byte[] getByteByPic(String imageUrl) throws IOException {
        try {
            URL httpUrl = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 将图片压缩到指定大小以内
     *
     * @param srcImgData 源图片数据
     * @param maxSize    目的图片大小
     * @return 压缩后的图片数据
     */
    public static byte[] compressUnderSize(byte[] srcImgData, long maxSize) {
        double scale = 0.9;
        byte[] imgData = Arrays.copyOf(srcImgData, srcImgData.length);

        if (imgData.length > maxSize) {
            do {
                try {
                    imgData = compress(imgData, scale);

                } catch (IOException e) {
                    throw new IllegalStateException("压缩图片过程中出错，请及时联系管理员！", e);
                }

            } while (imgData.length > maxSize);
        }

        return imgData;
    }

    /**
     * 按照 宽高 比例压缩
     *
     * @return 压缩后图片数据
     * @throws IOException 压缩图片过程中出错
     */
    public static byte[] compress(byte[] srcImgData, double scale) throws IOException {
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(srcImgData));
        int width = (int) (bi.getWidth() * scale); // 源图宽度
        int height = (int) (bi.getHeight() * scale); // 源图高度

        Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ImageIO.write(tag, "JPEG", bOut);

        return bOut.toByteArray();
    }

    //byte数组到图片
    public static void byte2image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) return;
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

}
