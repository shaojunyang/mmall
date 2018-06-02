package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author yangshaojun
 * @create 2018-06-02 下午12:50
 **/

public class RedisPool {
    // jedis连接池 静态
    private static JedisPool pool ;
    // 最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));
    // 最大空闲 连接实例个数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","20"));
    // 最小空闲 连接实例个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","20"));
    // 在borrow获取jedis 实例时 是否进行验证测试。true的话，那么得到的jedis实例肯定可以用
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));
    // 在return 一个 jedis 实例时 是否进行验证测试。true的话，那么放入 jedispoll中的jedis实例肯定可以用
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));


    // redis ip,配置文件中读取
    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置连接池配置
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        // 连接耗尽是否阻塞，false会抛出异常，true阻塞直到超时，默认true
        config.setBlockWhenExhausted(true);

        // 实例化连接池
        pool = new JedisPool(config,redisIp,redisPort,1000*2);

    }

    static {
        initPool();// 初始化连接池
    }

    // 开放连接池出去
    public static Jedis getJedis() {
        return pool.getResource();
    }

    // // 把Jedis实例放回正常的Resouce, 池子中
    public static void returnResource(Jedis jedis) {
            // 把Jedis实例放回连接池
            pool.returnResource(jedis);
    }

    // 把Jedis实例放回破损的Resouce
    public static void returnBrokenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("name","yangshaojun.com");
        returnResource(jedis);

        pool.destroy();// 临时调用。销毁连接池中的所有连接

        System.out.println(" end ");

    }


}
