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

    //    @Scheduled(cron="0 0 0 * * ? *") //00:00:00 everyday
//    @Scheduled(cron="0 15 15,20 * * 2020 *") //2020 15:00 and 20:00 /every 15mins
//   @Scheduled(fixedDelay=1000) //every 1s
//   @Scheduled(initialDelay=1000，fixdRate=6000) //delay 1s，after every 6s
    @Scheduled(fixedRate = 60000) //every minute
    public void expireTimeJudge(){
        List<ClinicalOrder> orderList = orderRepository.getByETime(new Date());
        if(orderList.isEmpty()){
            System.out.println("No order need to update");
            return;
        }
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
