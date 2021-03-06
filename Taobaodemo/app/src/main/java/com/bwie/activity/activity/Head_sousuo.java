package com.bwie.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bwie.activity.R;
import com.example.searchview_library.SearchALG;
import com.example.searchview_library.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 1.搜索界面
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/1 20:08
 */
public class Head_sousuo extends AppCompatActivity{
    private SearchView searchView;
    private SearchALG searchALG;
    private Button tv_search_search;
    private EditText sousuo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.headsousuo);
        searchView = (SearchView) findViewById(R.id.searchView);
        initData();

        searchView.setOnSearchListener(new MyOnSearchListener());
        //得到搜索按钮
        tv_search_search = (Button) searchView.findViewById(R.id.tv_search_search);
        sousuo = (EditText) searchView.findViewById(R.id.et_search_content);
        searchView.setGetEdittext(new SearchView.getEdittext() {

            @Override
            public void data(Map<String, String> map) {
                for(String key:map.keySet()){
                    if(key.equals("1")){
                        String s = map.get(key);
                        Log.i("sadadasd",s);
                        if(s!=null){
                            Intent intent=new Intent(Head_sousuo.this,Shangping.class);
                            intent.putExtra("string",s);
                            startActivity(intent);
                        }

                    }
                }

            }
        });



    }
    private List<String> changedHintDatas;

    class MyOnSearchListener implements SearchView.OnSearchListener {

        /**
         * 搜索回调
         * @param searchText 进行搜索的文本
         */
        @Override
        public void onSearch(String searchText) {
            if (!TextUtils.isEmpty(searchText)) {
                Toast.makeText(Head_sousuo.this, "完成搜索" + searchText, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Head_sousuo.this, "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 刷新提示列表
         * @param changedText 改变后的文本
         */
        @Override
        public void onRefreshHintList(String changedText) {
            if (changedHintDatas == null) {
                changedHintDatas = new ArrayList<>();
            } else {
                changedHintDatas.clear();
            }
            if (TextUtils.isEmpty(changedText)) {
                return;
            }

            for (int i = 0; i < hint_datas.size(); i++) {
                String hint_data = hint_datas.get(i);
                boolean isAdd = searchALG.isAddToHintList(hint_data, changedText);
                if (isAdd) {
                    changedHintDatas.add(hint_datas.get(i));
                }
            }

            /**
             * 根据搜索框文本的变化，动态的改变提示的listView
             */
            searchView.updateHintList(changedHintDatas);

        }
    }

    //热搜数据
    private List<String> hot_datas;
    //提示列表数据
    private List<String> hint_datas;

    private void initData() {
        hot_datas = new ArrayList<>();
        hint_datas = new ArrayList<>();

        searchALG = new SearchALG(this);

       // for (int i = 0; i < 10; i++) {
            hot_datas.add("哈伦裤 ");
            hot_datas.add("小皮鞋 ");
            hot_datas.add("卫衣 ");
        //}

        //设置热搜数据显示的列数
        searchView.setHotNumColumns(2);
        //设置热搜数据
        searchView.setHotSearchDatas(hot_datas);

        /**
         * 设置提示数据的集合
         */
        for (int i = 0; i < 10; i++) {
            hint_datas.add("ts"+"安卓学习" + "Android Hint " + i + " 你好");
        }

        /**
         * 设置自动保存搜索记录
         */
        searchView.keepSearchHistory(true);

        //设置提示列表的最大显示列数
        searchView.setMaxHintLines(8);
        //设置保存搜索记录的个数
        searchView.setMaxHistoryRecordCount(6);

    }
}
