package com.yuecheng.yue.ui.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import com.flyco.animation.BounceEnter.BounceRightEnter;
import com.flyco.animation.SlideExit.SlideLeftExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.flyco.dialog.widget.popup.BubblePopup;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.citywheel.CityPickerView;
import com.yuecheng.yue.R;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.ui.activity.YUE_ISetUserInfoView;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.ui.interactor.YUE_ISetUserInfoInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_SetUserInfoInteractorImpl;
import com.yuecheng.yue.util.ColorUtil;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.PhotoUtils;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import com.yuecheng.yue.widget.LoadDialog;
import com.yuecheng.yue.widget.datetimepicker.DatePickerPopWin;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static io.rong.imkit.plugin.image.PictureSelectorActivity.REQUEST_CODE_ASK_PERMISSIONS;

/**
 * Created by administraot on 2017/11/22.
 */

public class YUE_SetUserInfoViewPresenter {
    private String TAG = getClass().getSimpleName();
    private YUE_ISetUserInfoView mView;
    private Context mContext;
    private PhotoUtils mPhotoUtils;
    private Uri mSelectUri;
    private CityConfig mCityConfig;
    private CityPickerView mCityPicker;
    private String mColor;
    private YUE_ISetUserInfoInteractor mInteractor;
    private String mSign;//签名
    private String mNickName;//昵称
    private String mSex;//性别
    private String mBirthday;//生日
    private String mJob;//职业
    private String mCompany;//公司
    private String mEmail;//邮箱
    private String mNowAddress;//现居地
    private String mDetailAddress;//详细地址
    private String mHomeTownAddress;//家乡

    public YUE_SetUserInfoViewPresenter(Context context, YUE_ISetUserInfoView a) {
        super();
        this.mView = a;
        this.mContext = context;
        mColor = ColorUtil.int2Hex(CommonUtils.getColorByAttrId(mContext, R.attr
                .colorPrimary));
        mInteractor = new YUE_SetUserInfoInteractorImpl();
    }

    //  设置头像
    public void setUserIcon() {
//        弹出弹出框。选择图片
        actionSheetDialog();
    }

