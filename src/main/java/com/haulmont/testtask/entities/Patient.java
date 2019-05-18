package com.haulmont.testtask.entities;

public class Patient implements Entity {
    private long id;
    private String name;
    private String secname;
    private String otch;
    private int phoneNo;

    public Patient(long id, String name, String secname, String otch, int phoneNo) {
        this.id = id;
        this.name = name;
        this.secname = secname;
        this.otch = otch;
        this.phoneNo = phoneNo;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecname() {
        return secname;
    }

    public void setSecname(String secname) {
        this.secname = secname;
    }

    public String getOtch() {
        return otch;
    }

    public void setOtch(String otch) {
        this.otch = otch;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }
}