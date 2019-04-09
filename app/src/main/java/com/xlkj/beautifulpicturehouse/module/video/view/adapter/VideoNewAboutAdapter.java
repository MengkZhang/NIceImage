package com.xlkj.beautifulpicturehouse.module.video.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 * 相关视频adapter
 */

public class VideoNewAboutAdapter extends RecyclerView.Adapter {

    public static final String TAG = "-->VideoNewAboutAdapter";
    private Context mContext;
    private List<VideoDetailResBean.DataBean.VideoListBean> mList;


    public VideoNewAboutAdapter(Context mContext, List<VideoDetailResBean.DataBean.VideoListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_video_about_layout, parent, false);
        return new VideoAboutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        VideoAboutViewHolder viewHolder = (VideoAboutViewHolder) holder;
        Glide.with(mContext).load(mList.get(position).getImageUrl()).placeholder(R.drawable.su_colour_3).crossFade().into(viewHolder.mImageView);
        //到详情页
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickVideoAboutListener != null) {
                    mOnItemClickVideoAboutListener.onClickVideoAbout(view,position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        //如果大于10条 就返回10条
        if (mList != null && mList.size() != 0) {
            if (mList.size() <= 10) {
                return mList.size();
            } else {
                return 10;
            }
        } else {
            return 0;
        }
    }

    class VideoAboutViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        public VideoAboutViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_ival);
        }
    }

    public interface OnItemClickVideoAboutListener {
        void onClickVideoAbout(View view, int position);
    }

    private OnItemClickVideoAboutListener mOnItemClickVideoAboutListener;

    public void setOnItemClickVideoAboutListener(OnItemClickVideoAboutListener onItemClickVideoAboutListener) {
        mOnItemClickVideoAboutListener = onItemClickVideoAboutListener;
    }
}
