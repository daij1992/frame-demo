package com.dj.common.utils.rsa;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by daijie on 2017-8-17.
 */
public class Server2ClientDemo {

    private  static final String privateKey ="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANjdtaE8iq25ocgwnKWm5RgrSMF1HIHyDsFtOYApySt7R06QuUILOo90Hky9ucj5QlTYBES6oXgXvlDv3R3yBI4C4CcM5gK7sArX4YeLHyYaMuDMl6E3iGErVZ5zKD96p7jZjjwYdyB1qCEe0wuauANrdGYV3dhM6NpVwDEnNUxlAgMBAAECgYAIQmloeqXNeARxW2ib5n9TLFLZC1zKli15sG9AMlc3ELrmgxyLJ2JTlFiTE6a/wL6F/LQs6e68ZCsbagdB3LXPoNf1AXJN/QP8cKBaFFpJRQ+A0NWJWa+/CAACVxBlu8ll57ff8kdUh/FKccW3fdxERP+W3+f5MWT+gcRP0O3FAQJBAPJatVFYUdFhERoo+gaRGVbbtz1ud21u2nuZFS4wRneolOy2wz6uvzV13q1E8G7imrdtJrDO2+ZZmNGu38UktJsCQQDlE50Ta0mZshu6xJU9iwL74GfiM5sZk7V7UnM16yxGXiVzoixSeOmHzemDlwCMRMfAwr9yTg19uEqrKcVbv5L/AkEAo/QZld5Z+3vfRgNSof6nVctuO3Dv+mh0BgmZ+pVOFsVFep5pl45AbLpA2pc40MscP9rnwdUvjrYuXCwh1gzu4QJBAIkl7gQDVuRa+zkdmxBmmtm8J9YI+ZIdmikEWw/sBez3TsrlTE3wMDjrciQjkuNoBoQE1OQVkQQ1AxdpAc0FbHcCQQDkPW5YaobcRaQH1S4TXZqToTsvWzAIMEsZCqdu8jea+yaGpULn38tN5olDaKjm3+HtMuYH7bKPS6H9IoYo1ibb";
    private static  final  String  publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDY3bWhPIqtuaHIMJylpuUYK0jBdRyB8g7BbTmAKckre0dOkLlCCzqPdB5MvbnI+UJU2AREuqF4F75Q790d8gSOAuAnDOYCu7AK1+GHix8mGjLgzJehN4hhK1Wecyg/eqe42Y48GHcgdaghHtMLmrgDa3RmFd3YTOjaVcAxJzVMZQIDAQAB";


    public static void main(String[] args) throws Exception {
        String originalMsg = "{\"ret\":{\"ret_code\":\"0\",\"ret_msg\":\"成功\"},\"data\":[{\"menuId\":48,\"menuName\":\"首页\",\"themeId\":49,\"themeName\":\"YIUI6.5\",\"menuDirection\":0,\"menuFontSize\":20,\"creator\":\"李俊鹏\",\"locked\":\"unlocked\",\"updator\":\"李俊鹏\",\"createTime\":\"2017-06-13\",\"updateTime\":\"2017-06-13\"}]}";

        System.out.println("server 原始串:"+originalMsg);

        byte[] orignalBytes = originalMsg.getBytes("UTF-8");
        System.out.println("server String转字节数组 注意:编码UTF-8");
        String sign = Server.sign(orignalBytes,privateKey);
        System.out.println("server 服务端用私钥对明文字节数组进行签名 得到sign:"+sign);
        byte[] encrptBytes = Server.encryptByPrivateKey(orignalBytes,privateKey);
        System.out.println("server 服务端私钥加密后得到字节数组");
        String bytes2Base64 = Base64.encodeBase64String(encrptBytes);
        System.out.println("server 加密字节数组进行Base64编码 得到字符串:"+bytes2Base64);

        System.out.println("server 服务端 签名结束 结果:"+sign);
        System.out.println("server 服务端 加密结束 结果:"+bytes2Base64);;

        System.out.println("-------------------------华丽的分割线-----------------------------");

        System.out.println("client 客户端需要解密的原始串:"+sign);
        System.out.println("client 客户端需要验证的签名:"+bytes2Base64);

        byte[] encodeBytes = Base64.decodeBase64(bytes2Base64);
        System.out.println("client Base64解码原始串得到字节数组");

        byte[] decodeBytes = Client.decryptByPublicKey(encodeBytes,publicKey);
        System.out.println("client 客户端公钥解密得到字节数组");

        String decodeRes = new String(decodeBytes,"UTF-8");
        System.out.println("client 字节数组转String 注意:编码UTF-8");
        System.out.println("client 客户端 解密结束  结果:"+decodeRes);


        boolean flag = Client.verify(decodeRes.getBytes("UTF-8"),publicKey,sign);
        System.out.println("client 客户端 验签结束 结果:"+flag);







    }





}
