package com.hbfw.kesystem.bean;

public class DefaultBasePath {
    private String studentsBasePath;

    private String driverPath;

    private String preLoginDataPath;

    private String crawlerBasePath;

    private String webDriverPort;

    private String binaryExeName;

    public DefaultBasePath() {
    }

    public DefaultBasePath(String studentsBasePath, String driverPath, String preLoginDataPath, String crawlerBasePath, String webDriverPort, String binaryExeName) {
        this.studentsBasePath = studentsBasePath;
        this.driverPath = driverPath;
        this.preLoginDataPath = preLoginDataPath;
        this.crawlerBasePath = crawlerBasePath;
        this.webDriverPort = webDriverPort;
        this.binaryExeName = binaryExeName;
    }

    public String getStudentsBasePath() {
        return studentsBasePath;
    }

    public void setStudentsBasePath(String studentsBasePath) {
        this.studentsBasePath = studentsBasePath;
    }

    public String getDriverPath() {
        return driverPath;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

    public String getPreLoginDataPath() {
        return preLoginDataPath;
    }

    public void setPreLoginDataPath(String preLoginDataPath) {
        this.preLoginDataPath = preLoginDataPath;
    }

    public String getCrawlerBasePath() {
        return crawlerBasePath;
    }

    public void setCrawlerBasePath(String crawlerBasePath) {
        this.crawlerBasePath = crawlerBasePath;
    }

    public String getWebDriverPort() {
        return webDriverPort;
    }

    public void setWebDriverPort(String webDriverPort) {
        this.webDriverPort = webDriverPort;
    }

    public String getBinaryExeName() {
        return binaryExeName;
    }

    public void setBinaryExeName(String binaryExeName) {
        this.binaryExeName = binaryExeName;
    }
}
