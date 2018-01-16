package com.yuecheng.yue.ui.adapter;

import android.view.View;
import android.view.ViewStub;

import com.yuecheng.yue.R;

import static com.yuecheng.yue.ui.adapter.CircleViewHolder.TYPE_VIDEO;

/**
 * Created by Administrator on 2018/1/11.
 */

public class VideoViewHolder extends CircleViewHolder {
//    public CircleVideoView videoView;

    public VideoViewHolder(View itemView){
        super(itemView, TYPE_VIDEO);
    }

    @Override
    public void initSubView(int viewType, ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }

    }
}
