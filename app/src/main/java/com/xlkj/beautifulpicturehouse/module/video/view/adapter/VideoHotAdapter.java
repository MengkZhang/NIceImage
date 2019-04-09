package com.xlkj.beautifulpicturehouse.module.video.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.view.ui.activity.VideoDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/23.
 */

public class VideoHotAdapter extends RecyclerView.Adapter {
    public static final String TAG = "-->>VideoHotAdapter";
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOT = 2;
    private Context mContext;
    private List<VideoListResBean.DataBean.TypeListBean> mList;

    public VideoHotAdapter(Context mContext,List<VideoListResBean.DataBean.TypeListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    /**
     * 当条目是头部的时候-header占据三个单元格
     * 我的footer竟然作为一个cell出现在了界面上，这完全不是我想要的效果啊！ 冷静下来想想，肯定会有解决方法的吧。这时候我就该引入一个不太常用的方法了
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_FOOT ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType ==TYPE_ITEM) {
            //条目
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_video_hot_layout, parent, false);
            return new VideoHotViewHolder(view);
        } else {
            //底部
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
            return new FootViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) ==TYPE_ITEM) {
            //条目
            VideoHotViewHolder viewHolder = (VideoHotViewHolder) holder;
            try {
//                GlideUtils.loadDefault((mList.get(position).cover_url), viewHolder.mImageView, false, DecodeFormat.PREFER_ARGB_8888, DiskCacheStrategy.RESULT);
                Glide.with(mContext).load(mList.get(position).getImageUrl()).placeholder(R.drawable.da_yanse_cuilvideo).crossFade().into(viewHolder.mImageView);
                if (mList.get(position).getTypeName() != null || mList.get(position).getTypeName().equals("")|| mList.get(position).getTypeName().equals(" ")) {
                    viewHolder.tvTitle.setText(mList.get(position).getTypeName());
                } else {
                    viewHolder.tvTitle.setText("宅男们都震精啦~");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG,"onBindViewHolder加载数据异常");
            }
            //条目点击事件
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
//                    if (android.os.Build.VERSION.SDK_INT > 25) {
//                        //Android8.0
                        Intent intent = new Intent(mContext, VideoDetailActivity.class);
                        VideoListResBean.DataBean.TypeListBean typeListBean = mList.get(position);
                        intent.putExtra("dataBean",typeListBean);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
//                    } else {
                        //Android8.0以下
//                        Intent intent = new Intent(mContext, VideoDetailFollowActivity.class);
////                    Intent intent = new Intent(mContext, VideoDetailActivity.class);
//                        VideoListResBean.DataBean.TypeListBean typeListBean = mList.get(position);
//                        intent.putExtra("dataBean",typeListBean);
//                        //Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        mContext.startActivity(intent);
//                    }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG,"viewHolder跳转异常");
                    }

                }
            });
        } else {
            //底部
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("加载中......");
//            mTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == position + 1) {
            return TYPE_FOOT;
        } else {
            return TYPE_ITEM;
        }
    }

    class VideoHotViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView tvTitle;

        public VideoHotViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_pic_ivhl);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_ivhl);
        }
    }

    public void setFootView(int loadingState) {
        if (loadingState == 0) {
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("加载中......");
        } else if (loadingState == 2) {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("加载出错~");
        } else if (loadingState == 3) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        } else if (loadingState == 4) {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("---我是有底线的---");
        }
    }

    TextView mTextView;//底部内容
    ProgressBar mProgressBar;//底部进度条
    private class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.we_media_loading);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.we_media_progress);
        }
    }
}
