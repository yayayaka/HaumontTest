package com.haulmont.testtask.entities;

public class DoctorPrescrInfo implements Entity {
    private String docName;
    private String docSecname;
    private String docOtch;
    private int numPrescriptions;

    public DoctorPrescrInfo(String docName, String docSecname, String docOtch, int numPrescriptions) {
        this.docName = docName;
        this.docSecname = docSecname;
        this.docOtch = docOtch;
        this.numPrescriptions = numPrescriptions;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocSecname() {
        return docSecname;
    }

    public void setDocSecname(String docSecname) {
        this.docSecname = docSecname;
    }

    public String getDocOtch() {
        return docOtch;
    }

    public void setDocOtch(String docOtch) {
        this.docOtch = docOtch;
    }

    public int getNumPrescriptions() {
        return numPrescriptions;
    }

    public void setNumPrescriptions(int numPrescriptions) {
        this.numPrescriptions = numPrescriptions;
    }
}
