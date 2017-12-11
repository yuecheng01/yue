package com.yuecheng.yue.ui.bean;

import java.io.Serializable;

/**
 * Created by yuecheng on 2017/11/5.
 */

public class YUE_LoginBean implements Serializable {
    private int resuletcode;
    private DataBean value;

    public int getResuletcode() {
        return resuletcode;
    }

    public void setResuletcode(int resuletcode) {
        this.resuletcode = resuletcode;
    }

    public DataBean getValue() {
        return value;
    }

    public void setValue(DataBean value) {
        this.value = value;
    }

    public class DataBean{
        private int code ;
        private String userId;
        private String token;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
