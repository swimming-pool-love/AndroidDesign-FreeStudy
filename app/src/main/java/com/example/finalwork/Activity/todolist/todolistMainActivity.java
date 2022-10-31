package com.example.finalwork.Activity.todolist;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.finalwork.R;
import com.example.finalwork.Activity.alarm.AlarmMainActivity;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.Activity.card.CardViewPagerActivity;
import com.example.finalwork.Activity.course.MainActivity;
import com.example.finalwork.Activity.daoshuri.DaoshuriMainActivity;
import com.example.finalwork.Database.TodoTaskManager;
import com.example.finalwork.model.TodoTask;
import com.example.finalwork.Activity.share.RecommendActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class todolistMainActivity extends BaseActivity {

    private TodoTaskManager todoTaskManager;

    private ListView lvTasks;

    private List<TodoTask> tasks = new ArrayList<TodoTask>();

    private TaskAdapter taskAdapter;

    private ActionBar actionBar;
    private ImageButton alarm;
    private Intent intent_al;

    private TextView tv_time;
    private final static int COUNT = 1;
    private final static int TOTAL_TIME = 0;
    Timer timer;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist_activity_main);
        todoTaskManager = new TodoTaskManager(this);
        tasks = todoTaskManager.queryAll();
        // 2) Initialize the components

        initComponents();
        initAlarm();
        tv_time=(TextView)findViewById(R.id.tv_time);//计时器

        final Button btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = btn_start.getText().toString();//获取按钮字符串
                if(str.equals("开始")){ //切换按钮文字
                    btn_start.setText("暂停");
                    initView();
                }
                else{
                    btn_start.setText("开始");
                    timer.cancel();//终止线程
                }
            }
        });
    }

    private void initView() {
        timer = new Timer();
/**
 * 每一秒发送一次消息给handler更新UI
 * schedule(TimerTask task, long delay, long period)
 */
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(COUNT);
            }
        }, 0, 1000);
    }

    private Handler handler = new Handler(){
        int num = TOTAL_TIME;
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case COUNT:
                    tv_time.setText(String.valueOf(num)+"秒");
                    num++;
                    break;
                default:
                    break;
            }
        };
    };



    private void initAlarm() {
        alarm = findViewById(R.id.alarm);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_al = new Intent(todolistMainActivity.this, AlarmMainActivity.class);
                startActivity(intent_al);
            }
        });
    }

    protected void onResume(){
        super.onResume();
        tasks = todoTaskManager.queryAll();
        taskAdapter.notifyDataSetChanged();
    }

    protected void onDestroy() {
        super.onDestroy();
        todoTaskManager.close();
    }

    private void initComponents() {
        actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.handdraw_notepad);
        actionBar.setHomeButtonEnabled(true);
        lvTasks = (ListView) findViewById(R.id.lvTask);


        taskAdapter = new TaskAdapter();
        lvTasks.setAdapter(taskAdapter);
        lvTasks.setOnItemLongClickListener(onItemLongClickListener);
        lvTasks.setOnItemClickListener(onItemClickListener);
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String taskId = String.valueOf(id);
            Intent intent = new Intent(todolistMainActivity.this, todolistDetailActivity.class);
            intent.putExtra(todolistDetailActivity.TASK_ID, taskId);
            startActivity(intent);
            overridePendingTransition(R.anim.scale_in,R.anim.scale_left_out);
        }
    };

    OnItemLongClickListener onItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            delete(id);
            return false;
        }
    };

    public void delete(final long id){
        new AlertDialog.Builder(this).setTitle("Delete Items")
                .setMessage("Delete ? ")
                .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(todoTaskManager.delete("_id = ? ", new String[] {String.valueOf(id)})){
                            tasks = todoTaskManager.queryAll();
                            taskAdapter.notifyDataSetChanged();
                        }
                    }
                }).setNegativeButton("Nop", null).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todolist_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:
                Intent intent = new Intent(todolistMainActivity.this, todolistDetailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.scale_in,R.anim.scale_left_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class TaskAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public TaskAdapter() {
            layoutInflater = LayoutInflater.from(todolistMainActivity.this);
        }

        @Override
        public int getCount() {
            return tasks.size();
        }

        @Override
        public Object getItem(int position) {
            return tasks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return tasks.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.todolist_task_item, null);
                viewHolder.ivComplete = (ImageView)convertView.findViewById(R.id.ivComplete);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if("Y".equals(tasks.get(position).getFlagCompleted())){
                viewHolder.ivComplete.setVisibility(View.VISIBLE);
            }else{
                viewHolder.ivComplete.setVisibility(View.GONE);
            }
            viewHolder.tvTitle.setText(tasks.get(position).getTitle());
            return convertView;
        }

    }

    static class ViewHolder {
        ImageView ivComplete;
        TextView tvTitle;
    }


    // 底部导航栏，请注意本方法内容的变化
    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {

            case R.id.btnNavTime:
                open(DaoshuriMainActivity.class);
                break; // case R.id.btnNavTime
            case R.id.btnNavData:
                open(MainActivity.class);
                break; // case R.id.btnNavData
            case R.id.btnNavCard:
                open(CardViewPagerActivity.class);
                break; // case R.id.btnNavCard
            case R.id.btnNavShare:
                open(RecommendActivity.class);
                break; // case R.id.btnNavStudy

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