package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.PicDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/24.
 * 首页类型type adapter
 */

public class HomeTypeAdapter extends RecyclerView.Adapter {
    public static final String TAG = "-->>HomeTypeAdapter";
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOT = 2;
    private List<SearchResBean.DataBean.ImageListBean> mHotPicList;
    private Context mContext;

    public HomeTypeAdapter(Context mContext,List<SearchResBean.DataBean.ImageListBean> mHotPicList) {
        this.mContext = mContext;
        this.mHotPicList = mHotPicList;
    }

    /**
     * 当条目是头部的时候-header占据三个单元格
     * 我的header竟然作为一个cell出现在了界面上，这完全不是我想要的效果啊！ 冷静下来想想，肯定会有解决方法的吧。这时候我就该引入一个不太常用的方法了
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
            View view = inflater.inflate(R.layout.item_home_type_layout, parent, false);
            return new HomeTypeViewHolder(view);
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
            //条目
            HomeTypeViewHolder viewHolder = (HomeTypeViewHolder) holder;
            Glide.with(mContext).load(mHotPicList.get(position).getImageList().get(0).getImageUrl()).placeholder(R.drawable.da_yanse_lv).crossFade().into(viewHolder.mImageView);
            //条目点击事件
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jump2detail(position,0);
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

    /**
     * 跳转到详情
     */
    private void jump2detail(int position,int localPosition) {
        Intent intent = new Intent(mContext, PicDetailActivity.class);
//        HomeHotNewResBean.DataBean.TypeListBean.ImagListBean
        /**
         * imageUrl : http://t1.hddhhn.com/uploads/tu/201612/238/5qguvhnph2p.jpg
         * imageId : 168657_0
         * tagList : ["大尺度","气质","长发"]
         */
        ArrayList<SearchResBean.DataBean.ImageListBean.ImagListBean> imagList = mHotPicList.get(position).getImageList();
        String id = mHotPicList.get(position).getImageList().get(0).getImageId();
        String name = mHotPicList.get(position).getImageList().get(0).getTypeName();
        ArrayList<HomeHotNewResBean.DataBean.TypeListBean.ImagListBean> imagLists = new ArrayList<>();
        for (int i = 0; i < imagList.size(); i++) {
            HomeHotNewResBean.DataBean.TypeListBean.ImagListBean bean = new HomeHotNewResBean.DataBean.TypeListBean.ImagListBean();
            String imageId = imagList.get(i).getImageId();
            String imageUrl = imagList.get(i).getImageUrl();
            List<SearchResBean.DataBean.ImageListBean.ImagListBean.TagListBean> tagList = imagList.get(i).getTagList();
            bean.setImageId(imageId);
            bean.setImageUrl(imageUrl);
            imagLists.add(bean);
        }
//        String typeName = mHotPicList.get(position).;
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",imagLists);
        intent.putExtras(bundle);
        intent.putExtra("position",localPosition);
        intent.putExtra("picTitle",name);
        intent.putExtra("itemId",id);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mHotPicList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == position + 1) {
            return TYPE_FOOT;
        } else {
            return TYPE_ITEM;
        }
    }

    class HomeTypeViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        public HomeTypeViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_pic_ihtl);
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
