package com.hci.courses;

public class Courses {

    private String courseId;
    private String courseName;
    private int creditHour;
    private String subjectId;

    public Courses() {
        super();
    }

    public Courses(String courseId, String courseName, int creditHour, String subjectId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creditHour = creditHour;
        this.subjectId = subjectId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCreditHour() {
        return creditHour;
    }

    public void setCreditHour(int creditHour) {
        this.creditHour = creditHour;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
