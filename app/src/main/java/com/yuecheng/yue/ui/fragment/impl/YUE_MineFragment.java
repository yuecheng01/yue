package com.yuecheng.yue.ui.fragment.impl;
import android.os.Bundle;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseLazyFragment;

/**
 * Created by yuecheng on 2017/11/6.
 */

public class YUE_MineFragment extends YUE_BaseLazyFragment {
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_mine_layout);
    }
}
