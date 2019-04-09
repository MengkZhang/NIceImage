package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoListResBean;
import com.xlkj.beautifulpicturehouse.module.video.view.ui.activity.VideoDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 *视频搜索的adapter
 */

public class SearchVideoAdapter extends RecyclerView.Adapter {
    public static final String TAG = "-->SearchVideoAdapter";
    private Context mContext;
    private List<SearchResBean.DataBean.VideoListBean> mList;

    public SearchVideoAdapter(Context mContext, List<SearchResBean.DataBean.VideoListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_search_layout, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SearchViewHolder viewHolder = (SearchViewHolder) holder;
        if (mList != null && mList.size() != 0 && mList.get(position) != null && mList.get(position).getImageUrl() != null) {
            Glide.with(mContext).load(mList.get(position).getImageUrl()).placeholder(R.drawable.su_colour_1).crossFade().into(viewHolder.mImageView);
            //条目点击事件
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                try {
                    Intent intent = new Intent(mContext, VideoDetailActivity.class);
                    VideoListResBean.DataBean.TypeListBean typeListBean = new VideoListResBean.DataBean.TypeListBean();
                    String videoUrl = mList.get(position).getVideoUrl();
                    String followId = mList.get(position).getFollowId();
                    int isVip = mList.get(position).getIsVip();
                    String imageUrl = mList.get(position).getImageUrl();
                    String videoId = mList.get(position).getVideoId();
                    typeListBean.setVideoUrl(videoUrl);
                    typeListBean.setVideoId(videoId);
                    typeListBean.setTypeName("搜索视频");
                    typeListBean.setIsVip(isVip);
                    typeListBean.setFollowId(followId);
                    typeListBean.setImageUrl(imageUrl);
                    intent.putExtra("dataBean",typeListBean);
                    mContext.startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"到详情异常");
                }

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

    class SearchViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        public SearchViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_isl);
        }
    }
}
