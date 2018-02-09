package com.yuecheng.yue.widget.EmojiEditText.utils;


import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

/**
 * Created by Administrator on 2018/1/23.
 */

public class AdapterOnclickListener implements IAdapterClickListener {
    private int emotion_map_type;
    private EditText editText;

    public AdapterOnclickListener(int emotion_map_type) {
        super();
        this.emotion_map_type = emotion_map_type;
    }

    @Override
    public void attachToEditText(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

}
