package com.coolweather.xuexin3.pingLunAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterPingLun extends RecyclerView.Adapter<AdapterPingLun.ViewHolder>{

    //list
    AdapterZhuiPing listAdapter;
    List<PingLun.RepliesBean> list = new ArrayList<>();

    List<PingLun> mList;
    private View mView;


    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
        void onHuiFuClick(List<PingLun.RepliesBean> list,View view,RecyclerView rc);
    }

    private OnitemClick onitemClick;   //定义点击事件接口

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }





    public AdapterPingLun(List<PingLun> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pinglun, parent,false);
        ViewHolder viewHolder =new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PingLun p = mList.get(position);          //获取第pos个信息

        if(p.getLeave().getPhoto()!=null){
            Glide.with(mView.getContext()).load(p.getLeave().getPhoto()).into(holder.img);
        }else {
            holder.img.setImageResource(R.drawable.img_login_touxiang);
        }
        String s = p.getLeave().getLwNickName();
        if(s!=null)
            holder.neck.setText(s);
        s = p.getLeave().getDate();
        if(s!=null)
            holder.time.setText(s);
        s = p.getLeave().getContent();
        if(s!=null)
            holder.content.setText(": "+s);
        holder.huifi.setText("查看回复:"+""+p.getReplies().size());

        if (onitemClick != null) {
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    onitemClick.onItemClick(position);
                }
            });
            holder.huifi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onitemClick.onHuiFuClick(p.getReplies(),mView,holder.mRecyclerView);
                }
            });
        }


    }


//    /**
//     * list
//     * @param replies
//     */
//    public void initDatas(List<PingLun.RepliesBean> replies) {           //初始化数据
//        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view_pinglun);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        AdapterZhuiPing listAdapter = new AdapterZhuiPing(replies);
//        recyclerView.setAdapter(listAdapter);
//    }
//

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView neck;
        TextView time;
        TextView content;
        TextView huifi;
        RecyclerView mRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_pinglun_img);
            neck = itemView.findViewById(R.id.tv_pinglun_neck);
            time = itemView.findViewById(R.id.tv_pinglun_time);
            content = itemView.findViewById(R.id.tv_pinglun_content);
            huifi = itemView.findViewById(R.id.tv_pinglun_zhuiping);
            mRecyclerView = itemView.findViewById(R.id.recycler_view_pinglun);
        }
    }
}
