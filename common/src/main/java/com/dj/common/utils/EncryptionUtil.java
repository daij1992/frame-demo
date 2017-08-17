package com.dj.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by daijie on 2017-8-15.
 */
public class EncryptionUtil {

    /**
     *  MD5摘要:32字符 16进制
     *
     * @param data
     *            需要进行MD5的原始串
     * @return md5之后的摘要
     */
    public static String md5Hex(final String data)  {
        return DigestUtils.md5Hex(data);
    }


    /**
     *
     * @param data
     *           需要进行SHA1的原始串
     * @return  SHA1之后的摘要
     */
    public static String sha1Hex(String data){
        return  DigestUtils.sha1Hex(data);
    }



    public  static byte[] base64Decode(String data)  {
           return  Base64.decodeBase64(data);
    }


    public static  String encodeBase64String(byte[] data)  {
            return Base64.encodeBase64String(data);
    }



}
