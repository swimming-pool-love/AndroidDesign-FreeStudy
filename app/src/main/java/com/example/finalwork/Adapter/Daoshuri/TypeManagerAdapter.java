package com.example.finalwork.Adapter.Daoshuri;


import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.finalwork.R;
import com.example.finalwork.Helper.AssetsUtils;
import com.example.finalwork.model.TypeBean;

import java.util.List;

/**
 * 分类管理适配器
 */
public class TypeManagerAdapter extends RecyclerView.Adapter<TypeManagerAdapter.TypeHolder> {
    List<TypeBean> typeBeans;
    private Context context;
    OnLoginClick onLoginClick;

    public void setOnLoginClick(OnLoginClick onLoginClick) {
        this.onLoginClick = onLoginClick;
    }

    public void setTypeBeans(List<TypeBean> typeBeans) {
        this.typeBeans = typeBeans;
    }

    @Override
    public TypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.daoshuri_item_type_manager, parent, false);
        return new TypeHolder(inflate);
    }

    @Override
    public void onBindViewHolder(TypeHolder holder, @SuppressLint("RecyclerView") final int position) {
        TypeBean typeBean = typeBeans.get(position);
        holder.tvType.setText(typeBean.getName());
        holder.ivType.setImageBitmap(AssetsUtils.readBitmapFromAssets(context.getResources().getAssets(), typeBean.getImageRes()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLoginClick.onLongClick(position);
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeBeans == null ? 0 : typeBeans.size();
    }

    class TypeHolder extends RecyclerView.ViewHolder {
        private ImageView ivType;
        private TextView tvType;

        public TypeHolder(View itemView) {
            super(itemView);
            ivType = itemView.findViewById(R.id.iv_type);
            tvType = itemView.findViewById(R.id.tv_type);
        }
    }

    public interface OnLoginClick {
        void onLongClick(int position);

        void onClick(int position);
    }
}
