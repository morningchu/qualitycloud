package com.pdfread.standardreader.adapter;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hor.common.HttpManager;
import com.hor.common.StringUtils;
import com.pdfread.standardreader.R;
import com.pdfread.standardreader.bean.StandardData;
import com.pdfread.standardreader.contants.Contants;
import com.pdfread.standardreader.pdf.PDFReadActivity;
import com.pdfread.standardreader.utils.DBHelper;
import com.pdfread.standardreader.utils.FileUtils;
import com.pdfread.standardreader.utils.LoadingDialog;

public class SearchAdapter extends BaseAdapter {
	
	private Context context;
	private List<StandardData> datas;
	
	DBHelper dbHelper;

	public SearchAdapter(Context context, List<StandardData> datas) {
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
					R.layout.item_localsearch_layout, null);
			
			holder.imgView=(ImageView) convertView.findViewById(R.id.img_item);
			holder.textView_cataName=(TextView) convertView.findViewById(R.id.tv_2_item);
			holder.textView_name=(TextView) convertView.findViewById(R.id.stand_name);
			holder.textView_code=(TextView) convertView.findViewById(R.id.stand_code);
			holder.textView_time=(TextView) convertView.findViewById(R.id.time);
			holder.textView_status=(TextView) convertView.findViewById(R.id.stand_state);
			
