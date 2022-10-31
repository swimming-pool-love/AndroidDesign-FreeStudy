package com.example.finalwork.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper{

	private static final String dbname = "finaldb";
	private SQLiteDatabase sd ;

	@Override
	public void onCreate(SQLiteDatabase db) {

		//user表，直接搬万总的
		db.execSQL("create table if not exists user" +
				"(userId integer primary key  AUTOINCREMENT,"+
				"userName varchar(20) not null,"+
				"pwd varchar(20) not null," +
				"phone varchar(15))");

		//发布者分享日记编号id,发布者账号userId，分享内容shareContent,
		//分享图片shareImg,日记点赞个数shareReap,日记发布时间publishTime
		db.execSQL("create table if not exists share" +
				"(shareId integer primary key  AUTOINCREMENT," +
				"userName varchar(20)," +
				"shareTitle varchar(200)," +
				"shareLabel varchar(100)," +
				"shareContent varchar(1000)," +
				"shareImg blob," +
				"shareReap varchar(20)," +
				"publishTime DATETIME)");

		//评论者账号userId，评论商品编号itemId，评论内容comment，评论时间time
		db.execSQL("create table if not exists comments(" +
				"userName varchar(100)," +
				"itemId integer," +
				"comment varchar(1000)," +
				"time DATETIME)");

	}

	public SQLiteHelper(@Nullable Context context) {
		super(context, dbname, null, 2);
	}

	public SQLiteHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, dbname, factory, version);
		// TODO Auto-generated constructor stub
		sd=context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, factory);
		String ct="create table if not exists user (userId integer primary key  AUTOINCREMENT,"
		+"userName varchar not null,"
		+"pwd varchar not null, phone varchar);";
		sd.execSQL(ct);
	}
	
	public void insertuser(String username,String pwd,String phone) {

		String sql="insert into user (username,pwd,phone) values ('"+username+"','"+pwd+"','"+phone+"');";
		sd.execSQL(sql);
	}
	
	public void updateuser(String username, String pwd, String phone) {
		String sql="update user set pwd='"+pwd+"',phone='"+phone+"'where username='"+username+"';";
		sd.execSQL(sql);
	}
	
	public Cursor selectuser(String username) {
		String selection="username='"+username+"'";
		Cursor cr=sd.query( "user", null, selection, null, null, null,null);
		return cr;
	}
	
	public Cursor selectalluser() {
		Cursor cr=sd.query( "user", null, null, null, null, null,null);
		return cr;
	}
	
	public void deleteuser(String username) {
		String sql="delete from user where username='"+username+"';";
		sd.execSQL(sql);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
