package com.yuecheng.yue.ui.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.yuecheng.yue.R;
import com.yuecheng.yue.util.CodeUtils;
import com.yuecheng.yue.util.CommonUtils;

/**
 * 图形验证码的dialog
 *
 * Created by Administrator on 2018/1/23.
 */

public class PicCodeConfirDialog extends BaseDialog{
    private Context mContext;
    private ImageView mPicCode;
    private EditText mInputCode;
    private TextView mBtnConfir;
    public PicCodeConfirDialog(Context context) {
        super(context);
        this.mContext =context;
    }

    public void reSetPicCode(Bitmap bitmap){
        mPicCode.setImageBitmap(bitmap);
    }
    @Override
    public View onCreateView() {
        widthScale(0.85f);
        //填充弹窗布局
        View inflate = View.inflate(mContext, R.layout.dialog_piccodeconfig_layout, null);
        //显示图形验证码
        mPicCode = (ImageView) inflate.findViewById(R.id.code_img);
        //输入框
        mInputCode = (EditText) inflate.findViewById(R.id.code_et);
        //确认按钮
        mBtnConfir = (TextView)inflate.findViewById(R.id.code_confir);
        //设置图片
        mPicCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
//        mBtnConfir.setTextColor(CommonUtils.getColorByAttrId(mContext,R.attr.colorPrimary));
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        mPicCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onPicCodeClick(mInputCode.getText().toString());
            }
        });
        mBtnConfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onConfirClick();
            }
        });
    }
    private OnPicCodeClickListener mClickListener;
    public void addOnPicCodeClickListener(OnPicCodeClickListener codeClickListener){
        this.mClickListener = codeClickListener;
    }

    public String getInput() {
        return mInputCode.getText().toString();
    }

    public void setInput(String s) {
        mInputCode.setText(s);
    }

    public interface OnPicCodeClickListener{
        void onPicCodeClick(String inputStr);
        void onConfirClick();
    }
}
