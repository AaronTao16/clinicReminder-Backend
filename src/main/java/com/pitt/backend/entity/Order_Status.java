package com.pitt.backend.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "ord_status")
public class Order_Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long staId;
    @Column(name = "finished_time")
    private Instant fTime;
    @Column(name = "cur_status")
    private String curStat;

    @ManyToOne(targetEntity = ClinicalOrder.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ord_id", referencedColumnName = "ord_id", nullable = false)
    private ClinicalOrder clinicalOrder;

    public Order_Status(){}

    public Order_Status(Long staId, Instant fTime, String curStat, ClinicalOrder clinicalOrder) {
        this.staId = staId;
        this.fTime = fTime;
        this.curStat = curStat;
        this.clinicalOrder = clinicalOrder;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Instant getfTime() {
        return fTime;
    }

    public void setfTime(Instant fTime) {
        this.fTime = fTime;
    }

    public String getCurStat() {
        return curStat;
    }

    public void setCurStat(String curStat) {
        this.curStat = curStat;
    }

    public ClinicalOrder getOrder() {
        return clinicalOrder;
    }

    public void setOrder(ClinicalOrder clinicalOrder) {
        this.clinicalOrder = clinicalOrder;
    }
}
