package com.haulmont.testtask.entities;

import java.time.LocalDate;
import java.util.Date;

public class Prescription implements Entity { // рецепт
    private long id;
    private String description; // описание
    private Patient patient;
    private Doctor doctor;
    private LocalDate createDate; // дата создания
    private LocalDate expireDate; // срок окончания
    private Priority priority;

    public Prescription(long id, String description, Patient patient, Doctor doctor,
                        LocalDate createDate, LocalDate expireDate, Priority priority) {
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
