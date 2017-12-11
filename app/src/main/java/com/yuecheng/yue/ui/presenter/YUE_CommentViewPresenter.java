package com.yuecheng.yue.ui.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.yuecheng.yue.ui.activity.YUE_ICommentView;
import com.yuecheng.yue.ui.adapter.YUE_CommentAdapter;
import com.yuecheng.yue.ui.interactor.YUE_ICommentInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_CommentInteractorImpl;

/**
 * Created by administraot on 2017/12/2.
 */

public class YUE_CommentViewPresenter {
    private YUE_ICommentView mView;
    private Context mContext;
    private YUE_ICommentInteractor mInteractor;

    public YUE_CommentViewPresenter(Context context, YUE_ICommentView a) {
        this.mView = a;
        this.mContext = context;
        this.mInteractor = new YUE_CommentInteractorImpl();
    }

    public YUE_CommentAdapter getAdapter() {
        return  new YUE_CommentAdapter(mInteractor.getData());
    }
}
