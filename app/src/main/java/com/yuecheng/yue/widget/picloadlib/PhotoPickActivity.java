package com.yuecheng.yue.widget.picloadlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import com.yuecheng.yue.widget.picloadlib.util.Bimp;
import com.yuecheng.yue.widget.picloadlib.util.FileUtils;
import com.yuecheng.yue.widget.picloadlib.util.ImageItem;
import com.yuecheng.yue.widget.picloadlib.util.PublicWay;
import com.yuecheng.yue.widget.picloadlib.view.AlbumActivity;
import com.yuecheng.yue.widget.picloadlib.view.GalleryActivity;


public class PhotoPickActivity extends AppCompatActivity {
    private GridView mGv;
    private GridAdapter mAdapter;
    public static Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBitmap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
        PublicWay.activityList.add(this);
//   此部分主要是适配我的主题切换，这个库我没有集成项目的baseactivity，所以此处重复添加此代码了。
        String mThem = (String) YUE_SharedPreferencesUtils.getParam(this, YUE_SPsave.YUE_THEM,
                "默认主题");

        Window window = getWindow();
        switch (mThem) {
            case "默认主题":
                setTheme(R.style.AppThemeDefault);
                break;
            case "热情似火":
                setTheme(R.style.AppThemeRed);
                break;
            case "梦幻紫":
                setTheme(R.style.AppThemePurple);
                break;
            case "乌金黑":
                setTheme(R.style.AppThemeHardwareblack);
                break;
            default:
                if (android.os.Build.VERSION.SDK_INT >= 21)
                    window.setStatusBarColor(CommonUtils.getColorByAttrId(PhotoPickActivity.this,R.attr.colorPrimary));
                break;
        }
//   此部分主要是适配我的主题切换，这个库我没有集成项目的baseactivity，所以此处重复添加此代码了。
        setContentView(R.layout.activity_photopick_layout);
        initToolBar();
        initViews();
        initDataEvents();
    }

    private void initDataEvents() {
        mGv.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    private void initViews() {
        mGv = (GridView) findViewById(R.id.noScrollgridview);
        mAdapter = new GridAdapter(this);
        mAdapter.update();
        mGv.setAdapter(mAdapter);
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == Bimp.tempSelectBitmap.size()) {
                    actionSheetDialogNoTitle();
                } else {
                    Intent intent = new Intent(PhotoPickActivity.this,
                            GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", i);
                    startActivity(intent);
                }
            }
        });
    }

    //    弹出框
    private void actionSheetDialogNoTitle() {
        final String[] stringItems = {"图库", "相机"};
        final ActionSheetDialog dialog = new ActionSheetDialog(PhotoPickActivity.this, stringItems, null);
        dialog.isTitleShow(false)
                .cancelText(CommonUtils.getColorByAttrId(PhotoPickActivity.this,R.attr.colorPrimary))
                .itemTextColor(CommonUtils.getColorByAttrId(PhotoPickActivity.this, R.attr.colorPrimary))
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(PhotoPickActivity.this, AlbumActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_translate_in, R.anim
                                .activity_translate_out);
                        break;
                    case 1:
                        photo();
                        break;
                }
                dialog.dismiss();
            }
        });
    }

    private void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    private void initToolBar() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle("选择图片");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolBar.setOnMenuItemClickListener(onMenuItemClick);
    }

    Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_send:
                    //todo 右上角发布按钮事件

                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return true;
    }

    private static final int TAKE_PICTURE = 0x000001;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Bimp.tempSelectBitmap.add(takePhoto);
                }
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;
        private Context mContext;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            this.mContext = context;
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (null != Bimp.tempSelectBitmap && Bimp.tempSelectBitmap.size() == PublicWay.num) {
                return PublicWay.num;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        mContext.getResources(), R.drawable.icon_addpic_unfocused));
                if (position == PublicWay.num) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("----------", String.valueOf(PublicWay.activityList.size()));
            for (int i = 0; i < PublicWay.activityList.size(); i++) {
                if (null != PublicWay.activityList.get(i)) {
                    PublicWay.activityList.get(i).finish();
                }
            }
            PublicWay.activityList.clear();
//            Bimp.tempSelectBitmap.clear();
        }
        return true;
    }

    @Override
    protected void onRestart() {
        mAdapter.update();
        super.onRestart();
    }
}
