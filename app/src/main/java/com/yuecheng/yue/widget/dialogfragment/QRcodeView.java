package com.yuecheng.yue.widget.dialogfragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yuecheng.yue.R;

import java.util.List;

/**
 * 本页面是一个弹出二维码的
 * Created by administraot on 2017/12/1.
 */

public class QRcodeView extends DialogFragment {
    private boolean isLight=false;
    private int QRFRAGMENT_ID=111;
    @SuppressLint("ValidFragment")
     /* 私有构造方法，防止被实例化 */
    private QRcodeView() {
        super();
    }
    // 持有私有静态实例，防止被引用，此处赋值为null，
    private static QRcodeView instance = null;
    /* 静态工程方法，创建实例 */
    public static QRcodeView getInstance() {
        if (instance == null) {
            instance = new QRcodeView();
        }
        return instance;
    }
    //      重写DialogFragment的onCreateView方法
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        //去掉标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //帧布局用于存放ViewPager和下标指示器(TextView)
        FrameLayout flay = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        下标显示
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btnLight = new Button(getContext());
        param.gravity = Gravity.CENTER | Gravity.BOTTOM;
        param.setMargins(50, 50, 50, 50);
        btnLight.setLayoutParams(param);

        btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLight){
                    CodeUtils.isLightEnable(true);
                    isLight = true;
                }else {
                    CodeUtils.isLightEnable(false);
                    isLight = false;
                }

            }
        });
        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        //加入flay布局
        FrameLayout flayQRview = new FrameLayout(getContext());
        flayQRview.setLayoutParams(params);
        flayQRview.setId(QRFRAGMENT_ID);
        flay.addView(flayQRview);
        getActivity().getSupportFragmentManager().beginTransaction().replace(flayQRview.getId(),captureFragment);
        flay.addView(btnLight);
        return flay;
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
//            YUE_QRcodeActivity.this.setResult(RESULT_OK, resultIntent);
//            YUE_QRcodeActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
//            YUE_QRcodeActivity.this.setResult(RESULT_OK, resultIntent);
//            YUE_QRcodeActivity.this.finish();
        }
    };
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        //把Dialog设置为全屏
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private static final String TAG = "QRcodeView";

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
    }

}
