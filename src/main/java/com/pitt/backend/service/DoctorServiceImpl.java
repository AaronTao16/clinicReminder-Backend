package com.pitt.backend.service;

import com.pitt.backend.entity.Doctor;
import com.pitt.backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService{
    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor login(String username, String password){
        System.out.println(username + "  " + password);
        Doctor doctor = this.doctorRepository.findAllByUserName(username);
        return this.validation(doctor, password);
    }

    @Override
    public Doctor validation(Doctor doctor, String password) {
        if(doctor == null || doctor.getPassword() == null || password == null){
            //System.out.println(password+"  /  "+doctor.getPassword());
            return null;
        }
        System.out.println(password+"  /  "+doctor.getPassword());
        if(doctor.getPassword().equals(password))
            return doctor;
        else
            return null;
    }
}
