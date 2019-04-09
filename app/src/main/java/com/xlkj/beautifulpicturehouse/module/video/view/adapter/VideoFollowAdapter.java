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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.view.ui.activity.VideoDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 * 视频关注页adapter
 */

public class VideoFollowAdapter extends RecyclerView.Adapter {

    public static final String TAG = "-->VideoFollowAdapter";
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOT = 2;
    private Context mContext;
    private List<VideoDetailResBean.DataBean.VideoListBean> mList;
    private String followId;

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public VideoFollowAdapter(Context mContext, List<VideoDetailResBean.DataBean.VideoListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    /**
     * 没被关注过的状态
     * @param rlFollowBoMaster
     * @param tvFollowTxt
     */
    private void notFollowState(RelativeLayout rlFollowBoMaster, TextView tvFollowTxt) {
        tvFollowTxt.setText("关注");
        rlFollowBoMaster.setBackgroundResource(R.drawable.follow_checked_shape);
    }

    /**
     * 被关注过的状态
     * @param rlFollowBoMaster
     * @param tvFollowTxt
     */
    private void followState(RelativeLayout rlFollowBoMaster, TextView tvFollowTxt) {
        tvFollowTxt.setText("已关注");
        rlFollowBoMaster.setBackgroundResource(R.drawable.follow_unchecked_shape);
    }

    /**
     * 当条目是头部的时候-header占据三个单元格
     * 我的header竟然作为一个cell出现在了界面上，这完全不是我想要的效果啊！ 冷静下来想想，肯定会有解决方法的吧。这时候我就该引入一个不太常用的方法了
     *
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
                    //return getItemViewType(position) == TYPE_HEAD ? gridManager.getSpanCount() : 1;
                    if (getItemViewType(position) == TYPE_HEAD || getItemViewType(position) == TYPE_FOOT) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            //头部
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.head_video_follow_layout, parent, false);
            return new HeadViewHolder(view);
        } else if (viewType == TYPE_FOOT) {
            //底部
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
            return new FootViewHolder(view);
        } else {
            //条目
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_home_good_choice_del_layout, parent, false);
            return new VideoFollowViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            //头部
            HeadViewHolder viewHolder = (HeadViewHolder) holder;
            //显示已经关注的状态
            followState(viewHolder.rlFollowBoMaster,viewHolder.tvFollowTxt);

            VideoDetailResBean.DataBean videoDetailData = BeautyContent.getVideoDetailData();
            if (videoDetailData != null) {
                String avatarUrl = videoDetailData.getAvatarurl();
                if (avatarUrl != null) {
                    Glide.with(mContext).load(avatarUrl).into(viewHolder.mImageView);
                }
                String fansCount = videoDetailData.getFansCount();
                String playCount = videoDetailData.getPlayCount();
                if (fansCount != null && playCount != null) {
                    viewHolder.tvFensPlays.setText("粉丝 " + fansCount + " | " + "播放 " + playCount);
                }
                String videoCount = videoDetailData.getVideoCount();
                if (videoCount != null) {
                    viewHolder.tv_videos_hvfl.setText(videoCount + "视频");
                }
                String followId = videoDetailData.getFollowId();
                if (followId != null) {
                    // TODO: 2018/1/11 关注 
                }
            }



        } else {
            if (getItemViewType(position) == TYPE_ITEM) {
                //条目
                final String followId = getFollowId();
                VideoFollowViewHolder viewHolder = (VideoFollowViewHolder) holder;
                Glide.with(mContext).load(mList.get(position - 1).getImageUrl()).placeholder(R.drawable.su_colour_3).crossFade().into(viewHolder.mImageView);
                //是从关注页面进来 到本地页面播放
                // TODO: 2018/1/12 暂时这么处理--没有产品经理，凌乱中
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(mContext, VideoDetailActivity.class);
                            VideoListResBean.DataBean.TypeListBean typeListBean = new VideoListResBean.DataBean.TypeListBean();
                            String videoUrl = mList.get(position - 1).getVideoUrl();
                            int isVip = mList.get(position - 1).getIsVip();
                            String imageUrl = mList.get(position - 1).getImageUrl();
                            String videoId = mList.get(position - 1).getVideoId();
                            typeListBean.setVideoUrl(videoUrl);
                            typeListBean.setVideoId(videoId);
                            typeListBean.setTypeName("相关视频");
                            typeListBean.setIsVip(isVip);
                            typeListBean.setFollowId(followId);
                            typeListBean.setImageUrl(imageUrl);
                            intent.putExtra("dataBean",typeListBean);
                            mContext.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG,"跳转到视频详情页异常");
                        }

                    }
                });

            } else {
                //底部
                mProgressBar.setVisibility(View.INVISIBLE);
                mTextView.setVisibility(View.VISIBLE);
                //mTextView.setText("到底啦~");
                mTextView.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1 + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //头部
            return TYPE_HEAD;
        } else {
            //条目
            if (getItemCount() == position + 1) {
                return TYPE_FOOT;
            } else {
                return TYPE_ITEM;
            }
        }
    }

    /**
     * 条目viewHolder
     */
    class VideoFollowViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public VideoFollowViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_pic_ihgcdl);
        }
    }

    /**
     * 头部viewholder
     */
    class HeadViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        //粉丝播放量
        TextView tvFensPlays;
        //视频总数
        TextView tv_videos_hvfl;
        //关注波主
        RelativeLayout rlFollowBoMaster;
        //关注或者已经关注的文本
        TextView tvFollowTxt;

        public HeadViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.civ_head_hvfl);
            tvFensPlays = (TextView) itemView.findViewById(R.id.tv_fens_plays_count_hvfl);
            tv_videos_hvfl = (TextView) itemView.findViewById(R.id.tv_videos_hvfl);
            rlFollowBoMaster = (RelativeLayout) itemView.findViewById(R.id.rl_follow_hvfl);
            tvFollowTxt = (TextView) itemView.findViewById(R.id.tv_is_follow_lhl);
            rlFollowBoMaster.setVisibility(View.GONE);
        }
    }

    /**
     * 底部相关
     *
     * @param loadingState
     */
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
