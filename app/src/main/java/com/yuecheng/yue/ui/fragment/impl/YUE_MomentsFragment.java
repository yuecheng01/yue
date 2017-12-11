package com.yuecheng.yue.ui.fragment.impl;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseLazyFragment;
import com.yuecheng.yue.ui.activity.impl.YUE_FriendCircleActivity;

/**
 * Created by yuecheng on 2017/11/6.
 */

public class YUE_MomentsFragment extends YUE_BaseLazyFragment implements View.OnClickListener {
    private LinearLayout mFriendCircle;
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        View view =inflater.inflate(R.layout.fragment_moments_layout,null);
        setContentView(view);
        initViews(view);
        initDataEvents();
    }

    private void initViews(View view) {
        mFriendCircle = (LinearLayout) view.findViewById(R.id.friendcircle);
    }

    private void initDataEvents() {
        mFriendCircle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.friendcircle:
               startActivity(new Intent(getActivity(),YUE_FriendCircleActivity.class));
                break;
        }
    }
}
