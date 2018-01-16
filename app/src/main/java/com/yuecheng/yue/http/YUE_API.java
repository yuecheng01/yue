package com.yuecheng.yue.http;


import com.yuecheng.yue.ui.bean.YUE_FriendsListBean;
import com.yuecheng.yue.ui.bean.YUE_LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yuecheng on 2017/11/5.
 */

public interface YUE_API {

    @FormUrlEncoded
    @POST("yue/imapi.php/login")
    Observable<YUE_LoginBean> getYUE_LoginBean(@Field("userId") String userId, @Field("password")
                                               String password);
    @FormUrlEncoded
    @POST("yue/imapi.php/getFriendsList")
    Observable<YUE_FriendsListBean>getFriendsList(@Field("userId1") String userId1, @Field("statues")
                                                String statues);
}
