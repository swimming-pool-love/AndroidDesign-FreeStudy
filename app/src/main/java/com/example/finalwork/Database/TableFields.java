package com.example.finalwork.Database;

public class TableFields {
	
	public static final String DB_NAME = "ToDo.db";
	public static final String TABLE_TASKS = "tasks";
	public static final String TABLE_IMAGE_LINK = "task_image_link";
		
	public class TodoTask{		
		
		public static final String SQL_QUERY = 
				"select * from " + TABLE_TASKS + " order by flag_completed, complete_time desc, create_time desc";
		
		public static final String SQL_QUERY_BY_ID = 
				"select * from " + TABLE_TASKS + " where _id = ? ";		
			
		public static final String SQL_DROP_TASKS = "drop table if exists " + TABLE_TASKS;
		public static final String SQL_CREATE_TASKS = "create table if not exists " + TABLE_TASKS 
				             + " ( _id integer primary key autoincrement, "
							 + "   title varchar(30),"
							 + "   content varchar(200), "
							 + "   flag_completed char(1), "
							 + "   complete_time DATETIME,"
							 + "   create_time DATETIME DEFAULT CURRENT_TIMESTAMP "
							 + "  )";
		
		public static final String ID = "_id";
		public static final String TITLE = "title";
		public static final String CONTENT = "content";
		public static final String FLAG_COMPLETED = "flag_completed";
		public static final String COMPLETE_TIME = "complete_time";
		public static final String CREATE_TIME = "create_time";
	}
	
	public class TaskImageLinkFields {

		
		public static final String SQL_DROP_TASK_IMAGE_LINK = "drop table if exists " + TABLE_IMAGE_LINK;
		public static final String SQL_CREATE_TASK_IMAGE_LINK = "create table if not exists " + TABLE_IMAGE_LINK 
	            + " ( _id integer primary key autoincrement, "
				+ "   task_id integer,"
				+ "   image_path varchar(200), "
				+ "   create_time DATETIME DEFAULT CURRENT_TIMESTAMP "
				+ "  )";

	}
}
