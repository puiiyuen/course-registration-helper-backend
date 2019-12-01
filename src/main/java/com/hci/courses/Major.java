package com.hci.courses;

public class Major {

    private String majorId;
    private String majorName;
    private String degreeId;

    public Major(String majorId) {
        this.majorId = majorId;
    }

    public Major(String majorId, String majorName, String degreeId) {
        this.majorId = majorId;
        this.majorName = majorName;
        this.degreeId = degreeId;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }
}