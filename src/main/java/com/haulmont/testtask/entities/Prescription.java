package com.haulmont.testtask.entities;

import java.util.Date;

public class Prescription { // рецепт
    private long id;
    private String description; // описание
    private Patient patient;
    private Doctor doctor;
    private Date createDate; // дата создания
    private Date expireDate; // срок окончания
    private String priority;

    public Prescription(long id, String description, Patient patient, Doctor doctor,
                        Date createDate, Date expireDate, String priority) {
        this.id = id;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
