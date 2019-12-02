package com.hci.registration;

public class Registration {

    private String userId;
    private String courseId;
    private String courseName;
    private String courseStatus;
    private double gradePoint;

    public Registration() {
        super();
    }

    public Registration(String userId, String courseId, String courseName, String courseStatus, double gradePoint) {
        this.userId = userId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseStatus = courseStatus;
        this.gradePoint = gradePoint;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(double gradePoint) {
        this.gradePoint = gradePoint;
    }
}
