package com.example.finalwork.Activity.share;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.finalwork.Activity.course.MainActivity;
import com.example.finalwork.R;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.Activity.daoshuri.DaoshuriMainActivity;
import com.example.finalwork.Activity.card.CardViewPagerActivity;
import com.example.finalwork.Database.SQLiteHelper;
import com.example.finalwork.Activity.personalcenter.CheckMyPublishActivity;
import com.example.finalwork.Adapter.Share.recyclerAdapter.RecyclerGridAdapterOne;
import com.example.finalwork.Adapter.Share.recyclerAdapter.RecyclerGridAdapterThree;
import com.example.finalwork.Adapter.Share.recyclerAdapter.RecyclerGridAdapterTwo;
import com.example.finalwork.Adapter.Share.viewpageadpater.ViewPageAdapter;
import com.example.finalwork.Activity.todolist.todolistMainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RecommendActivity extends BaseActivity {

    String TABLENAME = "share";
    byte[] imageData;
    Bitmap imageBm;
    String username,rpwd;
    EditText etopwd,etnpwd,etnpwd2,etnphone;
    ViewPager viewPager;
    TabLayout tabLayout;
    List<View> views;
    List<String> titles;
    RecyclerView staggerRecycler1,staggerRecycler2,staggerRecycler3;
    SwipeRefreshLayout swipeRefreshLayout1,swipeRefreshLayout2,swipeRefreshLayout3;

    //模拟数据部分--经验栏
    String[] virtualUsername={ "zhf", "初见", "泡泡儿", "栓Q","海绵宝宝","秃发程序员","迟山","杨三疯","卢本伟"};
    String[] virtualTime={ "2022-10-22", "2022-10-09", "2022-09-09", "2022-08-24","2022-10-01","2020-08-29","2020-10-11"};
    String[] virtualTitle={ "如何高效背单词","考研备考经验", "android开发框架整理", "springBoot框架整理","机器学习神经网络笔记"};
    String[] virtualContent={ "耐心修炼","笔记框架整理内容\n", "总结热门框架\n轻松掌握\n",
            "基础部分\n提升部分\n进阶学习\n项目实战\n","核心知识点归纳\n后端知识\nMVC架构\nGradle知识点\n"};

    //模拟数据部分--计划栏
    String[] virtualTime1={ "2022-10-10", "2022-10-01", "2022-09-23", "2022-06-24","2022-09-09","2021-11-11","2021-12-25"};
    String[] virtualTitle1={ "我的学期计划","如何做每日计划", "自律逆袭|拖延症克星", "做好周复盘和周计划","考研狗第四周计划表打卡"};
    String[] virtualContent1={ "制定课表时间安排计划，充分利用时间\n","吃饭，休息，学习时间统筹安排\n", "四象限法则\n时间盘点\n",
            "提高网课效率\n准备好学习的仪式感\n反复整理笔记\n记笔记赶走困意\n","贵在用心\n赢在用心\n有态度才能出质量\n沉下心来才能做好事\n"};

    //模拟数据部分--分享栏
    String[] virtualTime2={ "2022-10-22", "2022-10-09", "2022-09-09", "2022-08-24","2022-10-01","2020-08-29","2020-10-11"};
    String[] virtualTitle2={ "日常学习plog","大三生活分享", "学习日常|自律学习", "生活学习plog","100天自律生活打卡day33"};
    String[] virtualContent2={ "除了我自己没人可以否定我","突然想分享一下日常~\n", "每一天都要充实开心\n有意义地度过呀\n",
            "保持学习\n多读书\n定期运动\n走出舒适圈\n","从厌学到学high\n分享有用的学习日常\n费曼学习法\n美好一天呀\n"};

    List<Map<String,Object>> experienceData = new ArrayList<Map<String,Object>>(); //经验专栏数据存放列表
    List<Map<String,Object>> planData = new ArrayList<Map<String,Object>>(); //计划专栏数据存放列表
    List<Map<String,Object>> dairyData = new ArrayList<Map<String,Object>>(); //日记专栏数据存放列表



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        //侧边导航栏初始化
        slidebarInit();
       //viewPager页面准备
        viewPager= this.findViewById(R.id.one_view_pager);
        tabLayout = this.findViewById(R.id.tab_layout);
        View viewOne = LayoutInflater.from(this).inflate(R.layout.share_experience,null);
        View viewTwo = LayoutInflater.from(this).inflate(R.layout.share_plan,null);
        View viewThree = LayoutInflater.from(this).inflate(R.layout.share_daily,null);
        //将view和title准备然后传入viewpagerAdapter中
        views = new ArrayList<>();
        views.add(viewOne);
        views.add(viewTwo);
        views.add(viewThree);
        titles = new ArrayList<>();
        titles.add("经验技巧");
        titles.add("计划安排");
        titles.add("日常分享");
        ViewPageAdapter adapter = new ViewPageAdapter(views, titles);
        for (String title : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);

        //找到分享经验模块的recyclerView
        staggerRecycler1 = viewOne.findViewById(R.id.grid_recyclerview1);
        //找到分享计划模块recyclerView
        staggerRecycler2 = viewTwo.findViewById(R.id.grid_recyclerview2);
        //找到分享日常模块recyclerView
        staggerRecycler3 = viewThree.findViewById(R.id.grid_recyclerview3);

        getDataForExperience();//获取经验分享专栏页面数据
        getDataForPlan();//获取计划安排专栏页面数据
        getDataForDiary();//获取日常分享专栏页面数据


        //设置瀑布流布局样式，2为列数
        staggerRecycler1.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        RecyclerGridAdapterOne gridAdapter = new RecyclerGridAdapterOne(this, experienceData);
        staggerRecycler1.setAdapter(gridAdapter);

        staggerRecycler2.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        RecyclerGridAdapterTwo gridAdapter2 = new RecyclerGridAdapterTwo(this, planData);
        staggerRecycler2.setAdapter(gridAdapter2);

        staggerRecycler3.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        RecyclerGridAdapterThree gridAdapter3 = new RecyclerGridAdapterThree(this, dairyData);
        staggerRecycler3.setAdapter(gridAdapter3);


        swipeRefreshLayout1 = viewOne.findViewById(R.id.refreshLayout);
        swipeRefreshLayout2 = viewTwo.findViewById(R.id.refreshLayout);
        swipeRefreshLayout3 = viewThree.findViewById(R.id.refreshLayout);
        //为下拉刷新，设置一组颜色
        swipeRefreshLayout1.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
        swipeRefreshLayout2.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
        swipeRefreshLayout3.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
        //设置触发刷新的距离
        swipeRefreshLayout1.setDistanceToTriggerSync(200);
        swipeRefreshLayout2.setDistanceToTriggerSync(200);
        swipeRefreshLayout3.setDistanceToTriggerSync(200);
        //设置滑动的距离
        swipeRefreshLayout1.setSlingshotDistance(400);
        swipeRefreshLayout2.setSlingshotDistance(400);
        swipeRefreshLayout3.setSlingshotDistance(400);
        swipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                experienceData.clear();
                getDataForExperience();
                gridAdapter.notifyDataSetChanged();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout1.isRefreshing()) {
                            swipeRefreshLayout1.setRefreshing(false);
                        }
                    }
                }, 1500);

            }

        });

        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                planData.clear();
                getDataForPlan();
                gridAdapter2.notifyDataSetChanged();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout2.isRefreshing()) {
                            swipeRefreshLayout2.setRefreshing(false);
                        }
                    }
                }, 1500);

            }

        });

        swipeRefreshLayout3.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                dairyData.clear();
                getDataForDiary();
                gridAdapter3.notifyDataSetChanged();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout3.isRefreshing()) {
                            swipeRefreshLayout3.setRefreshing(false);
                        }
                    }
                }, 1500);

            }

        });



        //悬浮button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecommendActivity.this,"进入发布界面",Toast.LENGTH_SHORT).show();
                Intent intentPublish = new Intent(RecommendActivity.this, PublishMainActivity.class);
                startActivity(intentPublish);
            }
        });
    }

    protected void getDataForExperience() {
        SQLiteHelper database = new SQLiteHelper(this);
        final SQLiteDatabase db = database.getWritableDatabase();
        Map<String,Object> item = null; //瀑布流项内容用Map存储
        Cursor cursor = db.query(TABLENAME,null,"shareLabel=?",new String[] {"经验栏"},null,null,null,null);//数据库查询

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                item = new HashMap<String,Object>(); //为列表项赋值
                item.put("shareId",cursor.getInt(0));
                item.put("userName",cursor.getString(1));
                item.put("shareTitle",cursor.getString(2));
                item.put("shareLabel",cursor.getString(3));

                item.put("shareContent",cursor.getString(4));
                item.put("publishTime",cursor.getString(7));
                imageData = cursor.getBlob(5);
                imageBm = BitmapFactory.decodeByteArray(imageData,0,imageData.length);
                item.put("shareImg",imageBm);
                cursor.moveToNext();
                experienceData.add(item); //加入到列表中
            }
        }
        Random random = new Random();
        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.experience_1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.experience_2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.experience_3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.experience_4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.experience_share);
        Bitmap[] virtualPictures ={bitmap1,bitmap2,bitmap3,bitmap4,bitmap5} ;
        for (int i = 0; i < 10; i++) {
            item = new HashMap<String,Object>();
            int n = random.nextInt(virtualPictures.length);
            item.put("shareImg",virtualPictures[n]);
            item.put("shareId", 0);
            item.put("userName",virtualUsername[n]);
            item.put("shareTitle",virtualTitle[n]);
            item.put("shareLabel","经验栏");
            item.put("shareContent",virtualContent[n] + "\n" + virtualContent[random.nextInt(virtualContent.length)]);
            item.put("publishTime",virtualTime[n]);
            experienceData.add(item);
        }

    }

    private void getDataForPlan() {
        SQLiteHelper database = new SQLiteHelper(this);
        final SQLiteDatabase db = database.getWritableDatabase();
        Map<String,Object> item; //瀑布流项内容用Map存储
        Cursor cursor = db.query(TABLENAME,null,"shareLabel=?",new String[]{"计划栏"},null,null,null,null);//数据库查询

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                item = new HashMap<String,Object>(); //为列表项赋值
                item.put("shareId",cursor.getInt(0));
                item.put("userName",cursor.getString(1));
                item.put("shareTitle",cursor.getString(2));
                item.put("shareLabel",cursor.getString(3));
                item.put("shareContent",cursor.getString(4));
                item.put("publishTime",cursor.getString(7));
                imageData = cursor.getBlob(5);
                imageBm = BitmapFactory.decodeByteArray(imageData,0,imageData.length);
                item.put("shareImg",imageBm);
                cursor.moveToNext();
                planData.add(item); //加入到列表中
            }
        }
        Random random = new Random();
        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.plan1_1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.plan_2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.plan_3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.plan_4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.plan_5);
        Bitmap[] virtualPictures ={bitmap1,bitmap2,bitmap3,bitmap4,bitmap5} ;
        for (int i = 0; i < 10; i++) {
            item = new HashMap<String,Object>();
            int n = random.nextInt(virtualPictures.length);
            item.put("shareImg",virtualPictures[n]);
            item.put("shareId", 0);
            item.put("userName",virtualUsername[n]);
            item.put("shareTitle",virtualTitle1[n]);
            item.put("shareLabel","经验栏");
            item.put("shareContent",virtualContent1[n] + "\n" + virtualContent[random.nextInt(virtualContent.length)]);
            item.put("publishTime",virtualTime1[n]);
            planData.add(item);
        }
    }

    private void getDataForDiary() {
        SQLiteHelper database = new SQLiteHelper(this);
        final SQLiteDatabase db = database.getWritableDatabase();
        Map<String,Object> item; //瀑布流项内容用Map存储
        Cursor cursor = db.query(TABLENAME,null,"shareLabel=?",new String[]{"记录栏"},null,null,null,null);//数据库查询
        
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                item = new HashMap<String,Object>(); //为列表项赋值
                item.put("shareId",cursor.getInt(0));
                item.put("userName",cursor.getString(1));
                item.put("shareTitle",cursor.getString(2));
                item.put("shareLabel",cursor.getString(3));
                item.put("shareContent",cursor.getString(4));
                item.put("publishTime",cursor.getString(7));
                imageData = cursor.getBlob(5);
                imageBm = BitmapFactory.decodeByteArray(imageData,0,imageData.length);
                item.put("shareImg",imageBm);
                cursor.moveToNext();
                dairyData.add(item); //加入到列表中
            }
        }
        Random random = new Random();
        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.diary_1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.diary_2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.diary_3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.diary_4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.diary_5);
        Bitmap[] virtualPictures ={bitmap1,bitmap2,bitmap3,bitmap4,bitmap5} ;
        for (int i = 0; i < 10; i++) {
            item = new HashMap<String,Object>();
            int n = random.nextInt(virtualPictures.length);
            item.put("shareImg",virtualPictures[n]);
            item.put("shareId", 0);
            item.put("userName",virtualUsername[n]);
            item.put("shareTitle",virtualTitle1[n]);
            item.put("shareLabel","经验栏");
            item.put("shareContent",virtualContent1[n] + "\n" + virtualContent[random.nextInt(virtualContent.length)]);
            item.put("publishTime",virtualTime1[n]);
            dairyData.add(item);
        }
    }



    private void slidebarInit() {
        //显示侧边栏图标
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navigationview = (NavigationView) findViewById(R.id.navigation_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.setSupportActionBar(toolbar);
        //将标题隐藏，否则会遮挡小图标
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);//初始化状态
        toggle.syncState();

        /*---------------------------添加头布局和尾布局-----------------------------*/
        //获取xml头布局view
        View headerView = navigationview.getHeaderView(0);
        //添加头布局的另外一种方式
        //View headview=navigationview.inflateHeaderView(R.layout.navigationview_header);

        //寻找头部里面的控件
        ImageView imageView = headerView.findViewById(R.id.iv_head);
        TextView textView=headerView.findViewById(R.id.tv_user);
        Intent intent=this.getIntent();
        username=intent.getStringExtra("username");
        rpwd=intent.getStringExtra("pwd");
        textView.setText(username);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了头像", Toast.LENGTH_LONG).show();
            }
        });

        ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.nav_menu_text_color);
        //设置item的条目标题颜色
        navigationview.setItemTextColor(csl);
        //设置item条目icon颜色,这里让icon呈现出原有的颜色
        navigationview.setItemIconTintList(null);

        //设置单个消息数量
        LinearLayout llAndroid = (LinearLayout) navigationview.getMenu().findItem(R.id.single_2).getActionView();
        TextView msg= (TextView) llAndroid.findViewById(R.id.msg_bg);
        msg.setText("99+");

        //设置条目点击监听
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    //修改密码
                    case R.id.single_1:
                        openPwdDialog();
                        break;
                    //我的发布
                    case R.id.single_2:
                        Intent intent = new Intent(RecommendActivity.this, CheckMyPublishActivity.class);
                        startActivity(intent);
                        break;
                    //vip专享
                    case R.id.single_3:
                        Toast.makeText(RecommendActivity.this,"vip功能尚未启动，敬请期待",Toast.LENGTH_SHORT).show();
                        break;
                    //关于我们
                    case R.id.single_4:
                        Toast.makeText(RecommendActivity.this,"free study,乐享学习的app",Toast.LENGTH_SHORT).show();
                        break;
                    //检查更新：
                    case R.id.single_5:
                        Toast.makeText(RecommendActivity.this,"当前已是最新版本了哦",Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }

            private void openPwdDialog() {
                AlertDialog.Builder builder=new AlertDialog.Builder(RecommendActivity.this);
                LayoutInflater inflater=LayoutInflater.from(RecommendActivity.this);
                View view=inflater.inflate(R.layout.changepwd, null);
                etopwd=view.findViewById(R.id.etopwd);
                etnpwd=view.findViewById(R.id.etnpwd);
                etnpwd2=view.findViewById(R.id.etnpwd2);
                etnphone=view.findViewById(R.id.etnphone);
//                builder.setTitle("修改密码");
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String opwd=etopwd.getText().toString();
                        String npwd=etnpwd.getText().toString();
                        String npwd2=etnpwd2.getText().toString();
                        String nphone=etnphone.getText().toString();
                        if(opwd.length()*npwd.length()*npwd2.length()>0) {
                            SQLiteHelper shelper=new SQLiteHelper(RecommendActivity.this,null,2);
                            Cursor cr=shelper.selectuser(username);
                            cr.moveToFirst();
                            if(opwd.equals(rpwd)) {
                                if(npwd.equals(npwd2)) {
                                    shelper.updateuser(username, npwd, nphone);
                                }
                                showMsg();
                            }
                        }
                    }

                    private void showMsg() {
                        // TODO Auto-generated method stub
                        AlertDialog.Builder builder=new AlertDialog.Builder(RecommendActivity.this);
//                        builder.setTitle("用户注册");
                        builder.setMessage("恭喜你，修改密码成功，点击确定返回登录界面");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                RecommendActivity.this.finish();
                            }
                        });
                        builder.create().show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }});
                builder.create().show();
            }
        });

        //点击退出登录按钮退出登录，设置监听事件
        Button btn_exit = (Button) findViewById(R.id.footer_item_out);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBackBackDialog();

            }

            private void showBackBackDialog() {
                final AlertDialog.Builder layoutDialog = new AlertDialog.Builder(RecommendActivity.this);
                final AlertDialog dialog = layoutDialog.create();
                dialog.show();

                //加载布局并初始化组件
                View dialogView = LayoutInflater.from(RecommendActivity.this).inflate(R.layout.publish_dialog_cancel_or_not,null);
                TextView dialogText = (TextView) ((View) dialogView).findViewById(R.id.alert_dialog_text);
                Button dialogBtnConfirm = (Button) dialogView.findViewById(R.id.alert_dialog_btn_confirm);
                Button dialogBtnCancel = (Button) dialogView.findViewById(R.id.alert_dialog_btn_cancel);
                //设置组件
                dialogText.setText("确定要忍心退出嘛？~");
                dialogBtnConfirm .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        System.exit(0);
                    }
                });
                dialogBtnCancel .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //在布局文件中设置了背景为圆角的shape后，发现上边显示的是我们的自定义的圆角的布局文件，
                //底下居然还包含了一个方形的白块，如何去掉这个白块,添加下面这句话
                dialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
                dialog.getWindow().setContentView(dialogView);//自定义布局应该在这里添加，要在dialog.show()的后面
            }
        });
    }

    // 底部导航栏，请注意本方法内容的变化
    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.btnNavStudy:
                open(todolistMainActivity.class);
                break; // case R.id.btnNavStudy
            case R.id.btnNavTime:
                open(DaoshuriMainActivity.class);
                break; // case R.id.btnNavTime
            case R.id.btnNavData:
                 open(MainActivity.class);
                 break; // case R.id.btnNavData
            case R.id.btnNavCard:
                open(CardViewPagerActivity.class);
                break; // case R.id.btnNavCard

        } // switch (v.getId())
    } // onNavButtonsTapped()

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