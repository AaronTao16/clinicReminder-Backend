package com.pitt.backend.repository;

import com.pitt.backend.entity.ClinicalOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;

@CrossOrigin({"http://localhost:4200", "http://localhost:8100"})
@Repository
public interface OrderRepository extends CrudRepository<ClinicalOrder, Long> {
//    @RestResource(path = "orders")
//    List<Order> findByDoctor_DocId(@Param("docId") Long docId);

    @RestResource(path = "orders")
    List<ClinicalOrder> getByPatient_PatIdAndStatus(@Param("patId") Long docId, @Param("status") int status);
//    @Query("select new Order(o.ordId, o.ordTitle, o.sTime, o.eTime, o.cTime, o.duration, o.des, o.pro, o.status, o.fTime) from Order o where o.patient.patId = ?1 and o.status = ?2  and o.cTime >= ?3 order by o.cTime desc , o.pro asc")
//    List<Order> checkOrderHistory(Long patient_patId, int status, Date date);

    @Query("SELECT NEW ClinicalOrder (o.ordId, o.ordTitle, o.sTime, o.eTime, o.cTime, o.duration, o.des, o.pro, o.status, o.fTime) FROM ClinicalOrder o WHERE o.patient.patId = ?1 AND o.status = ?2  AND o.cTime >= ?3 ORDER BY o.cTime desc , o.pro asc")
    List<ClinicalOrder> getByPatId(Long PatId, int status, Date date);

    @Query("SELECT NEW ClinicalOrder (o.ordId, o.ordTitle, o.sTime, o.eTime, o.cTime, o.duration, o.des, o.pro, o.status, o.fTime) FROM ClinicalOrder o WHERE o.patient.patId = ?1 ORDER BY o.status asc,o.eTime asc, o.duration asc, o.cTime desc, o.pro asc")
    List<ClinicalOrder> getByPatId_mobile(Long PatId);

    @Query("SELECT NEW ClinicalOrder (o.ordId, o.eTime, o.status) FROM ClinicalOrder o WHERE o.status = 1 AND o.eTime <= ?1")
    List<ClinicalOrder> getByETime(Date date);
}
