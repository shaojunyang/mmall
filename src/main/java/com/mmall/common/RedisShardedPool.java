package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangshaojun
 * @create 2018-06-02 下午12:50
 **/

public class RedisShardedPool {
    // jedis - shared 连接池 静态
    private static ShardedJedisPool pool ;
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
    private static String redis1Ip = PropertiesUtil.getProperty("redis.1.ip");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis.1.port"));

    private static String redis2Ip = PropertiesUtil.getProperty("redis.2.ip");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis.2.port"));


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

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port, 1000 * 2);
//        info1.setPassword("xxx"); // 设置连接密码
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip,redis2Port,1000 * 2);// 超时时间两秒
        // 添加到 List
        List<JedisShardInfo> shardInfoArrayList = new ArrayList<>();
        shardInfoArrayList.add(info1);
        shardInfoArrayList.add(info2);

        // 实例化连接池   第三个 参数 表示一致性 算法
        pool = new ShardedJedisPool(config, shardInfoArrayList, Hashing.MURMUR_HASH , Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initPool();// 初始化连接池
    }

    // 开放连接出去
    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    // // 把Jedis实例放回正常的Resouce, 池子中
    public static void returnResource(ShardedJedis jedis) {
            // 把Jedis实例放回连接池
            pool.returnResource(jedis);
    }

    // 把Jedis实例放回破损的Resouce
    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        ShardedJedis shardedJedis = pool.getResource();

//        shardedJedis.set("name","yangshaojun.com");

        for (int i = 0; i < 10; i++) {
            shardedJedis.set("key" + i , "value" + i);
        }


        returnResource(shardedJedis);

//        pool.destroy();// 临时调用。销毁连接池中的所有连接

        System.out.println(" end ");

    }


}
