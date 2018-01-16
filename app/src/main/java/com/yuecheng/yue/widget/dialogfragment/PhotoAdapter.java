package com.yuecheng.yue.widget.dialogfragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.yuecheng.yue.util.ImageLoaderUtils;
import com.yuecheng.yue.widget.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.assist.FailReason;
import io.rong.imageloader.core.listener.ImageLoadingListener;
import io.rong.imageloader.core.listener.ImageLoadingProgressListener;

/**
 * Created by administraot on 2017/12/1.
 */

public class PhotoAdapter extends PagerAdapter {
    /**
     * Fragment虽然不是上下文对象但是他有个方法getContext()对象
     * 我们使用Fragment是因为前面说到使用Glide加载图片时，Glide会根据传入的
     * Fragment或者Activity自行关联它们的生命周期，达到优化内存的效果。
     */
    private Fragment mContext;
    /**
     * 图片地址集合
     */
    private List<String> mList;
    /**
     * PhotoView集合，有多少个图片就创建多少个PhotoView。
     */
    private List<PhotoView> mPhoto = new ArrayList<>();
    private ProgressBar mProgressBar;

    /**
     * 构造方法，初始化适配器
     *
     * @param mContext
     * @param mList
     */
    public PhotoAdapter(Fragment mContext, List<String> mList,ProgressBar progressBar) {
        this.mContext = mContext;
        this.mList = mList;
        this.mProgressBar = progressBar;
        // initPhoto();

    }

    public interface OnPageClickListener {
        void onClick(View view, int position);
    }

    public static OnPageClickListener mOnPageClickListener;

    public static void addOnPageClickListener(OnPageClickListener listener) {
        mOnPageClickListener = listener;
    }

    private void initPhoto() {
        List<PhotoView> photos = new ArrayList<>();
        PhotoView v;
        //这个LayoutParams可以理解为java代码中的布局参数，相当于XML文件中的属性。
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        //设置宽度填充满父布局
        params.width = ViewPager.LayoutParams.MATCH_PARENT;
        //高度为自身大小
        params.height = ViewPager.LayoutParams.WRAP_CONTENT;
//        一次性创建需要的PhotoView
        for (int i = mPhoto.size(); i < mList.size(); i++) {
            v = new PhotoView(mContext.getContext());
            //将布局参数设置进PhotoView
            v.setLayoutParams(params);
            v.enable();
//            添加到集合中去
            photos.add(v);
            //使用Glide加载图片v
            ImageLoader.getInstance().displayImage(mList.get(i), v);
        }
        mPhoto.addAll(photos);
        photos.clear();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public void notifyDataSetChanged() {
//        initPhoto();
        super.notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        PhotoView p = new PhotoView(mContext.getContext());
        // 设置图片显示
        p.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p.enable();

        ImageLoaderUtils.displayImageUrl(mList.get(position), p,
                new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        // TODO Auto-generated method stub
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {
                        // TODO Auto-generated method stub
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri,
                                                  View view, Bitmap loadedImage) {
                        // TODO Auto-generated method stub
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri,
                                                   View view) {
                        // TODO Auto-generated method stub
                        mProgressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        //todo 在这里更新 ProgressBar的进度信息
                    }
                });
        // 然后将加载了图片的photoView添加到viewpager中，并且设置宽高
        container.addView(p, ViewPager.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPageClickListener.onClick(view, position);
            }
        });
        return p;

       /* container.addView(mPhoto.get(position));

        mPhoto.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPageClickListener.onClick(position);
            }
        });
        Log.i("TAG", "instantiateItem: "+mPhoto.get(position).getScaleType());
        return mPhoto.get(position);*/
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(mPhoto.get(position));
        container.removeView((View) object);
    }
}
