package com.yuecheng.yue.ui.interactor.impl;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.yuecheng.yue.http.ApiServicesManager;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.ui.interactor.YUE_ISetUserInfoInteractor;
import com.yuecheng.yue.util.YUE_LogUtils;
import java.io.File;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/1/23.
 */

public class YUE_SetUserInfoInteractorImpl implements YUE_ISetUserInfoInteractor {
    /**
     * 上传头像,
     * @param imageHeadericon 保存的头像名称
     * @param path  上传的路径
     * @param l 结果回调
     */
    @Override
    public void uploadHeaderIcon(String imageHeadericon,String path, final
    ICommonInteractorCallback l) {
        File file = new File(path);
        RequestBody requestFile = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("imageHeaderIcon", imageHeadericon)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        ApiServicesManager.getInstence().getYueapi()
                .uploadHeader(requestFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        l.addDisaposed(d);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String str = responseBody.toString();
                        YUE_LogUtils.d("jieguo:",str);
                    }

                    @Override
                    public void onError(Throwable e) {
                        l.loadFailed();
                    }

                    @Override
                    public void onComplete() {
                        l.loadCompleted();
                    }
                });
    }
}
