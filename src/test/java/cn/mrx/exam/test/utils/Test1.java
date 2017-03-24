package cn.mrx.exam.test.utils;

import cn.mrx.exam.utils.EncryptAndDecryptUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Mr.X
 * Date: 2017/3/19
 * Description:
 */
public class Test1 {

    @Test
    public void test(){
        System.out.println(EncryptAndDecryptUtil.base64Encrypt("admin:111111"));
        System.out.println(EncryptAndDecryptUtil.base64Decrypt("YWRtaW46MTExMTEx"));
        System.out.println(EncryptAndDecryptUtil.base64Decrypt("YWRtaW4=MTExMTEx"));
    }
}
