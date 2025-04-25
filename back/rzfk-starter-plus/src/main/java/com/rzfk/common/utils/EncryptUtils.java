package com.rzfk.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author jpx
 * @date 2019/11/18 14:04
 **/
public class EncryptUtils {
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String[] HEX_GID_ITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public EncryptUtils() {
    }

    public static byte[] encryptAES(byte[] bytes, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(1, generateAESKey(key));
            return cipher.doFinal(bytes);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static byte[] decryptAES(byte[] bytes, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(2, generateAESKey(key));
            return cipher.doFinal(bytes);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    private static SecretKeySpec generateAESKey(byte[] key) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            return secretKeySpec;
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static byte[] gzip(byte[] bytes) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(bytes);
            gzip.close();
            return out.toByteArray();
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static byte[] ungzip(byte[] bytes) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];

            int offset;
            while((offset = gzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }

            gzip.close();
            return out.toByteArray();
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }
    }

    public static String md5(String content) {
        return md5(content.getBytes());
    }

    public static String md5(byte[] bytes) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3);
        }

        digest.update(bytes);
        return bytesToHex(digest.digest());
    }

    public static String bytesToHex(byte[] b) {
        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            int n = b[i];
            if (n < 0) {
                n += 256;
            }

            buffer.append(HEX_GID_ITS[n / 16]).append(HEX_GID_ITS[n % 16]);
        }

        return buffer.toString();
    }

    public static String base64UrlEncode(String url) {
        String url2 = Base64.getUrlEncoder().encodeToString(url.getBytes());
        url2 = url2.replace("/", "_");
        url2 = url2.replace("+", "-");
        url2 = url2.replace("=", "");
        return url2;
    }

    public static String base64UrlDecode(String url) {
        int missing = (4 - url.length() % 4) % 4;
        if (missing > 0) {
            StringBuilder builder = new StringBuilder(url);

            for(int i = 0; i < missing; ++i) {
                builder.append("=");
            }

            url = builder.toString();
        }

        return new String(Base64.getDecoder().decode(url));
    }
}