package com.yuecheng.yue.ui.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import com.yuecheng.yue.ui.activity.YUE_IFriendCircleView;
import com.yuecheng.yue.ui.activity.impl.ImagePagerActivity;
import com.yuecheng.yue.ui.adapter.YUE_CircleAdapter;
import com.yuecheng.yue.ui.bean.CircleItem;
import com.yuecheng.yue.ui.bean.CommentConfig;
import com.yuecheng.yue.ui.bean.CommentItem;
import com.yuecheng.yue.ui.bean.FavortItem;
import com.yuecheng.yue.ui.bean.PhotoInfo;
import com.yuecheng.yue.ui.interactor.YUE_IFriendCircleViewInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_FriendCircleViewInteractorImpl;
import com.yuecheng.yue.util.YUE_ToastUtils;
import com.yuecheng.yue.widget.circle.DatasUtil;
import com.yuecheng.yue.widget.dialogfragment.PhotoAdapter;
import com.yuecheng.yue.widget.dialogfragment.ZoomPhotoView;
import com.yuecheng.yue.widget.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administraot on 2017/11/29.
 */

public class YUE_FriendCircleViewPresenter {
    private Context mContext;
    private YUE_IFriendCircleView mView;
    private YUE_IFriendCircleViewInteractor mInteractor;
    private YUE_CircleAdapter mAdapter;

    public YUE_FriendCircleViewPresenter(Context context, YUE_IFriendCircleView yue_iFriendCircleView) {
        mContext = context;
        mView = yue_iFriendCircleView;
        mInteractor = new YUE_FriendCircleViewInteractorImpl();
        mAdapter = new YUE_CircleAdapter(context);
        mAdapter.addItemClickListener(mCallBack);
    }

    //每条动态item中的事件回调
    YUE_CircleAdapter.CircleItemClickListenerCallBack mCallBack = new YUE_CircleAdapter.CircleItemClickListenerCallBack() {
        /**
         *
         * @Title: deleteCircle
         * @Description: 删除动态
         * @param  circleId
         * @return void    返回类型
         * @throws
         */
        @Override
        public void deleteCircle(String circleId) {
            mView.update2DeleteCircle(circleId);
        }

        /**
         *
         * @Title: deleteComment
         * @Description: 删除评论
         * @param @param circlePosition
         * @param @param commentId
         * @return void    返回类型
         * @throws
         */
        @Override
        public void deleteComment(int circlePosition, String commentItemId) {
            mView.update2DeleteComment(circlePosition, commentItemId);
        }

        /**
         * 弹出评论框
         * @param commentConfig
         */
        @Override
        public void showEditTextBody(CommentConfig commentConfig) {
            mView.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
        }

        /**
         *
         * @Title: addFavort
         * @Description: 点赞
         * @param  circlePosition
         * @return void    返回类型
         * @throws
         */
        @Override
        public void addFavort(int circlePosition) {
            FavortItem item = DatasUtil.createCurUserFavortItem();
            mView.update2AddFavorite(circlePosition, item);
        }

        /**
         *
         * @Title: deleteFavort
         * @Description: 取消点赞
         * @param @param circlePosition
         * @param @param favortId
         * @return void    返回类型 l
         * @throws
         */
        @Override
        public void deleteFavort(int circlePosition, String favortId) {
            mView.update2DeleteFavort(circlePosition, favortId);
        }

        /**
         * 这里两种预览方式 一种跳转至activity中预览,另一个为dialogfragment中预览
         * @param view
         * @param position
         * @param photos
         */
        @Override
        public void preViewPic(View view, int position, List<PhotoInfo> photos) {
            //first activity to preview
            //imagesize是作为loading时的图片size
            ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());

            List<String> photoUrls = new ArrayList<String>();
            for (PhotoInfo photoInfo : photos) {
                photoUrls.add(photoInfo.url);
            }
            ImagePagerActivity.startImagePagerActivity(mContext
                    , photoUrls, position, imageSize);

            //second dialogfragment to preview
           /* final ZoomPhotoView zoomPhotoView = ZoomPhotoView
                    .size(12)
                    .bacColor(Color.BLACK)
                    .color(Color.WHITE)
                    .current(position)
                    .build();
            *//**
             * 添加图片数据进去
             *//*
            zoomPhotoView.addImages(photos);
            *//**
             * 添加预览中的图片点击事件
             *//*
            zoomPhotoView.addPicClicListener(new ZoomPhotoView.onPageClickListener() {
                @Override
                public void onPicClick(View view, int position) {
                    PhotoView p = (PhotoView)view;
                    zoomPhotoView.dismiss();
                }
            });
            *//**
             * 弹出Dialog
             *//*
            zoomPhotoView.show(mView.getSupportFManager(), "tag");*/
        }

        /**
         * 点赞中人名的点击事件
         * @param userName
         * @param userId
         */
        @Override
        public void clickName(String userName, String userId) {
           YUE_ToastUtils.showmessage(userName + " &id = " +
                   userId);
        }

        /**
         * 评论列表中名字的点击事件
         * @param name
         * @param id
         */
        @Override
        public void onClickCommentName(String name, String id) {
            YUE_ToastUtils.showmessage(name + " &id = " +
                    id);
        }

    };

    public YUE_CircleAdapter getAdapter() {
        return mAdapter;
    }

    public void loadData(int loadType) {
        List<CircleItem> datas = DatasUtil.createCircleDatas();
        if (mView != null) {
            mView.update2loadData(loadType, datas);
        }
    }

    /**
     * @param content
     * @param config  CommentConfig
     * @return void    返回类型
     * @throws
     * @Title: addComment
     * @Description: 增加评论
     */
    public void addComment(String content, CommentConfig config) {
        CommentItem newItem = null;
        if (config.commentType == CommentConfig.Type.PUBLIC) {
            newItem = DatasUtil.createPublicComment(content);
        } else if (config.commentType == CommentConfig.Type.REPLY) {
            newItem = DatasUtil.createReplyComment(config.replyUser, content);
        }
        if (mView != null) {
            mView.update2AddComment(config.circlePosition, newItem);
        }
    }
}
