package com.example.finalwork.Activity.personalcenter;

import static com.example.finalwork.Activity.login.MainActivity.post_userid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finalwork.Adapter.PersonalCenter.RecyclerLineAdapter;
import com.example.finalwork.Adapter.PersonalCenter.updownRemove.myItemTouchHelperCallBack;
import com.example.finalwork.Database.SQLiteHelper;
import com.example.finalwork.R;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.model.Share;

import java.util.ArrayList;
import java.util.List;

public class CheckMyPublishActivity extends BaseActivity {

    SlideRecyclerView lineRecycler;
    List<Share> lineData = new ArrayList<Share>();


    //监听返回按钮，如果点击返回按钮则关闭当前Activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_center_my_publish);
        //设置返回按钮的actionbar
        ActionBar actionBar = getSupportActionBar(); //获取ActionBar
        if(actionBar != null){
            actionBar.setTitle("Check my publish");//设置ActionBar的标题
            actionBar.setSubtitle("我的发布");//副标题
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回按钮
        }
        //获得当前登录用户发布的日记记录
        getShareData();
        //找到布局
        lineRecycler = (SlideRecyclerView) findViewById(R.id.line_recy_view);
        // 设置为线性布局,创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lineRecycler.setLayoutManager(layoutManager);
        // 设置适配器
        RecyclerLineAdapter recyclerLineAdapter = new RecyclerLineAdapter(this);
        recyclerLineAdapter.setList(lineData);
        lineRecycler.setAdapter(recyclerLineAdapter);
        //上下滑动
        ItemTouchHelper.Callback callback = new myItemTouchHelperCallBack(recyclerLineAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(lineRecycler);
        //左滑删除
        recyclerLineAdapter.setOnItemClickListener(new RecyclerLineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //获取被删除share对象
                Share removeShare =  lineData.get(position);
                int shareId = removeShare.getShareId();
                //在lineRecyler中刷新新的数据
                lineData.remove(position);
                recyclerLineAdapter.notifyDataSetChanged();
                //同时对应从数据库删除该用户的该发布日志
                SQLiteHelper database = new SQLiteHelper(CheckMyPublishActivity.this);
                final SQLiteDatabase db = database.getWritableDatabase();
                db.execSQL("delete from share where shareId="+shareId);
                Toast.makeText(CheckMyPublishActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getShareData() {

        SQLiteHelper database = new SQLiteHelper(this);
        SQLiteDatabase db = database.getWritableDatabase();
        String TABLENAME = "share";
        Cursor c = db.query(TABLENAME,null,"userName=?",new String[]{post_userid},null,null,null,null);
        byte[] imageData;
        Bitmap imageBm;

        if(c.moveToFirst()){
            do{
                imageData = c.getBlob(5);
                imageBm = BitmapFactory.decodeByteArray(imageData,0,imageData.length);
                Share shareItem = new Share(
                        c.getInt(0), //shareId
                        c.getString(1),//userName
                        c.getString(2),//shareTitle
                        c.getString(3),//shareLabel
                        c.getString(4),//shareContent
                        imageBm,//shareImg
                        c.getString(7).toString()//publishTime
                );
                lineData.add(shareItem);
            }while(c.moveToNext());
        }
    }


}
