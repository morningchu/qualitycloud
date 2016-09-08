package com.pdfread.standardreader.adapter;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hor.common.StringUtils;
import com.pdfread.standardreader.R;
import com.pdfread.standardreader.bean.StandardData;
import com.pdfread.standardreader.pdf.PDFReadActivity;
import com.pdfread.standardreader.utils.DBHelper;
import com.pdfread.standardreader.utils.FileUtils;

public class DownLoadAdapter extends BaseAdapter {
	
	private Context context;
	private List<StandardData> datas;
	
	DBHelper dbHelper;

	public DownLoadAdapter(Context context, List<StandardData> datas) {
		super();
		this.context = context;
		this.datas = datas;
		dbHelper=new DBHelper(context);
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
			
			holder.rl_cata=(RelativeLayout) convertView.findViewById(R.id.rl_item_cata);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		final StandardData sd=datas.get(position);
		if(sd!=null){
			final String abolish = sd.getAbolishBasis();
			final String name = sd.getFileTitle();
			final String code = sd.getStandardNumber();
			final String time = sd.getYearNumber();
			final String status = sd.getStandardStateItemName();
			final String url = sd.getFileUrl();   ///已经做位path了
			final long gid = sd.getGid();
			final String itemName = sd.getItemName();
			final String materTime = sd.getMaterialDate();
			final String subTime = sd.getSubmitTime();
			
			if(!StringUtils.isEmpty(name)){
				holder.textView_name.setText(name);
			}
			
			if(!StringUtils.isEmpty(status)){
				//holder.textView_status.setText(status);
				
				if("现行有效".equals(status)){
					holder.textView_status.setBackgroundResource(R.drawable.effective);
				}else if("废止".equals(status)){
					holder.textView_status.setBackgroundResource(R.drawable.noeffective);
				}
				
			}
			
			if(!StringUtils.isEmpty(subTime)){
				holder.textView_time.setText(subTime);
			}
			
			if(!StringUtils.isEmpty(code)){
				holder.textView_code.setText(code);
			}
			
			holder.rl_cata.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					FileUtils fileUtils=new FileUtils();
					if(fileUtils.isFileExist(url.substring(url.lastIndexOf("/")+1))){
						
						if(status.equals("现行有效")){
							
							Intent intent = new Intent(context, PDFReadActivity.class);
							intent.putExtra("path", url);
							intent.putExtra("title", name);
							context.startActivity(intent);
							
						} else if(status.equals("废止")){
							Dialog dialog = new Dialog(context, R.style.Common_Dialog);
							View view = LayoutInflater.from(context).inflate(
									R.layout.dialog_feizhi_layout, null);
							dialog.setCanceledOnTouchOutside(false);
							dialog.show();
							dialog.getWindow().setContentView(view);
							
							aboutDialog(dialog, view ,abolish);
							
						}
						
					}
						
				}
					
			});
			
			holder.rl_cata.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					// TODO 删除操作
					Dialog dialog = new Dialog(context, R.style.Common_Dialog);
					View view = LayoutInflater.from(context).inflate(
							R.layout.dialog_delte_layout, null);
					dialog.setCanceledOnTouchOutside(false);
					dialog.show();
					dialog.getWindow().setContentView(view);
					
					delDialog(dialog, view ,code);
					
					return false;
				}
			});
			
		}
		
		return convertView;
	}
	
	protected void delDialog(final Dialog dialog, View view, final String code) {
		TextView tvCode=(TextView) view.findViewById(R.id.tv_delete_content);
		
		if(!StringUtils.isEmpty(code)){
			tvCode.setText(code);
		}
		
		view.findViewById(R.id.btn_confirm_del).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 检索数据库  删除数据
				SQLiteDatabase db2= dbHelper.getWritableDatabase();
				db2.delete("t_Standard", "standardNumber=?", new String[]{code});
				
				db2.close();
				dialog.dismiss();
				
			}
		});
		
		view.findViewById(R.id.btn_cancle_del).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
	}
	

	private class ViewHolder {
		private TextView textView_name;
		private TextView textView_code;
		private TextView textView_time;
		private TextView textView_status;
		
		private RelativeLayout rl_cata;
	}
	

	private void aboutDialog(final Dialog dialog, View view, String abo) {
		// TODO Auto-generated method stub
		TextView tvContent=(TextView) view.findViewById(R.id.tv_fz_content);
		if(!StringUtils.isEmpty(abo)){
			tvContent.setText(abo);
		}else{
			tvContent.setText("未知");
		}
		
		view.findViewById(R.id.btn_fz_close).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		view.findViewById(R.id.btn_fz_sure).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
	};
	
}
