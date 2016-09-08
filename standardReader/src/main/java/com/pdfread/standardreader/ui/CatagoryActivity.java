package com.pdfread.standardreader.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

import com.hor.common.StringUtils;
import com.pdfread.standardreader.R;
import com.pdfread.standardreader.adapter.CatagoryAdapter;
import com.pdfread.standardreader.bean.StandardData;
import com.pdfread.standardreader.contants.Contants;
import com.pdfread.standardreader.net.AsyncHttpClient;
import com.pdfread.standardreader.net.AsyncHttpResponseHandler;
import com.pdfread.standardreader.utils.CalculateUtil;
import com.pdfread.standardreader.utils.Loading;
import com.pdfread.standardreader.utils.SoftInputUtils;
import com.pdfread.standardreader.views.BaseLibTopbarView;
import com.pdfread.standardreader.views.XListView;
import com.pdfread.standardreader.views.XListView.IXListViewListener;

public class CatagoryActivity extends Activity implements OnClickListener,OnKeyListener{
	
	private XListView listView;
	protected int current_page;
	private CatagoryAdapter adapter;
	private ArrayList<StandardData> datas=new ArrayList<StandardData>();
	
	private int num;
	private String name;
	
	private AsyncHttpClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catagory);
		
		num=getIntent().getIntExtra("num", 0);
		name=getIntent().getStringExtra("name");
		
		client = new AsyncHttpClient(CatagoryActivity.this);
		
		initView();
		initData(1);
		
	}
	
	private void initData(final int page) {
		
		client.get(CatagoryActivity.this, Contants.BASEURL+"webStandard/"+"index2Page.action?name="+name+"&&currentPage="+page+"&&num="+num,
				new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				if (!StringUtils.isEmpty(content)){
					Loading.loadFinish(listView, CatagoryActivity.this);
					isLoading = false;
					try {
						JSONObject obj=new JSONObject(content);
						
						JSONArray array=obj.getJSONArray("result");
						
						if(array!=null && array.length()>0){
							StandardData sd;
							for(int i=0;i<array.length();i++){
								
								JSONObject obj2=array.getJSONObject(i);
								sd=new StandardData();
								sd.setAbolishBasis(obj2.getString("abolishBasis"));
								sd.setFileTitle(obj2.getString("fileTitle"));
								sd.setFileUrl(obj2.getString("fileUrl"));
								sd.setGid(obj2.getLong("gid"));
								sd.setMaterialDate(obj2.getString("materialDate"));
								sd.setStandardNumber(obj2.getString("standardNumber"));
								sd.setYearNumber(obj2.getString("yearNumber"));
								sd.setStandardStateItemName(obj2.getString("standardStateItemName"));
								sd.setSubmitTime(obj2.getString("submitTime"));
								sd.setItemName(obj2.getString("itemName"));
								
								datas.add(sd);
								
							}
							
							if (page == 1) {
								adapter=new CatagoryAdapter(CatagoryActivity.this, datas);
								//adapter=new CatagoryAdapter(CatagoryActivity.this, datas);
								listView.setAdapter(adapter);
							} else {
								if (adapter != null) {
									adapter.notifyDataSetChanged();
								}
							}
							
							if (datas.size() < 10) {
								listView.setPullLoadEnable(false);
							} else {
								listView.setPullLoadEnable(true);
							}
						} else {
							if (page == 1) {
								//LoadingDialog.finishLoading();
								listView.setAdapter(null);
							}
							listView.setPullLoadEnable(false);
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				Loading.loadFinish(listView, CatagoryActivity.this);
				isLoading = false;
			}
		});
		
	}
	
	protected boolean isLoading = false;

	private void initView() {
		View top_layout = findViewById(R.id.top_layout);
		CalculateUtil.setTitleParams(top_layout);
		
		
		BaseLibTopbarView topbar=(BaseLibTopbarView)findViewById(R.id.topbar_cata);
		topbar.recovery().setTitle(name+num+"项").setLeftText("返回", new OnClickListener() {
			
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
		tv_title.setText(name+num+"项");
		
		CalculateUtil.calculateTextSize(tv_title, Contants.TITLE_SIZE);
		
		TextView tv_left=(TextView) findViewById(R.id.tv_toleft);
		tv_left.setOnClickListener(this);
		
		etSearch = (EditText) findViewById(R.id.editText_cata);
		etSearch.setOnKeyListener(this);
		
		listView = (XListView) findViewById(R.id.listView1);
		listView.setFooterDividersEnabled(false);
		listView.setHeaderDividersEnabled(false);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(false);
		//listView.setOnItemClickListener(CatagoryActivity.this);
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				current_page = 1;
				datas.clear();
				listView.setPullLoadEnable(false);
				initData(current_page);
			}

			@Override
			public void onLoadMore() {
				if (isLoading) {
					return;
				}
				isLoading = true;
				current_page++;
				initData(current_page);
			}
		});
		
	}
	
	public static Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contants.SRART_DWON:

				break;
			case Contants.OVER_DWON:
				String path = (String) msg.obj;
				
				//ReadUtil.read(CatagoryActivity.this, path);
				
				break;
			case Contants.LOAD_DWON:
				
				break;
			default:
				break;
			}
		};
	};
	private EditText etSearch;
	
	
	@Override
	public void onClick(View v) {
		int id=v.getId();
		if (id == R.id.tv_toleft) {
			finish();
		} else {
			
		}
	}


	@Override
	public boolean onKey(View arg0, int keycode, KeyEvent event) {
		if (KeyEvent.KEYCODE_ENTER == keycode
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			String text = etSearch.getText().toString();
			if (text != null && !text.trim().equals("")) {
				SoftInputUtils.closeSoftInput(CatagoryActivity.this);
				
				//确认搜索
				Intent intent=new Intent(CatagoryActivity.this, StandardSearchActivity.class);
				intent.putExtra("keywords", text);
				startActivity(intent);
				
			}
		}
		return false;
	}

}
