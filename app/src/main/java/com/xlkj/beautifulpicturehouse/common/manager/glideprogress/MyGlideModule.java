package com.xlkj.beautifulpicturehouse.common.manager.glideprogress;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/1/23.
 * 接下来，新建一个MyGlideModule类并实现GlideModule接口，
 * 然后在registerComponents()方法中将我们刚刚创建的OkHttpGlideUrlLoader和OkHttpFetcher注册到Glide当中，将原来的HTTP通讯组件给替换掉，如下所示：
 *  最后，为了让Glide能够识别我们自定义的MyGlideModule，还得在AndroidManifest.xml文件当中加入如下配置才行：
 <meta-data
 android:name="com.xlkj.beautifulpicturehouse.common.manager.glideprogress.MyGlideModule"
 android:value="GlideModule" />
 OK，这样我们就把Glide中的HTTP通讯组件成功替换成OkHttp了。
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
//        glide.register(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory());

//        接下来我们需要启用这个拦截器，修改MyGlideModule中的代码，如下所示：
//        这里我们创建了一个OkHttpClient.Builder，然后调用addInterceptor()方法将刚才创建的ProgressInterceptor添加进去，
//        最后将构建出来的新OkHttpClient对象传入到OkHttpGlideUrlLoader.Factory中即可。
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ProgressInterceptor());
        OkHttpClient okHttpClient = builder.build();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory(okHttpClient));
    }
}
