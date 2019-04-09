package com.xlkj.beautifulpicturehouse.module.mine.view.ui.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.xlkj.beautifulpicturehouse.R;
import com.xlkj.beautifulpicturehouse.common.constant.BeautyContent;
import com.xlkj.beautifulpicturehouse.common.manager.videoScanner.VideoInfo;
import com.xlkj.beautifulpicturehouse.common.view.ui.activity.base.BaseStateActivity;
import com.xlkj.beautifulpicturehouse.common.view.widget.ZoomImageView;
import com.xlkj.beautifulpicturehouse.module.home.view.ui.activity.PicDetailActivity;
import com.youth.banner.transformer.DefaultTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地图片详情页
 */
public class LocalPicDetailActivity extends BaseStateActivity {

    private ViewPager mViewPager;
    //上一个页面的数据
    private List<VideoInfo> mList;
    //上一个页面列表的位置
    private int mPosition;
    //图片数据
    private ArrayList<ZoomImageView> mImageViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_pic_detail);
        getDataUp();
        initView();
        initImg();
        setViewPager();
    }

    public void onResume() {
        super.onResume();
        try {
            MobclickAgent.onResume(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onPause() {
        super.onPause();
        try {
            MobclickAgent.onPause(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置viewpagr
     */
    private void setViewPager() {
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViews.size();
            }

            // 滑动切换的时候销毁当前的View
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object)
            {
                container.removeView(mImageViews.get(position));
            }

            // 每次滑动的时候生成的View
            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                container.addView(mImageViews.get(position));
                return mImageViews.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        mViewPager.setAdapter(adapter);
        mViewPager.setPageTransformer(false,new DefaultTransformer());
        jump2someone(adapter);
    }


    /**
     * 设置ViewPager跳转到指定页面
     * @param adapter
     */
    private void jump2someone(PagerAdapter adapter) {
        //先强制设定跳转到指定页面
        try {
            //参数mCurItem是系统自带的
            Field field = mViewPager.getClass().getField("mCurItem");
            field.setAccessible(true);
            field.setInt(mViewPager,mPosition);
        }catch (Exception e){
            e.printStackTrace();
        }
        //然后调用下面的函数刷新数据
        adapter.notifyDataSetChanged();
        //再调用setCurrentItem()函数设置一次
        mViewPager.setCurrentItem(mPosition);
    }

    /**
     * 初始化图片数据
     */
    private void initImg() {
        for (int i = 0; i < mList.size(); i++) {
            ZoomImageView imageView = new ZoomImageView(LocalPicDetailActivity.this);
            Glide.with(this).load(mList.get(i).getPath()).into(imageView);
            mImageViews.add(imageView);
        }
    }

    private void getDataUp() {
        mList = BeautyContent.getVideoInfo();
        mPosition = getIntent().getIntExtra("position",0);
    }

    private void initView() {
        //返回键
        findViewById(R.id.iv_back_alpd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.vp_alpd);
    }
}
