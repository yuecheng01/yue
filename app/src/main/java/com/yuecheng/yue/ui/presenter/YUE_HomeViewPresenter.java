package com.yuecheng.yue.ui.presenter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.flyco.animation.BounceEnter.BounceRightEnter;
import com.flyco.animation.SlideExit.SlideLeftExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseAppManager;
import com.yuecheng.yue.ui.activity.YUE_IHomeView;
import com.yuecheng.yue.ui.activity.impl.YUE_HomeActivity;
import com.yuecheng.yue.ui.adapter.YUE_LeftMenuAdapter;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.ui.interactor.YUE_IHomeViewInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_HomeViewInteractorImpl;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by yuecheng on 2017/10/29.
 */

public class YUE_HomeViewPresenter {
    private YUE_IHomeView mHomeView;
    private YUE_IHomeViewInteractor mInteractor;
    private Context mContext;
    private List<Fragment> mlist;
    private boolean isfirst = true;
    public static String TAG = "HomeActivity";
    private YUE_LeftMenuAdapter mAdapter;


    public YUE_HomeViewPresenter(Context context, YUE_HomeActivity a) {
        this.mContext = context;
        this.mHomeView = a;
        mInteractor = new YUE_HomeViewInteractorImpl();
    }

    public List<Map<String, Object>> getdata() {
        return mInteractor.getdata();
    }

    public YUE_LeftMenuAdapter getAdapter() {
        mAdapter = new YUE_LeftMenuAdapter(mContext);
        return mAdapter;
    }

    public void loadView() {
        mlist = mInteractor.gerFragmentsList();
        if (isfirst) {
            //        首页默认加载第一页，消息页
            mHomeView.loadFragments(mlist, 0);
            isfirst = false;
        }
    }

    public void loadFragments(int index) {
        mHomeView.loadFragments(mlist, index);
    }

    //切换主题功能
    @SuppressLint("WrongConstant")
    public void changeThem() {
        final String[] stringItems = {"默认主题", "热情似火","梦幻紫","乌金黑"};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, null);
        dialog.isTitleShow(false)
                .itemTextColor(CommonUtils.getColorByAttrId(mContext,R.attr.colorPrimary))
                .cancelText(CommonUtils.getColorByAttrId(mContext,R.attr.colorPrimary))
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                T.showShort(mContext, stringItems[position]);
                YUE_SharedPreferencesUtils.setParam(mContext,YUE_SPsave.YUE_THEM,stringItems[position]);
                dialog.dismiss();
                YUE_BaseAppManager.getInstance().clear();
                final Intent themeintent = mHomeView.getIntent1();
                themeintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                mHomeView.jumpToActivity(themeintent);
            }
        });
       /* final ArrayList<DialogMenuItem> mMenuItem = new ArrayList<DialogMenuItem>() {
            {
                add(new DialogMenuItem("恬淡自然的", R.drawable.img_boy));
                add(new DialogMenuItem("热情似火的", R.drawable.img_girl));
            }
        };
        final NormalListDialog mThemdialog = new NormalListDialog(mContext, mMenuItem);
        mThemdialog.title("请选择")//
                .isTitleShow(false)//
                .titleTextSize_SP(18)//
                .titleBgColor(mContext.getResources().getColor(R.color.colorAccent))//
                .itemPressColor(Color.parseColor("#85D3EF"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(15)//
                .cornerRadius(10)//
                .widthScale(0.75f)//
                .showAnim(new BounceRightEnter())
                .dismissAnim(new SlideLeftExit())
                .show();

        mThemdialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                T.showShort(mContext, mMenuItems.get(position).mOperName);
                YUE_LogUtils.i(this.getClass().getSimpleName(), mMenuItem.get(position).mOperName);
                YUE_SharedPreferencesUtils.setParam(mContext,YUE_SPsave.YUE_THEM,mMenuItem.get(position).mOperName);
                mThemdialog.dismiss();
            }
        });*/
    }

    public void upData() {
       if (mAdapter!=null)
           mAdapter.upData(getdata());
    }
}
