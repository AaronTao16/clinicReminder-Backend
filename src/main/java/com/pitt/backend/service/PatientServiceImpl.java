package com.pitt.backend.service;

import com.pitt.backend.entity.ClinicalOrder;
import com.pitt.backend.entity.Doctor;
import com.pitt.backend.entity.Patient;
import com.pitt.backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatientServiceImpl implements PatientService{


    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<ClinicalOrder> findAllByDocId(Long docId) {
        return null;
    }

    @Override
    public List<Patient> getByDocId(Long docId){
        return patientRepository.getByDocId(docId);
    };

    @Override
    public Patient login(String username, String password){
        System.out.println(username + "  " + password);
        Patient patient = this.patientRepository.findByUserName(username);
        return this.validation(patient, password);
    }

    @Override
    public Patient validation(Patient patient, String password) {
        if(patient == null || patient.getPassword() == null || password == null){
            return null;
        }
        System.out.println(password+"  /  "+patient.getPassword());
        if(patient.getPassword().equals(password))
            return patient;
        else
            return null;
    }
}
