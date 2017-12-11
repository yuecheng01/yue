package com.yuecheng.yue.ui.interactor.impl;

import android.content.Context;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_CircleMomentsBean;
import com.yuecheng.yue.ui.interactor.YUE_IFriendCircleViewInteractor;
import com.yuecheng.yue.util.YUE_AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administraot on 2017/12/2.
 */

public class YUE_FriendCircleViewInteractorImpl implements YUE_IFriendCircleViewInteractor {

    public List<YUE_CircleMomentsBean> getData() {
        List<YUE_CircleMomentsBean> list = new ArrayList<>();
        YUE_CircleMomentsBean y = new YUE_CircleMomentsBean();
        List<String> imgs = new ArrayList<String>() {
            {
                add("https://timgsa.baidu" +
                        ".com/timg?image&quality=80&size=b9999_10000&sec=1511967009516&di" +
                        "=77e1047e5b3aba8bae43d1f5dcc1f56f&imgtype=0&src=http%3A%2F%2Ff7.topitme" +
                        ".com%2F7%2F1c%2F88%2F11258911656e6881c7o.jpg");
                add("https://timgsa.baidu" +
                        ".com/timg?image&quality=80&size=b9999_10000&sec=1511967009513&di" +
                        "=1c4512b85a20ec91e6b6e590fd173776&imgtype=0&src=http%3A%2F%2Fuploads" +
                        ".xuexila.com%2Fallimg%2F1506%2F633-150629202618.jpg");
                add("https://timgsa.baidu" +
                        ".com/timg?image&quality=80&size=b9999_10000&sec=1511967009451&di" +
                        "=511db6418ab580c88eee18a37c305b49&imgtype=0&src=http%3A%2F%2Fimg1.3lian" +
                        ".com%2F2015%2Fa1%2F46%2Fd%2F74.jpg");
                add("https://timgsa.baidu" +
                        ".com/timg?image&quality=80&size=b9999_10000&sec=1511967009445&di" +
                        "=7b98893ec4d03853e70b634f037df62a&imgtype=0&src=http%3A%2F" +
                        "%2Fimg4.xiazaizhijia.com%2Fwalls%2F20151019%2F1440x900_83419613f167bca" +
                        ".jpg");
                add("https://timgsa.baidu" +
                        ".com/timg?image&quality=80&size=b9999_10000&sec=1511967337010&di" +
                        "=6a9f253cd27ee46144ebe2df68a8acb6&imgtype=0&src=http%3A%2F%2Fscimg" +
                        ".jb51.net%2Fallimg%2F150713%2F14-150G31G222950.jpg");
                add("https://timgsa.baidu" +
                        ".com/timg?image&quality=80&size=b9999_10000&sec=1511967417818&di" +
                        "=cef949c3ba20374d435beacb625109bd&imgtype=0&src=http%3A%2F%2Fimgsrc" +
                        ".baidu" +
                        ".com%2Fimgad%2Fpic%2Fitem%2Fe850352ac65c10387071c8f8b9119313b07e89f8.jpg");
                add("https://timgsa.baidu" +
                        ".com/timg?image&quality=80&size=b9999_10000&sec=1512482772508&di" +
                        "=e4e8c2bd25ebc38019e56ffc8988b669&imgtype=0&src=http%3A%2F%2Fimgsrc" +
                        ".baidu" +
                        ".com%2Fimgad%2Fpic%2Fitem%2F10dfa9ec8a136327d62e9b099a8fa0ec09fac7cd.jpg");
                add("https://timgsa.baidu" +
                        ".com/timg?image&quality=80&size=b9999_10000&sec=1512482772506&di" +
                        "=27872b73bf6100872f0ffd3475567e30&imgtype=0&src=http%3A%2F%2Fimgsrc" +
                        ".baidu" +
                        ".com%2Fimgad%2Fpic%2Fitem%2F5ab5c9ea15ce36d32ae0f90a31f33a87e950b120.jpg");
            }
        };
        y.setImgcontent(imgs);
        y.setNickname("悦城");
        y.setTextcontent(YUE_AppUtils.getAppContext().getResources().getString(R.string.userheart));
        y.setUsericon("http://www.jingomall.cn/upload/sysconfigs/2017-06/59536b10569af.png");

        list.add(y);
        list.add(y);
        list.add(y);
        list.add(y);
        return list;
    }
}
