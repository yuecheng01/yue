package com.yuecheng.yue.ui.activity.impl;

import android.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivitySlideBack;
import com.yuecheng.yue.util.inject.CheckNet;
import com.yuecheng.yue.util.inject.FindView;
import com.yuecheng.yue.util.inject.OnClick;
import com.yuecheng.yue.util.inject.ViewUtils;

/**
 * Created by Administrator on 2018/2/8.
 */

public class AboutYueActivity extends YUE_BaseActivitySlideBack {
    @FindView(R.id.new_tv)
    private TextView mNew;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_aboutyue_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        ViewUtils.inject(this);
    initToolBars(R.id.toolbar,"关于YUE");
    }
    @CheckNet
    @OnClick(R.id.check_new_version)
    private void updateApp(View view){
        mNew.setVisibility(View.GONE);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.item_downdialog_layout);
        TextView tvContent = (TextView) window.findViewById(R.id.local_download);
        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
}
