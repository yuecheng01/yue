package com.yuecheng.yue.widget.EmojiEditText;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.yuecheng.yue.R;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public class EmojiEditextView extends LinearLayout {
    private ImageView imgEmoji;
    private EditText edit;
    private Button send;

    //表情扩展栏
    private List<Integer> emojiList;
    private int[] emojiIds = new int[107];
    private EmojiAdapter emojiAdapter;
    private GridView gridView;
    private Context mContext;

    public EmojiEditextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EmojiEditextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public EmojiEditextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    public String getTextValue(){
        return edit.getText().toString();
    }
    public void setTextValue(String s){
        edit.setText(s);
    }
    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.emojieditextview_layout, this);
        imgEmoji = (ImageView) findViewById(R.id.img_emoji);
        edit = (EditText) findViewById(R.id.et_content);
        send = (Button) findViewById(R.id.btn_send);
        send.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        gridView = (GridView) findViewById(R.id.emoji_grid);
        //设置表情点击事件
        imgEmoji.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gridView.getVisibility() == View.VISIBLE) {
                    gridView.setVisibility(View.GONE);
                    if (!CommonUtils.isShowSoftInput((Activity) mContext)){
                        CommonUtils.showSoftInput(mContext,imgEmoji);
                    }
                    CommonUtils.showKeyboard((Activity) mContext);
                } else {
                    gridView.setVisibility(View.VISIBLE);
                   if (CommonUtils.isShowSoftInput((Activity) mContext)){
                       CommonUtils.hideSoftInput(mContext,imgEmoji);
                   }
                }
            }
        });
        initEmoji();
        edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gridView.setVisibility(View.GONE);
            }
        });
        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.click();
            }
        });
    }

    private void initEmoji() {
        gridView = (GridView) findViewById(R.id.emoji_grid);
        emojiList = new ArrayList<>();
        for (int i = 0; i < 107; i++) {
            if (i < 10) {
                int id = getResources().getIdentifier(
                        "f00" + i,
                        "drawable", mContext.getPackageName());
                emojiIds[i] = id;
                emojiList.add(id);
                Log.i("LHD", "emoji id: f00" + i);
            } else if (i < 100) {
                int id = getResources().getIdentifier(
                        "f0" + i,
                        "drawable", mContext.getPackageName());
                emojiIds[i] = id;
                emojiList.add(id);
                Log.i("LHD", "emoji id: f0" + i);
            } else {
                int id = getResources().getIdentifier(
                        "f" + i,
                        "drawable", mContext.getPackageName());
                emojiIds[i] = id;
                emojiList.add(id);
                Log.i("LHD", "emoji id: f" + i);
            }
        }
        emojiAdapter = new EmojiAdapter(mContext, emojiList);
        gridView.setAdapter(emojiAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("LHD", "emoji grid: " + i);
                Bitmap bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), emojiIds[i % emojiIds.length]);
                ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
                String str = null;
                if (i < 10) {
                    str = "f00" + i;
                } else if (i < 100) {
                    str = "f0" + i;
                } else {
                    str = "f" + i;
                }
                SpannableString spannableString = new SpannableString(str);
                spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                edit.getText().insert(edit.getSelectionStart(), spannableString);
            }
        });
    }

    public void requestEditTextFocus() {
        edit.requestFocus();
    }

    public View getEditext() {
        return edit;
    }

    public void gridViewGone() {
        gridView.setVisibility(View.GONE);
    }

    public interface OnSendClickListener{
        void click();
    }
    private OnSendClickListener mListener;
    public void  addOnSendClickListener(OnSendClickListener listener){
        this.mListener = listener;
    }
}
