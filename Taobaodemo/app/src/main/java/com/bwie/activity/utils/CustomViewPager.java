package com.bwie.activity.utils;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.activity.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/7 20:34
 */
public class CustomViewPager extends RelativeLayout{
    private List<String> images;
    private ViewPager vp;
    private TextView tv_info;
    private LinearLayout ll_point;

    private List<ImageView> imageViews = new ArrayList<>();


    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //初始化控件
    private void init() {
        View view = inflate(getContext(), R.layout.custom_viewpager, null);
        //添加布局
        addView(view);
        vp = (ViewPager) view.findViewById(R.id.vp);

        ll_point = (LinearLayout) view.findViewById(R.id.ll_point);


    }

    //初始化小圆点
    private void initPoint() {
        for (int i = 0; i < images.size(); i++) {

            Log.i("tsfsfs",i+"");
            View view = new View(getContext());
            view.setBackgroundResource(R.drawable.point_bg);
            //动态生成小圆点
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.rightMargin = 20;
            ll_point.addView(view, params);
        }
        //给第一个选中
        View view = ll_point.getChildAt(0);
        view.setEnabled(false);
        //选中文本

    }

    //获取viewpager数据源
    private void initVp() {

        MyViewPagerAdapter adapter = new MyViewPagerAdapter();
        vp.setAdapter(adapter);
        //监听小圆点跟viewpager关联
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //   position %= images.length;
                int count = ll_point.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = ll_point.getChildAt(i);
                    //设置小圆点属性
                    view.setEnabled(i == (position %= images.size()) ? false : true);
                }
                //选中文本


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private Handler handler = new Handler();

    //开始轮播的方法
    public void startSwith() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //循环播放
                int currentItem = vp.getCurrentItem();
                currentItem++;
                vp.setCurrentItem(currentItem);
                handler.postDelayed(this, 2000);

            }
        }, 2000);
    }

    //停止轮播的方法
    public void stopSwith() {
        handler.removeCallbacksAndMessages(null);
    }

    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int i = position % images.size();
            ImageView imageView=new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(images.get(i)).into(imageView);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //    super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
    public void setData(List<String> list){
        this.images=list;
        init();
        initVp();
        initPoint();
    }

}
