package com.yuecheng.yue.http;


import com.yuecheng.yue.ui.bean.AddFridendBean;
import com.yuecheng.yue.ui.bean.IsRegisterOrNotBean;
import com.yuecheng.yue.ui.bean.MessageCodeBean;
import com.yuecheng.yue.ui.bean.RegisterBean;
import com.yuecheng.yue.ui.bean.ResetPasswordBean;
import com.yuecheng.yue.ui.bean.SearchedFriendBean;
import com.yuecheng.yue.ui.bean.YUE_FriendsListBean;
import com.yuecheng.yue.ui.bean.YUE_LoginBean;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yuecheng on 2017/11/5.
 */

public interface YUE_API {

    /**
     * 注册接口
     *
     * @param userId
     * @param nickname
     * @param password
     * @param passwordfir
     * @return
     */
    @FormUrlEncoded
    @POST("yue/imapi.php/register")
    Observable<RegisterBean> getRegisterBean(
            @Field("userId") String userId,
            @Field("nickname") String nickname,
            @Field("password") String password,
            @Field("passwordfir") String passwordfir
    );

    /**
     * 短信验证接口
     *
     * @param mMessageCode
     * @return
     */
    @FormUrlEncoded
    @POST("yue/imapi.php/getMessageCode")
    Observable<MessageCodeBean> getMessageCodeBean(
            @Field("messagecode") String mMessageCode
    );

    /**
     * 判断是否已经注册
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("yue/imapi.php/querySomeOneInfo")
    Observable<SearchedFriendBean> getSearchedFriendBean(
            @Field("userId") String userId
    );

    /**
     * 根据手机号查询某人昵称和头像
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("yue/imapi.php/isRegisterOrNot")
    Observable<IsRegisterOrNotBean> getIsRegisterOrNotBean(
            @Field("userId") String userId
    );

    /**
     * 登录接口
     *
     * @param userId
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("yue/imapi.php/login")
    Observable<YUE_LoginBean> getYUE_LoginBean(
            @Field("userId") String userId,
            @Field("password") String password
    );

    /**
     * 重置密码接口
     *
     * @param userId
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("yue/imapi.php/resetPassword")
    Observable<ResetPasswordBean> getResetPasswordBean(
            @Field("userId") String userId,
            @Field("password") String password,
            @Field("passwordfir") String passwordfir
    );

    /**
     * 获取好友列表接口
     *
     * @param userId
     * @param statues
     * @return
     */
    @FormUrlEncoded
    @POST("yue/imapi.php/getFriendsList")
    Observable<YUE_FriendsListBean> getFriendsList(
            @Field("userId") String userId,
            @Field("statues") String statues
    );

    /**
     * 请求添加好友
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("yue/imapi.php/addSomeoneFridend")
    Observable<AddFridendBean> getAddSomeoneFridendBean(
            @FieldMap Map<String, String> map
    );

    /**
     * 上传头像接口
     *
     * @param body
     * @return
     */
    @POST("yue/imapi.php/uploadheader")
    Observable<ResponseBody> uploadHeader(
            @Body RequestBody body
    );

}
