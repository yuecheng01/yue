package com.yuecheng.yue.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 全部展开的GridView，解决与ScrollView的冲突
 * 由于这两款控件都自带滚动条，嵌套便会出问题，即GridView或ListView会显示不全。
 *
 * @author yuyh.
 * @date 16/4/10.
 */
public class YUE_NotScrollGridView extends GridView {
    public YUE_NotScrollGridView(Context context) {
        super(context);
    }

    public YUE_NotScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
