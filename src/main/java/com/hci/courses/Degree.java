package com.hci.courses;

public class Degree {

    private String degreeId;
    private String degreeName;

    public Degree() {
        super();
    }

    public Degree(String degreeId, String degreeName) {
        this.degreeId = degreeId;
        this.degreeName = degreeName;
    }

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }
}
