package com.xlkj.beautifulpicturehouse.module.mine.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyCollectionResBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LocalVideoPlayActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 * 我的收藏视频adapter
 */

public class MyCollectionVideoAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MyCollectionResBean.DataBean.VideoListBean> mList;

    public MyCollectionVideoAdapter(Context mContext, List<MyCollectionResBean.DataBean.VideoListBean> mList) {
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
        viewHolder.tvTitle.setText(mList.get(position).getTypeName());
        viewHolder.tvName.setText("播主："+mList.get(position).getUserName());
        viewHolder.tvId.setText("ID："+mList.get(position).getUserId());
        Glide.with(mContext).load(mList.get(position).getAvatar()).placeholder(R.drawable.su_colour_1).crossFade().into(viewHolder.mImageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final String mp4Url = mList.get(position).getVideo();
                    if (TextUtils.isEmpty(mp4Url)) {
                        Toast.makeText(mContext, "此视频无播放网址哎╮(╯Д╰)╭", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    Intent intent = new Intent(mContext, VideoPlayActivity.class);
                    Intent intent = new Intent(mContext, LocalVideoPlayActivity.class);
                    intent.putExtra("url",mp4Url);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("-->MyCollectionVideoA","MyCollectionVideoAdapter跳转异常");
                }
            }
        });
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
        TextView tvName;
        TextView tvId;
        ImageView mImageView;
        public MyCollectionViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tv_bo_master_id_ivcl);
            tvName = (TextView) itemView.findViewById(R.id.tv_bo_master_name_ivcl);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_imcl);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_imcl);
        }
    }
}
