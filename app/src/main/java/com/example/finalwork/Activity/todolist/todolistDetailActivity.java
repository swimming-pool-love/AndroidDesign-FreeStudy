package com.example.finalwork.Activity.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.example.finalwork.R;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.Activity.card.CardViewPagerActivity;
import com.example.finalwork.Activity.course.MainActivity;
import com.example.finalwork.Activity.daoshuri.DaoshuriMainActivity;
import com.example.finalwork.Database.TableFields;
import com.example.finalwork.Database.TodoTaskManager;
import com.example.finalwork.model.TodoTask;
import com.example.finalwork.Activity.share.RecommendActivity;

import java.util.ArrayList;
import java.util.List;

public class todolistDetailActivity extends BaseActivity {

	private static final String TAG = "com.lms.todo.DetailActivity";

	public static final String TASK_ID = "TASK_ID";

	private EditText etTitle, etContent;

	private Button btnMarkComplete,btnMarkUnComplete;

	private TodoTaskManager todoTaskManager;

	private boolean flagForUpdate = false;

	private String taskId;

	private ActionBar actionBar;

	private boolean toMarkComplete = true;

	private List<View> imageViews;

	int count=60;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todolist_activity_detail);
		todoTaskManager = new TodoTaskManager(this);
		imageViews = new ArrayList<View>();

		initComponents();
	}


	protected void onDestroy(){
		super.onDestroy();
		todoTaskManager.close();
	}

	@SuppressLint("LongLogTag")
	private void initComponents(){

		actionBar = getSupportActionBar();
		actionBar.setIcon(R.drawable.handdraw_notepad);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		etTitle = (EditText)findViewById(R.id.etTitle);
		etContent = (EditText)findViewById(R.id.etContent);


		taskId = getIntent().getStringExtra(TASK_ID);
		if(taskId != null){
			TodoTask todoTask = todoTaskManager.queryById(taskId);
			if(todoTask != null){
				etTitle.setText(todoTask.getTitle());
				etContent.setText(todoTask.getContent());
				flagForUpdate = true;
				if("N".equals(todoTask.getFlagCompleted())){
					toMarkComplete = true;
				}else{
					toMarkComplete = false;
				}
			}

		}

		if(flagForUpdate){
			if(toMarkComplete){
				btnMarkComplete = (Button)findViewById(R.id.btnMarkComplete);
				btnMarkComplete.setVisibility(View.VISIBLE);
				btnMarkComplete.setOnClickListener(markCompleteListener);
			}else{
				btnMarkUnComplete = (Button)findViewById(R.id.btnMarkUnComplete);
				btnMarkUnComplete.setVisibility(View.VISIBLE);
				btnMarkUnComplete.setOnClickListener(markCompleteListener);
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todolist_detail_save_cancel, menu);
		return true;
	}


	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				AlertDialog.Builder builder=new AlertDialog.Builder(todolistDetailActivity.this);
				builder.setMessage("若确定，退出该界面计时将停止，若想继续则点击保存。");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						finish();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {

					}
				});
				builder.create().show();
				overridePendingTransition(R.anim.hold, R.anim.push_right_out);
				break;
			case R.id.action_save:
				save();
				overridePendingTransition(R.anim.hold, R.anim.push_right_out);
				break;
			case R.id.action_cancel:
				cancel();
				overridePendingTransition(R.anim.hold, R.anim.push_right_out);
				break;
		}
		return super.onOptionsItemSelected(item);
	}



	private boolean validation(String title, String content){
		if(title == null || "".equals(title.trim())){
			Toast.makeText(this, "Title is Empty!", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	public void save(){
		String title = etTitle.getText().toString();
		String content = etContent.getText().toString();
		if(validation(title, content)) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(TableFields.TodoTask.TITLE, title);
			contentValues.put(TableFields.TodoTask.CONTENT, content);
			contentValues.put(TableFields.TodoTask.FLAG_COMPLETED, "N");

			if (flagForUpdate) {
				todoTaskManager.update(contentValues, TableFields.TodoTask.ID + " = ? ", new String[]{taskId});
				//intent = new Intent(todolistDetailActivity.this, todolistMainActivity.class);
			} else {
				long id = todoTaskManager.insert(contentValues);
				taskId = String.valueOf(id);
			}
			finish();

		};
	}


	public void cancel(){
		new AlertDialog.Builder(this)
				.setMessage("Input will be lost , sure ? ")
				.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setNegativeButton("Nope", null).show();
	}

	OnClickListener markCompleteListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(flagForUpdate){
				ContentValues contentValues = new ContentValues();
				switch(v.getId()){
					case R.id.btnMarkComplete:
						contentValues.put(TableFields.TodoTask.FLAG_COMPLETED,"Y");
						break;
					case R.id.btnMarkUnComplete:
						contentValues.put(TableFields.TodoTask.FLAG_COMPLETED,"N");
						contentValues.put(TableFields.TodoTask.COMPLETE_TIME, "");
						break;
				}
				todoTaskManager.update(contentValues, TableFields.TodoTask.ID + " = ? ", new String[] {taskId});
				finish();
			}
		}

	};


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
