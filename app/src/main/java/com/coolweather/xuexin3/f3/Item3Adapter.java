package com.coolweather.xuexin3.f3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.R;

import java.util.List;


public class Item3Adapter extends RecyclerView.Adapter<Item3Adapter.ViewHolder> {

    List<ItemF3> mList;
    private View mView;

    public Item3Adapter(List<ItemF3> list){
        mList = list;
    }

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }

    private OnitemClick onitemClick;   //定义点击事件接口

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_f3, parent,false);
        ViewHolder viewHolder = new ViewHolder(mView);

        //点击事件
        mView.findViewById(R.id.ll_f3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(view.getContext(), "你摸我干嘛", Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemF3 p = mList.get(position);          //获取第pos个信息
        if(position<4){
            holder.img.setImageResource(Integer.valueOf(p.img));
        }else {
            Glide.with(mView.getContext()).load(p.img).into(holder.img);
        }
        if("".equals(p.name)|| p.name==null){
            holder.name.setText("好友");
        }else {
            holder.name.setText(p.name);
        }
        holder.time.setText(p.time);
        if(p.massage.length()>10){
            holder.massage.setText(p.massage.substring(0, 10)+"...");
        }else {
            holder.massage.setText(p.massage);
        }


        if (onitemClick != null) {
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    onitemClick.onItemClick(position);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        ImageView img;
        TextView name;
        TextView time;
        TextView massage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.ll_f3);
            img = itemView.findViewById(R.id.img_f3_img);
            name = itemView.findViewById(R.id.tv_f3_name);
            time = itemView.findViewById(R.id.tv_f3_time);
            massage = itemView.findViewById(R.id.tv_f3_massage);
        }
    }
}
