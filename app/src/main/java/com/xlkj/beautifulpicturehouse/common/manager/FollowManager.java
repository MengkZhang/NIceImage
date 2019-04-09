package com.xlkj.beautifulpicturehouse.common.manager;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xlkj.beautifulpicturehouse.common.http.HostType;
import com.xlkj.beautifulpicturehouse.common.http.RetrofitSingleton;
import com.xlkj.beautifulpicturehouse.module.home.apiservice.HomeApiService;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowReqBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.FollowResBean;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/1/20.
 * 点击关注或者取消关注的单例模式管理者
 */

public class FollowManager {

    private FollowManager() {

    }

    public static FollowManager single = new FollowManager();

    public static FollowManager getInstance() {
        return single;
    }

    /**
     * 点击关注或取消关注
      * @param isFollowState： 本地是否关注的状态，默认为false 若已经关注 则为true
     * @param rlFollow：       点击关注的控件
     * @param tvFollowTxt：    显示关注或已关注的文本
     * @param uId：            用户id
     * @param followId：       关注的波主id
     * @param type：           关注类型0人 1标签
     * @param mFollowCallBack：关注回调
     */
    public void setFollow(boolean isFollowState, final RelativeLayout rlFollow, final TextView tvFollowTxt, String uId, String followId, int type, final FollowCallBack mFollowCallBack) {
        //followId	string	被关注人Id
        //userId	string	用户Id
        //isFollow	int	0取消 1关注
        //type	int	关注类型0人 1标签
        FollowReqBean bean = new FollowReqBean();
        if (!isFollowState) {
            //点击就关注
            int isFollow = 1;
            bean.setIsFollow(isFollow + "");
        } else {
            //点击就取消关注
            int isFollow = 0;
            bean.setIsFollow(isFollow + "");
        }
        bean.setUserId(uId);
        bean.setAction("follow");
        bean.setType(type+"");
        bean.setFollowId(followId);
        //网络请求
        Retrofit retrofit = RetrofitSingleton.getInstance(HostType.MTW_INFO).getRetrofit();
        HomeApiService apiService = retrofit.create(HomeApiService.class);
        Call<FollowResBean> call = apiService.requestFollow(bean.getAction(), bean.getFollowId(), bean.getUserId(), bean.getIsFollow(), bean.getType());
        call.enqueue(new Callback<FollowResBean>() {
            @Override
            public void onResponse(Call<FollowResBean> call, Response<FollowResBean> response) {
                if (response != null && response.body() != null) {
                    mFollowCallBack.onSuccess(response.body());
                } else {
                    mFollowCallBack.onFailed("请求数据为空");
                }
            }

            @Override
            public void onFailure(Call<FollowResBean> call, Throwable t) {
                mFollowCallBack.onFailed("请求失败："+t.getMessage());
            }
        });
    }


    public interface FollowCallBack {
        void onSuccess(FollowResBean bean);
        void onFailed(String errorMsg);
    }
}
