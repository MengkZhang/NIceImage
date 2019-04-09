package com.xlkj.beautifulpicturehouse.module.video.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.util.TimeUtil;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListResBean;
import com.xlkj.beautifulpicturehouse.module.video.bean.VideoDetailResBean;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/26.
 * 相关视频和评论列表的adapter
 */

public class AboutVideoCommentListAdapter extends RecyclerView.Adapter {
    public static final String TAG = "-->AboutVideoC";
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOT = 2;
    private Context mContext;
    //头部数据-相关视频
    private List<VideoDetailResBean.DataBean.VideoListBean> headData;
    //条目数据-评论列表数据
    private List<CommentListResBean.DataBean.CommentListBean> mList;

    public AboutVideoCommentListAdapter(Context mContext,List<CommentListResBean.DataBean.CommentListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public List<VideoDetailResBean.DataBean.VideoListBean> getHeadData() {
        return headData;
    }

    public void setHeadData(List<VideoDetailResBean.DataBean.VideoListBean> headData) {
        this.headData = headData;
    }

    /**
     * 刷新头部
     */
    public void setRefreshSomeoneItem() {
        notifyItemChanged(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == TYPE_HEAD) {
            //头部
            View view = inflater.inflate(R.layout.item_about_video_comment_list_layout, parent, false);
            return new AboutVideoCommentListViewHeaderHolder(view);
        } else if (viewType == TYPE_FOOT) {
            //底部
            View view = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
            return new FootViewHolder(view);
        } else {
            //条目item_comment_list_layout
            View view = inflater.inflate(R.layout.item_comment_list_layout, parent, false);
            return new AboutVideoCommentListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            AboutVideoCommentListViewHeaderHolder viewHeaderHolder = (AboutVideoCommentListViewHeaderHolder) holder;
            //头部
            List<VideoDetailResBean.DataBean.VideoListBean> headData = getHeadData();
            if (headData != null && headData.size() != 0) {
                VideoNewAboutAdapter adapter = new VideoNewAboutAdapter(mContext, headData);
                viewHeaderHolder.mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickVideoAboutListener(new VideoNewAboutAdapter.OnItemClickVideoAboutListener() {
                    @Override
                    public void onClickVideoAbout(View view, int position) {
                        if (mOnHeadItemClickListener != null) {
                            mOnHeadItemClickListener.headClick(view,position);
                        }
                    }
                });
            } else {
                // TODO: 2018/1/26 相关视频没有数据-空的
            }
        } else {
            //条目和底部
            if (getItemViewType(position) == TYPE_ITEM) {
                //条目
                AboutVideoCommentListViewHolder viewHolder = (AboutVideoCommentListViewHolder) holder;
                Glide.with(mContext).load(mList.get(position - 1).getAvatarUlr()).into(viewHolder.ivHead);
                viewHolder.tvName.setText(mList.get(position - 1).getUserName());
                viewHolder.tvContent.setText(mList.get(position - 1).getContent());
                long l = System.currentTimeMillis();
                Log.e("==zxl", TimeUtil.getCurrentTime2SS(l));
                Log.e("==zxl",TimeUtil.getCurrentTime2SS(1516010494));
                viewHolder.tvTime.setText(TimeUtil.getCurrentTime2SS(mList.get(position - 1).getTime())+"");
            } else {
                //底部
                mProgressBar.setVisibility(View.INVISIBLE);
                mTextView.setVisibility(View.INVISIBLE);
                mTextView.setText("加载中......");
//            mTextView.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() != 0) {
            return mList.size() + 1 + 1;
        } else {
            //返回头部一条数据-无列表 无底部加载更多
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //头部
            return TYPE_HEAD;
        } else {
            //条目
            if (getItemCount() == position + 1) {
                //底部
                return TYPE_FOOT;
            } else {
                //真正的条目
                return TYPE_ITEM;
            }
        }
    }

    /**
     * 头部header的ViewHolder
     */
    class AboutVideoCommentListViewHeaderHolder extends RecyclerView.ViewHolder {

        //相关视频的水平列表
        RecyclerView mRecyclerView;
        public AboutVideoCommentListViewHeaderHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_shuiping_avd2);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    /**
     * 条目ViewHolder
     */
    class AboutVideoCommentListViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ivHead;
        TextView tvName;
        TextView tvContent;
        TextView tvTime;
        public AboutVideoCommentListViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_icll);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content_icll);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_icll);
            ivHead = (CircleImageView) itemView.findViewById(R.id.civ_head_icll);
        }
    }

    /**
     * 底部相关ViewHolder
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

    /**头部条目点击回调*/
    public interface OnHeadItemClickListener {
        void headClick(View view, int position);
    }

    private OnHeadItemClickListener mOnHeadItemClickListener;

    public void setOnHeadItemClickListener(OnHeadItemClickListener onHeadItemClickListener) {
        mOnHeadItemClickListener = onHeadItemClickListener;
    }
}
