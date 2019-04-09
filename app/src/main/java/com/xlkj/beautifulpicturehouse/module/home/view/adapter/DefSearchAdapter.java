package com.xlkj.beautifulpicturehouse.module.home.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.module.home.bean.DefSearchResBean;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.HomeTypeActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/24.
 * 默认搜索列表的adapter
 */

public class DefSearchAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<DefSearchResBean.DataBean> mList;

    public DefSearchAdapter(Context mContext, List<DefSearchResBean.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item__home_more_type_layout, parent, false);
        return new HomeMoreTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HomeMoreTypeViewHolder viewHolder = (HomeMoreTypeViewHolder) holder;
        viewHolder.mTextView.setText(mList.get(position).getSearchKey());
        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mContext.startActivity(new Intent(mContext, HomeTypeActivity.class));
                if (mOnDefItemClickListener != null) {
                    mOnDefItemClickListener.onDefClick(view,mList.get(position).getSearchId(),mList.get(position).getSearchKey());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HomeMoreTypeViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        RelativeLayout rlItem;
        public HomeMoreTypeViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_ihmtl);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item_ihtml);
        }
    }

    /**条目点击事件的回调*/
    public interface OnDefItemClickListener {
        void onDefClick(View v,String id,String name);
    }

    private OnDefItemClickListener mOnDefItemClickListener;

    public void setOnDefItemClickListener(OnDefItemClickListener onDefItemClickListener) {
        mOnDefItemClickListener = onDefItemClickListener;
    }
}
