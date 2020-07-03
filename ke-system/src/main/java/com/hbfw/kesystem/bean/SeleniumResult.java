package com.hbfw.kesystem.bean;

import org.openqa.selenium.WebDriver;

public class SeleniumResult {
    private LoginToken token;
    private WebDriver driver;

    public SeleniumResult() {
    }

    public SeleniumResult(LoginToken token, WebDriver driver) {
        this.token = token;
        this.driver = driver;
    }

    public LoginToken getToken() {
        return token;
    }

    public void setToken(LoginToken token) {
        this.token = token;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
