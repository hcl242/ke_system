package com.hbfw.kesystem.bean;

public class LoginToken {
    private String bkn;
    private String cookie;

    public LoginToken() {
    }

    public LoginToken(String bkn, String cookie) {
        this.bkn = bkn;
        this.cookie = cookie;
    }

    public String getBkn() {
        return bkn;
    }

    public void setBkn(String bkn) {
        this.bkn = bkn;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
