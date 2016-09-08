package com.pdfread.standardreader.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class MyProvider extends ContentProvider {
	// 定义一个UriMatcher类
	private static final UriMatcher MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		MATCHER.addURI("com.pdfread.standardreader.provider.MyProvider", "table_book", 1);
	}

	public MyProvider() {
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		return null;
	}

	@Override
	public boolean onCreate() {
		return false;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String whereClause,
			String[] whereArgs) {
		try {
//			SQLiteDatabase db = DataUtils.getDb(getContext());
//			db.update("table_book", values, whereClause, whereArgs);
//			Intent intent = new Intent();
//			intent.putExtra("bookid", whereArgs[0]);
//			intent.setAction(Contants.book_filter);// action与接收器相同
//			getContext().sendBroadcast(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

}
