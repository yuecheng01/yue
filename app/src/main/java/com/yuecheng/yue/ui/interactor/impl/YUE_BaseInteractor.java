package com.yuecheng.yue.ui.interactor.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

/**
 * Created by yuecheng on 2017/11/6.
 */

public  class YUE_BaseInteractor {
    public YUE_BaseInteractor() {
    }
private static String TAG = "bean--->";
    /**
     * 一个参数
     *
     * @param key
     * @param param
     * @param what
     * @param handler
     */
    public  void getData(String url,
                        String key,
                        String param,
                        final int what, final String tag,final Handler handler) {
        OkHttpUtils.post().url(url).tag(tag)
                .addParams(key, param)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        YUE_LogUtils.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, String s) {
                        YUE_LogUtils.i(TAG, s);
                        if (null != s) {
                            Message msg = new Message();
                            msg.what = what;
                            Bundle bundle = new Bundle();
                            bundle.putString(tag, s);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }
                });
    }

    /**
     * 两个参数
     *
     * @param key1
     * @param key2
     * @param param1
     * @param param2
     * @param what
     * @param handler
     */
    public  void getData(String url,
                        String key1, String key2,
                        String param1, String param2,
                        final int what,final String tag, final Handler handler) {
        OkHttpUtils.post().url(url).tag(tag)
                .addParams(key1, param1)
                .addParams(key2, param2)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        YUE_LogUtils.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, String s) {
                        YUE_LogUtils.i(TAG, s);
                        if (null != s) {
                            Message msg = new Message();
                            msg.what = what;
                            Bundle bundle = new Bundle();
                            bundle.putString(tag, s);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }
                });
    }


    /**
     * 三个参数
     *
     * @param key1
     * @param key2
     * @param param1
     * @param param2
     * @param what
     * @param handler
     */
    public  void getData(String url,
                        String key1, String key2, String key3,
                        String param1, String param2, String param3,
                        final int what,final String tag, final Handler handler) {
        OkHttpUtils.post().url(url).tag(tag)
                .addParams(key1, param1)
                .addParams(key2, param2)
                .addParams(key3, param3)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        YUE_LogUtils.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, String s) {
                        YUE_LogUtils.i(TAG, s);
                        if (null != s) {
                            Message msg = new Message();
                            msg.what = what;
                            Bundle bundle = new Bundle();
                            bundle.putString(tag, s);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }
                });
    }

    /**
     * 四个参数
     *
     * @param key1
     * @param key2
     * @param key3
     * @param key4
     * @param param1
     * @param param2
     * @param param3
     * @param param4
     * @param what
     * @param handler
     */
    public  void getData(String url,
                        String key1, String key2, String key3, String key4,
                        String param1, String param2, String param3, String param4,
                        final int what,final String tag, final Handler handler) {
        OkHttpUtils.post().url(url).tag(tag)
                .addParams(key1, param1)
                .addParams(key2, param2)
                .addParams(key3, param3)
                .addParams(key4, param4)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        YUE_LogUtils.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, String s) {
                        YUE_LogUtils.i(TAG, s);
                        if (null != s) {
                            Message msg = new Message();
                            msg.what = what;
                            Bundle bundle = new Bundle();
                            bundle.putString(tag, s);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }
                });
    }

    /**
     * 五个参数
     *
     * @param key1
     * @param key2
     * @param key3
     * @param key4
     * @param key5
     * @param param1
     * @param param2
     * @param param3
     * @param param4
     * @param param5
     * @param what
     * @param handler
     */
    public   void getData(String url,
                        String key1, String key2, String key3, String key4, String key5,
                        String param1, String param2, String param3, String param4, String param5,
                        final int what,final String tag, final Handler handler) {
        OkHttpUtils.post().url(url).tag(tag)
                .addParams(key1, param1)
                .addParams(key2, param2)
                .addParams(key3, param3)
                .addParams(key4, param4)
                .addParams(key5, param5)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        YUE_LogUtils.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, String s) {
                        YUE_LogUtils.i(TAG, s);
                        if (null != s) {
                            Message msg = new Message();
                            msg.what = what;
                            Bundle bundle = new Bundle();
                            bundle.putString(tag, s);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }
                });
    }

    public  void getData(String url,
                        String key1, String key2, String key3, String key4, String key5, String key6,
                        String param1, String param2, String param3, String param4, String param5, String param6,
                        final int what,final String tag, final Handler handler) {
        OkHttpUtils.post().url(url).tag(tag)
                .addParams(key1, param1)
                .addParams(key2, param2)
                .addParams(key3, param3)
                .addParams(key4, param4)
                .addParams(key5, param5)
                .addParams(key6, param6)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        YUE_LogUtils.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, String s) {
                        YUE_LogUtils.i(TAG, s);
                        if (null != s) {
                            Message msg = new Message();
                            msg.what = what;
                            Bundle bundle = new Bundle();
                            bundle.putString(tag, s);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }
                });
    }
}

