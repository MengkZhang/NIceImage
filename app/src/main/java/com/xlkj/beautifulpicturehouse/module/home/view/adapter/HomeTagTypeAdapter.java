package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.SearchActivity;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHeadResBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.HomeMoreTypeActivity;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.HomeTypeActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 * 首页标签类型的adapter
 */

public class HomeTagTypeAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<HomeHeadResBean.DataBean.TagListBean> mList;

    public HomeTagTypeAdapter(Context mContext, List<HomeHeadResBean.DataBean.TagListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_home_tag_type_layout, parent, false);
        return new TagTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TagTypeViewHolder viewHolder = (TagTypeViewHolder) holder;

        if (mList != null && mList.size() != 0) {

            if (position != mList.size()) {
                //不是最后一个栏目
                viewHolder.mTextView.setText(mList.get(position).getTagName());
                Glide.with(mContext).load(mList.get(position).getImageUrl()).placeholder(R.mipmap.ic_launcher).crossFade().into(viewHolder.mImageView);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, HomeTypeActivity.class);
                        //clickIdSearch: 0 是  1 不是
                        intent.putExtra("clickIdSearch","1");
                        //isEnterFromFollowTag是否是从我关注的标签页面进入列表页的 0：是  1：不是
                        intent.putExtra("isEnterFromFollowTag","1");
                        intent.putExtra("searchId",mList.get(position).getTagId());
                        intent.putExtra("typeName",mList.get(position).getTagName());
                        //Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
            } else {
                //是最后一个栏目 更多
                viewHolder.mTextView.setText("更多");
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, HomeMoreTypeActivity.class);
                        //Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
//        return mList.size();
        return mList.size() + 1;
    }

    class TagTypeViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        ImageView mImageView;

        public TagTypeViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_type_ihttl);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_ihttl);
        }
    }
}
