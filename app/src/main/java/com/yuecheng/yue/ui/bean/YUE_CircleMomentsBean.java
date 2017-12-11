package com.yuecheng.yue.ui.bean;

import java.util.List;

/**
 * Created by administraot on 2017/11/29.
 */

public class YUE_CircleMomentsBean{
    private String usericon;
    private String nickname;
    private String textcontent;
    private List<String> imgcontent;

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTextcontent() {
        return textcontent;
    }

    public void setTextcontent(String textcontent) {
        this.textcontent = textcontent;
    }

    public List<String> getImgcontent() {
        return imgcontent;
    }

    public void setImgcontent(List<String> imgcontent) {
        this.imgcontent = imgcontent;
    }
}
