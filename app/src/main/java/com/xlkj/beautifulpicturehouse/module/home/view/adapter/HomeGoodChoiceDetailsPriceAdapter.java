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
import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.PicDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 * 付费列表adapter
 */

public class HomeGoodChoiceDetailsPriceAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private ArrayList<ArrayList<GoodDetailResBean.DataBean.ImgListBean>> mList;

    public HomeGoodChoiceDetailsPriceAdapter(Context mContext, ArrayList<ArrayList<GoodDetailResBean.DataBean.ImgListBean>> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_home_good_choice_del_layout, parent, false);
        return new HomeGoodChoiceDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HomeGoodChoiceDetailViewHolder viewHolder = (HomeGoodChoiceDetailViewHolder) holder;
        Glide.with(mContext).load(mList.get(position).get(0).getImageUrl()).placeholder(R.drawable.su_colour_3).crossFade().into(viewHolder.mImageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump2detail(position,0);
            }
        });
    }

    /**
     * 到详情页
     * @param position
     * @param localPosition:从第几个位置进来的
     */
    private void jump2detail(int position,int localPosition) {
        Intent intent = new Intent(mContext, PicDetailActivity.class);
        ArrayList<GoodDetailResBean.DataBean.ImgListBean> imgListBeans = mList.get(position);
        String id = mList.get(position).get(0).getImageId();
        String name = mList.get(position).get(0).getTypeName();
        ArrayList<HomeHotNewResBean.DataBean.TypeListBean.ImagListBean> imagList = new ArrayList<>();
        for (int i = 0; i < imgListBeans.size(); i++) {
            HomeHotNewResBean.DataBean.TypeListBean.ImagListBean bean = new HomeHotNewResBean.DataBean.TypeListBean.ImagListBean();
            String imageId = imgListBeans.get(i).getImageId();
            String imageUrl = imgListBeans.get(i).getImageUrl();
            bean.setImageId(imageId);
            bean.setImageUrl(imageUrl);
            imagList.add(bean);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",imagList);
        intent.putExtras(bundle);
        intent.putExtra("position",localPosition);
        intent.putExtra("picTitle",name);
        intent.putExtra("itemId",id);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    class HomeGoodChoiceDetailViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        public HomeGoodChoiceDetailViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_pic_ihgcdl);
        }
    }


}
