package com.yuecheng.yue.ui.activity.impl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivity;
import com.yuecheng.yue.ui.activity.YUE_IHomeView;
import com.yuecheng.yue.ui.presenter.YUE_HomeViewPresenter;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.widget.YUE_CircleImageView;
import com.yuecheng.yue.widget.YUE_NoScrollListView;
import com.yuecheng.yue.widget.dialogfragment.QRcodeView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


public class YUE_HomeActivity extends YUE_BaseActivity implements YUE_IHomeView, View
        .OnClickListener {
    private RadioButton mChat;
    private RadioButton mContact;
    private RadioButton mMoment;
    private RadioButton mMine;
    private YUE_HomeViewPresenter mYUE_homeViewPresenter;
    private YUE_NoScrollListView mYUE_dampListView;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mSearchLayout;
    //    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private LinearLayout mSettings;
    private LinearLayout mChangeThem;
    private int REQUEST_CODE = 1;
    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        mYUE_homeViewPresenter = new YUE_HomeViewPresenter(this, this);
        initToolBar();
        initView();
        setViewEvents();


        mYUE_homeViewPresenter.getdata();
        mYUE_dampListView.setAdapter(mYUE_homeViewPresenter.getAdapter());

        clearButtonStatues();
        mChat.setChecked(true);
        mYUE_homeViewPresenter.loadView();
        mYUE_homeViewPresenter.connectRong();

        //设置左上角的图标响应
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolBar.setNavigationIcon(R.mipmap.haha); // API21以上才支持此方法修改返回按钮样式,此处我采取自己加一个不再用系统的返回按钮
//        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, 0, 0) {
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//        };
//        mActionBarDrawerToggle.syncState();
//        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle); //设置侧滑监听

    }

    private void initToolBar() {
        Toolbar mToolBar = findView(R.id.toolbar);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_titlebar, null);

        TextView mToolBarTitle = (TextView) view.findViewById(R.id.toolbartitle);
        YUE_CircleImageView mBackIcon = (YUE_CircleImageView) view.findViewById(R.id
                .toolbar_ic_back);

        mToolBarTitle.setText(getResources().getString(R.string.app_name));// TODO: 2017/11/5
        mBackIcon.setImageDrawable(getResources().getDrawable(R.mipmap.yue));// TODO: 2017/11/5
        mBackIcon.setOnClickListener(this);
        mToolBar.addView(view);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);//不使用系统的title
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //不使能app bar的导航功能
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_startchat:
                        ShowMessage("发起聊天");
//                        TODO
                        break;
                    case R.id.action_joinfriend:
//                        ShowMessage("添加好友");
//                        TODO
                        startActivity(YUE_SearchFriendActivity.class);
                        break;
                    case R.id.action_creategroup:
                        ShowMessage("创建群组");
//                        TODO
                        break;
                    case R.id.action_2weima:
//                        QRcodeView  qrcode = QRcodeView.getInstance();
//                        qrcode.show(getSupportFragmentManager(),"tag");
                        startActivityForResult(new Intent(YUE_HomeActivity.this,
                                YUE_QRcodeActivity.class), REQUEST_CODE);
                        break;
                    case R.id.action_forhelp:
                        ShowMessage("寻求帮助?");
//                        TODO
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_right, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //设置输入框字体颜色（坑啊，mmp弄了好久）
        mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id
                .search_src_text);
        mSearchAutoComplete.setTextColor(getResources().getColor(R.color.white));
        mSearchAutoComplete.setHintTextColor(getResources().getColor(R.color.black));
        //修改searview光标样式，避免和主题色相同而看不见，
        CommonUtils.setCursorIcon(mSearchView);
        //设置搜索框直接展开显示。左侧有放大镜(在搜索框中) 右侧有叉叉 可以关闭搜索框
        mSearchView.setIconified(true);
        //设置搜索框直接展开显示。左侧有放大镜(在搜索框外) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
//        mSearchView.setIconifiedByDefault(true);
        //设置搜索框直接展开显示。左侧无放大镜(在搜索框中) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
//        mSearchView.onActionViewExpanded();
        //设置输入框提示语
        mSearchView.setQueryHint("请输入关键字");
        //设置最大宽度
        mSearchView.setMaxWidth(CommonUtils.getScreenWidth(YUE_HomeActivity.this));
        //设置是否显示搜索框展开时的提交按钮
        mSearchView.setSubmitButtonEnabled(false);
        searViewListener();
        return super.onCreateOptionsMenu(menu);
    }

    // 让菜单同时显示图标和文字
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",
                            Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    private void searViewListener() {
        //搜索框展开时后面叉叉按钮的点击事件
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mSearchLayout.setVisibility(View.GONE);
                return false;
            }
        });
        //搜索图标按钮(打开搜索框的按钮)的点击事件
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchLayout.setVisibility(View.VISIBLE);
            }
        });
        //搜索框文字变化监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //todo
                showMessage("TextSubmit----->" + s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //todo
                showMessage("TextChange----->" + s);
                return false;
            }
        });
    }

    private void setViewEvents() {
        mChat.setOnClickListener(this);
        mContact.setOnClickListener(this);
        mMoment.setOnClickListener(this);
        mMine.setOnClickListener(this);

        mSettings.setOnClickListener(this);
        mChangeThem.setOnClickListener(this);
    }

    private void initView() {
        mChat = findView(R.id.chat);
        mContact = findView(R.id.contact);
        mMoment = findView(R.id.moment);
        mMine = findView(R.id.mine);
        mYUE_dampListView = findView(R.id.lv_setting);

        mDrawerLayout = findView(R.id.drawer);


        mSettings = findView(R.id.settings);
        mChangeThem = findView(R.id.changethem);
        mSearchLayout = findView(R.id.searchlayout);
    }


    public void clearButtonStatues() {
        mChat.setChecked(false);
        mContact.setChecked(false);
        mMoment.setChecked(false);
        mMine.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        clearButtonStatues();
        switch (v.getId()) {
            case R.id.chat:
                mChat.setChecked(true);
                mYUE_homeViewPresenter.loadFragments(0);
                break;
            case R.id.contact:
                mContact.setChecked(true);
                mYUE_homeViewPresenter.loadFragments(1);
                break;
            case R.id.moment:
                mMoment.setChecked(true);
                mYUE_homeViewPresenter.loadFragments(2);
                break;
            case R.id.mine:
                mMine.setChecked(true);
                mYUE_homeViewPresenter.loadFragments(3);
                break;
            case R.id.toolbar_ic_back:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.settings:
                startActivity(YUE_SettingsActivity.class);
                break;
            case R.id.usericon:

                break;
            case R.id.changethem:
                mYUE_homeViewPresenter.changeThem();
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }//如果搜索框还是打开的
            else if (mSearchAutoComplete.isShown()) {
                try {
                    Method method = mSearchView.getClass().getDeclaredMethod("onCloseClicked");
                    method.setAccessible(true);
                    method.invoke(mSearchView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                moveTaskToBack(false);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void loadFragments(List<Fragment> fragments, int index) {
        if (null != fragments && !fragments.isEmpty()) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ftr = fm.beginTransaction();
            ftr.replace(R.id.content, fragments.get(index));
            ftr.commit();
        }
    }

    @Override
    public void jump2Activity(Class a) {
        startActivity(a);
    }

    @Override
    public void showMessage(String s) {
        super.ShowMessage(s);
    }

    @Override
    public Intent getIntent1() {
        return getIntent();
    }

    @Override
    public void jumpToActivity(Intent themeintent) {
        startActivity(themeintent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(YUE_HomeActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
