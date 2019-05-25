package com.haulmont.testtask.entities;

import com.google.gwt.thirdparty.guava.common.base.Objects;

public class Doctor implements Entity {
    private long id;
    private String name;
    private String secname;
    private String otch;
    private String spec;

    public Doctor(long id, String name, String secname, String otch, String spec) {
        this.id = id;
        this.name = name;
        this.secname = secname;
        this.otch = otch;
        this.spec = spec;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return String.format("%s %s.%s.", secname, name.charAt(0), otch.charAt(0));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, secname, otch, spec);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Doctor other = (Doctor) obj;
        return Objects.equal(name, other.getName())
                && Objects.equal(secname, other.getSecname())
                && Objects.equal(otch, other.getOtch())
                && Objects.equal(spec, other.getSpec());
    }
}
