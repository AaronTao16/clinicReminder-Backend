package com.pitt.backend.controller;

import com.pitt.backend.entity.Doctor;
import com.pitt.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    /**
     * Login Method
     * @param doctor
     * @return Doctor Object
     */

    @PostMapping("login")
    public Doctor login(@RequestBody Doctor doctor) {
        System.out.println(doctor.getUserName());
        return this.doctorService.login(doctor.getUserName(), doctor.getPassword());
    }


    //    @GetMapping("/docs")
//    public Doctor findDoc(@RequestParam("username")String username){
//        return doctorRespository.findByUserName(username);
//    }
//
//    @GetMapping("/patients/{id}")
//    public List<Patient> patientsList(@PathVariable(name = "id") Long id) {
//        return this.patientRespository.findByDoctor_DocId(id);
//    }
}
