package com.pitt.backend.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "clinic_Order")
public class ClinicalOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ord_id")
    private Long ordId;
    @Column(name = "ord_title")
    private String ordTitle;
    @Column(name = "start_time")
    private Date sTime;
    @Column(name = "end_time")
    private Date eTime;

    @Column(name = "create_time")
    @CreatedDate
    private Date cTime;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "des")
    private String des;
    @Column(name = "priority")
    private int pro;

    @Column(name = "order_status")
    private int status;

    @LastModifiedDate
    @Column(name = "finished_time")
    private Date fTime;
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private Set<Order_Status> statusSet= new HashSet<>();


    @ManyToOne(targetEntity = Doctor.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", referencedColumnName = "doc_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pat_id", referencedColumnName = "pat_id", nullable = false)
    private Patient patient;

    public ClinicalOrder(){}

    public ClinicalOrder(Long ordId, Date eTime, int status) {
        this.ordId = ordId;
        this.eTime = eTime;
        this.status = status;
    }

    public ClinicalOrder(Long ordId, String ordTitle, Date sTime, Date eTime, Date cTime, Long duration, String des, int pro, int status, Date fTime) {
        this.ordId = ordId;
        this.ordTitle = ordTitle;
        this.sTime = sTime;
        this.eTime = eTime;
        this.cTime = cTime;
        this.duration = duration;
        this.des = des;
        this.pro = pro;
        this.status = status;
        this.fTime = fTime;
    }

    public Long getOrdId() {
        return ordId;
    }

    public void setOrdId(Long ordId) {
        this.ordId = ordId;
    }

    public String getOrdTitle() {
        return ordTitle;
    }

    public void setOrdTitle(String ordTitle) {
        this.ordTitle = ordTitle;
    }

    public Date getsTime() {
        return sTime;
    }

    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }

    public Date geteTime() {
        return eTime;
    }

    public void seteTime(Date eTime) {
        this.eTime = eTime;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getPro() {
        return pro;
    }

    public void setPro(int pro) {
        this.pro = pro;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getfTime() {
        return fTime;
    }

    public void setfTime(Date fTime) {
        this.fTime = fTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
