package com.kk.qqcommon;

import java.io.Serializable;

/**
 * 表示用户信息
 *
 * @author KK
 * @version 1.0
 */
public class User implements Serializable {
    public static final long serialVersionUID = 1L;

    // 用户名/ID
    private String userId;
    // 密码
    private String passwd;

    public User() {}

    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
