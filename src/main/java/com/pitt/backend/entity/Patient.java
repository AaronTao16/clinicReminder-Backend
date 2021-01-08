package com.pitt.backend.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pat_id")
    private Long patId;
    //    @Column(name = "username")
    private String userName;
    //    @Column(name = "password")
    private String password;
    @Column(name = "Last_name")
    private String lName;
    @Column(name = "First_name")
    private String fName;
    @Column(name = "age")
    private int age;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "dept")
    private String dept;
    @Column(name = "des")
    private String des;


    @Column(name = "high_unfinished_count")
    private int hCount;
    @Column(name = "middle_unfinished_count")
    private int mCount;
    @Column(name = "low_unfinished_count")
    private int lCount;

    @ManyToOne(targetEntity = Doctor.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", referencedColumnName = "doc_id", nullable = false)
    private Doctor doctor;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REFRESH)
    private Set<ClinicalOrder> clinicalOrderSet = new HashSet<>();

    public Patient(@NonNull Long patId, String userName, String password, String lName, String fName, int hCount, int mCount, int lCount) {
        this.patId = patId;
        this.userName = userName;
        this.password = password;
        this.lName = lName;
        this.fName = fName;
        this.hCount = hCount;
        this.mCount = mCount;
        this.lCount = lCount;
    }

    public Patient(Long patId, String lName, String fName, int hCount, int mCount, int lCount) {
        this.patId = patId;
        this.lName = lName;
        this.fName = fName;
        this.hCount = hCount;
        this.mCount = mCount;
        this.lCount = lCount;
    }

    public Long getPatId() {
        return patId;
    }

    public void setPatId(Long patId) {
        this.patId = patId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Set<ClinicalOrder> getClinicalOrderSet() {
        return clinicalOrderSet;
    }

    public void setClinicalOrderSet(Set<ClinicalOrder> clinicalOrderSet) {
        this.clinicalOrderSet = clinicalOrderSet;
    }

    public int gethCount() {
        return hCount;
    }

    public void sethCount(int hCount) {
        this.hCount = hCount;
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public int getlCount() {
        return lCount;
    }

    public void setlCount(int lCount) {
        this.lCount = lCount;
    }
}
