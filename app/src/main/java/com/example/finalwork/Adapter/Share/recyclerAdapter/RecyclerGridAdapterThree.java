package com.example.finalwork.Adapter.Share.recyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalwork.R;

import java.util.List;
import java.util.Map;

public class RecyclerGridAdapterThree extends RecyclerView.Adapter<RecyclerGridAdapterThree.ViewHolder>{
    Context context;
    List<Map<String, Object>> data;

    public RecyclerGridAdapterThree(Context context, List<Map<String, Object>> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerGridAdapterThree.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.share_dairy_item, viewGroup, false);
        return new RecyclerGridAdapterThree.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerGridAdapterThree.ViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).get("shareImg")).into(holder.img);
        holder.name.setText(data.get(position).get("userName").toString());
        holder.time.setText(data.get(position).get("publishTime").toString());
        holder.title.setText(data.get(position).get("shareTitle").toString());
        holder.content.setText(data.get(position).get("shareContent").toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;
        TextView time;
        TextView title;
        TextView content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_recy_item_3_pic);
            name = itemView.findViewById(R.id.tv_recy_item_3_name);
            time = itemView.findViewById(R.id.tv_recy_item_3_time);
            title = itemView.findViewById(R.id.tv_recy_item_3_title);
            content = itemView.findViewById(R.id.tv_recy_item_3_content);
        }
    }
}
