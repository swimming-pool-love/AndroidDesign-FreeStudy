package com.example.finalwork.Activity.alarm;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.finalwork.R;

public class PlayAlarmActivity extends Activity {

    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mp = MediaPlayer.create(this, R.raw.clockmusic2);
        mp.start();
        new AlertDialog.Builder(PlayAlarmActivity.this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("闹钟")
                .setMessage("快完成你制定的计划吧!!!")
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PlayAlarmActivity.this.finish();
                        mp.stop();
                        Intent intent=new Intent(PlayAlarmActivity.this,AlarmMainActivity.class);
                        startActivity(intent);
                    }
                })
                .show();

    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
    protected void onDestroy(){
        super.onDestroy();

        mp.stop();
        mp.release();
    }

}

