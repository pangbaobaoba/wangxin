package com.bwie.activity.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.activity.R;
import com.bwie.activity.activity.Head_sousuo;
import com.bwie.activity.bean.Lunbuo;
import com.bwie.activity.bean.Shouye;
import com.bwie.activity.utils.CustomViewPager;
import com.bwie.activity.utils.MarqueeTextView;
import com.bwie.activity.utils.OkHttp3Utils;
import com.bwie.activity.utils.Shouye_recycadper;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import activity.bwie.com.viewpagerlibrary.utils.AlphaPageTransformer;
import activity.bwie.com.viewpagerlibrary.utils.NonPageTransformer;
import activity.bwie.com.viewpagerlibrary.utils.RotateDownPageTransformer;
import activity.bwie.com.viewpagerlibrary.utils.RotateUpPageTransformer;
import activity.bwie.com.viewpagerlibrary.utils.RotateYTransformer;
import activity.bwie.com.viewpagerlibrary.utils.ScaleInTransformer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/1 20:32
 */
public class Fragment1 extends Fragment  {
   private String path="http://m.yunifang.com/yunifang/mobile/home";
    private String path1="http://qiang.mogujie.com/jsonp/actGroupItem/1?callback=jQuery21104587953138117029_1504264031748&groupKey=11q&_=1504264031749";
    private EditText myhead_ed;
    private CustomViewPager vp;
    private View view;
    private List<String> list;
    private LinearLayout ll;
    private LinearLayout ll_text;
    private String [] textArrays = new String[]{"this is content No.1","this is content No.2","this is content No.3"};
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String obj = (String) msg.obj;
                    Gson gson=new Gson();
                    Lunbuo lunbuo = gson.fromJson(obj, Lunbuo.class);
                    Lunbuo.DataBean data = lunbuo.getData();
                    List<Lunbuo.DataBean.Ad1Bean> ad1 = data.getAd1();
                    list=new ArrayList<>();
                    for (int i=0;i<ad1.size();i++){
                        String image = ad1.get(i).getImage();
                        list.add(image);


                    }