    private void actionSheetDialog() {
        final String[] stringItems = {"从相册选择", "拍照"};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, null);
        dialog.isTitleShow(false)
                .cancelText(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary))
                .itemTextColor(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary))
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mHomeView.showMessage(stringItems[position]);
                //todo 选择照片
                switch (position) {
                    case 0://相册
                        mPhotoUtils.selectPicture((Activity) mView);
                        break;
                    case 1://相机
                        checkCameraPermission();
                        mPhotoUtils.takePicture((Activity) mView);
                        break;
                }
                dialog.dismiss();
            }
        });
    }

    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest
                    .permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }  else {
                    new AlertDialog.Builder(mContext)
                            .setMessage("您需要在设置里打开相机权限。")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions((Activity) mContext,new String[]{Manifest.permission
                                                    .CAMERA},
                                    REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            })
                            .setNegativeButton("取消", null)
                            .create().show();
                }
                return;
            }
    }

    public void dealResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtils.INTENT_CROP:
            case PhotoUtils.INTENT_TAKE:
            case PhotoUtils.INTENT_SELECT:
                mPhotoUtils.onActivityResult((Activity) mView, requestCode, resultCode, data);
                break;
        }
    }

    //选择照片后回调监听
    public void pickIconListener() {
        mPhotoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onPhotoResult(Uri uri) {
                if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                    mSelectUri = uri;
                    mView.setIcon(mSelectUri);
                    uploadHeaderIcon(mSelectUri.getPath());
//                    YUE_LogUtils.i(TAG,uri.getEncodedPath());// ------>
// /storage/emulated/0/crop_file.jpg
//                    LoadDialog.show(mContext);
//                    request(GET_QI_NIU_TOKEN);
                }
            }

            @Override
            public void onPhotoCancel() {

            }
        });
    }

    private void uploadHeaderIcon(String path) {
        LoadDialog.show(mContext);
        mInteractor.uploadHeaderIcon(YUE_SharedPreferencesUtils.getParam(mContext, YUE_SPsave
                        .YUE_LOGING_PHONE,"")+
                "_imageHeaderIcon",
                path,new
                ICommonInteractorCallback(){
            @Override
            public void loadSuccess(Object object) {
                LoadDialog.dismiss(mContext);
                YUE_LogUtils.d(TAG,object.toString());
            }

            @Override
            public void loadFailed() {

            }

            @Override
            public void loadCompleted() {

            }

            @Override
            public void addDisaposed(Disposable disposable) {

            }
        });
    }

    //设置性别
    public void setUserSex() {
        userSexNormalListDialogNoTitle();
    }

    private void userSexNormalListDialogNoTitle() {
        final ArrayList<DialogMenuItem> mMenuItems = new ArrayList<DialogMenuItem>() {
            {
                add(new DialogMenuItem("男", R.drawable.img_boy));
                add(new DialogMenuItem("女", R.drawable.img_girl));
            }
        };
        final NormalListDialog dialog = new NormalListDialog(mContext, mMenuItems);
        dialog.title("请选择,性别按生理区分")//
                .isTitleShow(true)//
                .titleTextSize_SP(18)//
                .titleBgColor(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary))//
                .itemPressColor(Color.parseColor("#85D3EF"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(15)//
                .cornerRadius(10)//
                .widthScale(0.75f)//
                .showAnim(new BounceRightEnter())
                .dismissAnim(new SlideLeftExit())
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                T.showShort(mContext, mMenuItems.get(position).mOperName);
                YUE_LogUtils.i(this.getClass().getSimpleName(), mMenuItems.get(position).mOperName);
                mView.setSex(mMenuItems.get(position).mOperName);
                dialog.dismiss();
            }
        });
    }

    //设置签名
    public void setUserDisplay() {
        View view = View.inflate(mContext, R.layout.setuserdisplay_pop_layout, null);
        final EditText mUserDesc = (EditText) view.findViewById(R.id.et_setdesc);
        //设置框内字显示旧签名
        mUserDesc.setText(mView.getUserSign());
        //设置光标到末尾
        mUserDesc.setSelection(mView.getUserSign().length());
        BubblePopup bubblePopup = new BubblePopup(mContext, view);
        bubblePopup.anchorView(mView.getWrappedView())//控件
                .gravity(Gravity.BOTTOM)
                .widthScale(1)
                .triangleHeight(10)
                .triangleWidth(20)
                .bubbleColor(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary))
                .dimEnabled(true)
                .showAnim(new BounceRightEnter())
                .dismissAnim(new SlideLeftExit())
                .show();
        bubblePopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //消失则把已经输入的赋值
                mView.setUserSign(mUserDesc.getText().toString());
            }
        });
    }

    //设置生日
    public void setUserBirtyday() {
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(mContext, new
                DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        mView.setBirthday(dateDesc);
                    }
                }).textConfirm("确定") //text of confirm button
                .textCancel("取消") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary)) //color
                // of cancel button
                .colorConfirm(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary))//color
                // of confirm button
                .minYear(1960) //min year in loop
                .maxYear(2017) // max year in loop
                .dateChose("2006-08-01") // date chose when init popwindow
                .build();
        pickerPopWin.showPopWin((Activity) mView);
    }

    //设置地址
    public void setUserAddress() {
        mCityConfig = new CityConfig.Builder(mContext).title("选择地区")
                .titleBackgroundColor(mColor)
                .textSize(14)
                .titleTextColor("#ffffff")
                .textColor(mColor)
                .confirTextColor("#ffffff")
                .cancelTextColor("#ffffff")
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//三级联动
                .showBackground(true)//显示半透明背景
                .visibleItemsCount(5)
                .province("湖北")
                .city("十堰")
                .district("竹溪县")//这三个配合定位使用
                .provinceCyclic(false)
                .cityCyclic(true)
                .districtCyclic(true)
                .itemPadding(16)
                .setCityInfoType(CityConfig.CityInfoType.DETAIL)
                .build();
        mCityPicker = new CityPickerView(mCityConfig);
        mCityPicker.show();
        mCityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                if (district != null)
                    //返回结果
                    YUE_LogUtils.i(mContext.getClass().getSimpleName(),(province.toString() + "-" + city.toString()));
                    mView.setNowAddress(province.toString() + "-" + city.toString() + "-" +
                            district
                            .toString());
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void setUserHomeAddress() {

        mCityConfig = new CityConfig.Builder(mContext).title("选择地区")
                .titleBackgroundColor(mColor)
                .textSize(14)
                .titleTextColor("#ffffff")
                .textColor(mColor)
                .confirTextColor("#ffffff")
                .cancelTextColor("#ffffff")
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//省市三级联动
                .showBackground(true)//显示半透明背景
                .visibleItemsCount(5)
                .province("湖北")
                .city("十堰")
//                .district("竹溪县")//这三个配合定位使用
                .provinceCyclic(false)
                .cityCyclic(true)
//                .districtCyclic(true)
                .itemPadding(16)
                .setCityInfoType(CityConfig.CityInfoType.DETAIL)
                .build();
        mCityPicker = new CityPickerView(mCityConfig);
        mCityPicker.show();
        mCityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                if (district != null)
                    //返回结果
                    YUE_LogUtils.i(mContext.getClass().getSimpleName(),(province.toString() + "-" + city.toString()));
                    mView.setHomeTownAddress(province.toString() + "-" + city.toString());
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void submitUpdateInfo() {

    }
}
