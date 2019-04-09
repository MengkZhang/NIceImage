package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.PicDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class SearchAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<SearchResBean.DataBean.ImageListBean> mList;

    public SearchAdapter(Context mContext,List<SearchResBean.DataBean.ImageListBean> mList) {
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
//        Glide.with(mContext).load("http://s.3987.com/wallpaper_back/Public/simg/2017-08-10/598bc9ffd0d7b.jpg").into(viewHolder.mImageView);
        if (mList != null && mList.size() != 0 && mList.get(position) != null && mList.get(position).getImageList() != null) {
            Glide.with(mContext).load(mList.get(position).getImageList().get(0).getImageUrl()).placeholder(R.drawable.su_colour_1).crossFade().into(viewHolder.mImageView);
            //条目点击事件
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jump2detail(position,0);
                }
            });
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
        ArrayList<SearchResBean.DataBean.ImageListBean.ImagListBean> imagList = mList.get(position).getImageList();
        ArrayList<HomeHotNewResBean.DataBean.TypeListBean.ImagListBean> imagLists = new ArrayList<>();
        String id = mList.get(position).getImageList().get(0).getImageId();
        String name = mList.get(position).getImageList().get(0).getTypeName();
        for (int i = 0; i < imagList.size(); i++) {
            HomeHotNewResBean.DataBean.TypeListBean.ImagListBean bean = new HomeHotNewResBean.DataBean.TypeListBean.ImagListBean();
            String imageId = imagList.get(i).getImageId();
            String imageUrl = imagList.get(i).getImageUrl();
            List<SearchResBean.DataBean.ImageListBean.ImagListBean.TagListBean> tagList = imagList.get(i).getTagList();
            bean.setImageId(imageId);
            bean.setImageUrl(imageUrl);
            imagLists.add(bean);
        }
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
