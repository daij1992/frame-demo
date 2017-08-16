package com.dj.common.utils.rsa.android2server;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * Android 客户端工具类,包含 公钥验签 公钥加密 公钥解密
 *
 * Created by daijie on 2017-8-16.
 */
public class Client {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取Cipher实例 指定算法名称
     * 包含密码学算法名称,比如DES;也可以在后面包含模式和填充方式,比如RSA/ECB/NoPadding。(客户端和服务端必须保持一致)
     * RSA/ECB/NoPadding:相同明文+相同密钥=密文相同
     * RSA/ECB/PKCS1Padding:相同明文+相同密钥=密文不同
     * 注意:andorid的实现是 Bouncy Castle 而java是Sun
     */
    private static final String   CIPHER_TRANSFORMATION = "RSA/ECB/NoPadding";


    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     *
     * @return
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }


    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {

        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);;
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }


    public static void main(String[] args) {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGG/uSeAtea5ZkgcjxbQu7G7Ef+x01cxP+38GQHMcIIlkBxk7NuiUGGnbCshHS497b53Ai/saSyEZ+1Of97YZZ8LnQcSsLBmMoiteVxuZn/Gu3BYQYOoYL072583JGoNgL9IQTqgZmsU0/LRFxqqZhvS5t3LiyxjBNtA44B7EkKQIDAQAB";

        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJFobyXUSnZGC5A5VjrSZEr6J5tawdXCvdQ2R4YHzBZHgU1kVQuzUN8Luf7ENuFvFR7w3YyM82AF5NneEzRK3fmkwkCFP4ZtmttMua2QPsMd/OgMOVZAtKgvddGxwauxLOcuLpyxzeNi825niilfKCIO0CxG2jcM86tBwx2qWrhrAgMBAAECgYAWGuVCv/ot7soVO9IzEQb0KwFYabyr7XYES9gltQu+m1I53LXdGH6D2fmFlHivXr68PGvB0S+yelLnGA6dVYBXF4PT+MNPh77hfynjasNCWT4cl2fHjJiJAzho/auiuGbv1Axi7EhYF6raWSowGPOJiAJh/REppjor/4Il7P+5uQJBAMzOlMh+fDoZgKVU5m7ZSg/LGV6bAxfWnJUbpf4b1ideEmY/c9oS3q75yTzHBkbwda3RtO0xQuKwTsIVLwGQcZ0CQQC1wPZDd1Aq3HZt2j2lJLFXKB/jFJMhdws5SoRZAVMBogNXQXy+4GUi5vbnEVIJMft63wP3wgOFTixUJfFagZenAkEAxCaKKqexdLeeFTqF495p50xeRNRzlu4tU0WCqieg6F1Zg+H7o/10luKWkIYRBfogytPQ02bdp1yyYYGGXponpQJACJsFTfZ1PkJH/TWLG6mcF/NXZyoPOVNonaLQl2I/quEf5V69aFA5mahz4kboqnTKYmvyxSVTg1lLMgTF47IlGwJBAMZ5c7RhjHqs+qqRrYh1SrPjrgm7H4zpxA+0e4plO19n1ZPUCamxEyUE+hmSxvH7UCHmTJniOFai0DNy+6qFSD0=";






    }

}
