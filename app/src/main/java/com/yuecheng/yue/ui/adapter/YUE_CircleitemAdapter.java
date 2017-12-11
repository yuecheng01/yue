package com.yuecheng.yue.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_CircleMomentsBean;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.ImageLoaderUtils;
import com.yuecheng.yue.widget.YUE_CircleImageView;
import com.yuecheng.yue.widget.YUE_NotScrollGridView;
import com.yuecheng.yue.widget.photoview.Info;
import com.yuecheng.yue.widget.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import io.rong.imageloader.core.ImageLoader;

/**
 * Created by administraot on 2017/11/29.
 */

public class YUE_CircleitemAdapter extends RecyclerView.Adapter<YUE_CircleitemAdapter.ViewHolder> {
    private List<YUE_CircleMomentsBean> mData;
    private List<String> mImgList;
    public YUE_CircleitemAdapter(List<YUE_CircleMomentsBean> data) {
        this.mData = data;

    }

    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    //    定义外部调用的回调
    public interface OnItemClickListener {
        void onPhotoItemClick(View view, int position, List<String> imglist);

        void onPingLunClick(int position);

        void onDianZanClick(int position);
    }

    private static OnItemClickListener mOnItemClickListener = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_circle_moment_layout, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ImageLoaderUtils.displayImageUrl(mData.get(position).getUsericon(), holder
                .mCircleImgView,null,null);
        holder.mUserName.setText(mData.get(position).getNickname());
        holder.mUserDesc.setText(mData.get(position).getTextcontent());
        mImgList = mData.get(position).getImgcontent();
        GridAdapter adapter = new GridAdapter();
        holder.mGridView.setAdapter(adapter);

        /*holder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onPhotoItemClick(view,position,mImgList);
                    }
                });
            }
        });*/
        holder.mDianZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onDianZanClick(position);
            }
        });
        holder.mPinLun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onPingLunClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public YUE_CircleImageView mCircleImgView;
        public TextView mUserName;
        public TextView mUserDesc;
        public YUE_NotScrollGridView mGridView;
        public ImageView mDianZan;
        public ImageView mPinLun;
        public TextView mDianZanNum;
        public TextView mPinLunNum;

        public ViewHolder(View itemView) {
            super(itemView);
            mCircleImgView = (YUE_CircleImageView) itemView.findViewById(R.id.user_icon);
            mUserName = (TextView) itemView.findViewById(R.id.user_displayname);
            mUserDesc = (TextView) itemView.findViewById(R.id.user_circle_desc);
            mGridView = (YUE_NotScrollGridView) itemView.findViewById(R.id.user_grid_photo);
            mDianZan = (ImageView) itemView.findViewById(R.id.dianzan);
            mPinLun = (ImageView) itemView.findViewById(R.id.pinlun);
            mDianZanNum = (TextView) itemView.findViewById(R.id.dianzan_num);
            mPinLunNum = (TextView) itemView.findViewById(R.id.pinlun_num);
        }
    }

    private class GridAdapter extends BaseAdapter {

        public GridAdapter() {
        }

        @Override
        public int getCount() {
            return mImgList.size();
        }

        @Override
        public Object getItem(int i) {
            return mImgList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder mHolder = null;
            if (null == convertView) {
                convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                        .item_circle_grid_layout, viewGroup, false);
                mHolder = new ViewHolder();
                mHolder.mImage = (PhotoView) convertView.findViewById(R.id.img);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(mImgList.get(i), mHolder.mImage);
            mHolder.mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mHolder.mImage.disenable();
            //设置每张图片大小
            ViewGroup.LayoutParams para = mHolder.mImage.getLayoutParams();
            para.width = (CommonUtils.getScreenWidth(viewGroup.getContext())-20)/3;//显示3列
            para.height = para.width;
            mHolder.mImage.setLayoutParams(para);
            //设置图片点击事件
            mHolder.mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onPhotoItemClick(view, i, mImgList);
                }
            });
            return convertView;
        }

        public class ViewHolder {
            public PhotoView mImage;
        }
    }
}
