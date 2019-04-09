package com.xlkj.beautifulpicturehouse.common.manager.glideprogress;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/1/23.
 * 替换通讯组件
 通过第二篇文章的源码分析，我们知道了Glide内部HTTP通讯组件的底层实现是基于HttpUrlConnection来进行定制的。但是HttpUrlConnection的可扩展性比较有限，我们在它的基础之上无法实现监听下载进度的功能，因此今天的第一个大动作就是要将Glide中的HTTP通讯组件替换成OkHttp。

 关于HTTP通讯组件的替换原理和替换方式，我在第六篇文章当中都介绍得比较清楚了，这里就不再赘述。下面我们就来开始快速地替换一下。

 新建一个OkHttpFetcher类，并且实现DataFetcher接口，代码如下所示：
 */

public class OkHttpFetcher implements DataFetcher<InputStream> {

    private final OkHttpClient client;
    private final GlideUrl url;
    private InputStream stream;
    private ResponseBody responseBody;
    private volatile boolean isCancelled;

    public OkHttpFetcher(OkHttpClient client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url.toStringUrl());
        for (Map.Entry<String, String> headerEntry : url.getHeaders().entrySet()) {
            String key = headerEntry.getKey();
            requestBuilder.addHeader(key, headerEntry.getValue());
        }
        Request request = requestBuilder.build();
        if (isCancelled) {
            return null;
        }
        Response response = client.newCall(request).execute();
        responseBody = response.body();
        if (!response.isSuccessful() || responseBody == null) {
            throw new IOException("Request failed with code: " + response.code());
        }
        stream = ContentLengthInputStream.obtain(responseBody.byteStream(),
                responseBody.contentLength());
        return stream;
    }

    @Override
    public void cleanup() {
        try {
            if (stream != null) {
                stream.close();
            }
            if (responseBody != null) {
                responseBody.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return url.getCacheKey();
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }
}

