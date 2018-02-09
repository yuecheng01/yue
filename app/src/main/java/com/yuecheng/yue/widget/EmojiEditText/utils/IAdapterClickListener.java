package com.yuecheng.yue.widget.EmojiEditText.utils;

import android.widget.AdapterView;
import android.widget.EditText;

/**
 * Created by Administrator on 2018/1/23.
 */

public interface IAdapterClickListener extends AdapterView.OnItemClickListener {
    void attachToEditText(EditText editText);
}
