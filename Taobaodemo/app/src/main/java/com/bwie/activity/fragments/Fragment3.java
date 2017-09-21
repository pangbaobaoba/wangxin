package com.bwie.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.activity.R;
import com.bwie.activity.activity.Payactivity;
import com.bwie.activity.activity.Zhuce;
import com.bwie.activity.bean.GoodsBean;
import com.bwie.activity.bean.ShopCardBean;
import com.bwie.activity.bean.StoreBean;
import com.bwie.activity.utils.MyBaseExpandableListAdapter;
import com.bwie.activity.utils.OkHttp3Utils;
import com.bwie.activity.utils.SharedUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/1 20:32
 */
public class Fragment3 extends Fragment{



    //定义父列表项List数据集合
    List<Map<String, Object>> parentMapList = new ArrayList<Map<String, Object>>();
    //定义子列表项List数据集合
    List<List<Map<String, Object>>> childMapList_list = new ArrayList<List<Map<String, Object>>>();
    MyBaseExpandableListAdapter myBaseExpandableListAdapter;
    private View view;
    CheckBox id_cb_select_all;
    LinearLayout id_ll_normal_all_state;
    LinearLayout id_ll_editing_all_state;
    RelativeLayout id_rl_cart_is_empty;
    RelativeLayout id_rl_foot;
    private List<Map<String, Object>> childMapList;
    TextView id_tv_edit_all;
    private String delete="http://169.254.229.178/mobile/index.php?act=member_cart&op=cart_del ";
    private String path="http://169.254.229.178/mobile/index.php?act=member_cart&op=cart_list";
    Handler handler = new Handler() {



        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String obj = (String) msg.obj;
                    Log.i("tag", obj);
                    Gson gson = new Gson();
                    ShopCardBean shopCardBean = gson.fromJson(obj, ShopCardBean.class);
                    /***一级列表***/

                    List<ShopCardBean.DatasBean.CartListBean> cart_list = shopCardBean.getDatas().getCart_list();

                    for (int i = 0; i < cart_list.size(); i++) {
                        Map<String, Object> parentMap = new HashMap<String, Object>();
                        String store_name = cart_list.get(i).getStore_name();
                        String store_id = cart_list.get(i).getStore_id();
                        StoreBean storeBean = new StoreBean(store_id, store_name, false, false);
                        parentMap.put("parentName", storeBean);
                        parentMapList.add(parentMap);

                        //提供当前父列的子列数据
                        childMapList = new ArrayList<Map<String, Object>>();

                        for (int j = 0; j < cart_list.get(i).getGoods().size(); j++) {
                            List<ShopCardBean.DatasBean.CartListBean.GoodsBean> goods = cart_list.get(i).getGoods();
                            String goods_id = goods.get(j).getGoods_id();
                            String goods_image_url = goods.get(j).getGoods_image_url();
                            String goods_name = goods.get(j).getGoods_name();
                            String goods_num = goods.get(j).getGoods_num();
                            String store_name1 = goods.get(j).getStore_name();
                            // String goods_price = goods.get(j).getGoods_price();
                            double goods_price = Double.parseDouble(goods.get(j).getGoods_price());
                            // String goods_total = goods.get(j).getGoods_total();
                            Double goods_total = Double.parseDouble(goods.get(j).getGoods_total());
                            int num = Integer.parseInt(goods.get(j).getGoods_num());
                            String cart_id = goods.get(j).getCart_id();
                            GoodsBean goodsBean = new GoodsBean(goods_id, goods_name, goods_image_url, store_name1, goods_price, goods_total, num, GoodsBean.STATUS_VALID, false, false);
                            goodsBean.setCard_id(cart_id);
                            Map<String, Object> childMap = new HashMap<String, Object>();
                            childMap.put("childName", goodsBean);
                            childMapList.add(childMap);
                        }
                        childMapList_list.add(childMapList);
                    }
                    /***二级列表***/
                    //数据
                    myBaseExpandableListAdapter.setData(parentMapList, childMapList_list);
                    expandableListView.setAdapter(myBaseExpandableListAdapter);

                    for (int i = 0; i < parentMapList.size(); i++) {
                        expandableListView.expandGroup(i);
                    }


                    expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });

                    ImageView id_iv_back = (ImageView) view.findViewById(R.id.id_iv_back);
                    id_iv_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "click :back", Toast.LENGTH_SHORT).show();
                        }
                    });
                    getIterface(myBaseExpandableListAdapter);

                    getd();
                   delete();
                    break;
            }
        }
    };
    private ExpandableListView expandableListView;

    private String loginkey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

   myBaseExpandableListAdapter = new  MyBaseExpandableListAdapter(getActivity());
        SharedUtil instances = SharedUtil.getInstances();
        Boolean aBoolean = (Boolean) instances.getValueByKey(getActivity(), "boolean", false);
        loginkey = (String) instances.getValueByKey(getActivity(), "loginkey", "sdfgh");

        if(aBoolean){
            view = View.inflate(getActivity(), R.layout.fragment03, null);
            expandableListView = (ExpandableListView) view.findViewById(R.id.id_elv_listview);
            getData(loginkey);

        }else{
            Intent intent=new Intent(getActivity(), Zhuce.class);
            startActivity(intent);
        }



        return view;
    }


    public void getd() {
        id_ll_normal_all_state = (LinearLayout) view.findViewById(R.id.id_ll_normal_all_state);
        id_ll_editing_all_state = (LinearLayout) view.findViewById(R.id.id_ll_editing_all_state);
        id_rl_cart_is_empty = (RelativeLayout) view.findViewById(R.id.id_rl_cart_is_empty);
        TextView id_tv_save_star_all = (TextView) view.findViewById(R.id.id_tv_save_star_all);

        id_tv_save_star_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "收藏多选商品", Toast.LENGTH_SHORT).show();
            }
        });
        TextView id_tv_delete_all = (TextView) view.findViewById(R.id.id_tv_delete_all);
        id_tv_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBaseExpandableListAdapter.removeGoods();
                // Toast.makeText(MainActivity.this, "删除多选商品", Toast.LENGTH_SHORT).show();
            }
        });
        id_tv_edit_all = (TextView) view.findViewById(R.id.id_tv_edit_all);

        id_tv_edit_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    if (MyBaseExpandableListAdapter.EDITING.equals(tv.getText())) {
                        myBaseExpandableListAdapter.setupEditingAll(true);
                        tv.setText(MyBaseExpandableListAdapter.FINISH_EDITING);
                        changeFootShowDeleteView(true);//这边类似的功能 后期待使用观察者模式
                    } else {
                        myBaseExpandableListAdapter.setupEditingAll(false);
                        tv.setText(MyBaseExpandableListAdapter.EDITING);
                        changeFootShowDeleteView(false);//这边类似的功能 后期待使用观察者模式
                    }

                }
            }
        });
        id_cb_select_all = (CheckBox) view.findViewById(R.id.id_cb_select_all);
        id_cb_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) v;
                    myBaseExpandableListAdapter.setupAllChecked(checkBox.isChecked());
                }
            }
        });


    }
    public void getData(String key) {
        //请求网络

        Map<String, String> map = new HashMap<>();
        Log.i("userkey", key);
        map.put("key", key);
        OkHttp3Utils.doPost(path, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = 1;
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
    }
    public void changeFootShowDeleteView(boolean showDeleteView) {

        if (showDeleteView) {
            id_tv_edit_all.setText(MyBaseExpandableListAdapter.FINISH_EDITING);

            id_ll_normal_all_state.setVisibility(View.INVISIBLE);
            id_ll_editing_all_state.setVisibility(View.VISIBLE);
        } else {
            id_tv_edit_all.setText(MyBaseExpandableListAdapter.EDITING);

            id_ll_normal_all_state.setVisibility(View.VISIBLE);
            id_ll_editing_all_state.setVisibility(View.INVISIBLE);
        }
    }


    public void getIterface(MyBaseExpandableListAdapter myBaseExpandableListAdapter) {
        final TextView id_tv_totalPrice = (TextView) view.findViewById(R.id.id_tv_totalPrice);

        final TextView id_tv_totalCount_jiesuan = (TextView) view.findViewById(R.id.id_tv_totalCount_jiesuan);
        id_tv_totalCount_jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click：结算", Toast.LENGTH_SHORT).show();
                List<GoodsBean> list=new ArrayList<GoodsBean>();
                for(int i=0;i<childMapList.size();i++){
                    GoodsBean childName = (GoodsBean) childMapList.get(i).get("childName");
                    boolean checked = childName.isChecked();
                    if(checked){
                        list.add(childName);

                    }
                }
               Intent intent=new Intent(getActivity(), Payactivity.class);
               Bundle bundle=new Bundle();
                bundle.putSerializable("goodsBean", (Serializable) list);
                Log.i("list",list.toString());
             intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });

        myBaseExpandableListAdapter.setOnGoodsCheckedChangeListener(new MyBaseExpandableListAdapter.OnGoodsCheckedChangeListener() {
            @Override
            public void onGoodsCheckedChange(int totalCount, double totalPrice) {
                id_tv_totalPrice.setText(String.format(getString(R.string.total), totalPrice + ""));
                id_tv_totalCount_jiesuan.setText(String.format(getString(R.string.jiesuan), totalCount + ""));
            }
        });

        myBaseExpandableListAdapter.setOnAllCheckedBoxNeedChangeListener(new MyBaseExpandableListAdapter.OnAllCheckedBoxNeedChangeListener() {
            @Override
            public void onCheckedBoxNeedChange(boolean allParentIsChecked) {
                id_cb_select_all.setChecked(allParentIsChecked);
            }
        });

        myBaseExpandableListAdapter.setOnEditingTvChangeListener(new MyBaseExpandableListAdapter.OnEditingTvChangeListener() {
            @Override
            public void onEditingTvChange(boolean allIsEditing) {

                changeFootShowDeleteView(allIsEditing);//这边类似的功能 后期待使用观察者模式

            }
        });

        myBaseExpandableListAdapter.setOnCheckHasGoodsListener(new MyBaseExpandableListAdapter.OnCheckHasGoodsListener() {
            @Override
            public void onCheckHasGoods(boolean isHasGoods) {
                setupViewsShow(isHasGoods);
            }
        });
    }

    private void setupViewsShow(boolean isHasGoods) {
        id_rl_foot = (RelativeLayout) view.findViewById(R.id.id_rl_foot);
        if (isHasGoods) {
            expandableListView.setVisibility(View.VISIBLE);
            id_rl_cart_is_empty.setVisibility(View.GONE);
            id_rl_foot.setVisibility(View.VISIBLE);
            id_tv_edit_all.setVisibility(View.VISIBLE);
        } else {
            expandableListView.setVisibility(View.GONE);
            id_rl_cart_is_empty.setVisibility(View.VISIBLE);
            id_rl_foot.setVisibility(View.GONE);
            id_tv_edit_all.setVisibility(View.GONE);
        }
    }

    public void delete(){
        myBaseExpandableListAdapter.setiDelate(new MyBaseExpandableListAdapter.IDelate() {
            @Override
            public void delete(int groupPosition, int childPosition) {
                GoodsBean childName = (GoodsBean) childMapList_list.get(groupPosition).get(childPosition).get("childName");
                String goods = childName.getCard_id();
                Map<String,String> map = new HashMap<String, String>();
                Log.i("delete",goods+"");
                map.put("key",loginkey);
                map.put("cart_id",goods);
                OkHttp3Utils.doPost(delete, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
            }
        });
        myBaseExpandableListAdapter.setiBay(new MyBaseExpandableListAdapter.IBay() {
            @Override
            public void boy(int groundPosintion, int childPosition) {
                GoodsBean childName = (GoodsBean) childMapList.get(childPosition).get("childName");
               // childName.setIsChecked(true);
            }
        });
    }

}
