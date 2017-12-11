package com.yuecheng.yue.util;

/**
 * Created by administraot on 2017/12/2.
 */

import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.yuecheng.yue.R;

import java.io.File;

import io.rong.imageloader.cache.disc.DiskCache;
import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.assist.ImageScaleType;
import io.rong.imageloader.core.listener.ImageLoadingListener;
import io.rong.imageloader.core.listener.ImageLoadingProgressListener;

/**
 * imageLoader加载图片的工具类
 * 在使用imageLoader之前必须现在applicition类中，设置imageLoader
 *
 * @author Administrator
 *         <p>
 *         图片如果需要缓存到硬盘的的话，注意需要添加硬盘读写权限
 */
public class ImageLoaderUtils {

    static public DisplayImageOptions.Builder options_builder = new DisplayImageOptions.Builder()
            .resetViewBeforeLoading(true) // 在加载之前设置一个视图
            .cacheInMemory(true) // 图片是否缓存到内存
            .cacheOnDisk(true) // 图片是否缓存到硬盘
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // 设置图片显示样式
            .showImageOnLoading(R.drawable.plugin_camera_no_pictures)//在加载图片的过程中，显示的图片
            .bitmapConfig(Config.RGB_565) // bitmap的配置
            .handler(new Handler()); // 创建一个handler异步显示图片

    /**
     * 设置全局图片选项设置
     *
     * @param aOnloadingImageId
     * @param aEmptyUriImageId
     * @param aLoadFailImageId
     */
    static public void setGlobalImageOptions(int aOnloadingImageId,
                                             int aEmptyUriImageId, int aLoadFailImageId) {
        //例如
//      .showImageOnLoading(R.drawable.ic_stub) // resource
//      .showImageForEmptyUri(R.drawable.ic_empty) // resource
//      .showImageOnFail(R.drawable.ic_error) // resource
        options_builder.showImageOnLoading(aOnloadingImageId)//图片加载过程中显示的图片
                .showImageForEmptyUri(aEmptyUriImageId)//图片为空的时候显示什么
                .showImageOnFail(aLoadFailImageId);//图片加载失败之后显示什么

    }

    /**
     * 根据url加载图片，并且给view展示，并且可以添加监听。
     *
     * @param aUrl      图片的额加载地址
     * @param aView     要显示图片的view
     * @param aListener 加载图片的监听
     */

    static public void displayImageUrl(String aUrl, ImageView aView, ImageLoadingListener
            aListener, ImageLoadingProgressListener listener) {
        Log.i("TAG", "displayImageUrl aUrl = " + aUrl);

        if (aListener == null && listener == null) {
            //如果没添加监听的话，调用这个方法
            ImageLoader.getInstance().displayImage(aUrl, aView, options_builder.build());
        } else {
            //添加了监听，调用这个方法
            ImageLoader.getInstance().displayImage(aUrl, aView, options_builder.build(), aListener);
        }
    }

    /**
     * 根据url确定是否需要缓存
     *
     * @param url
     * @return
     */
    static public boolean isImageUrlCached(String url) {
        if (url == null)
            return false;
        try {
            DiskCache cache = ImageLoader.getInstance().getDiskCache();
            if (cache == null)
                return false;
            File file = cache.get(url);
            if (file != null && file.exists()) {//如果文件存在，并且文件的缓存的目标存在的话，设置缓存文件
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    static public void cacheImageUrl(String url, ImageLoadingListener aListener) {
        ImageLoader.getInstance().loadImage(url, options_builder.build(), aListener);
    }

    /**
     * 检查文件路径是否存在，根据得到的路径，创建一个文件出来
     *
     * @param aPath
     */
    static public void checkPath(String aPath) {
        new File(aPath).getParentFile().mkdirs();
    }
}