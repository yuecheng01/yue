package com.yuecheng.yue.ui.interactor.impl;

import android.content.Context;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_CircleMomentsBean;
import com.yuecheng.yue.ui.interactor.YUE_IFriendCircleViewInteractor;
import com.yuecheng.yue.util.YUE_AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administraot on 2017/12/2.
 */

public class YUE_FriendCircleViewInteractorImpl implements YUE_IFriendCircleViewInteractor {

    public List<YUE_CircleMomentsBean> getData() {
        List<YUE_CircleMomentsBean> list = new ArrayList<>();
        YUE_CircleMomentsBean y = new YUE_CircleMomentsBean();
        List<String> imgs = new ArrayList<String>() {
            {
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2IoRodnnI8KJjSszgXXc8ApXa_!!2178875332.jpg");
            }
        };
        y.setImgcontent(imgs);
        y.setNickname("悦城");
        y.setTextcontent(YUE_AppUtils.getAppContext().getResources().getString(R.string.userheart));
        y.setUsericon("http://www.jingomall.cn/upload/sysconfigs/2017-06/59536b10569af.png");

        YUE_CircleMomentsBean y2 = new YUE_CircleMomentsBean();
        List<String> imgs2 = new ArrayList<String>() {
            {
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2IoRodnnI8KJjSszgXXc8ApXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2t6VmddbJ8KJjy1zjXXaqapXa_!!2178875332.jpg");
            }
        };
        y2.setImgcontent(imgs2);
        y2.setNickname("悦城");
        y2.setTextcontent(YUE_AppUtils.getAppContext().getResources().getString(R.string
                .userheart));
        y2.setUsericon("http://www.jingomall.cn/upload/sysconfigs/2017-06/59536b10569af.png");

        YUE_CircleMomentsBean y3 = new YUE_CircleMomentsBean();
        List<String> imgs3 = new ArrayList<String>() {
            {
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2IoRodnnI8KJjSszgXXc8ApXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2t6VmddbJ8KJjy1zjXXaqapXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2rrVxdcnI8KJjSsziXXb8QpXa_!!2178875332.jpg");
            }
        };
        y3.setImgcontent(imgs3);
        y3.setNickname("悦城");
        y3.setTextcontent(YUE_AppUtils.getAppContext().getResources().getString(R.string
                .userheart));
        y3.setUsericon("http://www.jingomall.cn/upload/sysconfigs/2017-06/59536b10569af.png");

        YUE_CircleMomentsBean y4 = new YUE_CircleMomentsBean();
        List<String> imgs4 = new ArrayList<String>() {
            {
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2IoRodnnI8KJjSszgXXc8ApXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2t6VmddbJ8KJjy1zjXXaqapXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2rrVxdcnI8KJjSsziXXb8QpXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2rh4mdnTI8KJjSsphXXcFppXa_!!2178875332.jpg");
            }
        };
        y4.setImgcontent(imgs4);
        y4.setNickname("悦城");
        y4.setTextcontent(YUE_AppUtils.getAppContext().getResources().getString(R.string
                .userheart));
        y4.setUsericon("http://www.jingomall.cn/upload/sysconfigs/2017-06/59536b10569af.png");

        YUE_CircleMomentsBean y5 = new YUE_CircleMomentsBean();
        List<String> imgs5 = new ArrayList<String>() {
            {
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2IoRodnnI8KJjSszgXXc8ApXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2t6VmddbJ8KJjy1zjXXaqapXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2rrVxdcnI8KJjSsziXXb8QpXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2rh4mdnTI8KJjSsphXXcFppXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2z8pqdh6I8KJjSszfXXaZVXXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2lWpLdb_I8KJjy1XaXXbsxpXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2g5ljdlDH8KJjSszcXXbDTFXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2p.NodnnI8KJjSszgXXc8ApXa_!!2178875332.jpg");
                add("http://lly.jingomall.com.cn/upload/goodsContent/2017-12/TB2SVlLdf6H8KJjSspmXXb2WXXa_!!2178875332.jpg");
            }
        };
        y5.setImgcontent(imgs5);
        y5.setNickname("悦城");
        y5.setTextcontent(YUE_AppUtils.getAppContext().getResources().getString(R.string
                .userheart));
        y5.setUsericon("http://www.jingomall.cn/upload/sysconfigs/2017-06/59536b10569af.png");

        list.add(y);
        list.add(y2);
        list.add(y3);
        list.add(y4);
        list.add(y5);
        return list;
    }
}
