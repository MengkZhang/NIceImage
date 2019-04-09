package com.xlkj.beautifulpicturehouse.module.mine.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.manager.videoScanner.VideoInfo;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFootStayResBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LocalPicDetailActivity;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LocalVideoPlayActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 * 我的足迹(历史足迹)adapter
 */

public class MyFootStayHistoryAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MyFootStayResBean.DataBean.HistoryListBean> mList;

    public MyFootStayHistoryAdapter(Context mContext, List<MyFootStayResBean.DataBean.HistoryListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_my_foot_stay_layout, parent, false);
        return new MyFootStayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyFootStayViewHolder viewHolder = (MyFootStayViewHolder) holder;
        viewHolder.tvTitle.setText(mList.get(position).getTypeName());
        Glide.with(mContext).load(mList.get(position).getImageUrl()).placeholder(R.drawable.su_colour_1).crossFade().into(viewHolder.ivIcon);
        int type = mList.get(position).getType();
        //资源类型0图片 1视频
        if (type == 0) {
            final List<VideoInfo> imgData = new ArrayList<>();
            viewHolder.ivPicVideo.setBackgroundResource(R.drawable.biaoqian_meitu);
            viewHolder.llBoMaster.setVisibility(View.GONE);
            //组装详情页面需要的数据
            if (mList != null && mList.size() != 0) {
                for (int i = 0; i < mList.size(); i++) {
                    VideoInfo videoInfo = new VideoInfo();
                    String imageUrl = mList.get(i).getImageUrl();
                    String typeName = mList.get(i).getTypeName();
                    videoInfo.setPath(imageUrl);
                    videoInfo.setDisplayName(typeName);
                    imgData.add(videoInfo);
                }

            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //设置数据 方便到详情页取而用之
                    BeautyContent.setVideoInfo(imgData);
                    Intent intent = new Intent(mContext, LocalPicDetailActivity.class);
                    intent.putExtra("position",position);
                    mContext.startActivity(intent);
                }
            });
        } else {
            viewHolder.ivPicVideo.setBackgroundResource(R.drawable.biaoqian_shiping);
            viewHolder.llBoMaster.setVisibility(View.VISIBLE);
            viewHolder.tvBoName.setText("播主："+mList.get(position).getUserName());
            viewHolder.tvBoId.setText("ID："+mList.get(position).getUserId());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String mp4Url = mList.get(position).getVideo();
                    if (TextUtils.isEmpty(mp4Url)) {
                        Toast.makeText(mContext, "此视频无播放网址哎╮(╯Д╰)╭", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(mContext, LocalVideoPlayActivity.class);
                    intent.putExtra("url",mp4Url);
                    mContext.startActivity(intent);
                }
            });
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

    class MyFootStayViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPicVideo;
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvBoName;
        TextView tvBoId;
        //波主相关
        LinearLayout llBoMaster;
        public MyFootStayViewHolder(View itemView) {
            super(itemView);
            ivPicVideo = (ImageView) itemView.findViewById(R.id.iv_pic_video_imfsl);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_imfsl);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_imfsl);
            tvBoName = (TextView) itemView.findViewById(R.id.tv_bo_master_imfsl);
            tvBoId = (TextView) itemView.findViewById(R.id.tv_id_imfsl);
            llBoMaster = (LinearLayout) itemView.findViewById(R.id.ll_bo_master_imfsl);
        }
    }
}
