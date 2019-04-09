package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.util.TimeUtil;
import com.xlkj.beautifulpicturehouse.module.home.bean.CommentListResBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/15.
 * 评论列表adapter
 */

public class CommentListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<CommentListResBean.DataBean.CommentListBean> mList;

    public CommentListAdapter(Context mContext,List<CommentListResBean.DataBean.CommentListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_comment_list_layout, parent, false);
        return new CommentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentListViewHolder viewHolder = (CommentListViewHolder) holder;
        Glide.with(mContext).load(mList.get(position).getAvatarUlr()).into(viewHolder.ivHead);
        viewHolder.tvName.setText(mList.get(position).getUserName());
        viewHolder.tvContent.setText(mList.get(position).getContent());
//        1516010494  "time": 1516015193,
        long l = System.currentTimeMillis();
        Log.e("==zxl",TimeUtil.getCurrentTime2SS(l));
        Log.e("==zxl",TimeUtil.getCurrentTime2SS(1516010494));
        viewHolder.tvTime.setText(TimeUtil.getCurrentTime2SS(mList.get(position).getTime())+"");
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() != 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    class CommentListViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ivHead;
        TextView tvName;
        TextView tvContent;
        TextView tvTime;
        public CommentListViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_icll);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content_icll);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_icll);
            ivHead = (CircleImageView) itemView.findViewById(R.id.civ_head_icll);
        }
    }
}
