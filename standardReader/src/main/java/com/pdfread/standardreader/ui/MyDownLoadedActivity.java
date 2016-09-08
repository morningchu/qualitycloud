package com.pdfread.standardreader.ui;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.hor.common.StringUtils;
import com.pdfread.standardreader.R;
import com.pdfread.standardreader.adapter.CollectionAdapter;
import com.pdfread.standardreader.bean.StandardData;
import com.pdfread.standardreader.pdf.PDFReadActivity;
import com.pdfread.standardreader.utils.CalculateUtil;
import com.pdfread.standardreader.utils.DBHelper;
import com.pdfread.standardreader.utils.FileUtils;
import com.pdfread.standardreader.views.BaseLibTopbarView;

public class MyDownLoadedActivity extends Activity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	private ListView listView;
	private ArrayList<StandardData> datas;

	DBHelper dbHelper = new DBHelper(this);

	private SimpleCursorAdapter adapter2;

	private int itemPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_downloaded);

		datas = new ArrayList<StandardData>();

		initView();

		initData();
	}

	private void initData() {

		SQLiteDatabase db2 = dbHelper.getReadableDatabase();
		Cursor cursor = db2.rawQuery(
				"select _id,fileTitle,standardNumber,standardStateItemName"
						+ " ,submitTime from t_Standard order by _id desc",
				null);

		String[] fromColumns = new String[] { "fileTitle", "standardNumber",
				"standardStateItemName", "submitTime" };

		int[] toLayoutIds = new int[] { R.id.stand_name, R.id.stand_code,
				R.id.stand_state, R.id.time };

		adapter2 = new CollectionAdapter(getApplicationContext(),
				R.layout.item_catagory_layout, cursor, fromColumns,
				toLayoutIds, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		listView.setAdapter(adapter2);

		query();

	}

	private void initView() {
		View top_layout = findViewById(R.id.title_down);
		CalculateUtil.setTitleParams(top_layout);

		BaseLibTopbarView topbar = (BaseLibTopbarView) findViewById(R.id.topbar_down);
		topbar.recovery().setTitle("已经下载标准")
				.setLeftText("返回", new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						finish();
					}
				});

		topbar.setLeftImageButton(R.drawable.icon_back, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("已经下载标准");

		TextView tv_left = (TextView) findViewById(R.id.tv_toleft);
		tv_left.setOnClickListener(this);

		listView = (ListView) findViewById(R.id.down_listview);

		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);

	}

	private void del(String path) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("t_Standard", "_id=?",
				new String[] { "" + adapter2.getItemId(itemPosition) });

		// TODO
		File file = new File(path);
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
//				Toast.makeText(MyDownLoadedActivity.this, "删除" + path,
//						Toast.LENGTH_SHORT).show();
			}
		}

		query();
	}

	private void query() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor clsCurosr = db.rawQuery(
				"select _id,fileTitle,standardNumber,standardStateItemName"
						+ " ,submitTime from t_Standard order by _id desc",
				null);
		adapter2.swapCursor(clsCurosr);

	}

	protected void delDialog(final Dialog dialog, View view, final String code,
			final String path) {
		TextView tvCode = (TextView) view.findViewById(R.id.tv_delete_content);

		CalculateUtil.calculateTextSize(tvCode, 30);
		
		if (!StringUtils.isEmpty(code)) {
			tvCode.setText("您确定要删除已经已经下载标准里的，" + code);
		}

		view.findViewById(R.id.btn_confirm_del).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 检索数据库 删除数据

						del(path);

						dialog.dismiss();

					}
				});

		view.findViewById(R.id.btn_cancle_del).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

	}

	private void aboutDialog(final Dialog dialog, View view, String abo) {
		// TODO Auto-generated method stub
		TextView tvContent = (TextView) view.findViewById(R.id.tv_fz_content);
		if (!StringUtils.isEmpty(abo)) {
			tvContent.setText(abo);
		} else {
			tvContent.setText("未知");
		}

		view.findViewById(R.id.btn_fz_close).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}

				});

		view.findViewById(R.id.btn_fz_sure).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}

				});

	};

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.tv_toleft) {
			finish();
		} else {
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> listview, View v,
			int position, long id) {

		String url = null;
		String code = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cur = db
				.rawQuery(
						"select _id, standardNumber,path from t_Standard order by _id desc",
						null);

		if (cur.moveToPosition(position)) {
			code = cur.getString(cur.getColumnIndex("standardNumber"));
			url = cur.getString(cur.getColumnIndex("path"));
		}

		itemPosition = position;
		Dialog dialog = new Dialog(MyDownLoadedActivity.this,
				R.style.Common_Dialog);
		View view = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.dialog_delte_layout, null);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		dialog.getWindow().setContentView(view);

		delDialog(dialog, view, code, url);

		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> listview, View v, int position,
			long id) {
		String url = null;
		String name = null;
		String status = null;
		String abolish = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cur = db
				.rawQuery(
						"select _id,abolishBasis,fileTitle,standardStateItemName,path from t_Standard order by _id desc",
						null);

		if (cur.moveToPosition(position)) {
			abolish = cur.getString(cur.getColumnIndex("abolishBasis"));
			name = cur.getString(cur.getColumnIndex("fileTitle"));
			status = cur.getString(cur.getColumnIndex("standardStateItemName"));
			url = cur.getString(cur.getColumnIndex("path"));
		}
		FileUtils fileUtils = new FileUtils();
		if (fileUtils.isFileExist(url.substring(url.lastIndexOf("/") + 1))) {

			if (status.equals("现行有效")) {

				Intent intent = new Intent(getApplicationContext(),
						PDFReadActivity.class);
				intent.putExtra("path", url);
				intent.putExtra("title", name);
				startActivity(intent);

			} else if (status.equals("废止")) {

				Dialog dialog = new Dialog(getApplicationContext(),
						R.style.Common_Dialog);
				View view = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.dialog_feizhi_layout, null);
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
				dialog.getWindow().setContentView(view);

				aboutDialog(dialog, view, abolish);

			}

		}

	}

}
