package com.pdfread.standardreader.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hor.common.StringUtils;
import com.pdfread.standardreader.R;
import com.pdfread.standardreader.bean.RelativeSearchData;

public class RelativeStandardAdapter extends BaseAdapter {
	
	private Context context;
	private List<RelativeSearchData> datas;

	public RelativeStandardAdapter(Context context, List<RelativeSearchData> datas) {
		super();
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		
		if(convertView==null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_catagory_layout, null);
			
			holder.textView_name=(TextView) convertView.findViewById(R.id.stand_name);
			holder.textView_code=(TextView) convertView.findViewById(R.id.stand_code);
			holder.textView_time=(TextView) convertView.findViewById(R.id.time);
			holder.textView_status=(TextView) convertView.findViewById(R.id.stand_state);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		RelativeSearchData sd=datas.get(position);
		if(sd!=null){
			String name=sd.getFileTitle();
			String code=sd.getStandardNumber();
			String time=sd.getMaterialDate();
			String status=sd.getStandardStateItemName();
			
			if(!StringUtils.isEmpty(name)){
				holder.textView_name.setText(name);
			}
			
			if(!StringUtils.isEmpty(status)){
				//holder.textView_status.setText(status);
				
				if("现行有效".equals(status)){
					holder.textView_status.setBackgroundResource(R.drawable.effective);
				}else if("废止ֹ".equals(status)){
					holder.textView_status.setBackgroundResource(R.drawable.noeffective);
				}
				
			}
			
			if(!StringUtils.isEmpty(time)){
				holder.textView_time.setText(time);
			}
			
			if(!StringUtils.isEmpty(code)){
				holder.textView_code.setText(code);
			}
			
		}
		
		return convertView;
	}
	
	private class ViewHolder {
		private TextView textView_name;
		private TextView textView_code;
		private TextView textView_time;
		private TextView textView_status;
	}

}
