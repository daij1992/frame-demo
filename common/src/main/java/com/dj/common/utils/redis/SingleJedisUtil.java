package com.dj.common.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *  非集群Jedis工具类
 *
 * Created by daijie on 2017-8-8.
 */
public class SingleJedisUtil {


    public static JedisPool pool;

    private final static Logger LOGGER = LoggerFactory.getLogger(SingleJedisUtil.class);

    public JedisPool getPool() {
        return pool;
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }


    /**
     * 删除该键
     *
     * @param key
     */
    public static void del(String key) {

        Jedis jedis = pool.getResource();

        try {

            jedis.del(key);

        } catch (Exception e) {

            LOGGER.error("del(String key) Exception", e);
            throw new RuntimeException(e);

        } finally {

            jedis.close();
        }

    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param seconds
     */
    public static void expire(String key, int seconds) {

        Jedis jedis = pool.getResource();

        try {

            jedis.expire(key, seconds);

        } catch (Exception e) {

            LOGGER.error("expire(String key, Integer seconds) Exception", e);
            throw new RuntimeException(e);

        } finally {

            jedis.close();

        }

    }




    /**
     * 获取值
     * @param key
     * @return
     */
    public static String get(String key) {

        Jedis jedis = pool.getResource();

        String value = null;

        try {

            value = jedis.get(key);

        } catch (Exception e) {

            LOGGER.error("get(String key) Exception", e);
            throw new RuntimeException(e);

        } finally {

            jedis.close();
        }

        return value;
    }


    /**
     * 设置key value
     * @param key
     * @param value
     */
    public static void set(String key,String value){

        Jedis jedis = pool.getResource();

        try{
            jedis.set(key,value);
        }catch (Exception e){
            LOGGER.error("set(String key,String value) Exception",e);
            throw new RuntimeException(e);
        }finally {
            jedis.close();
        }


    }


    /**
     * 设置 key value expire
     * @param key
     * @param value
     * @param seconds
     */
    public static void set(String key,String value,Integer seconds){

        Jedis jedis = pool.getResource();

        try{
            jedis.set(key,value);
            jedis.expire(key,seconds);

        }catch (Exception e){
            LOGGER.error("set(String key,String value) Exception",e);
            throw new RuntimeException(e);
        }finally {
            jedis.close();
        }
    }


    /**
     * 获取存储在哈希表中指定字段的值
     * @param key
     * @param filed
     * @return
     */
    public static String hget(String key,String filed){
        Jedis jedis = pool.getResource();

        String value = null;
        try{
            value = jedis.hget(key,filed);
        }catch (Exception e){
            LOGGER.error("hget(String key,String filed)",e);
            throw new RuntimeException(e);
        }finally {
            jedis.close();
        }

        return value;
    }


    /**
     * 将哈希表 key 中的字段 field 的值设为 value 。
     * @param key
     * @param filed
     * @param value
     */
    public static void hset(String key,String filed,String value){

        Jedis jedis = pool.getResource();

        try{
            jedis.hset(key,filed,value);
        }catch (Exception e){
            LOGGER.error("hset(String key,String filed,String value)",e);
            throw new RuntimeException(e);
        }finally {
            jedis.close();
        }


    }






}
