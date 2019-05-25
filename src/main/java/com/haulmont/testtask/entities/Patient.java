package com.haulmont.testtask.entities;

import com.google.gwt.thirdparty.guava.common.base.Objects;

public class Patient implements Entity {
    private long id;
    private String name;
    private String secname;
    private String otch;
    private long phoneNo;

    public Patient(long id, String name, String secname, String otch, long phoneNo) {
        this.id = id;
        this.name = name;
        this.secname = secname;
        this.otch = otch;
        this.phoneNo = phoneNo;
    }

    public Patient() {
        id = -1;
        name = "";
        secname = "";
        otch = "";
        phoneNo = -1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return String.format("%s %s.%s.", secname, name.charAt(0), otch.charAt(0));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, secname, otch, phoneNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Patient other = (Patient) obj;
        return Objects.equal(name, other.getName())
                && Objects.equal(secname, other.getSecname())
                && Objects.equal(otch, other.getOtch())
                && Objects.equal(phoneNo, other.getPhoneNo());
    }
}
