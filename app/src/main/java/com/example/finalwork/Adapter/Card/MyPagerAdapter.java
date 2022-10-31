package com.example.finalwork.Adapter.Card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.finalwork.R;
import com.example.finalwork.model.ViewPagerItemBean;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private List<ViewPagerItemBean> mData;
    private Context mContext;

    public MyPagerAdapter(List<ViewPagerItemBean> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.cardviewpager_item, container, false);
        ImageView imageView1 = (ImageView) inflate.findViewById(R.id.img_card_item);
        TextView textView = (TextView) inflate.findViewById(R.id.title_card_item);
        Glide.with(mContext).load(mData.get(position).getImg_url()).into(imageView1);
        textView.setText(mData.get(position).getTilte_text() + "");
        container.addView(inflate);
        return inflate;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }
}
