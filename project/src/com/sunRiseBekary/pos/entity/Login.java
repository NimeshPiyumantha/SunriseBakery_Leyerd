package com.sunRiseBekary.pos.entity;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class Login {
    private String UserName;
    private String Password;

    public Login() {
    }

    public Login(String userName, String password) {
        UserName = userName;
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Login{" +
                "UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
