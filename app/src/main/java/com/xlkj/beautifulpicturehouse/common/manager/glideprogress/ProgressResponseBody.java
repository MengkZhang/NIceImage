package com.xlkj.beautifulpicturehouse.common.manager.glideprogress;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Administrator on 2018/1/23.
 * 接下来就要到今天最复杂的部分了，也就是下载进度的具体计算。
 * 我们需要新建一个ProgressResponseBody类，并让它继承自OkHttp的ResponseBody，然后在这个类当中去编写具体的监听下载进度的逻辑，代码如下所示：
 */

public class ProgressResponseBody extends ResponseBody {

    private static final String TAG = "ProgressResponseBody";

    private BufferedSource bufferedSource;

    private ResponseBody responseBody;

    private ProgressListener listener;

    public ProgressResponseBody(String url, ResponseBody responseBody) {
        this.responseBody = responseBody;
        listener = ProgressInterceptor.LISTENER_MAP.get(url);
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new ProgressSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private class ProgressSource extends ForwardingSource {

        long totalBytesRead = 0;

        int currentProgress;

        ProgressSource(Source source) {
            super(source);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            long fullLength = responseBody.contentLength();
            if (bytesRead == -1) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += bytesRead;
            }
            int progress = (int) (100f * totalBytesRead / fullLength);
            Log.d(TAG, "download progress is " + progress);
            if (listener != null && progress != currentProgress) {
                listener.onProgress(progress);
            }
            if (listener != null && totalBytesRead == fullLength) {
                listener = null;
            }
            currentProgress = progress;
            return bytesRead;
        }
    }

}
/**
 * 其实这段代码也不是很难，下面我来简单解释一下。首先，我们定义了一个ProgressResponseBody的构造方法，该构造方法中要求传入一个url参数和一个ResponseBody参数。
 * 那么很显然，url参数就是图片的url地址了，而ResponseBody参数则是OkHttp拦截到的原始的ResponseBody对象。
 * 然后在构造方法中，我们调用了ProgressInterceptor中的LISTENER_MAP来去获取该url对应的监听器回调对象，有了这个对象，待会就可以回调计算出来的下载进度了。

 由于继承了ResponseBody类之后一定要重写contentType()、contentLength()和source()这三个方法，
 我们在contentType()和contentLength()方法中直接就调用传入的原始ResponseBody的contentType()和contentLength()方法即可，这相当于一种委托模式。
 但是在source()方法中，我们就必须加入点自己的逻辑了，因为这里要涉及到具体的下载进度计算。

 那么我们具体看一下source()方法，这里先是调用了原始ResponseBody的source()方法来去获取Source对象，接下来将这个Source对象封装到了一个ProgressSource对象当中，
 最终再用Okio的buffer()方法封装成BufferedSource对象返回。

 那么这个ProgressSource是什么呢？它是一个我们自定义的继承自ForwardingSource的实现类。ForwardingSource也是一个使用委托模式的工具，
 它不处理任何具体的逻辑，只是负责将传入的原始Source对象进行中转。但是，我们使用ProgressSource继承自ForwardingSource，那么就可以在中转的过程中加入自己的逻辑了。

 可以看到，在ProgressSource中我们重写了read()方法，然后在read()方法中获取该次读取到的字节数以及下载文件的总字节数，
 并进行一些简单的数学计算就能算出当前的下载进度了。这里我先使用Log工具将算出的结果打印了一下，再通过前面获取到的回调监听器对象将结果进行回调。
 */
