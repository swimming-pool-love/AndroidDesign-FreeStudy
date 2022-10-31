package com.example.finalwork.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;


import androidx.annotation.RequiresApi;


import com.example.finalwork.R;
import com.example.finalwork.Activity.daoshuri.DaoshuriMainActivity;
import com.example.finalwork.Helper.MatterComparator;
import com.example.finalwork.model.Matter;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationService extends Service {


    private Matter mMatter;
    private List<Matter> mMatterList = new ArrayList<>();
    public NotificationService() {
        LitePal.getDatabase();
        mMatterList = DataSupport.findAll(Matter.class);
        mMatterList = sortMatterList(mMatterList);
        mMatter = mMatterList.get(0);

    }


    /**
     * 按优先级排序列表(根据日期）
     */

    private List<Matter> sortMatterList(List<Matter> matterList){
        Collections.sort(matterList,new MatterComparator());
        return  matterList;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent Intent = new Intent(this, DaoshuriMainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, Intent, 0);
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器　
        if (mMatter!=null){
            builder.setContentIntent(pi)// 设置PendingIntent
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.time)) // 设置下拉列表中的图标(大图标)
                    .setContentTitle("首要事件") // 设置下拉列表里的标题
                    .setContentText(mMatter.getMatterContent())
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.time) // 设置状态栏内的小图标
                    .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        }else{
            builder.setContentIntent(pi)// 设置PendingIntent
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.time)) // 设置下拉列表中的图标(大图标)
                    .setContentTitle("首要事件") // 设置下拉列表里的标题
                    .setContentText("还没有倒数日呢")
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.time) // 设置状态栏内的小图标
                    .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        }



        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        startForeground(110, notification);// 开始前台服务


        return flags;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
    }
}
