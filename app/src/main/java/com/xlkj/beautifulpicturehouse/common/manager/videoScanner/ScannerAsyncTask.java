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
 * 递归遍历文件获取本地视频并显示
 */
public class ScannerAsyncTask extends AsyncTask<Void,Integer,List<VideoInfo>> {

    private Context context;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private GridLayoutManager gridLayoutManager;
    private LocalVideoAdapter videoAdapter;
    private String path1;
    private LinearLayout llNoData;

    public ScannerAsyncTask(Context context, ProgressBar progressBar, RecyclerView recyclerView, String path1, LinearLayout llNoData) {
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
        videoInfos=filterVideo(videoInfos);
        Log.i("tga","最后的大小"+videoInfos.size());
        return videoInfos;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
        gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        videoAdapter = new LocalVideoAdapter(context, videoInfos);
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
     * 获取视频文件
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
                    if (name.equalsIgnoreCase(".mp4")
                            || name.equalsIgnoreCase(".3gp")
                            || name.equalsIgnoreCase(".wmv")
                            || name.equalsIgnoreCase(".ts")
                            || name.equalsIgnoreCase(".rmvb")
                            || name.equalsIgnoreCase(".mov")
                            || name.equalsIgnoreCase(".m4v")
                            || name.equalsIgnoreCase(".avi")
                            || name.equalsIgnoreCase(".m3u8")
                            || name.equalsIgnoreCase(".3gpp")
                            || name.equalsIgnoreCase(".3gpp2")
                            || name.equalsIgnoreCase(".mkv")
                            || name.equalsIgnoreCase(".flv")
                            || name.equalsIgnoreCase(".divx")
                            || name.equalsIgnoreCase(".f4v")
                            || name.equalsIgnoreCase(".rm")
                            || name.equalsIgnoreCase(".asf")
                            || name.equalsIgnoreCase(".ram")
                            || name.equalsIgnoreCase(".mpg")
                            || name.equalsIgnoreCase(".v8")
                            || name.equalsIgnoreCase(".swf")
                            || name.equalsIgnoreCase(".m2v")
                            || name.equalsIgnoreCase(".asx")
                            || name.equalsIgnoreCase(".ra")
                            || name.equalsIgnoreCase(".ndivx")
                            || name.equalsIgnoreCase(".xvid")) {
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

    /**10M=10485760 b,小于10m的过滤掉
     * 过滤视频文件
     * @param videoInfos
     * @return
     */
    private List<VideoInfo> filterVideo(List<VideoInfo> videoInfos){
        List<VideoInfo> newVideos=new ArrayList<VideoInfo>();
        for(VideoInfo videoInfo:videoInfos){
            File f=new File(videoInfo.getPath());
//            if(f.exists()&&f.isFile()&&f.length()>10485760){
            if(f.exists()&&f.isFile()&&f.length()>1048576){
                newVideos.add(videoInfo);
                Log.i("TGA","文件大小"+f.length());
            }else {
                Log.i("TGA","文件太小或者不存在");
            }
        }
        return newVideos;
    }

}


