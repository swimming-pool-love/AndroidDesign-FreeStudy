package com.example.finalwork.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;


import com.example.finalwork.R;
import com.example.finalwork.Activity.daoshuri.DaoshuriMainActivity;
import com.example.finalwork.Activity.daoshuri.MatterAddActivity;
import com.example.finalwork.Activity.daoshuri.MatterDetailActivity;
import com.example.finalwork.model.Matter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MatterAppWidget extends AppWidgetProvider {

    private RemoteViews mRemoteViews;
    private List<Matter> mMatterList = new ArrayList<>();
    public static final String ACTION_DONE = "com.example.finalwork.daoshuri.widget.ACTION_DONE";
    public static final String EXTRA_DO = "com.example.finalwork.daoshuri.widget.EXTRA_DO";
    public static final int OPEN_DETAIL = 1;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            mRemoteViews = initView(context,appWidgetManager,appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }


    }

    private RemoteViews initView(Context context, AppWidgetManager widgetManager, int appWidgetId){
        RemoteViews mViews = new RemoteViews(context.getPackageName(), R.layout.daoshuri_matter_app_widget);
        mMatterList = DataSupport.findAll(Matter.class);

        Intent listIntent = new Intent(context, ListViewService.class);
        mViews.setRemoteAdapter(R.id.appwidget_list, listIntent);
        mViews.setEmptyView(R.id.appwidget_list, android.R.id.empty);
        return mViews;
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {



        // 设置 AppWidget Open Detail 点击事件
        final Intent openDetailIntent = new Intent(context, MatterAppWidget.class);
        openDetailIntent.setAction(ACTION_DONE);
        openDetailIntent.setData(Uri.parse(openDetailIntent.toUri(Intent.URI_INTENT_SCHEME)));
        final PendingIntent donePendingIntent =
                PendingIntent.getBroadcast(context,0, openDetailIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        mRemoteViews.setPendingIntentTemplate(R.id.appwidget_list, donePendingIntent);
        // 设置 AppWidget label 点击事件
        Intent labelIntent = new Intent(context, DaoshuriMainActivity.class);
        PendingIntent labelPendingIntent = PendingIntent.getActivity(context, 0, labelIntent, 0);
        mRemoteViews.setOnClickPendingIntent(R.id.appwidget_text, labelPendingIntent);

        // 设置 AppWidget add 点击事件
        Intent addIntent = new Intent(context, MatterAddActivity.class);
        PendingIntent addPendingIntent = PendingIntent.getActivity(context,0,addIntent,0);
        mRemoteViews.setOnClickPendingIntent(R.id.appwidget_add,addPendingIntent);


        appWidgetManager.updateAppWidget(appWidgetId,mRemoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction().equals(ACTION_DONE)) {
            int doInt = intent.getExtras().getInt(EXTRA_DO);
            int position = intent.getIntExtra("pos",0);
            Matter matter = (Matter) intent.getSerializableExtra("matter");
            switch (doInt) {
                case OPEN_DETAIL:
                    MatterDetailActivity.actionStart(context,matter,mMatterList,position);
                    break;
            }
        }

        super.onReceive(context, intent);

        // 更新 Widget
        final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        final ComponentName cn = new ComponentName(context, MatterAppWidget.class);
        mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.appwidget_list);
    }
}

