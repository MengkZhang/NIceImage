package com.xlkj.beautifulpicturehouse.common.manager.videoScanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity.LocalVideoPlayActivity;

import java.util.List;

/**
 * Created by 1 on 2017/10/9.
 */
public class LocalVideoAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<VideoInfo> data;

    public LocalVideoAdapter(Context context, List<VideoInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_layout_local_video, parent, false);
        return new LocalVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LocalVideoViewHolder viewHolder = (LocalVideoViewHolder) holder;
        try {
//            GlideUtils.loadDefault(data.get(position).getPath(), viewHolder.imageView, false, DecodeFormat.PREFER_ARGB_8888, DiskCacheStrategy.RESULT);
            Glide.with(context).load(data.get(position).getPath()).placeholder(R.drawable.da_yanse_cuilvideo_local).crossFade().into(viewHolder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ClickUtils.isFastDoubleClick()) {
//                    return;
//                }
//                final String mp4Url = mAdapter.getData().get(position).mp4Url;
                final String mp4Url = data.get(position).getPath();
                if (TextUtils.isEmpty(mp4Url)) {
                    Toast.makeText(context, "此视频无播放网址哎╮(╯Д╰)╭", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(context, LocalVideoPlayActivity.class);
                intent.putExtra("url",mp4Url);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalVideoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public LocalVideoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_local_item);
        }
    }

}
