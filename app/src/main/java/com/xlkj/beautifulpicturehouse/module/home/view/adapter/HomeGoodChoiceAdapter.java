package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomePicGoodChoiceResBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.HomeGoodChoiceDetailsActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */

public class HomeGoodChoiceAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<HomePicGoodChoiceResBean.DataBean.TypeListBean> mList;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOT = 2;

    public HomeGoodChoiceAdapter(Context mContext,List<HomePicGoodChoiceResBean.DataBean.TypeListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_home_good_choice_layout, parent, false);
//        return new HomeGoodChoiceViewHolder(view);

        //条目
        if (viewType == TYPE_ITEM) {
            View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_home_good_choice_layout, parent, false);
            return new HomeGoodChoiceViewHolder(view);
        } else {
            //底部
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
            return new FootViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            HomeGoodChoiceViewHolder viewHolder = (HomeGoodChoiceViewHolder) holder;
            Glide.with(mContext).load(mList.get(position).getImageUrl()).placeholder(R.drawable.colour_4).crossFade().into(viewHolder.mImageView);
            viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, HomeGoodChoiceDetailsActivity.class);
                    intent.putExtra("imageId",mList.get(position).getImageId());
                    intent.putExtra("imageUrl",mList.get(position).getImageUrl());
                    intent.putExtra("followId",mList.get(position).getFollowId());
                    //android.util.AndroidRuntimeException
                    //Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        } else {
            //底部
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("---我是有底线的---");
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

    class HomeGoodChoiceViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        public HomeGoodChoiceViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_ihgcl);
        }
    }


    public void setFootView(int loadingState) {
        if (loadingState == 0) {
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("加载中......");
        } else if (loadingState == 2) {
            mProgressBar.setVisibility(View.INVISIBLE);
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
