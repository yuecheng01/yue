/*
    ShengDao Android Client, CommonUtils
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.yuecheng.yue.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.yuecheng.yue.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;


/**
 * [公共工具类，与Android API相关的辅助类]
 *
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-30
 *
 **/
public class CommonUtils {

    @SuppressWarnings("unused")
    private static final String tag = CommonUtils.class.getSimpleName();

    /** 网络类型 **/
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;


    /**
     * 根据key获取config.properties里面的值
     * @param context
     * @param key
     * @return
     */
    public static String getProperty(Context context, String key) {
        try {
            Properties props = new Properties();
            InputStream input = context.getAssets().open("config.properties");
            if (input != null) {
                props.load(input);
                return props.getProperty(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 验证手机号码是否错误
     *
     * @param mobiles 手机号码
     * @return 手机号码是否是否正确（true---正确，false---错误的）
     */
    public static boolean isMobileNO(String mobiles) {
                    /*
                        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
                        联通：130、131、132、152、155、156、185、186
                        电信：133、153、180、189、（1349卫通）
                        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
                    */
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 验证电子邮件是否错误
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        //电子邮件
        //  String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        //  String regex = "^([a-z0-9A-Z]+[-|_|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$";
        String regex = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

        return Pattern.matches(regex, email);
    }
    /**
     * 检测网络是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase(Locale.getDefault()).equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }


    /**
     * 判断SDCard是否存在,并可写
     *
     * @return
     */
    public static boolean checkSDCard() {
        String  flag = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(flag);
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕显示信息对象
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm;
    }

    /**
     * dp转pixel
     */
    public static float dpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /**
     * pixel转dp
     */
    public static float pixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 生成二维码图片
     * @param str
     * @return
     */
//	public static Bitmap create2DCode(String str) {
//		try {
//			// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
//			BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
//			int width = matrix.getWidth();
//			int height = matrix.getHeight();
//
//			// 二维矩阵转为一维像素数组,也就是一直横着排了
//			int[] pixels = new int[width * height];
//			for (int y = 0; y < height; y++) {
//				for (int x = 0; x < width; x++) {
//					if (matrix.get(x, y)) {
//						pixels[y * width + x] = 0xff000000;
//					}
//				}
//			}
//
//			Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//			// 通过像素数组生成bitmap,具体参考api
//			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//			return bitmap;
//		} catch (WriterException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}


    /**
     * 短信分享
     *
     * @param mContext
     * @param smstext 短信分享内容
     * @return
     */
    public static Boolean sendSms(Context mContext, String smstext) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent mIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        mIntent.putExtra("sms_body", smstext);
        mContext.startActivity(mIntent);
        return null;
    }

    /**
     * 邮件分享
     *
     * @param mContext
     * @param title 邮件的标题
     * @param text 邮件的内容
     * @return
     */
    public static void sendMail(Context mContext, String title, String text) {
        // 调用系统发邮件
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // 设置文本格式
        emailIntent.setType("text/plain");
        // 设置对方邮件地址
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
        // 设置标题内容
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        // 设置邮件文本内容
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
        mContext.startActivity(Intent.createChooser(emailIntent, "Choose Email Client"));
    }

    /**
     * 隐藏软键盘
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * 显示软键盘
     * @param activity
     */
    public static void showKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!imm.isActive()) {
                imm.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * 是否横屏
     * @param context
     * @return true为横屏，false为竖屏
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否是平板
     * 这个方法是从 Google I/O App for Android 的源码里找来的，非常准确。
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @ColorInt
    public static int getColorByAttrId(Context context, @AttrRes int attrIdForColor) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrIdForColor, typedValue, true);
        return typedValue.data;
    }
    //设置searchview的光标样式
    public static void setCursorIcon(SearchView searchview) {
        try {

            Class cls = Class.forName("android.support.v7.widget.SearchView");
            Field field = cls.getDeclaredField("mSearchSrcTextView");
            field.setAccessible(true);
            TextView tv  = (TextView) field.get(searchview);


            Class[] clses = cls.getDeclaredClasses();
            for(Class cls_ : clses)
            {
                Log.e("TAG", cls_.toString());
                if(cls_.toString().endsWith("android.support.v7.widget.SearchView$SearchAutoComplete"))
                {
                    Class targetCls = cls_.getSuperclass().getSuperclass().getSuperclass().getSuperclass();
                    Field cuosorIconField = targetCls.getDeclaredField("mCursorDrawableRes");
                    cuosorIconField.setAccessible(true);
                    cuosorIconField.set(tv, R.drawable.color_cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "ERROR setCursorIcon = " + e.toString());
        }
    }
    /**
     * 获取手机联系人
     */
    public static List<Map<String, String>> getAllContactInfo(Context context) {
        SystemClock.sleep(3000);
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        // 1.获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        // 2.获取内容提供者的地址:com.android.contacts
        // raw_contacts表的地址 :raw_contacts
        // view_data表的地址 : data
        // 3.生成查询地址
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        // 4.查询操作,先查询raw_contacts,查询contact_id
        // projection : 查询的字段
        Cursor cursor = resolver.query(raw_uri, new String[] { "contact_id" },
                null, null, null);
        // 5.解析cursor
        while (cursor.moveToNext()) {
            // 6.获取查询的数据
            String contact_id = cursor.getString(0);
            // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
            // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
            // 判断contact_id是否为空
            if (!TextUtils.isEmpty(contact_id)) {//null   ""
                // 7.根据contact_id查询view_data表中的数据
                // selection : 查询条件
                // selectionArgs :查询条件的参数
                // sortOrder : 排序
                // 空指针: 1.null.方法 2.参数为null
                Cursor c = resolver.query(date_uri, new String[] { "data1",
                                "mimetype" }, "raw_contact_id=?",
                        new String[] { contact_id }, null);
                Map<String, String> map = new HashMap<String, String>();
                // 8.解析c
                while (c.moveToNext()) {
                    // 9.获取数据
                    String data1 = c.getString(0);
                    String mimetype = c.getString(1);
                    // 10.根据类型去判断获取的data1数据并保存
                    if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                        // 电话
                        map.put("phone", data1);
                    } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                        // 姓名
                        map.put("name", data1);
                    }
                }
                // 11.添加到集合中数据
                list.add(map);
                // 12.关闭cursor
                c.close();
            }
        }
        // 12.关闭cursor
        cursor.close();
        return list;
    }
    public static void showSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public static boolean isShowSoftInput(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //获取状态信息
        return imm.isActive();//true 打开
    }

    interface IKeyBoardVisibleListener{
        void onSoftKeyBoardVisible(boolean visible , int windowBottom);
    }


    private static boolean isFirst = true;
    public interface OnGetSoftHeightListener {
        void onShowed(int height);
    }
    public interface OnSoftKeyWordShowListener {
        void hasShow(boolean isShow);
    }

    /** * 获取软键盘的高度 * *
     @param rootView *
     @param listener
     */
    public static void getSoftKeyboardHeight(final View rootView, final OnGetSoftHeightListener listener) {
        final ViewTreeObserver.OnGlobalLayoutListener layoutListener
                = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isFirst) {
                    final Rect rect = new Rect();
                    rootView.getWindowVisibleDisplayFrame(rect);
                    final int screenHeight = rootView.getRootView().getHeight();
                    final int heightDifference = screenHeight - rect.bottom;
//设置一个阀值来判断软键盘是否弹出
                    boolean visible = heightDifference > screenHeight / 3;
                    if (visible) {
                        isFirst = false;
                        if (listener != null) {
                            listener.onShowed(heightDifference);
                        }
                        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        };
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }



    /** * 判断软键盘是否弹出
     * * @param rootView
     * @param listener
     *备注：在不用的时候记得移除OnGlobalLayoutListener
     */
    public static ViewTreeObserver.OnGlobalLayoutListener doMonitorSoftKeyWord(final View rootView, final OnSoftKeyWordShowListener listener) {
        final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);
                final int screenHeight = rootView.getRootView().getHeight();
                Log.e("TAG",rect.bottom+"#"+screenHeight);
                final int heightDifference = screenHeight - rect.bottom;
                boolean visible = heightDifference > screenHeight / 3;
                if (listener != null)
                    listener.hasShow(visible);
            }
        };
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
        return layoutListener;
    }
}