			holder.rl_search=(RelativeLayout) convertView.findViewById(R.id.rl_item_localsearch);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		final StandardData sd=datas.get(position);
		if(sd!=null){
			String abo=sd.getAbolishBasis();
			String name=sd.getFileTitle();
			String code=sd.getStandardNumber();
			String time=sd.getYearNumber();
			String status=sd.getStandardStateItemName();
			final String url=sd.getFileUrl();
			String itemName=sd.getItemName();
			
			if(!StringUtils.isEmpty(itemName)){
				holder.textView_cataName.setText(itemName);
				
				if("综合类".equals(itemName)){
					holder.imgView.setImageResource(R.drawable.icon01);
				}else if("工业类".equals(itemName)){
					holder.imgView.setImageResource(R.drawable.icon02);
				}else if("农业类".equals(itemName)){
					holder.imgView.setImageResource(R.drawable.icon03);
				}else if("服务类".equals(itemName)){
					holder.imgView.setImageResource(R.drawable.icon04);
				}else if("能源、安全、环保类".equals(itemName)){
					holder.imgView.setImageResource(R.drawable.icon05);
				}else if("旅游类".equals(itemName)){
					holder.imgView.setImageResource(R.drawable.icon06);
				}else if("建筑类".equals(itemName)){
					holder.imgView.setImageResource(R.drawable.icon07);
				}else if("食品类".equals(itemName)){
					holder.imgView.setImageResource(R.drawable.icon08);
				}else{
					holder.imgView.setImageResource(R.drawable.icon01);
				}
				
			}
			
			if(!StringUtils.isEmpty(name)){
				holder.textView_name.setText(name);
			}
			
			if(!StringUtils.isEmpty(status)){
				//holder.textView_status.setText(status);
				
				if("现行有效".equals(status)){
					holder.textView_status.setBackgroundResource(R.drawable.effective);
				}else if("废止ֹ".equals(status)){
					holder.textView_status.setBackgroundResource(R.drawable.noeffective);
				}else if("复审".equals(status)){
					holder.textView_status.setBackgroundResource(R.drawable.fushen);
				}
				
			}
			
			if(!StringUtils.isEmpty(time)){
				holder.textView_time.setText(time);
			}
			
			if(!StringUtils.isEmpty(code)){
				holder.textView_code.setText(code);
			}
			
			holder.rl_search.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final String path = Environment
							.getExternalStorageDirectory().getAbsolutePath()+"/standard/" + url.substring(url.lastIndexOf("/")+1);
					
					LoadingDialog.isLoading(context);
						//下载
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								FileUtils fileUtils=new FileUtils();
								if(fileUtils.isFileExist(url.substring(url.lastIndexOf("/")+1))){
									
									sd.setFileUrl(path);
									Message msg = Message.obtain();
									msg.obj = sd;
									msg.what = Contants.IS_EXIECT;
									
									mHandler.sendMessage(msg);
									
								}else{
									
									String str=HttpManager.downLoadResource("http://cloud.gzqts.gov.cn/dfbz/"+url, path);
									sd.setFileUrl(str);
									if(!StringUtils.isEmpty(str)){
										Message msg = Message.obtain();
										msg.obj = sd;
										msg.what = Contants.OVER_DWON;
										
										mHandler.sendMessage(msg);
									}
									
								}
								
							}
						}).start();
					}
					
			});
			
		}
		
		return convertView;
	}
	
	private class ViewHolder {
		private RelativeLayout rl_search;
		private ImageView imgView;
		private TextView textView_cataName;
		private TextView textView_name;
		private TextView textView_code;
		private TextView textView_time;
		private TextView textView_status;
	}
	
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contants.IS_EXIECT:
				StandardData ss = (StandardData) msg.obj;
				
				LoadingDialog.finishLoading();
				
				if(ss.getStandardStateItemName().equals("现行有效")){
					
					Intent intent = new Intent(context, PDFReadActivity.class);
					intent.putExtra("path", ss.getFileUrl());
					intent.putExtra("title", ss.getFileTitle());
					context.startActivity(intent);
					
				} else if(ss.getStandardStateItemName().equals("废止")){
					Dialog dialog = new Dialog(context, R.style.Common_Dialog);
					View view = LayoutInflater.from(context).inflate(
							R.layout.dialog_feizhi_layout, null);
					dialog.setCanceledOnTouchOutside(false);
					dialog.show();
					dialog.getWindow().setContentView(view);
					
					aboutDialog(dialog, view ,ss.getAbolishBasis());
					
				} else if(ss.getStandardStateItemName().equals("复审")){
					Dialog dialog = new Dialog(context, R.style.Common_Dialog);
					View view = LayoutInflater.from(context).inflate(
							R.layout.dialog_fushen_layout, null);
					dialog.setCanceledOnTouchOutside(false);
					dialog.show();
					dialog.getWindow().setContentView(view);
					
					showFSDialog(dialog, view);
				}
				
				break;
			case Contants.OVER_DWON:
				StandardData ss2 = (StandardData) msg.obj;
				
				LoadingDialog.finishLoading();
				
				if(ss2.getFileUrl().contains(".pdf")){
					
					if(ss2.getStandardStateItemName().equals("现行有效")){
						Intent intent2 = new Intent(context, PDFReadActivity.class);
					    intent2.putExtra("path", ss2.getFileUrl());
					    intent2.putExtra("title", ss2.getFileTitle());
					    context.startActivity(intent2);
					} else if(ss2.getStandardStateItemName().equals("废止")){
						Dialog dialog = new Dialog(context, R.style.Common_Dialog);
						View view = LayoutInflater.from(context).inflate(
								R.layout.dialog_feizhi_layout, null);
						dialog.setCanceledOnTouchOutside(false);
						dialog.show();
						dialog.getWindow().setContentView(view);
						
						aboutDialog(dialog, view ,ss2.getAbolishBasis());
					} else if(ss2.getStandardStateItemName().equals("复审")){
						Dialog dialog = new Dialog(context, R.style.Common_Dialog);
						View view = LayoutInflater.from(context).inflate(
								R.layout.dialog_fushen_layout, null);
						dialog.setCanceledOnTouchOutside(false);
						dialog.show();
						dialog.getWindow().setContentView(view);
						
						showFSDialog(dialog, view);
						
					}
					
					// TODO  插入数据库
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					String sql="insert into t_Standard (abolishBasis, fileTitle, itemName, materialDate, standardNumber" +
							", standardStateItemName, submitTime, gid , path)" +
							" values(?, ?, ?, ?, ?, ?, ?, ? ,?)";
					
					db.execSQL(sql, new String[] { ss2.getAbolishBasis(), ss2.getFileTitle(), ss2.getItemName(), 
							ss2.getMaterialDate(), ss2.getStandardNumber(), ss2.getStandardStateItemName(), ss2.getSubmitTime(),
							ss2.getGid()+"", ss2.getFileUrl()});
					
					db.close();
					
				}else{
					Toast.makeText(context, "下载出错", Toast.LENGTH_SHORT).show();
				}
				
				break;
			default:
				break;
			}
		}

		private void showFSDialog(final Dialog dialog, View view) {
			// TODO Auto-generated method stub
			TextView tvContent=(TextView) view.findViewById(R.id.tv_fs_content);
			tvContent.setText("该标准在复审，现在不能打开");
			
			view.findViewById(R.id.btn_fs_close).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			
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
	};

}
