
package com.yuecheng.yue.db;

import com.yuecheng.yue.util.YUE_AppUtils;


/**
 * Created by Administrator on 2017/12/29.
 */


public class DBManager {

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static DBManager mInstance; //单例

    private DBManager() {
        if (mInstance == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new
                    DaoMaster.DevOpenHelper(YUE_AppUtils.getAppContext(), "yue-db", null);
            //此处为自己需要处理的表
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static DBManager getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {//保证异步处理安全操作
                if (mInstance == null) {
                    mInstance = new DBManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}

