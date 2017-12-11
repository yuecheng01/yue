package com.yuecheng.yue.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuecheng on 2017/10/29.
 */

public class YUE_BaseAppManager {
    private static YUE_BaseAppManager instance = null;
    private static List<Activity> mActivities = new LinkedList<Activity>();

    private YUE_BaseAppManager() {

    }

    /*
    * 单例
    * */
    public static YUE_BaseAppManager getInstance() {
        if (null == instance) {
            synchronized (YUE_BaseAppManager.class) {
                if (null == instance) {
                    instance = new YUE_BaseAppManager();
                }
            }
        }
        return instance;
    }

    public int size() {
        return mActivities.size();
    }

    public synchronized Activity getForwardActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }

    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    public synchronized void clear() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }
    }

    public synchronized void clearToTop() {
        for (int i = mActivities.size() - 2; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }
    }
}
