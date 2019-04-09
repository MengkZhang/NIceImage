package com.xlkj.beautifulpicturehouse.module.video.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.util.GlideImgManager;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoHeadResBean;
import com.xlkj.beautifulpicturehouse.module.video.view.ui.activity.VideoTypeActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 * 视频首页标签分类的adapter
 */

public class VideoTypeAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<VideoHeadResBean.DataBean.TagListBean> mList;

    public VideoTypeAdapter(Context mContext, List<VideoHeadResBean.DataBean.TagListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_video_type_layout, parent, false);
        return new VideoTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final VideoTypeViewHolder viewHolder = (VideoTypeViewHolder) holder;
        GlideImgManager.glideLoader(mContext, mList.get(position).getImageUrl(), R.mipmap.ic_launcher, R.mipmap.ic_launcher, viewHolder.mImageView, 1);
        viewHolder.mTextView.setText(mList.get(position).getTagName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(mContext, VideoTypeActivity.class);
                    //如果是标签页的跳转 这里需要传标签的id
                    intent.putExtra("typeName",mList.get(position).getTagId());
                    //0 点击搜索中的更多传的是关键字  1 其他搜索（视频标签到列表）传的是id
                    intent.putExtra("byId","1");
                    //标题
                    intent.putExtra("title",mList.get(position).getTagName());
                    //Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("-->VideoTypeAdapter","VideoTypeAdapter跳转异常");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class VideoTypeViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView;
        public VideoTypeViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_ivtl);
            mTextView = (TextView) itemView.findViewById(R.id.tv_name_ivtl);
        }
    }
 }
