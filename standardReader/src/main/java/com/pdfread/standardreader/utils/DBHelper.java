package com.pdfread.standardreader.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, "StandardDB.db", null, 5);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql="create table t_Standard(_id integer primary key,abolishBasis varchar," +
				" fileTitle varchar, itemName varchar, materialDate varchar, standardNumber varchar , standardStateItemName varchar" +
				", submitTime varchar, gid varchar , path varchar)";
		
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		if(newVersion>oldVersion){
			db.execSQL("drop t_Standard if exists t_Standard");
			
			onCreate(db);
		}
		
	}

}
