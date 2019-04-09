package com.xlkj.beautifulpicturehouse.module.mine.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.manager.videoScanner.VideoInfo;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionResBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LocalPicDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 * 我的收藏图片adapter
 */

public class MyCollectionAdapter extends RecyclerView.Adapter {

    public static final String TAG = "-->MyCollectionAdapter";
    private Context mContext;
    private List<MyCollectionResBean.DataBean.ImageListBean> mList;

    public MyCollectionAdapter(Context mContext,List<MyCollectionResBean.DataBean.ImageListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_my_collection_layout, parent, false);
        return new MyCollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyCollectionViewHolder viewHolder = (MyCollectionViewHolder) holder;
        if (mList != null && mList.size() != 0) {
            viewHolder.tvTitle.setText(mList.get(position).getTypeName());
            Glide.with(mContext).load(mList.get(position).getImageUrl()).placeholder(R.drawable.su_colour_1).crossFade().into(viewHolder.mImageView);
            final List<VideoInfo> imgData = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                VideoInfo videoInfo = new VideoInfo();
                String imageUrl = mList.get(i).getImageUrl();
                String typeName = mList.get(i).getTypeName();
                videoInfo.setPath(imageUrl);
                videoInfo.setDisplayName(typeName);
                imgData.add(videoInfo);
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //设置数据 方便到详情页取而用之
                    BeautyContent.setVideoInfo(imgData);
                    jump2detail(position);
                }
            });
        } else {
            Log.e(TAG,"onBindViewHolder数据为空");
        }
    }

    private void jump2detail(int localPosition) {
        try {
            Intent intent = new Intent(mContext, LocalPicDetailActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            intent.putExtra("position",localPosition);
            //Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"jump2detail跳转异常");
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() != 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    class MyCollectionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView mImageView;
        public MyCollectionViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_imcl);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_imcl);
            itemView.findViewById(R.id.ll_bo_master_lmcl).setVisibility(View.GONE);
        }
    }
}
