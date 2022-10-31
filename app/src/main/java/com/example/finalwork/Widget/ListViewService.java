package com.example.finalwork.Widget;

import android.content.Context;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.example.finalwork.R;
import com.example.finalwork.Helper.MatterComparator;
import com.example.finalwork.Helper.Utility;
import com.example.finalwork.model.Matter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class ListViewService extends RemoteViewsService{


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFatory(this.getApplicationContext(),intent);
    }

    public static class ListRemoteViewsFatory implements RemoteViewsFactory{
        public Context mContext;

        private List<Matter> matters = new ArrayList<>();

        public  ListRemoteViewsFatory(Context context,Intent intent){
            mContext = context;
        }

        @Override
        public void onCreate() {
            matters  = DataSupport.findAll(Matter.class);
            matters = sortMatterList(matters);
        }


        private List<Matter> sortMatterList(List<Matter> matterList){
            Collections.sort(matterList,new MatterComparator());
            return  matterList;
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            matters.clear();
        }

        @Override
        public int getCount() {
            return matters.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(),R.layout.daoshuri_widget_list_item);
            Matter matter = matters.get(position);
            views.setTextViewText(R.id.matter_content, matter.getMatterContent());
            views.setTextViewText(R.id.matter_day_count, String.valueOf(Utility.getDateInterval(matter.getTargetDate())));


            //open detai activity intent
            Intent doneIntent = new Intent();
            doneIntent.putExtra("pos",position);
            doneIntent.putExtra("matter",matter);
            doneIntent.putExtra(MatterAppWidget.EXTRA_DO, MatterAppWidget.OPEN_DETAIL);
            views.setOnClickFillInIntent(R.id.matter_content, doneIntent);





            return views;
        }

        /* 在更新界面的时候如果耗时就会显示 正在加载... 的默认字样，但是你可以更改这个界面
         * 如果返回null 显示默认界面
         * 否则 加载自定义的，返回RemoteViews
         */
        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}
