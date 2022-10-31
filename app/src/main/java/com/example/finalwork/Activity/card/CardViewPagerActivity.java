package com.example.finalwork.Activity.card;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.viewpager.widget.ViewPager;

import com.example.finalwork.Activity.course.MainActivity;
import com.example.finalwork.Adapter.Card.MyPagerAdapter;
import com.example.finalwork.R;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.Activity.daoshuri.DaoshuriMainActivity;
import com.example.finalwork.Activity.share.RecommendActivity;
import com.example.finalwork.Activity.todolist.todolistMainActivity;
import com.example.finalwork.model.ViewPagerItemBean;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardViewPagerActivity extends BaseActivity {

    private final int[] mData = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,R.drawable.img_6,R.drawable.img_7,R.drawable.img_8,R.drawable.img_9};
    private ViewPager mViewPager;
//    String[] musics= {};
//    ImageButton btnPlay;
    int index,op;
    boolean isplaying;
    ImageButton btnPlay;
    String [] files,musics;
    View.OnClickListener ls;
    int playstatus,cposition;
//    int op;
    Intent intent;
    MusicService musicservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_pager);
        mViewPager = ((ViewPager) findViewById(R.id.cardViewPager));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new MyPagerAdapter(getViewPagerData(),this));
        mViewPager.setPageTransformer(true,  new AlphaAndScalePageTransformer());
//        MusicInit();第二种播放方法
        btnPlay = this.findViewById(R.id.btnPlay);
        getMusicList();
        connect();//第一种播放方法
        bindButton();

    }

    /*Handler hander = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if ((musicservice != null)&&(msg.what==321))
            {
                long duration = musicservice.getDuration();
                long position = musicservice.getPostion();
                Date datedur = new Date(duration);
                Date dpostion = new Date(position);
                SimpleDateFormat sd = new SimpleDateFormat("mm:ss");
                String text = sd.format(dpostion) + "/" + sd.format(datedur);
                CardViewPagerActivity.this.setTitle(musics[cposition]);
                tvpro.setText(text);
                playpro.setMax((int)duration);
                playpro.setProgress((int)position);
                CardViewPagerActivity.this.setTitle(musics[cposition]);
                hander.sendEmptyMessageDelayed(321, 500);
            }
        }
    };*/



    protected void changemusic(int cposition2) {
        // TODO Auto-generated method stub
        if(musicservice!=null)
        {
            musicservice.index = cposition2;
            musicservice.changemuisc();
//            hander.sendEmptyMessage(321);
        }
    }
//实现背景音乐播放的方法一：
    private void connect() {
        // TODO Auto-generated method stub
        intent = new Intent(CardViewPagerActivity.this,MusicService.class);
        this.bindService(intent, cn, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection cn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            musicservice = ((MusicService.Mybinder)service).getService();
            musicservice.list = musics;
            musicservice.index = cposition;
            musicservice.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    /*if (cposition==musics.length-1)
                        cposition = 0;
                    else
                        cposition ++;
                    changemusic(cposition);*/
                    cposition = 0;
                    changemusic(cposition);
                }});
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            musicservice = null;
        }};

    private void bindButton() {
        // TODO Auto-generated method stub
        playstatus = 0;
        cposition = 0;
        ls = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId())
                {
                    case R.id.btnPlay:
                        if (playstatus==0)
                        {
                            playstatus = 1;
                            musicservice.playmusic();
//                            hander.sendEmptyMessage(321);
                            btnPlay.setImageResource(R.drawable.play);
                        }
                        else if (playstatus==1)
                        {
                            playstatus = 0;
                            musicservice.stopmusic();
                            btnPlay.setImageResource(R.drawable.pause);
                        }
                        break;
                }
            }};
        btnPlay.setOnClickListener(ls);
    }

    private void getMusicList() {
        // TODO Auto-generated method stub
        AssetManager assetmanager = this.getAssets();
        try {
            files = assetmanager.list("");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0;i<files.length;i++)
        {
            if (files[i].endsWith(".mp3"))
                list.add(files[i]);
        }
        musics = list.toArray(new String [0]);
    }

