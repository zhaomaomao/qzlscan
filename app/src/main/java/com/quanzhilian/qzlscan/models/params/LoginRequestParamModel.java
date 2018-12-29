package com.quanzhilian.qzlscan.models.params;

/*
* 登录请求模型
*/
public class LoginRequestParamModel {
    /**
     * 登录用户（admin / 员工登录名）
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
