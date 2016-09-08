package com.midian.qualitycloud.ui.adapter;

import java.util.ArrayList;

import com.midian.qualitycloud.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GirdAdapter extends BaseAdapter {
	private ArrayList<String> girdList = new ArrayList<String>();
	private Context context;
	
	
	public GirdAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return girdList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return girdList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.item_grid_view,
					null);
			holder.tv = (TextView) convertView.findViewById(R.id.grid_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
	
	class ViewHolder{
		TextView tv;
	}
}
