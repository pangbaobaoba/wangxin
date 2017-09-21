package com.bwie.activity.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.activity.R;
import com.bwie.activity.bean.Shouye;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/11 20:06
 */
public class Shouye_recycadper extends RecyclerView.Adapter<Shouye_recycadper.Myholderpager>{

    private Context context;
    private List<Shouye.DataBean.ItemListBean> list;

    public Shouye_recycadper(Context context, List<Shouye.DataBean.ItemListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Myholderpager onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.shouyeitem, null);
        Myholderpager myholderpager=new Myholderpager(inflate);
        return myholderpager;
    }

    @Override
    public void onBindViewHolder(Myholderpager holder, int position) {
        String image = list.get(position).getImage();
        Picasso.with(context).load(image).into(holder.imageview);
        String title = list.get(position).getTitle();
        holder.textview.setText(title);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myholderpager extends RecyclerView.ViewHolder {
        private  ImageView imageview;
        private  TextView textview;

        public Myholderpager(View itemView) {
            super(itemView);
            imageview = (ImageView) itemView.findViewById(R.id.shouyeitem_image);
            textview = (TextView) itemView.findViewById(R.id.shouyeitem_text);

        }
    }
}
