package com.example.finalwork.Adapter.PersonalCenter;

import android.annotation.SuppressLint;
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
import com.example.finalwork.model.Share;
import com.example.finalwork.Adapter.PersonalCenter.updownRemove.ItemTouchHelperAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerLineAdapter extends RecyclerView.Adapter<RecyclerLineAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    Context context;
    List<Share> data;
    private OnItemClickListener mOnItemClickListener; //删除的监听

    public RecyclerLineAdapter(Context context) {
        this.context = context;
        data = new ArrayList<Share>();
    }
    public void setList(List<Share> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.personal_center_my_publish_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder,  int position) {

        //左滑删除事件
        holder.publish_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, holder.getAdapterPosition());
                }
            }
        });

        Share shareItem = data.get(position);
        Glide.with(context).load(shareItem.getShareImg()).into(holder.img);
        holder.userName.setText(shareItem.getUserName());
        holder.title.setText(shareItem.getShareTitle());
        holder.label.setText(shareItem.getShareLabel());
        holder.publishTime.setText(shareItem.getPublishTime());
        holder.content.setText(shareItem.getShareContent());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(data,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView userName;
        TextView title;
        TextView label;
        TextView publishTime;
        TextView content;
        TextView publish_delete; //左滑删除的textView


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_recy_item_4_pic);
            userName = itemView.findViewById(R.id.tv_recy_item_4_name);
            title = itemView.findViewById(R.id.tv_recy_item_4_title);
            label = itemView.findViewById(R.id.tv_recy_item_4_label);
            publishTime = itemView.findViewById(R.id.tv_recy_item_4_publishTime);
            content = itemView.findViewById(R.id.tv_recy_item_4_content);
            publish_delete = itemView.findViewById(R.id.publish_delete);

        }
    }

}
