package com.yuecheng.yue.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by yuecheng on 2017/11/3.
 * 阻尼listView
 */

public class YUE_DampListView extends ListView implements Runnable{
    private float mLastDownY = 0f;
    private int mDistance = 0;
    private int mStep = 10;
    private boolean mPositive = false;

    public YUE_DampListView(Context context) {
        super(context);
    }

    public YUE_DampListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YUE_DampListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public YUE_DampListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mLastDownY == 0f && mDistance == 0) {
                    mLastDownY = ev.getY();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mLastDownY != 0f) {
                    mDistance = (int) (mLastDownY - ev.getY());
                    if ((mDistance < 0 && getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0) || (mDistance > 0 && getLastVisiblePosition() == getCount() - 1)) {
                        mDistance /= 2;
                        scrollTo(0, mDistance);
                        return true;
                    }
                }
                mDistance = 0;
                break;
            case MotionEvent.ACTION_UP:
                if (mDistance != 0) {
                    mStep = 1;
                    mPositive = (mDistance >= 0);
                    this.post(this);
                    return true;
                }
                mLastDownY = 0f;
                mDistance = 0;
                break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void run() {
        mDistance += mDistance > 0 ? -mStep : mStep;
        scrollTo(0, mDistance);
        if ((mPositive && mDistance <= 0) || (!mPositive && mDistance >= 0)) {
            scrollTo(0, 0);
            mDistance = 0;
            mLastDownY = 0f;
            return;
        }
        mStep += 1;
        this.postDelayed(this, 10);
    }
}
