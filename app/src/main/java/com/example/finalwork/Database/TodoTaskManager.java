package com.example.finalwork.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.finalwork.model.TodoTask;

import java.util.ArrayList;
import java.util.List;


public class TodoTaskManager extends DatabaseManager{

    private static final String TAG = "com.lms.todo.db.TodoTaskManager";

    public TodoTaskManager(Context context) {
        super(context);
    }

    public long insert(ContentValues contentValues){
        return super.insert(TableFields.TABLE_TASKS, contentValues);
    }

    public long update(ContentValues contentValues, String whereClause, String[] whereArgs){
        return super.update(TableFields.TABLE_TASKS, contentValues, whereClause, whereArgs);
    }

    public boolean delete(String whereClause, String[] whereArgs){
        return super.delete(TableFields.TABLE_TASKS, whereClause, whereArgs);
    }

    @SuppressLint("LongLogTag")
    public static List<TodoTask> queryAll(){
        List<TodoTask> list = new ArrayList<TodoTask>();

        Cursor cursor = database.rawQuery(TableFields.TodoTask.SQL_QUERY, null);
        if(cursor == null){

        }else if(!cursor.moveToFirst()){

        }else{
            int columnId = cursor.getColumnIndex(TableFields.TodoTask.ID);
            int columnTitle = cursor.getColumnIndex(TableFields.TodoTask.TITLE);
            int columnContent = cursor.getColumnIndex(TableFields.TodoTask.CONTENT);
            int columnFlagCompleted = cursor.getColumnIndex(TableFields.TodoTask.FLAG_COMPLETED);

            do{
                int id = cursor.getInt(columnId);
                String title = cursor.getString(columnTitle);
                String content = cursor.getString(columnContent);
                String flagCompleted = cursor.getString(columnFlagCompleted);

                TodoTask task = new TodoTask();
                task.setId(id);
                task.setTitle(title);
                task.setContent(content);
                task.setFlagCompleted(flagCompleted);

                list.add(task);

            }while(cursor.moveToNext());
        }
        cursor.close();
        Log.v(TAG, "Total Records : " + list.size());
        return list;
    }

    public TodoTask queryById(String taskId){
        TodoTask task = null;
        Cursor cursor = database.rawQuery(TableFields.TodoTask.SQL_QUERY_BY_ID, new String[] {taskId});
        if(cursor == null){

        }else if(!cursor.moveToFirst()){

        }else{
            int columnId = cursor.getColumnIndex(TableFields.TodoTask.ID);
            int columnTitle = cursor.getColumnIndex(TableFields.TodoTask.TITLE);
            int columnContent = cursor.getColumnIndex(TableFields.TodoTask.CONTENT);
            int columnFlagCompleted = cursor.getColumnIndex(TableFields.TodoTask.FLAG_COMPLETED);

            int id = cursor.getInt(columnId);
            String title = cursor.getString(columnTitle);
            String content = cursor.getString(columnContent);
            String flagCompleted = cursor.getString(columnFlagCompleted);

            task = new TodoTask();
            task.setId(id);
            task.setTitle(title);
            task.setContent(content);
            task.setFlagCompleted(flagCompleted);
        }
        cursor.close();
        return task;
    }
}
