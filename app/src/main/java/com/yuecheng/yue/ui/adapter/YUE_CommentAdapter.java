package com.yuecheng.yue.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_CommentsBean;
import com.yuecheng.yue.util.ImageLoaderUtils;
import com.yuecheng.yue.widget.YUE_CircleImageView;

import java.util.List;

/**
 * Created by administraot on 2017/12/2.
 */

public class YUE_CommentAdapter extends RecyclerView.Adapter<YUE_CommentAdapter.ViewHolder> {
    private List<YUE_CommentsBean> mDatas;

    public YUE_CommentAdapter( List<YUE_CommentsBean>list) {
        super();
        this.mDatas =list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_layout,parent,false);
        ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoaderUtils.displayImageUrl(mDatas.get(position).getUsericon(),holder.mCommentIcon,null,null);
        holder.mCommentId.setText(mDatas.get(position).getUserid());
        holder.mCommentTime.setText(mDatas.get(position).getCommenttime());
        holder.mCommentCotent.setText(mDatas.get(position).getCommentcontent());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public YUE_CircleImageView mCommentIcon;
        public TextView mCommentId;
        public TextView mCommentTime;
        public TextView mCommentCotent;
        public ViewHolder(View itemView) {
            super(itemView);
            mCommentIcon = (YUE_CircleImageView) itemView.findViewById(R.id.commenticon);
            mCommentId = (TextView) itemView.findViewById(R.id.commentid);
            mCommentTime = (TextView) itemView.findViewById(R.id.commenttime);
            mCommentCotent = (TextView) itemView.findViewById(R.id.commentcontent);
        }
    }
}
