package com.xlkj.beautifulpicturehouse.module.mine.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.view.ui.dialog.share.DisplayUtil;
import com.xlkj.beautifulpicturehouse.module.mine.bean.VipResBean;

import java.util.List;


/**
 * Created by Administrator on 2017/12/26.
 */

public class VIPAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<VipResBean> mList;

    public VIPAdapter(Context mContext,List<VipResBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_vip_open_layout, parent, false);
        return new VipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VipViewHolder vipViewHolder = (VipViewHolder) holder;
        vipViewHolder.tvPrice.setText(mList.get(position).getPrice());
        vipViewHolder.tvName.setText(mList.get(position).getName());
        vipViewHolder.tvMonth.setText(mList.get(position).getMonth()+"个月");
        vipViewHolder.rlOpenVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "敬请期待", Toast.LENGTH_SHORT).show();
                DisplayUtil.showPayDialog(mContext,"90");
            }
        });
        vipViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class VipViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPrice;
        TextView tvMonth;
        RelativeLayout rlOpenVip;
        public VipViewHolder(View itemView) {
            super(itemView);
            rlOpenVip = (RelativeLayout) itemView.findViewById(R.id.rl_item_ivol);
            tvName = (TextView) itemView.findViewById(R.id.tv_vip_name_ivol);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price_ivol);
            tvMonth = (TextView) itemView.findViewById(R.id.tv_month_ivol);
        }
    }
}
