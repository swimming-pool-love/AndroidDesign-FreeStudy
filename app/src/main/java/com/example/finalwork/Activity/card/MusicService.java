package com.example.finalwork.Activity.card;

////实现背景音乐播放的方法一：
import java.io.IOException;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service{

    MediaPlayer mp;
    String[] list;
    int index=0;
    boolean isfirst = true;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return ibinder;
    }

    public final IBinder ibinder = new Mybinder();
    public class Mybinder extends Binder
    {
        MusicService getService()
        {
            return MusicService.this;
        }
    }

    public void onCreate()
    {
        Log.d("tag", "创建服务");
        if (mp == null)
            mp = new MediaPlayer();
    }

    protected void perpareMp() {
        // TODO Auto-generated method stub
        Log.d("tag", "创建播放器");
        try
        {
            if (mp == null)
                mp = new MediaPlayer();
            String path = list[index];
            mp.reset();
            AssetFileDescriptor afd = this.getAssets().openFd(path);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            isfirst = false;
        }
        catch (IOException e)
        {
            e.getStackTrace();
        }
    }

    public void onDestroy()
    {
        if (mp != null) {
            mp.stop();
            mp.release();
        }
    }

//    public void stopservice() {
//        // TODO Auto-generated method stub
//        if (mp.isPlaying())
//            mp.stop();
//        mp.release();
//    }

    public void changemuisc() {
        // TODO Auto-generated method stub
        perpareMp();
        mp.start();
    }

    public void stopmusic() {
        // TODO Auto-generated method stub
        mp.pause();
    }

    public void playmusic() {
        // TODO Auto-generated method stub
        if (isfirst)
        {
            perpareMp();
        }
        mp.start();
    }

    public long getDuration()
    {
        return mp.getDuration();
    }

//    public long getPostion()
//    {
//        return mp.getCurrentPosition();
//    }
//
//    public void setPostion(int postion)
//    {
//        mp.seekTo(postion);
//    }
}
//实现背景音乐播放的方法二：
/*
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;


import java.io.IOException;

public class MusicService extends Service {
    String[] musics;
    int index,op;
    MediaPlayer mp;
    boolean isfirst=true;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        if(mp==null) {
            mp =new MediaPlayer();
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                index=0;
            }});
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        musics=intent.getStringArrayExtra("musics");
        op=intent.getIntExtra("op", -1);
        index=intent.getIntExtra("index", -1);
        switch(op) {
            case 1:
                playmusic();
                break;
            case 2:
                pausemusic();
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }


    private void pausemusic() {
        // TODO Auto-generated method stub
        mp.pause();
    }

    private void playmusic() {
        // TODO Auto-generated method stub
        if(isfirst) {
            preparemusic();
        }
        mp.start();
    }

    private void preparemusic() {
        // TODO Auto-generated method stub
        mp.reset();
        try {
            AssetFileDescriptor afd=this.getAssets().openFd(musics[index]);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            isfirst=false;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mp.stop();
        mp.release();
        mp=null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
*/
