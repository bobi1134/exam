package cn.mrx.exam.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * @ClassName: EncryptionUtil
 * @Author: Mr.X
 * @Date: 2017/3/23 21:12
 * @Description:
 * @Version 1.0
 */
public class EncryptAndDecryptUtil {

    /**
     * Base64加密，可逆
     * @return
     */
    public static String base64Encrypt(String str){
        return new String(new String(Base64.encodeBase64(str.getBytes())));
    }

    /**
     * Base64解密，可逆
     * @return
     */
    public static String base64Decrypt(String str){
        return new String(new String(Base64.decodeBase64(str.getBytes())));
    }
}
