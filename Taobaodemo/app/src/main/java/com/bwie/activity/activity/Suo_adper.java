package com.bwie.activity.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.activity.R;
import com.bwie.activity.bean.Saosuo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/6 19:52
 */
public class Suo_adper extends RecyclerView.Adapter<Suo_adper.MyViewHolder>{
    private Context context;
    private List<Saosuo.ResultBean.WallBean.DocsBean> data;

    public Suo_adper(Context context, List<Saosuo.ResultBean.WallBean.DocsBean> data) {
        this.context = context;
        this.data = data;
    }

    //创建view设置给Viewholder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sousuo_item, null);
        MyViewHolder myViewHolder=new MyViewHolder(view);

        return myViewHolder;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

             String img = data.get(position).getImg();
             Picasso.with(context).load(img).placeholder(R.mipmap.ic_launcher).into(holder.imageView);
             String title = data.get(position).getTitle();
             double price = data.get(position).getPrice();
             holder.title.setText(title);
             holder.money.setText("￥"+price+"");


    }

    //数据长度
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
        private TextView title;
        private TextView money;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.sousou_imag);
            title= (TextView) itemView.findViewById(R.id.sousuo_title);
            money= (TextView) itemView.findViewById(R.id.sousuo_money);

        }
    }
}
