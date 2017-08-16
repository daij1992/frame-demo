package com.dj.common.utils.rsa.android2server;


import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.dj.common.utils.EncryptionUtil;
import  com.dj.common.utils.rsa.android2server.Client;

/**
 * Created by daijie on 2017-8-16.
 */
public class Demo {




    public  static void client2server(String publicKey,String privateKey,String original) throws Exception {
        System.out.println("客户端->服务端    开始");
        //1. 场景一:  客户端->服务端
        /*
        客户端
                用公钥加密

         服务端
               服务端用私钥解密
         */

        System.out.println("client  加密原始串:"+original);

        byte [] clientBytes = original.getBytes("UTF-8");
        System.out.println("client 原始串转字节数组 注意:编码UTF-8");

        byte[] clientEncrptBytes = Client.encryptByPublicKey(clientBytes,publicKey);
        System.out.println("client 客户端公钥加密后得到字节数组");

        String clientBase64 = EncryptionUtil.encodeBase64(clientEncrptBytes,"UTF-8");
        System.out.println("client 加密字节数组进行Base64编码 获得字符串:"+clientBase64+" 注意:编码UTF-8");


        System.out.println("------------- 客户端 结束 ----------------");


        System.out.println("server 解密原始串:"+clientBase64);
        //byte[] serverBytes = EncryptionUtil.base64Decode(clientBase64.getBytes("UTF-8"));
        byte[] serverBytes =EncryptionUtil.base64Decode(clientBase64,"UTF-8").getBytes("UTF-8");
        System.out.println("server 原始串进行Base64解码获取字节数组 注意:编码UTF-8");

        byte[] serverDecryptBytes = Server.decryptByPrivateKey(serverBytes,privateKey);
        System.out.println("server 服务端私钥解密后得到字节数组");

        String serverDecryptString = new String(serverDecryptBytes,"UTF-8");
        System.out.println("server 字节数组转字符串:"+serverDecryptString+"  注意:编码UTF-8");




        System.out.println("------------- 服务端 结束 ----------------");



        System.out.println("客户端->服务端    结束");



    }




    public static void main(String[] args) throws Exception {

        //0.前提:生成公钥和私钥
        Map<String,Object> map = Server.genKeyPair();
        String publicKey = Server.getPublicKey(map);
        String privateKey = Server.getPrivateKey(map);

        System.out.println("公钥:"+publicKey);
        System.out.println("私钥:"+privateKey);




        client2server(publicKey,privateKey,"daijie");









        //2. 场景一:   服务端->客户端
       /*
       服务端:
                服务端用私钥加密



       客户端
               服务端用私钥解密
       */


    }
}
