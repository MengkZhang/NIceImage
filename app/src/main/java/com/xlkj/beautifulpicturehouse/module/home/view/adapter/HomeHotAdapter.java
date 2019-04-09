package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotNewResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResBean;
import com.xlkj.beautifulpicturehouse.module.home.bean.HomeHotPicResMgqBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.PicDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class HomeHotAdapter extends RecyclerView.Adapter {
    public static final String TAG = "-->>HomeHotAdapter";
    private Context mContext;
//    private List<HomeHotPicResMgqBean.DataBean.InfoBean> mHotPicList;
    private List<HomeHotNewResBean.DataBean.TypeListBean> mHotPicList;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOT = 2;

    public HomeHotAdapter(Context mContext,List<HomeHotNewResBean.DataBean.TypeListBean> mHotPicList) {
        this.mContext = mContext;
        this.mHotPicList = mHotPicList;
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
        //条目
        if (viewType == TYPE_ITEM) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_home_hot_layout, parent, false);
            return new HomeHotViewHolder(view);
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
            HomeHotViewHolder viewHolder = (HomeHotViewHolder) holder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jump2detail(position,0);
                }
            });
            try {
                viewHolder.mTextViewTitle.setText(mHotPicList.get(position).getTypeName());
                viewHolder.mTextViewCount.setText(mHotPicList.get(position).getImagList().size()+"P");
                viewHolder.mTextViewCollection.setText(mHotPicList.get(position).getLikeCount()+"");
                int size = mHotPicList.get(position).getImagList().size();
                if (size >= 6) {
                    Glide.with(mContext).load(mHotPicList.get(position).getImagList().get(0).getImageUrl()).placeholder(R.drawable.da_yanse_cuilv).crossFade().into(viewHolder.mImageView1);
                    Glide.with(mContext).load(mHotPicList.get(position).getImagList().get(1).getImageUrl()).placeholder(R.drawable.da_yanse_lv).crossFade().into(viewHolder.mImageView2);
                    Glide.with(mContext).load(mHotPicList.get(position).getImagList().get(2).getImageUrl()).placeholder(R.drawable.xiao_yanse_caolv).crossFade().into(viewHolder.mImageView3);
                    Glide.with(mContext).load(mHotPicList.get(position).getImagList().get(3).getImageUrl()).placeholder(R.drawable.xiao_yanse_huang).crossFade().into(viewHolder.mImageView4);
                    Glide.with(mContext).load(mHotPicList.get(position).getImagList().get(4).getImageUrl()).placeholder(R.drawable.xiao_yanse_hui).crossFade().into(viewHolder.mImageView5);
                    Glide.with(mContext).load(mHotPicList.get(position).getImagList().get(5).getImageUrl()).placeholder(R.drawable.xiao_yanse_qinlv).crossFade().into(viewHolder.mImageView6);
                    viewHolder.mImageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jump2detail(position,0);
                        }
                    });
                    viewHolder.mImageView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jump2detail(position,1);
                        }
                    });
                    viewHolder.mImageView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jump2detail(position,2);
                        }
                    });
                    viewHolder.mImageView4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jump2detail(position,3);
                        }
                    });
                    viewHolder.mImageView5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jump2detail(position,4);
                        }
                    });
                    viewHolder.mImageView6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jump2detail(position,5);
                        }
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG,"onBindViewHolder显示数据异常");
            }
        } else {
            //底部
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("加载中......");
//            mTextView.setText("");
        }
    }

    /**
     * 跳转到详情
     * @param position
     * @param localPosition
     */
    private void jump2detail(int position,int localPosition) {
        try {
            Intent intent = new Intent(mContext, PicDetailActivity.class);
            ArrayList<HomeHotNewResBean.DataBean.TypeListBean.ImagListBean> imagList = mHotPicList.get(position).getImagList();
            String typeName = mHotPicList.get(position).getTypeName();
            String typeId = mHotPicList.get(position).getImagList().get(0).getImageId();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data",imagList);
            intent.putExtras(bundle);
            intent.putExtra("position",localPosition);
            intent.putExtra("picTitle",typeName);
            intent.putExtra("itemId",typeId);
            //Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"到详情页异常");
        }
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

    class HomeHotViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewCollection;
        TextView mTextViewTitle;
        TextView mTextViewCount;
        ImageView mImageView1;
        ImageView mImageView2;
        ImageView mImageView3;
        ImageView mImageView4;
        ImageView mImageView5;
        ImageView mImageView6;
        public HomeHotViewHolder(View itemView) {
            super(itemView);
            mTextViewCollection = (TextView) itemView.findViewById(R.id.tv_collection_ihhl);
            mTextViewCount = (TextView) itemView.findViewById(R.id.tv_count_ihhl);
            mTextViewTitle = (TextView) itemView.findViewById(R.id.tv_title_ihhl);
            mImageView1 = (ImageView) itemView.findViewById(R.id.iv_pic_ihhl1);
            mImageView2 = (ImageView) itemView.findViewById(R.id.iv_pic_ihhl2);
            mImageView3 = (ImageView) itemView.findViewById(R.id.iv_pic_ihhl3);
            mImageView4 = (ImageView) itemView.findViewById(R.id.iv_pic_ihhl4);
            mImageView5 = (ImageView) itemView.findViewById(R.id.iv_pic_ihhl5);
            mImageView6 = (ImageView) itemView.findViewById(R.id.iv_pic_ihhl6);
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
