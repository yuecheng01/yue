package com.yuecheng.yue.widget;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * InputFilter[] filters = {new InputFilterEx(30)};
 * mEditText.setFilters(filters);
 * Created by yuecheng on 2017/12/26.
 */

public class InputFilterEx implements InputFilter {
    //****************************************************************
    /*类型	        单个占用字符数	    单类型最多输入数量          **
    小写字母/数字	        1	                    30              需
    大写字母	            2	                    15              求→ 即，不管单类型输入还是组合
    汉字	                2	                    15              **   输入，总字符数不能超过30个
    Emoj表情	            2	                    15              **   字符。
    特殊符号	            2	                    15              **/
    //****************************************************************
    int MAX_EN;// 最大长度

    public InputFilterEx(int mAX_EN) {
        super();
        MAX_EN = mAX_EN;
    }

    /**
     *
     * @param source: 将要插入的字符串，来自键盘输入、粘贴
     * @param start: source的起始位置，为0（暂时没有发现其它值的情况）
     * @param end: source的长度
     * @param dest: EditText中已经存在的字符串
     * @param dstart: 插入点的位置
     * @param dend: 插入点的结束位置，一般情况下等于dstart；如果选中一段字符串（这段字符串将会被替换），dstart的值就插入点的结束位置
     * @return
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                               int dend) {
        int destCount = dest.toString().length() + getChineseCount(dest.toString()) +
                getAZCount(dest.toString());// + (dest.toString().length() - getPointCount(dest.toString()))
        Log.d("destCount--->",destCount+"");
        int sourceCount = source.toString().length() + getChineseCount(source.toString()) +
                getAZCount(source.toString());// + (source.toString().length() - getPointCount(source.toString()))
        String input = "";
        int count = 0;
        int i = 0;
        if (destCount + sourceCount > MAX_EN) {
            if (destCount < MAX_EN) {
                while (!(destCount + count >= MAX_EN)) {
                    ++i;
                    input = source.subSequence(0, i).toString();
                    count = input.toString().length()
                            + getChineseCount(input.toString())+getAZCount(input.toString());//+ (input.toString().length() - getPointCount(input.toString()))
                    if (destCount + count > MAX_EN) {
                        --i;
                    }
                }
                return i == 0 ? "" : source.subSequence(0, i).toString();
            }
            return "";
        } else {
            return source;
        }
    }

    String regExCh = "[\\u4e00-\\u9fa5]"; // unicode编码，获取汉字字符数

    private int getChineseCount(String str) {
        int count = 0;
        Pattern p = Pattern.compile(regExCh);
        Matcher m = p.matcher(str);
        while (m.find()) {
            count++;
        }
        return count;
    }

    String regExAZ = "[A-Z]"; // 判断是否为大写字母数

    private int getAZCount(String str) {
        int count = 0;
        Pattern p = Pattern.compile(regExAZ);
        Matcher m = p.matcher(str);
        while (m.find()) {
            count++;
        }
        return count;
    }

    String regExPo = "((?=[\\\\x21-\\\\x7e]+)[^A-Za-z0-9])"; // 获取非特殊符号字符数

    private int getPointCount(String str) {
        int count = 0;
        Pattern p = Pattern.compile(regExPo);
        Matcher m = p.matcher(str);
        while (m.find()) {
            count++;
        }
        return count;
    }
}

