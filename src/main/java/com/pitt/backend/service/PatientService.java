package com.pitt.backend.service;

import com.pitt.backend.entity.ClinicalOrder;
import com.pitt.backend.entity.Doctor;
import com.pitt.backend.entity.Patient;

import java.util.List;


public interface PatientService {

    /**
     *
     * @param docId
     * @return
     */
    List<ClinicalOrder> findAllByDocId(Long docId);

    /**
     *
     * @param docId
     * @return
     */
    List<Patient> getByDocId(Long docId);

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    Patient login(String userName, String password);

    /**
     *
     * @param patient
     * @param password
     * @return
     */
    Patient validation(Patient patient, String password);
}
