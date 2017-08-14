package com.dj.common.utils.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

/**
 *  集群工具类
 * Created by daijie on 2017-8-8.
 */
public class JedisClusterUtil {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(JedisClusterUtil.class);

    public static JedisCluster jedisCluster;

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }


    public  static  void del(String key){
        jedisCluster.del(key);
    }


    public static  void set(String key,String value,int second){
        try {
            jedisCluster.set(key, value);
            jedisCluster.expire(key, second);
        }catch (Exception e){
            LOGGER.error("set(String key,String value,int second) Exception", e);
            throw new RuntimeException(e);
        }
    }


}
