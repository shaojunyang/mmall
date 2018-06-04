package com.mmall.task;

import com.mmall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
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
    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTask1() {
        log.info("关闭订单定时任务启动");
       int  hour = 1;
       iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }
}