                    List<Lunbuo.DataBean.Ad5Bean> ad5 = data.getAd5();
                    for(int i=0;i<ad5.size();i++){
                        String image = ad5.get(i).getImage();
                        ImageView imageView=new ImageView(getContext());
                        Picasso.with(getContext()).load(image).into(imageView);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
                        params.leftMargin=60;
                        params.topMargin=10;
                        ll.addView(imageView, params);
                        String title = ad5.get(i).getTitle();
                        TextView textView=new TextView(getContext());
                        LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(100,40);
                        params1.leftMargin=62;
                        params1.topMargin=15;
                        textView.setTextColor(Color.GRAY);
                        textView.setText(title);
                        ll_text.addView(textView,params1);
                    }
                    vp.setData(list);
                    List<Lunbuo.DataBean.Ad8Bean> ad8 = data.getAd8();
                    for(int i=0;i<ad8.size();i++){
                        switch (i){
                            case 0:
                                String image = ad8.get(i).getImage();
                                ImageView imageView=new ImageView(getContext());
                                Picasso.with(getContext()).load(image).into(imageView);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.leftMargin=20;
                                params.topMargin=10;
                                ll_right.addView(imageView, params);
                                break;
                            case 1:
                                String image1 = ad8.get(i).getImage();

                                String description = ad8.get(i).getDescription();

                                TextView textView1=new TextView(getContext());
                                textView1.setText(description);

                                ImageView imageView1=new ImageView(getContext());
                                Picasso.with(getContext()).load(image1).into(imageView1);
                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                               // LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                               // LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params1.leftMargin=15;
                                params1.topMargin=10;


                                textView1.setTextColor(Color.BLACK);
                                ll_left.addView(imageView1, params1);

                                ll_left.addView(textView1,params1);
                                break;
                            case 2:
                                String image2 = ad8.get(i).getImage();

                                String description2 = ad8.get(i).getDescription();

                                TextView textView2=new TextView(getContext());
                                textView2.setText(description2);

                                ImageView imageView2=new ImageView(getContext());
                                Picasso.with(getContext()).load(image2).into(imageView2);
                                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                // LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                // LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params2.leftMargin=15;
                                params2.topMargin=10;


                                textView2.setTextColor(Color.BLACK);
                                ll_left1.addView(imageView2, params2);

                                ll_left1.addView(textView2,params2);
                                break;
                        }

                    }
                    break;
                case 1:
                    String  obj1 = (String) msg.obj;
                    String substring = obj1.substring(41, obj1.length() - 1);
                    Gson gson1=new Gson();
                    Shouye shouye = gson1.fromJson(substring, Shouye.class);
                    Shouye.DataBean data1 = shouye.getData();
                    List<Shouye.DataBean.ItemListBean> itemList = data1.getItemList();
                    Shouye_recycadper shouye_recycadper=new Shouye_recycadper(getActivity(),itemList);
                    recyclerview.setAdapter(shouye_recycadper);
                    break;
            }
        }
    };
    private MarqueeTextView marqueetextView;
    private LinearLayout ll_right;
    private RelativeLayout ll_left;
    private RelativeLayout ll_left1;

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    int[] imgRes = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d,
            R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.i};
    private String title;
    private RecyclerView recyclerview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment1, null);
        myhead_ed = (EditText) view.findViewById(R.id.myhead_ed);
       vp = (CustomViewPager) view.findViewById(R.id.custom_vp);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        ll_text = (LinearLayout) view.findViewById(R.id.ll_text);
        marqueetextView = (MarqueeTextView) view.findViewById(R.id.marqueetextView);
        ll_right = (LinearLayout) view.findViewById(R.id.ll_right);
        ll_left = (RelativeLayout) view.findViewById(R.id.ll_left);
        ll_left1 = (RelativeLayout) view.findViewById(R.id.ll_left1);
        marqueetextView.setTextArraysAndClickListener(textArrays);
        mViewPager = (ViewPager) view.findViewById(R.id.id_viewpager);
        //展示多彩轮播图
        mViewPager = (ViewPager) view.findViewById(R.id.id_viewpager);
        recyclerview = (RecyclerView) view.findViewById(R.id.recycylerview);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        recyclerview.setLayoutManager(gridLayoutManager);
        mViewPager.setPageMargin(40);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter = new PagerAdapter()
        {
            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                ImageView view = new ImageView(getActivity());
//                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                view.setLayoutParams(lp);
//                view.setText(position + ":" + view);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
//                view.setBackgroundColor(Color.parseColor("#44ff0000"));
                view.setImageResource(imgRes[position]);
                container.addView(view);
//                view.setAdjustViewBounds(true);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
                container.removeView((View) object);
            }

            @Override
            public int getCount()
            {
                return imgRes.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object o)
            {
                return view == o;
            }
        });
        mViewPager.setPageTransformer(true, new AlphaPageTransformer());
        // 点击调转
        myhead_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Head_sousuo.class);
                startActivity(intent);

            }
        });

        OkHttp3Utils okHttp3Utils=new OkHttp3Utils();
        OkHttp3Utils.doGet(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message message=new Message();
                message.what=0;
                message.obj=string;
               handler.sendMessage(message);
            }
        });
      okHttp3Utils.doGet(path1, new Callback() {
          @Override
          public void onFailure(Call call, IOException e) {

          }

          @Override
          public void onResponse(Call call, Response response) throws IOException {

              String string = response.body().string();
              Message message=new Message();
              message.what=1;
              message.obj=string;
              handler.sendMessage(message);
          }
      });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        vp.startSwith();
    }

    @Override
    public void onPause() {
        super.onPause();
        vp.stopSwith();
    }

    @Override
    public void onDestroy() {
        marqueetextView.releaseResources();
        super.onDestroy();
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        String[] effects = this.getResources().getStringArray(R.array.magic_effect);
       for (String effect : effects)
          menu.add(effect);
      return true;
    }
    //多彩轮播图
//  @Override
//  public boolean onCreateOptionsMenu(Menu menu)
//  {
//      String[] effects = this.getResources().getStringArray(R.array.magic_effect);
//      for (String effect : effects)
//          menu.add(effect);
//      return true;
//  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        String title = item.getTitle().toString();
        mViewPager.setAdapter(mAdapter);

        if ("RotateDown".equals(title))
        {
            mViewPager.setPageTransformer(true, new RotateDownPageTransformer());
        } else if ("RotateUp".equals(title))
        {
            mViewPager.setPageTransformer(true, new RotateUpPageTransformer());
        } else if ("RotateY".equals(title))
        {
            mViewPager.setPageTransformer(true, new RotateYTransformer(45));
        } else if ("Standard".equals(title))
        {
//            mViewPager.setClipChildren(false);
            mViewPager.setPageTransformer(true, NonPageTransformer.INSTANCE);
        } else if ("Alpha".equals(title))
        {
//            mViewPager.setClipChildren(false);
            mViewPager.setPageTransformer(true, new AlphaPageTransformer());
        } else if ("ScaleIn".equals(title))
        {
            mViewPager.setPageTransformer(true, new ScaleInTransformer());
        } else if ("RotateDown and Alpha".equals(title))
        {
            mViewPager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer()));
        }else if ("RotateDown and Alpha And ScaleIn".equals(title))
        {
            mViewPager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer(new ScaleInTransformer())));
        }

        setTitle(title);

        return true;
    }

    public void setTitle(String title) {

        this.title = title;
    }
}
