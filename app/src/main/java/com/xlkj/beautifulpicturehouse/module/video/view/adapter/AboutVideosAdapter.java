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
 * Created by Administrator on 2018/1/16.
 * 相关视频adapter new
 */

public class AboutVideosAdapter extends RecyclerView.Adapter {

    public static final String TAG = "-->AboutVideosAdapter";
    private Context mContext;
    private List<VideoDetailResBean.DataBean.VideoListBean> mList;

    public AboutVideosAdapter(Context mContext,List<VideoDetailResBean.DataBean.VideoListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_home_good_choice_del_layout, parent, false);
        return new AboutVideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AboutVideosViewHolder viewHolder = (AboutVideosViewHolder) holder;
        Glide.with(mContext).load(mList.get(position).getImageUrl()).placeholder(R.drawable.su_colour_3).crossFade().into(viewHolder.mImageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemAboutVideosListener != null) {
                    mOnItemAboutVideosListener.onItemAboutVideoClick(view,position);
                }

//                VideoListResBean.DataBean.TypeListBean contentBean = BeautyContent.getContentBean();
//                if (contentBean != null) {
//                    //跳转到详情页播放
//                    final String followId = contentBean.getFollowId();
//                    final String imageUrl = mList.get(position).getImageUrl();
//                    int isCollect = mList.get(position).getIsCollect();
//                    final int isVip = mList.get(position).getIsVip();
//                    final String videoId = mList.get(position).getVideoId();
//                    final String videoUrl = mList.get(position).getVideoUrl();
//                    Intent intent = new Intent(mContext, VideoDetailFollowActivity.class);
//                    VideoListResBean.DataBean.TypeListBean typeListBean = new VideoListResBean.DataBean.TypeListBean();
//                    typeListBean.setFollowId(followId);
//                    typeListBean.setImageUrl(imageUrl);
//                    typeListBean.setIsVip(isVip);
//                    typeListBean.setTypeName("todo name");
//                    typeListBean.setVideoId(videoId);
//                    typeListBean.setVideoUrl(videoUrl);
//                    intent.putExtra("dataBean", typeListBean);
//                    mContext.startActivity(intent);
//                } else {
//                    //跳转到本地播放
//                    final String mp4Url = mList.get(position).getVideoUrl();
//                    if (TextUtils.isEmpty(mp4Url)) {
//                        Toast.makeText(mContext, "此视频无播放网址哎╮(╯Д╰)╭", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    Intent intent = new Intent(mContext, VideoPlayActivity.class);
//                    intent.putExtra("url", mp4Url);
//                    mContext.startActivity(intent);
//                }

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

    class AboutVideosViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        public AboutVideosViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_pic_ihgcdl);
        }
    }

    /**相关视频条目点击事件的回调*/
    public interface OnItemAboutVideosListener {
        void onItemAboutVideoClick(View v,int position);
    }

    private OnItemAboutVideosListener mOnItemAboutVideosListener;

    public void setOnItemAboutVideosListener(OnItemAboutVideosListener onItemAboutVideosListener) {
        mOnItemAboutVideosListener = onItemAboutVideosListener;
    }
}
