package com.hbfw.kesystem.bean;

public class LoginMessage {
    private int loginStatus; //0 成功;1 错误;2 异常;3 不存在
    private String msg;
    private String data;

    public LoginMessage() {
    }

    public LoginMessage(int loginStatus, String msg, String data) {
        this.loginStatus = loginStatus;
        this.msg = msg;
        this.data = data;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
