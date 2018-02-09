package com.yuecheng.yue.ui.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25.
 */

public class SearchedFriendBean {
    @Override
    public String toString() {
        return "SearchedFriendBean{" +
                "resulecode=" + resulecode +
                ", value=" + value +
                '}';
    }

    /**
     * resulecode : 1
     * value : [{"nickname":"yuecheng01","portraituri":null}]
     */

    private int resulecode;
    private List<ValueBean> value;

    public int getResulecode() {
        return resulecode;
    }

    public void setResulecode(int resulecode) {
        this.resulecode = resulecode;
    }

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean {
        @Override
        public String toString() {
            return "ValueBean{" +
                    "nickname='" + nickname + '\'' +
                    ", portraituri=" + portraituri +
                    '}';
        }

        /**
         * nickname : yuecheng01
         * portraituri : null
         */

        private String nickname;
        private String portraituri;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPortraituri() {
            return portraituri;
        }

        public void setPortraituri(String portraituri) {
            this.portraituri = portraituri;
        }
    }
}
