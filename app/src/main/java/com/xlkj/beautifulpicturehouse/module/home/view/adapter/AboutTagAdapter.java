package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutTagResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 * 相关标签adapter
 */

public class AboutTagAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<AboutTagResBean.DataBean.TagBean> mList;

    public AboutTagAdapter(Context mContext,List<AboutTagResBean.DataBean.TagBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) (mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.item_about_tag_layout, parent, false);
        return new AboutTagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AboutTagViewHolder viewHolder = (AboutTagViewHolder) holder;
        viewHolder.mTextView.setText(mList.get(position).getTagName());
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() != 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    class AboutTagViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        public AboutTagViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_iatl);
        }
    }
}
