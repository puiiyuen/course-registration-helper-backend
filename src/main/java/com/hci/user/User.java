/**
 * User Class
 *
 * @author Peiyuan
 * 2019-11-16
 */

package com.hci.user;


public class User {

    private int userId;
    private String username;
    private String phone;
    private String email;
    private String password;

    public User(){
        super();
    }

    public User(User user) {
        this.setUserId(user.getUserId());
        this.setPassword(user.getPassword());
        this.setUsername(user.getUsername());
        this.setPhone(user.getPhone());
        this.setEmail(user.getEmail());
    }

    public User defaultUser(){
        User du = new User();
        du.setEmail("noname@no.com");
        du.setPhone("000-000-000");
        du.setPassword("null");
        du.setUserId(-1);
        du.setUsername("noname");
        return du;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