//实现背景音乐播放的方法二：
    private void MusicInit() {
        // TODO Auto-generated method stub
        btnPlay=this.findViewById(R.id.btnPlay);
        AssetManager manager =this.getAssets();
        try {
            String[] files=manager.list("");
            List<String> buffer =new ArrayList<String>();
            for(int i=0;i<files.length;i++) {
                if(files[i].endsWith(".mp3")) {
                    buffer.add(files[i]);
                }
            }
            musics=buffer.toArray(new String[0]);
        }catch(IOException e){
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
        index=0;
        op=-1;
        isplaying=false;
        View.OnClickListener ls=new View.OnClickListener() {
            Intent intent=new Intent(CardViewPagerActivity.this,MusicService.class);
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch(v.getId()) {
                    case R.id.btnPlay:
                        if(!isplaying) {
                            op=1;	//设置op=1为播放歌曲
                            isplaying=true;
                            btnPlay.setImageResource(R.drawable.play);
                        }else {
                            op=2;	//设置op=2为暂停歌曲
                            isplaying=false;
                            btnPlay.setImageResource(R.drawable.pause);
                        }
                        break;

                }
                intent.putExtra("musics", musics);
                intent.putExtra("index", index);
                intent.putExtra("op", op);
                CardViewPagerActivity.this.startService(intent);
                CardViewPagerActivity.this.setTitle(musics[index]);
            }};
        btnPlay.setOnClickListener(ls);
    }

    private List<ViewPagerItemBean> getViewPagerData() {
        List<ViewPagerItemBean> pagerItemBeanList = new ArrayList<>(mData.length);
        /*for (int i = 0; i < mData.length; i++) {
            pagerItemBeanList.add(new ViewPagerItemBean(mData[i], (i+1)+" / "+mData.length));
        }*/
        pagerItemBeanList.add(new ViewPagerItemBean(mData[0], "But every once in a while you find someone who's iridescent"
                +", and when you do, nothing will ever compare\n" +
                "世人万千种，浮云莫去求，斯人若彩虹，遇上方知有。"));
        pagerItemBeanList.add(new ViewPagerItemBean(mData[1], "And when they let you down You get up off the ground. Cause morning rolls around and it’s another day of sun." +
                "当他们让你失望时，你应该振作起精神。因为早晨会照样来到，又是阳光灿烂的一天。" ));
        pagerItemBeanList.add(new ViewPagerItemBean(mData[2], "All my life I stood up to everyone and everything because it made me feel important. You do it cause you mean it." +
                "\n" +
                "有目的的生活有时候也会是一种悲哀。差距总是让人失落。" ));
        pagerItemBeanList.add(new ViewPagerItemBean(mData[3], "Our deepest fear is not that we are inadequate. Our deepest fear is that we are powerful beyond measure." +
                "\n" +
                "我们最怕的不是别人看不起我们，我们最怕的是我们前途无量。" ));
        pagerItemBeanList.add(new ViewPagerItemBean(mData[4], "I used to think that my life was a tragedy. But now I realize, it's a comedy." +
                "\n" +
                "我曾以为，我的人生是场悲剧。但现在我意识到，它原来是场喜剧。"));
        pagerItemBeanList.add(new ViewPagerItemBean(mData[5], "Hope is a good thing and maybe the best of things. And no good thing ever dies." +
                "\n" +
                "希望是一个好东西，也许是世间最好的东西，好东西是不会消逝的。" ));
        pagerItemBeanList.add(new ViewPagerItemBean(mData[6], " You got a dream, you gotta protect it. People can't do something themselves, they wanna tell you you can't do it. If you want something, go get it." +
                "\n" +
                "如果你有梦想的话，就必须捍卫它。那些一事无成的人总是想告诉你，你也成不了大器。如果你有理想的话，就要去努力实现。"));
        pagerItemBeanList.add(new ViewPagerItemBean(mData[7], "Is life always this hard. Or is it just when you're a kid?" +
                "\n" +
                "-Always like this." +
                "\n" +
                "-生活是否总是如此艰辛？还是仅仅童年才如此？" +
                "\n" +
                "-总是如此。" ));
        pagerItemBeanList.add(new ViewPagerItemBean(mData[8], "You're not your job. You're not how much money you have in the bank. You are the all-singing, all-dancing crap of the world." +
                "\n" +
                "工作不能代表你，银行存款也不能代表你，你只是平凡众生中的一个。"));
        return pagerItemBeanList;
    }



    /**
     * onNavButtonsTapped(): 点击导航栏上的标签时触发。
     *
     * @param v 点击的按钮对象，用 v.getId() 获取其资源 ID。
     */
    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.btnNavStudy:
                open(todolistMainActivity.class);
                CardViewPagerActivity.this.finish();
                break; // case R.id.btnNavStudy
            case R.id.btnNavTime:
                open(DaoshuriMainActivity.class);
                CardViewPagerActivity.this.finish();
                break; // case R.id.btnNavTime
            case R.id.btnNavShare:
                open(RecommendActivity.class);
                CardViewPagerActivity.this.finish();
                break; // case R.id.btnNavShare
            case R.id.btnNavData:
                open(MainActivity.class);
                CardViewPagerActivity.this.finish();
                break; // case R.id.btnNavData

        } // switch (v.getId())
    } // onNavButtonsTapped()

    /**
     * onKeyDown(): 按下回退键时触发。
     * 弹出对话框询问是否退出程序。
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
            return true;
        } // if (keyCode == KeyEvent.KEYCODE_BACK)
        else {
            return super.onKeyDown(keyCode, event);
        } // else
    } // onKeyDown()


}