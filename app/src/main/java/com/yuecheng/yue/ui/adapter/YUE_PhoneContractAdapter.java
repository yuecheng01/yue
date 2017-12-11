package com.yuecheng.yue.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_ContactsInfo;
import com.yuecheng.yue.widget.YUE_CircleImageView;

import java.util.List;

/**
 * Created by administraot on 2017/12/8.
 */

public class YUE_PhoneContractAdapter extends RecyclerView.Adapter<YUE_PhoneContractAdapter
        .ViewHolder> {

    private List<YUE_ContactsInfo> mList;

    public YUE_PhoneContractAdapter(List<YUE_ContactsInfo> list) {
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_phonecontact_layout, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mName.setText(mList.get(position).getName());
        holder.mPhoneNum.setText(mList.get(position).getNumber());
        holder.mIcon.setImageBitmap(mList.get(position).getContactPhoto());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mPhoneNum;
        public YUE_CircleImageView mIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.username);
            mPhoneNum = (TextView) itemView.findViewById(R.id.userphonenum);
            mIcon = (YUE_CircleImageView) itemView.findViewById(R.id.usericon);
        }
    }
    public void setFilter( List<YUE_ContactsInfo> list){
        mList.clear();
        mList = list;
        notifyDataSetChanged();
    };
}
