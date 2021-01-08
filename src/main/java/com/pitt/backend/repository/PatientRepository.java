package com.pitt.backend.repository;

import com.pitt.backend.entity.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin({"http://localhost:4200", "http://localhost:8100"})
@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    @RestResource(path = "patients")
    List<Patient> findByDoctor_DocId(@Param("docId") Long docId);

    @Query("select new Patient(p.patId, p.fName, p.lName, p.hCount, p.mCount, p.lCount) from Patient p where p.doctor.docId = ?1 order by p.hCount desc , p.mCount desc , p.lCount desc ")
    List<Patient> getByDocId(Long docId);

    @Query("select new Patient(p.patId, p.userName, p.password, p.fName, p.lName, p.hCount, p.mCount, p.lCount) from Patient p where p.userName = ?1")
    Patient findByUserName(String username);

    int countByDoctorDocId(Long id);
}
