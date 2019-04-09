package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHeadResBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.HomeTypeActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/24.
 * 首页更多分类的adapter
 */

public class HomeMoreTypeAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<HomeHeadResBean.DataBean.TagListBean> mList;

    public HomeMoreTypeAdapter(Context mContext,List<HomeHeadResBean.DataBean.TagListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item__home_more_type_layout, parent, false);
        return new HomeMoreTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HomeMoreTypeViewHolder viewHolder = (HomeMoreTypeViewHolder) holder;
        viewHolder.mTextView.setText(mList.get(position).getTagName());
        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HomeTypeActivity.class);
                //clickIdSearch: 0 是  1 不是
                intent.putExtra("clickIdSearch","1");
                //isEnterFromFollowTag是否是从我关注的标签页面进入列表页的 0：是  1：不是
                intent.putExtra("isEnterFromFollowTag","1");
                intent.putExtra("searchId",mList.get(position).getTagId());
                intent.putExtra("typeName",mList.get(position).getTagName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HomeMoreTypeViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        RelativeLayout rlItem;
        public HomeMoreTypeViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_ihmtl);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item_ihtml);
        }
    }
}
