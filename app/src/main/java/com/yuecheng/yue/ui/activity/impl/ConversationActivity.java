package com.yuecheng.yue.ui.activity.impl;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivity;
import com.yuecheng.yue.widget.picloadlib.view.AlbumActivity;
import com.yuecheng.yue.widget.picloadlib.view.ImageFile;

import java.util.Locale;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by administraot on 2017/11/20.
 */

public class ConversationActivity extends YUE_BaseActivity {

   private Toolbar mToolBar;

    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    @Override
    protected void initViewsAndEvents() {
        initViews();
        initDataEvents();

    }

    private void initDataEvents() {
        Intent intent = getIntent();
        mTargetId = intent.getData().getQueryParameter("targetId");
        //intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
        initToolBar();
        enterFragment(mConversationType, mTargetId);
    }

    private void initToolBar() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle(mTargetId);
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));

        //        toolbar 返回按钮监听
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConversationActivity.this.finish();
            }
        });
    }

    private void initViews() {
        mToolBar = findView(R.id.toolbar);

    }
    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_conversation_layout;
    }
}
