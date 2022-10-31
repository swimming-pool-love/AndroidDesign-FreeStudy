package com.example.finalwork.Database;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TodoDatabaseHelper extends SQLiteOpenHelper{
	
	private static final String TAG = "com.lms.sqlitedemo.DatabaseHelper";

	private static final int DATABASE_VERSION = 2;	
	
	@SuppressLint("LongLogTag")
	public TodoDatabaseHelper(Context context){
		super(context, TableFields.DB_NAME, null, DATABASE_VERSION);
		Log.v(TAG, "DatabaseHelper Contructor.");
	}
	
	@SuppressLint("LongLogTag")
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v(TAG, "DatabaseHelper On Create.");		
		db.execSQL(TableFields.TodoTask.SQL_CREATE_TASKS);
		db.execSQL(TableFields.TaskImageLinkFields.SQL_CREATE_TASK_IMAGE_LINK);
	}	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(newVersion > oldVersion){
		}
	}

}
