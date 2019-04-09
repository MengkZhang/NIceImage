//package com.xlkj.beautifulpicturehouse.module.home.view.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.xlkj.beautifulpicturehouse.R;
//import com.xlkj.beautifulpicturehouse.module.home.bean.GoodDetailResBean;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2018/1/2.
// */
//
//public class HomeGoodChoiceDetailAdapter extends RecyclerView.Adapter{
//    public static final int TYPE_HEAD = 0;
//    public static final int TYPE_ITEM = 1;
//    public static final int TYPE_FOOT = 2;
//    private Context mContext;
//    private List<GoodDetailResBean.DataBean.InfoBean.ListBean> mList;
//
//    public HomeGoodChoiceDetailAdapter(Context mContext, List<GoodDetailResBean.DataBean.InfoBean.ListBean> mList) {
//        this.mContext = mContext;
//        this.mList = mList;
//    }
//
//    /**
//     * 当条目是头部的时候-header占据三个单元格
//     * 我的header竟然作为一个cell出现在了界面上，这完全不是我想要的效果啊！ 冷静下来想想，肯定会有解决方法的吧。这时候我就该引入一个不太常用的方法了
//     * @param recyclerView
//     */
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    return getItemViewType(position) == TYPE_HEAD ? gridManager.getSpanCount() : 1;
//                }
//            });
//        }
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_HEAD) {
//            //头部
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.head_home_gc_det_layout, parent, false);
//            return new HeadViewHolder(view);
//        } else if(viewType ==TYPE_FOOT) {
//            //底部
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
//            return new FootViewHolder(view);
//        } else {
//            //条目
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.item_home_good_choice_del_layout, parent, false);
//            return new HomeGoodChoiceDetailViewHolder(view);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (position == 0) {
//            //头部
//            HeadViewHolder viewHolder = (HeadViewHolder) holder;
//            Glide.with(mContext).load("http://s.3987.com/uploadfile/2018/0102/5a4ad33aec091.jpg").into(viewHolder.mImageView);
//        } else {
//            //条目
////            HomeGoodChoiceDetailViewHolder viewHolder = (HomeGoodChoiceDetailViewHolder) holder;
//////            Glide.with(mContext).load("http://s.3987.com/uploadfile/2017/1225/thumb_5a406187736e1.jpg").into(viewHolder.mImageView);
////            Glide.with(mContext).load(mList.get(position - 1).getThumb()).into(viewHolder.mImageView);
//
//            if (getItemViewType(position) == TYPE_ITEM) {
//                //条目
//                HomeGoodChoiceDetailViewHolder viewHolder = (HomeGoodChoiceDetailViewHolder) holder;
////            Glide.with(mContext).load("http://s.3987.com/uploadfile/2017/1225/thumb_5a406187736e1.jpg").into(viewHolder.mImageView);
//                Glide.with(mContext).load(mList.get(position - 1).getThumb()).placeholder(R.drawable.su_colour_3).crossFade().into(viewHolder.mImageView);
//            } else {
//                //底部
//                mProgressBar.setVisibility(View.INVISIBLE);
//                mTextView.setVisibility(View.VISIBLE);
//                //mTextView.setText("到底啦~");
//                mTextView.setText("");
//            }
//        }
//    }
//
//    @Override
//    public int getItemCount() {
////        return mList.size() + 1;
//        return mList.size() + 1 + 1;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            //头部
//            return TYPE_HEAD;
//        } else {
//            //条目
////            return TYPE_ITEM;
//            if (getItemCount() == position + 1) {
//                return TYPE_FOOT;
//            } else {
//                return TYPE_ITEM;
//            }
//        }
//    }
//
//    class HomeGoodChoiceDetailViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView mImageView;
//        public HomeGoodChoiceDetailViewHolder(View itemView) {
//            super(itemView);
//            mImageView = (ImageView) itemView.findViewById(R.id.iv_pic_ihgcdl);
//        }
//    }
//
//    /**
//     * 头部viewholder
//     */
//    class HeadViewHolder extends RecyclerView.ViewHolder {
//        ImageView mImageView;
//        public HeadViewHolder(View itemView) {
//            super(itemView);
//            mImageView = (ImageView) itemView.findViewById(R.id.iv_hhgdl);
//        }
//    }
//
//    public void setFootView(int loadingState) {
//        if (loadingState == 0) {
//            mProgressBar.setVisibility(View.VISIBLE);
//            mTextView.setVisibility(View.VISIBLE);
//            mTextView.setText("加载中......");
//        } else if (loadingState == 2) {
//            mProgressBar.setVisibility(View.INVISIBLE);
//            mTextView.setVisibility(View.VISIBLE);
//            mTextView.setText("加载出错~");
//        } else if (loadingState == 3) {
//            mProgressBar.setVisibility(View.INVISIBLE);
//            mProgressBar.setVisibility(View.INVISIBLE);
//        } else if (loadingState == 4) {
//            mProgressBar.setVisibility(View.GONE);
//            mTextView.setVisibility(View.VISIBLE);
//            mTextView.setText("已显示全部");
//        }
//    }
//
//    TextView mTextView;//底部内容
//    ProgressBar mProgressBar;//底部进度条
//    private class FootViewHolder extends RecyclerView.ViewHolder {
//
//        public FootViewHolder(View itemView) {
//            super(itemView);
//            mTextView = (TextView) itemView.findViewById(R.id.we_media_loading);
//            mProgressBar = (ProgressBar) itemView.findViewById(R.id.we_media_progress);
//        }
//    }
//}
