package com.yuecheng.yue.ui.fragment.impl;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseLazyFragment;
import com.yuecheng.yue.util.inject.FindView;
import com.yuecheng.yue.util.inject.OnClick;
import com.yuecheng.yue.util.inject.ViewUtils;

/**
 * Created by yuecheng on 2017/11/6.
 */

public class YUE_MineFragment extends YUE_BaseLazyFragment {
    @FindView(R.id.tv)
    private TextView mTextView;
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_mine_layout);
        ViewUtils.inject(YUE_MineFragment.this.getContentView(),YUE_MineFragment.this);
        mTextView.setText("敬请期待!");
    }
}