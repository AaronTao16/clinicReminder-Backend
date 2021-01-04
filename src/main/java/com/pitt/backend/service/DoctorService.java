package com.pitt.backend.service;

import com.pitt.backend.entity.Doctor;
import org.springframework.stereotype.Service;

@Service
public interface DoctorService {
    /**
     * Login
     * @param username
     * @param password
     * @return true login success
     */
    Doctor login(String username, String password);

    /**
     *
     * @param doctor
     * @param password
     * @return true password validated
     */
    Doctor validation(Doctor doctor, String password);
}
