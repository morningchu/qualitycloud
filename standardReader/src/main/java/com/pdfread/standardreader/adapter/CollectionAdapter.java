package com.pdfread.standardreader.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.hor.common.StringUtils;
import com.pdfread.standardreader.R;

public class CollectionAdapter extends SimpleCursorAdapter {

	private Cursor m_cursor;
	private Context m_context;
	private LayoutInflater miInflater;

	public CollectionAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		m_context = context;
		m_cursor = c;
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		View convertView = null;
		if (arg0 == null) {
			convertView = miInflater.inflate(R.layout.item_catagory_layout, null);
		} else {
			convertView = arg0;
		}
		
		TextView textView_name = (TextView) convertView
				.findViewById(R.id.stand_name);
		TextView textView_code = (TextView) convertView
				.findViewById(R.id.stand_code);
		TextView textView_time = (TextView) convertView
				.findViewById(R.id.time);
		TextView textView_status = (TextView) convertView
				.findViewById(R.id.stand_state);
		
		textView_name.setTextColor(arg1.getResources().getColor(R.color.standard_name));
		textView_code.setTextColor(arg1.getResources().getColor(R.color.first_page_cata));
		textView_time.setTextColor(arg1.getResources().getColor(R.color.desc_color));

		String title = arg2.getString(arg2.getColumnIndex("fileTitle"));
		String number = arg2.getString(arg2.getColumnIndex("standardNumber"));
		String time = arg2.getString(arg2.getColumnIndex("submitTime"));
		String cursor = arg2.getString(arg2.getColumnIndex("standardStateItemName"));
		
		textView_name.setText(title);
		textView_code.setText(number);
		textView_time.setText(time);
		//textView_status.setText(arg2.getString(arg2.getColumnIndex("standardStateItemName")));
		
		if(!StringUtils.isEmpty(cursor)){
			
			if("现行有效".equals(cursor)){
				textView_status.setBackgroundResource(R.drawable.effective);
			}else if("废止".equals(cursor)){
				textView_status.setBackgroundResource(R.drawable.noeffective);
			}
			
		}
		
	}

}
