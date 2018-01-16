package com.yuecheng.yue.ui.bean;

import java.io.Serializable;

/**
 * Created by yuecheng on 2017/11/5.
 */

public class YUE_LoginBean implements Serializable {
    /**
     * resuletcode : 1
     * value : {"code":200,"userId":"15171391343","token":"PyCLnmR9KuwOcu/nt6E5RGB68LGf66RzIDHwehWtd08WiX5IqRJNVBRKbHmNzDV8HYvsOTFvX7MI9EgQ2jtHUg0ciF4bkNAl"}
     */

    private int resuletcode;
    private ValueBean value;

    public int getResuletcode() {
        return resuletcode;
    }

    public void setResuletcode(int resuletcode) {
        this.resuletcode = resuletcode;
    }

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public static class ValueBean {
        /**
         * code : 200
         * userId : 15171391343
         * token : PyCLnmR9KuwOcu/nt6E5RGB68LGf66RzIDHwehWtd08WiX5IqRJNVBRKbHmNzDV8HYvsOTFvX7MI9EgQ2jtHUg0ciF4bkNAl
         */

        private int code;
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
