package com.yuecheng.yue.ui.dialog;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.yuecheng.yue.R;

/**
 * Created by Administrator on 2018/1/25.
 */

public class AddFriendDialog extends BaseDialog {
    private Context mContext;
    private TextView mTitle;
    private EditText mMessageEditText;
    private TextView mBtnSend;

    public AddFriendDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView() {
        widthScale(0.8f);
        //填充弹窗布局
        View inflate = View.inflate(mContext, R.layout.dialog_addfriend_layout, null);
        mTitle = (TextView) inflate.findViewById(R.id.title);
        mMessageEditText = (EditText) inflate.findViewById(R.id.message_et);
        mBtnSend = (TextView) inflate.findViewById(R.id.send_bt);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListerer.onBtnClick(mMessageEditText.getText().toString().trim());
            }
        });
    }
    public void setTittle(SpannableString s){
        mTitle.setText(s);
    }
    public void addBtnClickListerer(BtnClickListerer listerer){
        this.mListerer = listerer;
    }
    private BtnClickListerer mListerer;
    public interface BtnClickListerer{
        void onBtnClick(String messageContent);
    }
}
