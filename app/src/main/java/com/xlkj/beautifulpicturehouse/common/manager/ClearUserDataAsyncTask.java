package com.xlkj.beautifulpicturehouse.common.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.xlkj.beautifulpicturehouse.common.util.FileUtils;
import com.xlkj.beautifulpicturehouse.common.util.PreferencesUtils;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LoginActivity;


/**
 *
 * @author 1
 * @date 2017/9/30
 */
public class ClearUserDataAsyncTask extends AsyncTask<Void,Void,Boolean> {

    private Context mContext;
    private ClearUserInfoCallBack callBack;

    public ClearUserDataAsyncTask(Context mContext, ClearUserInfoCallBack callBack) {
        super();
        this.mContext = mContext;
        this.callBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            callBack.success(aBoolean);
        } else {
            callBack.error("删除用户信息失败");
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        //清理用户昵称头像等
        FileUtils.clearCacheDataToFile(mContext,"user_info");
        //清理用户UID
//        PreferencesUtils.clearString(mContext,"uid");
        PreferencesUtils.putString(mContext,"uid","");
        //清理用户VIP信息
        FileUtils.clearCacheDataToFile(mContext,"vip_info");

//        PreferencesUtils.putString(mContext,"uid","");
//        PreferencesUtils.putString(mContext,"token","");
//        PreferencesUtils.putBoolean(mContext,"isLogin",false);
        return true;
    }

    public interface ClearUserInfoCallBack {

        void success(boolean b);

        void error(String msg);
    }
}
