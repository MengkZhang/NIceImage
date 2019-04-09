package com.xlkj.beautifulpicturehouse.common.manager.videoScanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LocalPicDetailActivity;

import java.util.List;

/**
 * Created by 1 on 2017/10/9.
 * 本地图片adapter
 */
public class LocalPicAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<VideoInfo> data;

    public LocalPicAdapter(Context context, List<VideoInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_layout_local_video, parent, false);
        return new LocalVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LocalVideoViewHolder viewHolder = (LocalVideoViewHolder) holder;
        try {
            Glide.with(context).load(data.get(position).getPath()).placeholder(R.drawable.xiao_yanse_caolv_ono).crossFade().into(viewHolder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置数据 方便到详情页取而用之
                BeautyContent.setVideoInfo(data);
                jump2detail(position);
                // TODO: 2018/1/4 跳转图片详情页 可以滑动viewpager
            }
        });

    }

    private void jump2detail(int localPosition) {
        Intent intent = new Intent(context, LocalPicDetailActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        intent.putExtra("position",localPosition);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalVideoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView ivPlayIcon;
        public LocalVideoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_local_item);
            ivPlayIcon = (ImageView) itemView.findViewById(R.id.iv_play_illv);
            ivPlayIcon.setVisibility(View.GONE);
        }
    }

}
