package com.example.finalwork.Activity.daoshuri;

import static java.lang.Math.abs;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork.Activity.course.MainActivity;
import com.example.finalwork.R;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.Activity.card.CardViewPagerActivity;
import com.example.finalwork.Adapter.Daoshuri.MatterGridAdapter;
import com.example.finalwork.Adapter.Daoshuri.MatterLinearAdapter;
import com.example.finalwork.Helper.MatterComparator;
import com.example.finalwork.Helper.Utility;
import com.example.finalwork.model.Matter;
import com.example.finalwork.Activity.share.RecommendActivity;
import com.example.finalwork.Activity.todolist.todolistMainActivity;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DaoshuriMainActivity extends BaseActivity {


    private List<Matter> mMatterList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MatterGridAdapter MyAdapterGrid;
    private MatterLinearAdapter MyAdapterLinear;
    private View headerView;
    //set default to listview
    private boolean viewStatus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        baseInit(viewStatus);
        switchView(viewStatus);


    }

    /**
     * 基本视图和控件初始化
     *
     * @param isSwitch
     */

    private void baseInit(boolean isSwitch) {
        if (isSwitch) {
            setContentView(R.layout.daoshuri_activity_main_linear);
            headerView = findViewById(R.id.header);

        } else {
            setContentView(R.layout.daoshuri_activity_main);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //deal with database
        LitePal.getDatabase();


        //get list and setDatabase
        mMatterList = DataSupport.findAll(Matter.class);

        //set recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);


    }

//
//    /**
//     * 切换视图
//     *
//     * @param isSwitch
//     */

    public void switchView(boolean isSwitch) {
        if (isSwitch) {
            //set to linear recyclerview
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            MyAdapterLinear = new MatterLinearAdapter(mMatterList);
            mRecyclerView.setAdapter(MyAdapterLinear);
            //切换视图后马上刷新一下
            onResume();

        } else {
            //set to grid recyclerview
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(layoutManager);
            MyAdapterGrid = new MatterGridAdapter(mMatterList);
            mRecyclerView.setAdapter(MyAdapterGrid);
            onResume();
        }
    }
//
//    /**
//     * 填充线性列表头部
//     *
//     * @param v
//     */
//
    private void fillInHeader(View v) {
        TextView headContent, headDate, headCount;
        headContent = v.findViewById(R.id.head_event_content);
        headDate = v.findViewById(R.id.head_event_date);
        headCount = v.findViewById(R.id.head_days_count);
        Matter matter = mMatterList.get(0);
        headContent.setText(matter.getMatterContent());
        headDate.setText(new SimpleDateFormat("yyy年MM月dd日").
                format(matter.getTargetDate()));
        long count = Utility.getDateInterval(matter.getTargetDate());

        headCount.setText(Long.toString(abs(count)));


    }


    /**
     * 没有倒数日的时候头部回复原来
     *
     * @param v
     */

    private void clearHeader(View v) {
        TextView headContent, headDate, headCount;
        headContent = v.findViewById(R.id.head_event_content);
        headDate = v.findViewById(R.id.head_event_date);
        headCount = v.findViewById(R.id.head_days_count);
        headContent.setText("");
        headCount.setText("");
        headDate.setText("");
    }

    /**
     * 按优先级排序列表(根据日期）
     */

    private List<Matter> sortMatterList(List<Matter> matterList) {
        Collections.sort(matterList, new MatterComparator());
        return matterList;
    }

    /**
     * 两种不同的视图的刷新
     *
     * @param adapter
     */


    private void doRefreshForGrid(MatterGridAdapter adapter) {
        if (!mMatterList.isEmpty()) {
            mMatterList = adapter.getMatterList();
            mMatterList = sortMatterList(mMatterList);
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
        }


    }


    private void doRefreshForLinear(MatterLinearAdapter adapter) {
        if (!mMatterList.isEmpty()) {
            adapter = new MatterLinearAdapter(mMatterList);
            mRecyclerView.setAdapter(adapter);
            mMatterList = sortMatterList(mMatterList);
            Log.i("INFO", "sorting list");
            adapter.notifyDataSetChanged();
        } else {
            clearHeader(headerView);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("INFO", "calling onResume!");
        if (viewStatus) {
            //if true
            doRefreshForLinear(MyAdapterLinear);
            //刷新的时候重新填充头部
            if (!mMatterList.isEmpty())
                fillInHeader(headerView);
        } else {
            //false
            doRefreshForGrid(MyAdapterGrid);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.daoshuri_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_view:
                //switch view
                if (viewStatus == true) {
                    viewStatus = false;
                    baseInit(viewStatus);
                    switchView(viewStatus);
                } else {
                    viewStatus = true;
                    baseInit(viewStatus);
                    switchView(viewStatus);
                }
                return true;
            case R.id.add_matter:
                MatterAddActivity.actionStart(this, mMatterList);
                Log.i("INFO", "enter add matter activity");
                return true;
            case R.id.delete_all_matter:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage("删除的数据无法恢复(点击屏幕取消删除)")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DataSupport.deleteAll(Matter.class);
                                mMatterList.clear();
                                Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                                if (viewStatus == true) {
                                    MyAdapterLinear.notifyDataSetChanged();
                                    clearHeader(headerView);
                                } else
                                    MyAdapterGrid.notifyDataSetChanged();

                            }
                        })
                        .setCancelable(true)
                        .setTitle("确认删除吗")
                        .create();
                dialog.show();
                return true;

            case R.id.type_list:
                startActivity(new Intent(DaoshuriMainActivity.this, TypeListActivity.class));
                break;
            default:
                break;
        }
        return false;
    }

    // 底部导航栏，请注意本方法内容的变化
    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.btnNavStudy:
                open(todolistMainActivity.class);
                break; // case R.id.btnNavStudy
            case R.id.btnNavShare:
                open(RecommendActivity.class);
                break; // case R.id.btnNavShare
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
