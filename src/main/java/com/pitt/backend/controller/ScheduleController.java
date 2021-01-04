package com.pitt.backend.controller;

import com.pitt.backend.entity.ClinicalOrder;
import com.pitt.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Component
@Service
public class ScheduleController{
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    //    @Scheduled(cron="0 0 0 * * ? *") //每天00:00:00执行一次
//    @Scheduled(cron="0 15 15,20 * * 2005 *") //2005年每天15点整合和20点整每隔15分钟执行一次
//   @Scheduled(fixedDelay=1000) //距离上次执行结果每1s执行一次
//   @Scheduled(initialDelay=1000，fixdRate=6000) //第一次延迟1s，之后每隔6s执行一次
    @Scheduled(fixedRate = 60000) //距离项目启动每1分钟执行一次
    public void expireTimeJudge(){
        //首先拿到所有的过期且expire=0的条目
        List<ClinicalOrder> orderList = orderRepository.getByETime(new Date());
        if(orderList.isEmpty()){
            System.out.println("No order need to update");
            return;
        }
        //遍历更新expire=1
        for(ClinicalOrder order : orderList){
            String sql = String.format(
                    "UPDATE `clinic_order` SET `order_status` = 2 WHERE `ord_id` = '%d'", order.getOrdId()
            );
            System.out.println("Auto updating: " + order.getStatus() + "order " + order.getOrdId() + "Should be ended at " + order.geteTime());
            try{
                jdbcTemplate.execute(sql);
            }catch (Exception exception){
                System.out.println("Scheduled Error");
            }
        }
    }

}
