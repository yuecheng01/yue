package com.yuecheng.yue.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.db.entity.Friend;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.widget.YUE_CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 */

public class SelectFriendAdapter extends BaseRecycleViewAdapter implements SectionIndexer {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .adapter_item_selectcontacts_layout, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)){
            String firstLetters = String.valueOf(((Friend) datas.get(position)).getLetters()
                    .charAt(0));
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(firstLetters);
        }else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(((Friend)datas.get(position)).getDisplayName())) {
            viewHolder.tvTitle.setText(((Friend)datas.get(position)).getNickName());
        } else {
            viewHolder.tvTitle.setText(((Friend)datas.get(position)).getDisplayName());
        }

    }


    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        YUE_LogUtils.d("yueselectfriend",getItemCount()+"");
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = ((Friend)datas.get(i)).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return ((Friend)datas.get(position)).getLetters().charAt(0);
    }
    @Override
    public int getItemCount() {
        return datas.size() > 0 ? datas.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * 首字母
         */
        TextView tvLetter;
        /**
         * 昵称
         */
        TextView tvTitle;
        /**
         * 头像
         */
        YUE_CircleImageView mImageView;
        /**
         * userid
         */
//            TextView tvUserId;
        /**
         * 是否被选中的checkbox
         */
        CheckBox isSelect;
        LinearLayout mItem;
        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.dis_friendname);
            tvLetter = (TextView) itemView.findViewById(R.id.dis_catalog);
            mImageView = (YUE_CircleImageView) itemView.findViewById(R.id.dis_frienduri);
            isSelect = (CheckBox) itemView.findViewById(R.id.dis_select);
            mItem = (LinearLayout) itemView.findViewById(R.id.dis_frienditem);
        }
    }

}
