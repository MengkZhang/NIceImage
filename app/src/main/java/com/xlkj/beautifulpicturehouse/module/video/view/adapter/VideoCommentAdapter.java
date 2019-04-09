package com.xlkj.beautifulpicturehouse.module.video.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xlkj.beautifulpicturehouse.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class VideoCommentAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public VideoCommentAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_video_comment_layout, parent, false);
        return new VideoCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
    class VideoCommentViewHolder extends RecyclerView.ViewHolder {

        public VideoCommentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
