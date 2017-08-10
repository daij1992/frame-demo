package com.dj.common.utils.redis;


import redis.clients.jedis.JedisCluster;

/**
 *  集群工具类
 * Created by daijie on 2017-8-8.
 */
public class JedisClusterUtil {


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
        jedisCluster.set(key,value);
        jedisCluster.expire(key,second);
    }


}
