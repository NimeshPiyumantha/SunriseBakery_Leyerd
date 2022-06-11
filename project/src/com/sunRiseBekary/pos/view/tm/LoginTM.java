package com.sunRiseBekary.pos.view.tm;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class LoginTM {
    private String UserName;
    private String Password;

    public LoginTM() {
    }

    public LoginTM(String userName, String password) {
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
        return "LoginTM{" +
                "UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
