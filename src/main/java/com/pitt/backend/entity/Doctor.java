package com.pitt.backend.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id")
    private Long docId;

    private String userName;
    private String password;
    @Column(name = "Last_name")
    private String lName;
    @Column(name = "First_name")
    private String fName;
    @Column(name = "age")
    private int age;
    @Column(name = "dept")
    private String dept;
    @Column(name = "pos")
    private String pos;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REFRESH)
    private Set<Patient> patientSet = new HashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REFRESH)
    private Set<ClinicalOrder> clinicalOrderSet = new HashSet<>();

    public Doctor(){}

    public Doctor(Long docId, String userName, String password){
        this.docId = docId;
        this.userName = userName;
        this.password = password;
    }

    public Doctor(Long docId, String userName, String password, String lName, String fName, int age, String dept, String pos, String email, Set<Patient> patientSet, Set<ClinicalOrder> clinicalOrderSet) {
        this.docId = docId;
        this.userName = userName;
        this.password = password;
        this.lName = lName;
        this.fName = fName;
        this.age = age;
        this.dept = dept;
        this.pos = pos;
        this.email = email;
        this.patientSet = patientSet;
        this.clinicalOrderSet = clinicalOrderSet;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Patient> getPatientSet() {
        return patientSet;
    }

    public void setPatientSet(Set<Patient> patientSet) {
        this.patientSet = patientSet;
    }

    public Set<ClinicalOrder> getOrderSet() {
        return clinicalOrderSet;
    }

    public void setOrderSet(Set<ClinicalOrder> clinicalOrderSet) {
        this.clinicalOrderSet = clinicalOrderSet;
    }
}
