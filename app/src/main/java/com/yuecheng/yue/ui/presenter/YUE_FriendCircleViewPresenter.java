package com.yuecheng.yue.ui.presenter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.activity.YUE_IFriendCircleView;
import com.yuecheng.yue.ui.activity.impl.YUE_CommentActivity;
import com.yuecheng.yue.ui.adapter.YUE_CircleitemAdapter;
import com.yuecheng.yue.ui.bean.YUE_CircleMomentsBean;
import com.yuecheng.yue.ui.interactor.YUE_IFriendCircleViewInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_FriendCircleViewInteractorImpl;
import com.yuecheng.yue.widget.dialogfragment.PhotoAdapter;
import com.yuecheng.yue.widget.dialogfragment.ZoomPhotoView;
import com.yuecheng.yue.widget.photoview.Info;
import com.yuecheng.yue.widget.photoview.PhotoView;

import java.util.List;

/**
 * Created by administraot on 2017/11/29.
 */

public class YUE_FriendCircleViewPresenter {
    private Context mContext;
    private YUE_IFriendCircleView mView;
    private YUE_IFriendCircleViewInteractor mInteractor;

    public YUE_FriendCircleViewPresenter(Context context,YUE_IFriendCircleView yue_iFriendCircleView) {
        mContext =context;
        mView = yue_iFriendCircleView;
        mInteractor = new YUE_FriendCircleViewInteractorImpl();
    }

    public YUE_CircleitemAdapter getAdapter(){
        List<YUE_CircleMomentsBean> mlist = mInteractor.getData();
        YUE_CircleitemAdapter mAdapter = new YUE_CircleitemAdapter(mlist);
        return mAdapter;
    }

    public YUE_CircleitemAdapter.OnItemClickListener getListener() {
        YUE_CircleitemAdapter.OnItemClickListener listener = new YUE_CircleitemAdapter.OnItemClickListener(){


            @Override
            public void onPhotoItemClick(View view, int position, final List<String> imglist) {
                //todo 图片
                final ZoomPhotoView zoomPhotoView = ZoomPhotoView
                        .size(12)
                        .bacColor(Color.BLACK)
                        .color(Color.WHITE)
                        .current(position)
                        .build();
                /**
                 * 添加图片数据进去
                 */
                zoomPhotoView.addImages(imglist);

                /**
                 * 弹出Dialog
                 */
                zoomPhotoView.show(mView.getSupportFManager(),"tag");
                PhotoAdapter.addOnPageClickListener(new PhotoAdapter.OnPageClickListener() {
                    @Override
                    public void onClick(View v,int position) {

                       PhotoView p = (PhotoView) v;
                        zoomPhotoView.dismiss();
                        // Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();

                        //   Log.e("jhkhshdhashdjkas",position+"");
                    }
                });
            }

            @Override
            public void onPingLunClick(int position) {
                //todo 评论
                mView.jump2Activity(YUE_CommentActivity.class);
            }

            @Override
            public void onDianZanClick(int position) {
                //todo 点赞
                mView.showZan();
            }
        };
        return listener;
    }
}
