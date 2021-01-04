package com.pitt.backend.controller;

import com.pitt.backend.entity.Patient;
import com.pitt.backend.repository.PatientRepository;
import com.pitt.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("patient")
public class PatientController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PatientService patientService;

    @Autowired
    PatientRepository patientRepository;

    @GetMapping("{docId}")
    public List<Patient> getByDocId(@PathVariable("docId") String docId){
        Long id = Long.parseLong(docId);
        return patientRepository.getByDocId(id);
    }

    @PostMapping("login")
    public Patient login(@RequestBody Patient patient) {
        System.out.println(patient.getUserName());
        return this.patientService.login(patient.getUserName(), patient.getPassword());
    }

//    @GetMapping("{id}")
//    public List<Patient> findByDocId(@PathVariable Long docId){
//        List<Patient> patOrderList = new ArrayList<>();
//
//        RowCallbackHandler rowCallbackHandler = new RowCallbackHandler() {
//            /**
//             * 该方法用于执行jdbcTemplate.query后的回调，每行数据回调1次。比如Teacher表中有两行数据，则回调此方法两次。
//             *
//             * @param resultSet 查询结果，每次一行
//             * @throws SQLException 查询出错时，将抛出此异常，暂时不处理。
//             */
//            @Override
//            public void processRow(ResultSet resultSet) throws SQLException {
//                Patient patient = new Patient();
//                patient.setPatId(resultSet.getLong("pat_id"));
//                patient.setfName(resultSet.getString("First_name"));
//                patient.setlName(resultSet.getString("Last_name"));
//                patient.sethCount(resultSet.getInt("high_unfinished_count"));
//                patient.setmCount(resultSet.getInt("middle_unfinished_count"));
//                patient.setlCount(resultSet.getInt("low_unfinished_count"));
//
//                /*将得到的teacher添加到要返回的数组中*/
//                patOrderList.add(patient);
//            }
//        };
//
//        String query = String.format("select p.pat_id, p.First_name, p.Last_name, p.high_unfinished_count, p.middle_unfinished_count, p.low_unfinished_count from patient p" +
////                "join doctor d on co.doc_id = d.doc_id" +
////                "join patient p on co.pat_id = p.pat_id" +
//                "where doc_id = %d" +
//                "order by p.high_unfinished_count, p.middle_unfinished_count", docId);
//
//        jdbcTemplate.query(query, rowCallbackHandler);
//
//        return patOrderList;
//    }


}
