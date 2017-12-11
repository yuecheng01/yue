package com.yuecheng.yue.ui.interactor.impl;

import com.yuecheng.yue.ui.bean.YUE_CommentsBean;
import com.yuecheng.yue.ui.interactor.YUE_ICommentInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administraot on 2017/12/2.
 */

public class YUE_CommentInteractorImpl implements YUE_ICommentInteractor {
    @Override
    public List<YUE_CommentsBean> getData() {
        List<YUE_CommentsBean> list =new ArrayList<>();
        YUE_CommentsBean y =new YUE_CommentsBean();
        y.setUserid("15171391343");
        y.setUsericon("http://www.jingomall.cn/upload/sysconfigs/2017-06/59536b10569af.png");
        y.setCommenttime("2017-12-2");
        y.setCommentcontent("当女人爱上女人。。。");
        list.add(y);
        list.add(y);
        list.add(y);
        list.add(y);
        list.add(y);
        list.add(y);
        list.add(y);
        list.add(y);
        list.add(y);
        list.add(y);
        list.add(y);
        list.add(y);
        return list;
    }
}
