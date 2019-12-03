/**
 * User Class
 *
 * @author Peiyuan
 * 2019-11-16
 */

package com.hci.user;


public class User {

    private String userId;
    private String nickname;
    private String degree;
    private String major;
    private String degreeId;
    private String majorId;

    public User(){
        super();
    }

    public User(String userId, String nickname, String degree, String major, String degreeId, String majorId) {
        this.userId = userId;
        this.nickname = nickname;
        this.degree = degree;
        this.major = major;
        this.degreeId = degreeId;
        this.majorId = majorId;
    }

    public User(User user){
        this.setUserId(user.getUserId());
        this.setNickname(user.getNickname());
        this.setDegree(user.getDegree());
        this.setMajor(user.getMajor());
        this.setMajorId(user.getMajorId());
        this.setDegreeId(user.getDegreeId());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }
}
