package com.example.finalwork.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
	
	protected static SQLiteDatabase database;
	
	protected TodoDatabaseHelper todoDatabaseHelper;
	
	public DatabaseManager(Context context){
		if (todoDatabaseHelper == null) {
			todoDatabaseHelper = new TodoDatabaseHelper(context);
		}
		database = todoDatabaseHelper.getWritableDatabase();
	}	
	
	public void close(){
		database.close();
	}
	
	public void recreateTable(){
		database.execSQL(TableFields.TodoTask.SQL_DROP_TASKS);
		database.execSQL(TableFields.TodoTask.SQL_CREATE_TASKS);		
		database.execSQL(TableFields.TaskImageLinkFields.SQL_DROP_TASK_IMAGE_LINK);
		database.execSQL(TableFields.TaskImageLinkFields.SQL_CREATE_TASK_IMAGE_LINK);
	}
	
	public long insert(String tableName,ContentValues contentValues){
		return database.insert(tableName, null, contentValues);		
	}
	
	public long update(String tableName, ContentValues contentValues, String whereClause, String[] whereArgs){
		return database.update(tableName, contentValues, whereClause, whereArgs);
	}
	
	public boolean delete(String tableName, String whereClause, String[] whereArgs){		
		database.delete(tableName, whereClause, whereArgs);
		return true;
	}

}
