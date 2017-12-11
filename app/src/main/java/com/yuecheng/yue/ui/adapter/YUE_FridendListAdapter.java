package com.yuecheng.yue.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_FriendsBean;
import com.yuecheng.yue.widget.YUE_CircleImageView;

import java.util.List;

import io.rong.imageloader.core.ImageLoader;

import static android.R.id.list;

/**
 * Created by yuecheng on 2017/11/11.
 */

public class YUE_FridendListAdapter extends BaseAdapter implements SectionIndexer {
    private List<YUE_FriendsBean> mFrindList;
    private Context mContext;

    public YUE_FridendListAdapter(Context context, List<YUE_FriendsBean> list) {
        this.mContext = context;
        this.mFrindList = list;
    }

    /**
     * 传入新的数据 刷新UI的方法
     */
    public void updateListView(List<YUE_FriendsBean> list) {
        this.mFrindList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mFrindList != null) return mFrindList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mFrindList == null)
            return null;

        if (position >= mFrindList.size())
            return null;

        return mFrindList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final YUE_FriendsBean mContent = mFrindList.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.friendlist_item_layout, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.friendname);
            viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
            viewHolder.mImageView = (YUE_CircleImageView) convertView.findViewById(R.id.frienduri);
            viewHolder.tvUserId = (TextView) convertView.findViewById(R.id.friend_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            String letterFirst = mContent.getLetters();
            if(!TextUtils.isEmpty(letterFirst)){
                letterFirst = String.valueOf(letterFirst.toUpperCase().charAt(0));
            }
            viewHolder.tvLetter.setText(letterFirst);
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        if (mContent.isExitsDisplayName()) {
            viewHolder.tvTitle.setText(this.mFrindList.get(position).getDisplayname());
        } else {
            viewHolder.tvTitle.setText(this.mFrindList.get(position).getNickname());
        }
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mFrindList.get(i).getLetters();
            char firstChar = sortStr.charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mFrindList.get(position).getLetters().charAt(0);
    }
    final static class ViewHolder {
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
        TextView tvUserId;
    }
}
