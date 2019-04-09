package com.xlkj.beautifulpicturehouse.module.mine.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.module.mine.bean.MyFollowResBean;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.BoMasterDetailActivity;

import java.net.Inet4Address;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/10.
 * 我关注的波主adapter
 */

public class MyFollowMasterAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MyFollowResBean.DataBean.UserListBean> mList;

    public MyFollowMasterAdapter(Context mContext,List<MyFollowResBean.DataBean.UserListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_master_follow_layout, parent, false);
        return new MyFollowMasterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyFollowMasterViewHolder viewHolder = (MyFollowMasterViewHolder) holder;
        viewHolder.tvName.setText("播主："+mList.get(position).getUserName());
        viewHolder.tvId.setText("ID："+mList.get(position).getUserId());
        viewHolder.mRelativeLayout.setBackgroundResource(R.drawable.follow_unchecked_shape);
        Glide.with(mContext).load(mList.get(position).getAvatar())/*.placeholder(R.drawable.su_colour_1).crossFade()*/.into(viewHolder.ivHead);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(mContext, BoMasterDetailActivity.class);
                    intent.putExtra("followId",mList.get(position).getUserId());
                    intent.putExtra("bo_name",mList.get(position).getUserName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("-->MyFollowMas","MyFollowMas跳转异常");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() != 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    class MyFollowMasterViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivHead;
        TextView tvName;
        TextView tvId;
        RelativeLayout mRelativeLayout;
        public MyFollowMasterViewHolder(View itemView) {
            super(itemView);
            ivHead = (CircleImageView) itemView.findViewById(R.id.iv_head_ifml);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_ifml);
            tvId = (TextView) itemView.findViewById(R.id.tv_id_ifml);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl_follow_tfl);
        }
    }
}
