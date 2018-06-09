package com.mmall.util;

import com.mmall.common.RedisPool;
import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import sun.rmi.runtime.Log;

/**
 * @author yangshaojun
 * @create 2018-06-02 下午1:32
 **/

@Slf4j
public class RedisSharedPoolUtil {
    /**
     *  重新设置key的有效期
     * @param key
     * @Parme exTime 过期时间- 单位 秒
     * @return  Long 有效期
     */
    public static Long expire(String key, int exTime) {
        ShardedJedis jedis = null;
        Long result = null;

        try {
            // 获取连接
            jedis = RedisShardedPool.getJedis();
            // 结果
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("set key : {} value : {} error", key, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisShardedPool.returnResource(jedis);
        return result;
    }



    /**
     * 设置的key 不存在时才可以设置成功 setnx
     * @param key
     * @param value
     * @return
     */
    public static Long setnx(String key, String value) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            // 获取连接
            jedis = RedisShardedPool.getJedis();
            // 结果
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("setnx key : {} value : {} error", key, value, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisShardedPool.returnResource(jedis);
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
        ShardedJedis jedis = null;
        String result = null;

        try {
            // 获取连接
            jedis = RedisShardedPool.getJedis();
            // 结果
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("set key : {} value : {} error", key, value, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisShardedPool.returnResource(jedis);
        return result;
    }



    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public static String getSet(String key, String value) {
        ShardedJedis jedis = null;
        String result = null;

        try {
            // 获取连接
            jedis = RedisShardedPool.getJedis();
            // 结果
            result = jedis.getSet(key, value);
        } catch (Exception e) {
            log.error("getSet key : {} value : {} error", key, value, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisShardedPool.returnResource(jedis);
        return result;
    }


    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public static String set(String key, String value) {
        ShardedJedis jedis = null;
        String result = null;

        try {
            // 获取连接
            jedis = RedisShardedPool.getJedis();
            // 结果
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key : {} value : {} error", key, value, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisShardedPool.returnResource(jedis);
        return result;
    }


/**
     * 获取值
     * @param key
     * @return
     */
    public static String get(String key) {
        ShardedJedis jedis = null;
        String result = null;

        try {
            // 获取连接
            jedis = RedisShardedPool.getJedis();
            // 结果
            result = jedis.get(key);
        } catch (Exception e) {

            log.error("get key : {}error", key, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    public static Long del(String key) {
        ShardedJedis jedis = null;
        Long result = null;

        try {
            // 获取连接
            jedis = RedisShardedPool.getJedis();
            // 结果
            result = jedis.del(key);
        } catch (Exception e) {

            log.error("get key : {}error", key, e);
            // 出现异常。把连接放入破损的 连接池资源中
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        // 没有出现异常，把连接放回正常的连接池中
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
        ShardedJedis jedis = RedisShardedPool.getJedis();
        jedis.set("a","a1");
        System.out.println(get("a"));

        setEx("ex","val",100);

        expire("a",50);

        del("a");
        System.out.println("end");

    }

}
