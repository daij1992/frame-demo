package com.dj.common.utils.rsa;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by daijie on 2017-8-17.
 */
public class Client2ServerDemo {


    private  static final String privateKey ="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANjdtaE8iq25ocgwnKWm5RgrSMF1HIHyDsFtOYApySt7R06QuUILOo90Hky9ucj5QlTYBES6oXgXvlDv3R3yBI4C4CcM5gK7sArX4YeLHyYaMuDMl6E3iGErVZ5zKD96p7jZjjwYdyB1qCEe0wuauANrdGYV3dhM6NpVwDEnNUxlAgMBAAECgYAIQmloeqXNeARxW2ib5n9TLFLZC1zKli15sG9AMlc3ELrmgxyLJ2JTlFiTE6a/wL6F/LQs6e68ZCsbagdB3LXPoNf1AXJN/QP8cKBaFFpJRQ+A0NWJWa+/CAACVxBlu8ll57ff8kdUh/FKccW3fdxERP+W3+f5MWT+gcRP0O3FAQJBAPJatVFYUdFhERoo+gaRGVbbtz1ud21u2nuZFS4wRneolOy2wz6uvzV13q1E8G7imrdtJrDO2+ZZmNGu38UktJsCQQDlE50Ta0mZshu6xJU9iwL74GfiM5sZk7V7UnM16yxGXiVzoixSeOmHzemDlwCMRMfAwr9yTg19uEqrKcVbv5L/AkEAo/QZld5Z+3vfRgNSof6nVctuO3Dv+mh0BgmZ+pVOFsVFep5pl45AbLpA2pc40MscP9rnwdUvjrYuXCwh1gzu4QJBAIkl7gQDVuRa+zkdmxBmmtm8J9YI+ZIdmikEWw/sBez3TsrlTE3wMDjrciQjkuNoBoQE1OQVkQQ1AxdpAc0FbHcCQQDkPW5YaobcRaQH1S4TXZqToTsvWzAIMEsZCqdu8jea+yaGpULn38tN5olDaKjm3+HtMuYH7bKPS6H9IoYo1ibb";
    private static  final  String  publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDY3bWhPIqtuaHIMJylpuUYK0jBdRyB8g7BbTmAKckre0dOkLlCCzqPdB5MvbnI+UJU2AREuqF4F75Q790d8gSOAuAnDOYCu7AK1+GHix8mGjLgzJehN4hhK1Wecyg/eqe42Y48GHcgdaghHtMLmrgDa3RmFd3YTOjaVcAxJzVMZQIDAQAB";


    public static void main(String[] args) throws Exception {

        String originalMsg = "{\"name\":\"BeJson\",\"url\":\"http://www.bejson.com\",\"page\":88,\"isNonProfit\":true,\"address\":{\"street\":\"科技园路.\",\"city\":\"江苏苏州\",\"country\":\"中国\"},\"links\":[{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"}]}";

        System.out.println("client  原始串:"+originalMsg);
        System.out.println("公钥:"+publicKey);

        byte [] originalBytes = originalMsg.getBytes("UTF-8");
        System.out.println("client String转字节数组 注意:编码UTF-8");

        byte[] encrptBytes =  Client.encryptByPublicKey(originalBytes,publicKey);
        System.out.println("client 客户端公钥加密后得到字节数组");

        String encrptRes =  Base64.encodeBase64String(encrptBytes);
        System.out.println("client 加密字节数组进行Base64编码 获得字符串:"+encrptRes);


        System.out.println("客户端 加密 结束  加密结果:"+encrptRes);

        System.out.println("-------------------------华丽的分割线-----------------------------");

        System.out.println("server  需要解密的原始串:"+encrptRes);
        byte[] encodeBytes = Base64.decodeBase64(encrptRes);
        System.out.println("server Base64解码字符串 获取的字符数组");

        byte[] decodeBytes = Server.decryptByPrivateKey(encodeBytes,privateKey);
        System.out.println("server 服务端私钥解密后得到字节数组");

        String decodeRes = new String(decodeBytes,"UTF-8");
        System.out.println("server 字节数组转String 注意:编码UTF-8");


        System.out.println("服务端 解密 结束  解密结果:"+decodeRes);


    }

}
