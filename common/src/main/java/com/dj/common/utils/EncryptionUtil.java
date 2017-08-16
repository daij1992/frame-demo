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


    /**
     *
     * @param data
     *              需要进行Base64解码的原始串
     * @param charset
     *          字符集 UTF-8   GBK  ISO-8859-1(保证编码/解码字符集一致)
     * @return
     *              base64 解码 之后的字符串
     */
    public  static String base64Decode(String data,String charset)  {
        try{
            return new String(Base64.decodeBase64(data.getBytes(charset)),charset);
        }catch (UnsupportedEncodingException e){
            throw  new RuntimeException(e);
        }
    }


    /**
     *
     * @param data
     *          需要进行Base64 编码的的原始串
     * @param charset
     *          字符集 UTF-8   GBK  ISO-8859-1(保证编码/解码字符集一致)
     * @return
     *          base64 编码 之后的字符串
     */
    public static  String encodeBase64(String data,String charset)  {
       try{
           return new String(Base64.encodeBase64(data.getBytes(charset)),charset);
       }catch (UnsupportedEncodingException e){
           throw  new RuntimeException(e);
       }
    }



}
