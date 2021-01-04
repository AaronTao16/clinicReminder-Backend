package com.pitt.backend.controller;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.pitt.backend.entity.ClinicalOrder;
import com.pitt.backend.repository.OrderRepository;
import com.pitt.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import com.alibaba.druid.sql.SQLUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.alibaba.druid.sql.SQLUtils.toSQLString;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    /**
     * Create Order Method
     * @param clinicalOrder
     * @return Boolean  -- Create successfully or not
     */
    @PostMapping("newOrder")
    public boolean add(@RequestBody ClinicalOrder clinicalOrder) {
        System.out.println(clinicalOrder.getsTime());
        System.out.println(clinicalOrder.geteTime());
        System.out.println(clinicalOrder.getPro());
        System.out.println(clinicalOrder.getPatient().getPatId());
        System.out.println(clinicalOrder.getDuration());

        int priority = clinicalOrder.getPro();
        String sql;
        if(priority == 1){
            sql = String.format(
                    "UPDATE `patient` SET `high_unfinished_count` = `high_unfinished_count`+1 WHERE `pat_id` = '%d'", clinicalOrder.getPatient().getPatId()
            );
        } else if(priority == 2){
            sql = String.format(
                    "UPDATE `patient` SET `middle_unfinished_count` = `middle_unfinished_count`+1 WHERE `pat_id` = '%d'", clinicalOrder.getPatient().getPatId()
            );
        } else {
            sql = String.format(
                    "UPDATE `patient` SET `low_unfinished_count` = `low_unfinished_count`+1 WHERE `pat_id` = '%d'", clinicalOrder.getPatient().getPatId()
            );
        }
        System.out.println(priority);
        System.out.println(sql);

        try{
//            Long orderId = orderService.save(order).getOrdId();

            jdbcTemplate.execute(sql);
            clinicalOrder.setStatus(1);
            orderService.save(clinicalOrder);
//            this.greeting(clinicalOrder);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    /**
     *
     * @param patId
     * @return
     */
    @GetMapping("{patId}")
    public List<ClinicalOrder> getByPatId(@PathVariable("patId") Long patId){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        Date curTime = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println(dateFormat.format(curTime));
        return orderRepository.getByPatId(patId, 2, curTime);
    }

    @GetMapping("barchart/{patId}")
    public List<Map<String, String>> getHistoryByPatId(@PathVariable("patId") Long patId){
        List<Map<String, String>> ret = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date curTime = calendar.getTime();
        System.out.println(curTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String seven_ago = dateFormat.format(curTime);
        System.out.println(seven_ago);
        RowCallbackHandler rowCallbackHandler = new RowCallbackHandler() {

            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Map<String, String> map = new HashMap<>();
                map.put("cDate", resultSet.getString("cDate"));
                map.put("ordCount", resultSet.getString("count"));
                System.out.println(resultSet.getString("cDate") + " " + resultSet.getString("count"));
                ret.add(map);
            }
        };

        String query = String.format("SELECT Date(create_time) 'cDate', count(co.ord_id) 'total' FROM `clinic_order` co where co.pat_id = %d and co.order_status = 2 and DATEDIFF( now(), co.create_time)<=7 group by `cDate`", patId);
        String query1 = String.format("select a.click_date as 'cDate', ifnull(b.count, 0) as 'count' from(\n" +
                "    SELECT curdate() as 'click_date'\n" +
                "    union all\n" +
                "    SELECT date_sub(curdate(), interval 1 day) as 'click_date'\n" +
                "    union all\n" +
                "    SELECT date_sub(curdate(), interval 2 day) as 'click_date'\n" +
                "    union all\n" +
                "    SELECT date_sub(curdate(), interval 3 day) as 'click_date'\n" +
                "    union all\n" +
                "    SELECT date_sub(curdate(), interval 4 day) as 'click_date'\n" +
                "    union all\n" +
                "    SELECT date_sub(curdate(), interval 5 day) as 'click_date'\n" +
                "    union all\n" +
                "    SELECT date_sub(curdate(), interval 6 day) as 'click_date'\n" +
                ") as a left join (SELECT Date(create_time) 'last7', count(co.ord_id) 'count' FROM `clinic_order` co where co.pat_id = %d and co.order_status = 2 and DATEDIFF(now(), co.create_time)<=7 group by `last7`) as b on a.click_date = b.last7 ", patId);
//        try {
//            SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(query1, "mysql");
//            List<SQLStatement> statementList = parser.parseStatementList();
//            query1 = toSQLString(statementList, "mysql");
//            System.out.println(query1);
//        } catch (ParserException e) {
//            System.out.println("SQL转换中发生了错误："+e.getMessage());
//        }

        jdbcTemplate.query(query1, rowCallbackHandler);

        return ret;
    }

    /**
     *
     * @param PatId
     * @return
     */
    @GetMapping("mobile/{patId_mobile}")
    public List<ClinicalOrder> getByPatId_mobile(@PathVariable("patId_mobile") String PatId){
        Long patId = Long.parseLong(PatId);
        return orderRepository.getByPatId_mobile(patId);
    }

    /**
     *
     * @param clinicalOrder
     * @return
     */
    @PostMapping("mobile_update")
    public Boolean updateByOrdId_mobile(@RequestBody ClinicalOrder clinicalOrder){
        int priority = clinicalOrder.getPro();
        System.out.println(clinicalOrder.getPatient().getPatId());
        String sql;
        if(priority == 1){
            sql = String.format(
                    "UPDATE `patient` SET `high_unfinished_count` = `high_unfinished_count`-1 WHERE `pat_id` = '%d'", clinicalOrder.getPatient().getPatId()
            );
        } else if(priority == 2){
            sql = String.format(
                    "UPDATE `patient` SET `middle_unfinished_count` = `middle_unfinished_count`-1 WHERE `pat_id` = '%d'", clinicalOrder.getPatient().getPatId()
            );
        } else {
            sql = String.format(
                    "UPDATE `patient` SET `low_unfinished_count` = `low_unfinished_count`-1 WHERE `pat_id` = '%d'", clinicalOrder.getPatient().getPatId()
            );
        }

        String sql1 = String.format(
                "UPDATE `clinic_order` SET `order_status` = 3 WHERE `ord_id` = '%d'", clinicalOrder.getOrdId()
        );

        try{
            jdbcTemplate.execute(sql1);
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception exception){
            return false;
        }
    }
//
//    @MessageMapping("/newReminder")
//    @SendTo("/topic/greetings")
//    public ClinicalOrder greeting(ClinicalOrder reminder) throws Exception {
//        Thread.sleep(1000); // simulated delay
//        return reminder;
//    }


//    @GetMapping("{patId}")
////    @CrossOrigin("*")
//    public List<Order> findOrderList(@PathVariable String patId){
//        System.out.println("method in" + patId);
//        Long id = Long.parseLong(patId);
//        List<Order> orderList = new ArrayList<>();
//        System.out.println(id);
//        RowCallbackHandler rowCallbackHandler = new RowCallbackHandler() {
//
//            @Override
//            public void processRow(ResultSet resultSet) throws SQLException {
//                Order order = new Order();
//                Doctor doctor = new Doctor();
//                Patient patient = new Patient();
//                order.setOrdId(resultSet.getLong("ord_id"));
//                order.setOrdTitle(resultSet.getString("ord_title"));
//                order.setsTime(resultSet.getDate("start_time"));
//                order.seteTime(resultSet.getDate("end_time"));
//                order.setcTime(resultSet.getDate("create_time"));
//                order.setfTime(resultSet.getDate("finished_time"));
//                order.setDuration(resultSet.getLong("duration"));
//                order.setDes(resultSet.getString("des"));
//                order.setStatus(resultSet.getInt("order_status"));
////                doctor.setDocId(resultSet.getLong("doc_id"));
////                order.setDoctor(doctor);
////                patient.setPatId(resultSet.getLong("pat_id"));
////                order.setPatient(patient);
//
//
//                orderList.add(order);
//            }
//        };
//
//
//
//        String query = String.format("select * from clinic_Order co where co.pat_id = %d and co.status = -1 order by co.status, co.priority", id);
//
//        jdbcTemplate.query(query, rowCallbackHandler);
//
//        System.out.println(orderList.get(0).getPro());
//        System.out.println(orderList.get(1).getPro());
//        return orderList;
//    }

}
