package com.mmall.task;

import com.mmall.service.IOrderService;
import com.mmall.util.RedisSharedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时关单
 *
 * @author yangshaojun
 * @create 2018-06-04 下午9:48
 **/

@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;


    /**
     * 每（1分钟的整数倍执行一次）定时任务
     * 会关闭 1 个小时之前 下单但是未付款的订单
     */
//    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTask1() {
        log.info("关闭订单定时任务启动");
        int hour = 1;
        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }


    /**
     * 每（1分钟的整数倍执行一次）定时任务
     * 会关闭 1 个小时之前 下单但是未付款的订单
     */
//    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTask2() {
        log.info("关闭订单定时任务启动");
        long timeout = 5000; // 分布式锁，锁的毫秒数

         Long setnxResult = RedisSharedPoolUtil.setnx("CLOSE_ORDER_TASK_LOCK", String.valueOf(System.currentTimeMillis() + timeout));

        if (setnxResult != null && setnxResult.intValue() == 1 ) {
            // 如果返回值=1，代表设置成功、获取锁
            closeOrder("CLOSE_ORDER_TASK_LOCK");
        }else {
            log.info("没有获取到分布式锁 -> ", "CLOSE_ORDER_TASK_LOCK");
        }

        log.info("关闭订单定时任务结束");
    }


    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV3() {

        log.info("关闭订单定时任务启动");

        long timeout = 5000; // 分布式锁，锁的毫秒数
        Long setnxResult = RedisSharedPoolUtil.setnx("CLOSE_ORDER_TASK_LOCK", String.valueOf(System.currentTimeMillis() + timeout));
        if (setnxResult != null && setnxResult.intValue() == 1 ) {
            // 如果返回值=1，代表设置成功、获取锁
            closeOrder("CLOSE_ORDER_TASK_LOCK");
        }else {
            // 没有获取到锁,继续判断、判断时间戳、看是否可以重置并获取到锁
            String lockValueStr = RedisSharedPoolUtil.get("CLOSE_ORDER_TASK_LOCK");
            //  如果没有获取到锁，就说明锁存在， 并且当前时间大于 取出的值，
            if (lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {
                //代表这个锁是可以失效的了

                // getSet， set新值的时候，先返回旧值， 再重置value
                String getSetResult = RedisSharedPoolUtil.getSet("CLOSE_ORDER_TASK_LOCK", String.valueOf(System.currentTimeMillis() + timeout));
                // 再次用当前时间戳 getSet， 返回给定的key的旧值， -> 旧值判断是否可以获取锁
                // 当key没有旧值时，即key不存在时。返回nil-> 获取锁
                // 这里我们set一个新值，获取旧的值
                if (getSetResult == null || (getSetResult != null && StringUtils.equals(getSetResult, lockValueStr))) {
                    // 真正获取到锁
                    closeOrder("CLOSE_ORDER_TASK_LOCK");
                } else {
                    log.info("没有获取到分布式锁 ： {}", " CLOSE_ORDER_TASK_LOCK");
                }

            }else{
                    log.info("没有获取到分布式锁 ： {}", " CLOSE_ORDER_TASK_LOCK");
            }
        }
        log.info("关闭订单定时任务结束");
    }
    /**
     *设置 key的有效期 50秒，防止死锁
     * @param lockName
     */
    private void closeOrder(String lockName) {
        // 设置 key的有效期 50秒，防止死锁
        RedisSharedPoolUtil.expire("CLOSE_ORDER_TASK_LOCK", 50 );
        log.info("获取 {} ->，ThreadName-> {}","CLOSE_ORDER_TASK_LOCK",Thread.currentThread().getName());
        int hour = 1;
//        iOrderService.closeOrder(1);
        // 及时的释放锁
        RedisSharedPoolUtil.del("CLOSE_ORDER_TASK_LOCK");
        log.info("释放 {} -> ，ThreadName-> {}","CLOSE_ORDER_TASK_LOCK",Thread.currentThread().getName());
        log.info("=============");
    }





}
