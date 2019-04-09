package com.xlkj.beautifulpicturehouse.common.manager.videoScanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2017/10/9.
 * 递归遍历文件获取本地图片并显示
 */
public class ScannerPicAsyncTask extends AsyncTask<Void,Integer,List<VideoInfo>> {

    private Context context;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private GridLayoutManager gridLayoutManager;
    private LocalPicAdapter videoAdapter;
    private String path1;
    private LinearLayout llNoData;

    public ScannerPicAsyncTask(Context context, ProgressBar progressBar, RecyclerView recyclerView, String path1, LinearLayout llNoData) {
        this.context = context;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        this.path1 = path1;
        this.llNoData = llNoData;
    }

    private List<VideoInfo> videoInfos=new ArrayList<VideoInfo>();

    @Override
    protected List<VideoInfo> doInBackground(Void... params) {
        //这是遍历整个根路径
//        videoInfos=getVideoFile(videoInfos, Environment.getExternalStorageDirectory());
//        String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() +
//                "/WallPaperVideo/video/";
        File path = new File(path1);
        videoInfos=getVideoFile(videoInfos, path);
        Log.i("tga","最后的大小"+videoInfos.size());
        return videoInfos;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
        gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        videoAdapter = new LocalPicAdapter(context, videoInfos);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<VideoInfo> videoInfos) {
        super.onPostExecute(videoInfos);
        progressBar.setVisibility(View.INVISIBLE);
        if (videoInfos.size() == 0) {
            llNoData.setVisibility(View.VISIBLE);
        } else {
            llNoData.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(videoAdapter);

    }

    /**
     * 获取图片文件
     * @param list
     * @param file
     * @return
     */
    private List<VideoInfo> getVideoFile(final List<VideoInfo> list, File file) {

        file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {

                String name = file.getName();

                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".jpg")
                            || name.equalsIgnoreCase(".png")
                            || name.equalsIgnoreCase(".jpeg")
                            ) {
                        VideoInfo video = new VideoInfo();
                        file.getUsableSpace();
                        video.setDisplayName(file.getName());
                        video.setPath(file.getAbsolutePath());
                        Log.i("tga","name"+video.getPath());
                        list.add(video);
                        return true;
                    }
                    //判断是不是目录
                } else if (file.isDirectory()) {
                    getVideoFile(list, file);
                }
                return false;
            }
        });

        return list;
    }

}


