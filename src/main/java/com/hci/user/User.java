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

    public User(){
        super();
    }

    public User(String userId, String nickname, String degree, String major) {
        this.userId = userId;
        this.nickname = nickname;
        this.degree = degree;
        this.major = major;
    }

    public User(User user){
        this.setUserId(user.getUserId());
        this.setNickname(user.getNickname());
        this.setDegree(user.getDegree());
        this.setMajor(user.getMajor());
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

}
