package com.dj.common.utils.http;

import com.beust.jcommander.internal.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by daijie on 2017-8-15.
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    private static PoolingHttpClientConnectionManager cm;
    private static RequestConfig requestConfig;
    private static final  int  MAX_TOTAL = 100;
    private static final int DEFAULT_MAX_PER_ROUTE = 20;
    private static final int MAX_TIMEOUT = 1000;



    private static void init(){
        if(cm == null){
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(MAX_TOTAL);//最大连接数
            cm.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);//每路由最大连接数
            LOGGER.info("初始化连接池成功  MaxTotal:{}    DefaultMaxPerRoute:{}",MAX_TOTAL,DEFAULT_MAX_PER_ROUTE);
        }
        if(requestConfig == null){
            RequestConfig.Builder  configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
            // 设置读取超时
            configBuilder.setSocketTimeout(MAX_TIMEOUT);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
            requestConfig = configBuilder.build();
            LOGGER.info("初始化requestConfig成功  ConnectionRequestTimeout:{}    SocketTimeout:{}   ConnectionRequestTimeout:{}",MAX_TIMEOUT,MAX_TIMEOUT,MAX_TIMEOUT);
        }
    }

    private static CloseableHttpClient getClient(){
        init();
        if(requestConfig == null || cm == null){
            throw  new  RuntimeException("requestConfig Or  cm is null");
        }
        //关闭hostname verification
        return HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
    }


    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, String charset,
                               Map<Object, Object> params) {
        LOGGER.info("请求的URL:{}    charset:{}",url,charset);
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (Object key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        url += param;
        LOGGER.info("请求的参数:{}",param);
        String result = null;
        HttpClient httpClient = getClient();
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, charset);
            }
        } catch (IOException e) {
            LOGGER.error("请求:"+url+"  发生错误",e);
            e.printStackTrace();
        }
        LOGGER.debug("请求:{}   返回:{}",url,result);
        return result;
    }


    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param url
     *            API接口URL
     * @param params
     *            参数map
     * @return
     */
    public static String doPost(String url, String charset,
                                Map<Object, Object> params) {
        LOGGER.info("POST-FORM 请求的URL:{}    charset:{}",url,charset);
        CloseableHttpClient httpClient = getClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<Object, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey()
                        .toString(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset
                    .forName(charset)));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, charset);
        } catch (IOException e) {
            LOGGER.error("POST-FORM 请求:"+url+"  发生错误",e);
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOGGER.error("POST-FORM 请求:"+url+"  发生错误",e);
                    e.printStackTrace();
                }
            }
        }
        LOGGER.debug("POST-FORM 请求:{}   返回:{}",url,httpStr);
        return httpStr;
    }


    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param url
     * @param json
     *            json对象
     * @return
     */
    public static String doPost(String url, String charset, Object json) {
        LOGGER.info("POST-JSON 请求的URL:{}    charset:{}    json:{}",url,charset,json);
        CloseableHttpClient httpClient = getClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;

        try {
            StringEntity stringEntity = new StringEntity(json.toString(),
                    charset);// 解决中文乱码问题
            stringEntity.setContentEncoding(charset);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, charset);
        } catch (IOException e) {
            LOGGER.error("POST-JSON 请求:"+url+"  发生错误",e);
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOGGER.error("POST-JSON 请求:"+url+"  发生错误",e);
                    e.printStackTrace();
                }
            }
        }
        LOGGER.debug("POST-JSON 请求:{}   返回:{}",url,httpStr);
        return httpStr;
    }


    /**
     * 发送 POST 请求（HTTP），不带输入数据（编码默认UTF-8）
     *
     * @param url
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, "UTF-8", Maps.newHashMap());
    }
    /**
     * 发送 GET 请求（HTTP），不带输入数据（编码默认UTF-8）
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, "UTF-8", Maps.newHashMap());
    }

    public static void main(String[] args) {
//        String get = "http://www.baidu.com/s?wd=abc&rsv_spt=1&rsv_iqid=0xc02b634d00023ae8&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=4&rsv_sug1=3&rsv_sug7=100&rsv_sug2=0&inputT=1524&rsv_sug4=1889";
//        Map<Object,Object> map = new HashMap<Object,Object>();
//        map.put("wd","abc");
//        map.put("rsv_spt","1");
//
//
//            System.out.println(doGet(get,"UTF-8",map));


        ExecutorService service = Executors.newFixedThreadPool(10);

        for(int i = 0;i< 200000;i++){
        service.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("111");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });}

        /*for(int i = 0;i< 20;i++){
            service.submit(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info("执行thread :{} start",Thread.currentThread().getName());
//                    String get = "http://www.baidu.com/s?wd=abc&rsv_spt=1&rsv_iqid=0xc02b634d00023ae8&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=4&rsv_sug1=3&rsv_sug7=100&rsv_sug2=0&inputT=1524&rsv_sug4=1889";
                    String get = "http://www.ba1idu.com?id="+System.currentTimeMillis();
                    Map<Object,Object> map = new HashMap<Object,Object>();
                    map.put("wd","abc");
                    map.put("rsv_spt","1");
                    doGet(get,"UTF-8",map);
                    LOGGER.info("执行thread :{} end",Thread.currentThread().getName());
                }
            });
            LOGGER.info("放入task:{}",i);
        }
*/






    }





}
