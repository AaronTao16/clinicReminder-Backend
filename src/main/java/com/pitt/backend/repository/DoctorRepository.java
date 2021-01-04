package com.pitt.backend.repository;

import com.pitt.backend.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@CrossOrigin("http://localhost:4200")
@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {

    /**
     * find doctor by username -- login
     *@param username
     *
    */
    @Query("Select new Doctor(d.docId, d.userName, d.password) from Doctor d where d.userName = ?1")
    Doctor findAllByUserName(String username);

    Page<Doctor> findByDocId(@Param("docId") Long id, Pageable pageable);

}
