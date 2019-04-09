package com.xlkj.beautifulpicturehouse.common.manager.glideprogress;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/1/23.
 * 实现下载进度监听
 那么，将HTTP通讯组件替换成OkHttp之后，我们又该如何去实现监听下载进度的功能呢？这就要依靠OkHttp强大的拦截器机制了。
 我们只要向OkHttp中添加一个自定义的拦截器，就可以在拦截器中捕获到整个HTTP的通讯过程，然后加入一些自己的逻辑来计算下载进度，这样就可以实现下载进度监听的功能了。
 拦截器属于OkHttp的高级功能，不过即使你之前并没有接触过拦截器，我相信你也能轻松看懂本篇文章的，因为它本身并不难。
 确定了实现思路之后，那我们就开始动手吧。首先创建一个没有任何逻辑的空拦截器，新建ProgressInterceptor类并实现Interceptor接口，代码如下所示：
 这个拦截器中我们可以说是什么都没有做。就是拦截到了OkHttp的请求，然后调用proceed()方法去处理这个请求，最终将服务器响应的Response返回。
 */

public class ProgressInterceptor implements Interceptor {

    //然后我们在ProgressInterceptor中加入注册下载监听和取消注册下载监听的方法。修改ProgressInterceptor中的代码，如下所示
    //可以看到，这里使用了一个Map来保存注册的监听器，Map的键是一个URL地址。
    //之所以要这么做，是因为你可能会使用Glide同时加载很多张图片，而这种情况下，必须要能区分出来每个下载进度的回调到底是对应哪个图片URL地址的。
    static final Map<String, ProgressListener> LISTENER_MAP = new HashMap<>();

    public static void addListener(String url, ProgressListener listener) {
        LISTENER_MAP.put(url, listener);
    }

    public static void removeListener(String url) {
        LISTENER_MAP.remove(url);
    }

    //好的，现在计算下载进度的逻辑已经完成了，那么我们快点在拦截器当中使用它吧。修改ProgressInterceptor中的代码，如下所示：
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        ResponseBody body = response.body();
        Response newResponse = response.newBuilder().body(new ProgressResponseBody(url, body)).build();
        return newResponse;
    }
    /**
     * 这里也都是一些OkHttp的简单用法。我们通过Response的newBuilder()方法来创建一个新的Response对象，并把它的body替换成刚才实现的ProgressResponseBody，
     * 最终将新的Response对象进行返回，这样计算下载进度的逻辑就能生效了。
     代码写到这里，我们就可以来运行一下程序了。现在无论是加载任何网络上的图片，都应该是可以监听到它的下载进度的。
     */

}
