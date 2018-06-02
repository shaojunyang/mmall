package com.mmall.util;

import com.mmall.common.ExceptionResolver;
import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author yangshaojun
 * @create 2018-06-02 下午1:32
 **/

@Slf4j
public class RedisPoolUtil {
    /**
     *  重新设置key的有效期
     * @param key
     * @Parme exTime 过期时间- 单位 秒
     * @return  Long 有效期
     */
    public static Long expire(String key, int exTime) {
        Jedis jedis = null;
        Long result = null;

        try {
            // 获取连接
            jedis = RedisPool.getJedis();
            // 结果
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("set key : {} value : {} error", key, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisPool.returnResource(jedis);
        return result;
    }



    /**
     * 设置带有效期 值
     * @param key
     * @param value
       * @Parme exTime 过期时间- 单位 秒
     * @return
     */
    public static String setEx(String key, String value, int exTime) {
        Jedis jedis = null;
        String result = null;

        try {
            // 获取连接
            jedis = RedisPool.getJedis();
            // 结果
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("set key : {} value : {} error", key, value, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisPool.returnResource(jedis);
        return result;
    }



    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;

        try {
            // 获取连接
            jedis = RedisPool.getJedis();
            // 结果
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key : {} value : {} error", key, value, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisPool.returnResource(jedis);
        return result;
    }
/**
     * 获取值
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = null;
        String result = null;

        try {
            // 获取连接
            jedis = RedisPool.getJedis();
            // 结果
            result = jedis.get(key);
        } catch (Exception e) {

            log.error("get key : {}error", key, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    public static Long del(String key) {
        Jedis jedis = null;
        Long result = null;

        try {
            // 获取连接
            jedis = RedisPool.getJedis();
            // 结果
            result = jedis.del(key);
        } catch (Exception e) {

            log.error("get key : {}error", key, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();
        jedis.set("a","a1");
        System.out.println(get("a"));

        setEx("ex","val",100);

        expire("a",50);

        del("a");
        System.out.println("end");

    }

}
