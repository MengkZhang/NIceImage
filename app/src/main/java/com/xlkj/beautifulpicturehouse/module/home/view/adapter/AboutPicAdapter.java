package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.rxbus.RxBus;
import com.xlkj.beautifulpicturehouse.module.home.bean.AboutPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.RxPostFinishPicDetailBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.SearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.PicDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 * 相关美图adapter
 */

public class AboutPicAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<AboutPicResBean.DataBean.RelatedListBeanX> mList;

    public AboutPicAdapter(Context mContext, List<AboutPicResBean.DataBean.RelatedListBeanX> mList) {
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
        if (mList != null && mList.size() != 0 && mList.get(position) != null && mList.get(position).getRelatedList() != null) {
            Glide.with(mContext).load(mList.get(position).getRelatedList().get(0).getImageUrl()).placeholder(R.drawable.su_colour_1).crossFade().into(viewHolder.mImageView);
            //条目点击事件
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
//                        jump2detail(position,0);
                        if (mOnItemAboutClickListener != null) {
                            mOnItemAboutClickListener.onItemClick(view,position,mList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("-->AboutPicAdapter","跳转到详情异常");
                        return;
                    }
                }
            });
        }
    }

    /**
     * 跳转到详情
     */
//    private void jump2detail(int position,int localPosition) {
//
//        Intent intent = new Intent(mContext, PicDetailActivity.class);
//        /**
//         * imageUrl : http://t1.hddhhn.com/uploads/tu/201612/238/5qguvhnph2p.jpg
//         * imageId : 168657_0
//         * tagList : ["大尺度","气质","长发"]
//         */
//        ArrayList<AboutPicResBean.DataBean.RelatedListBeanX.RelatedListBean> relatedList = mList.get(position).getRelatedList();
//        String id = mList.get(position).getRelatedList().get(0).getImageId();
//        String name = mList.get(position).getRelatedList().get(0).getTypeName();
//        ArrayList<HomeHotNewResBean.DataBean.TypeListBean.ImagListBean> imagList = new ArrayList<>();
//        for (int i = 0; i < relatedList.size(); i++) {
//            HomeHotNewResBean.DataBean.TypeListBean.ImagListBean bean = new HomeHotNewResBean.DataBean.TypeListBean.ImagListBean();
//            String imageId = relatedList.get(i).getImageId();
//            String imageUrl = relatedList.get(i).getImageUrl();
//            bean.setImageId(imageId);
//            bean.setImageUrl(imageUrl);
//            imagList.add(bean);
//        }
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("data",imagList);
//        intent.putExtras(bundle);
//        intent.putExtra("position",localPosition);
//        intent.putExtra("picTitle",name);
//        intent.putExtra("itemId",id);
//        mContext.startActivity(intent);
//    }

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

    /**条目点击事件*/
    public interface OnItemAboutClickListener {
        void onItemClick(View v,int position,List<AboutPicResBean.DataBean.RelatedListBeanX> mList);
    }

    private OnItemAboutClickListener mOnItemAboutClickListener;

    public void setOnItemAboutClickListener(OnItemAboutClickListener onItemAboutClickListener) {
        mOnItemAboutClickListener = onItemAboutClickListener;
    }
}
