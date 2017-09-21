package com.bwie.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bwie.activity.R;
import com.bwie.activity.bean.Laolishi;
import com.bwie.activity.bean.Saosuo;
import com.bwie.activity.utils.Laolishiapder;
import com.bwie.activity.utils.OkHttp3Utils;
import com.bwie.activity.utils.Sou_utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/5 21:04
 */
public class Shangping extends AppCompatActivity{
    private RecyclerView rv;
    private String url;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           switch (msg.what){
               case 0:


                         String obj = (String) msg.obj;
                         String substring = obj.substring(11, obj.length() - 2);
                         Gson gson=new Gson();
                         Saosuo sousuo = gson.fromJson(substring, Saosuo.class);
                         Saosuo.ResultBean result = sousuo.getResult();
                         Saosuo.ResultBean.WallBean wall = result.getWall();
                         List<Saosuo.ResultBean.WallBean.DocsBean> docs = wall.getDocs();
                         Log.i("tag",docs.toString());
                         Suo_adper adper=new Suo_adper(Shangping.this,docs);
                         rv.setAdapter(adper);


                   break;
               case 1:
                   String obj1 = (String) msg.obj;
                   Gson gson1=new Gson();
                   Laolishi laolishi = gson1.fromJson(obj1, Laolishi.class);
                   Laolishi.DatasBean datas = laolishi.getDatas();
                   final List<Laolishi.DatasBean.GoodsListBean> goods_list = datas.getGoods_list();
                   Laolishiapder laolishi1=new Laolishiapder(goods_list,Shangping.this);
                   viewlistview.setAdapter(laolishi1);
                   viewlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                         Intent intent=new Intent(Shangping.this,Tiaozhuangwc.class);
                           String goods_id = goods_list.get(i).getGoods_id();
                           String goods_image_url = goods_list.get(i).getGoods_image_url();
                           intent.putExtra("goods_id",goods_id);
                           intent.putExtra("imag",goods_image_url);
                           startActivity(intent);
                       }
                   });

                   break;
           }
        }
    };
    private ViewPager viewpager;
    private List<View> arrayList;
    private String path;
    private ListView viewlistview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shangpin);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        arrayList=new ArrayList<>();
        View view = View.inflate(Shangping.this, R.layout.shousuobuju1, null);
        View view1= View.inflate(Shangping.this, R.layout.shousuo2, null);
        View view2 = View.inflate(Shangping.this, R.layout.shousuo3, null);
        arrayList.add(view);
        arrayList.add(view1);
        arrayList.add(view2);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        viewlistview = (ListView) view1.findViewById(R.id.viewlistview);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(Shangping.this,2);
        rv.setLayoutManager(gridLayoutManager);
       //对劳力士页面进行分页加载

        //对viewpager进行监听
        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
               container.addView(arrayList.get(position));

                return arrayList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
               container.removeView((View)object);
            }
        });
        Intent intent = getIntent();
        String string = intent.getStringExtra("string");
        Log.i("asdfgh",string);

        if(string.equals("劳力士")){
            Log.i("sdf",string);
            path = "http://169.254.229.178/mobile/index.php?act=goods&op=goods_list&page=100";
            getserver(path);

        }else {
            String s = Sou_utils.jiami(Sou_utils.convertStringToUTF8(string));
            url = "http://list.mogujie.com/search?_version=61&ratio=3%3A4&q="+s+"&cKey=46&minPrice=&_mgjuuid=dbcc6b5c-fcf7-49f4-b807-3e85fbcccc5b&ppath=&page=1&maxPrice=&sort=pop&userId=&cpc_offset=&priceList=&_=1504520539364&callback=jsonp1";

            //获取数据
            getData(url);
        }
  }



    //获取数据
    private void getData(String url) {
        OkHttp3Utils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                Message message=handler.obtainMessage(0,string);
                handler.sendMessage(message);

            }
        });
    }
    private void getserver(String url) {
        OkHttp3Utils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                Message message=handler.obtainMessage(1,string);
                handler.sendMessage(message);

            }
        });
    }
}
