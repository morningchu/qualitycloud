package com.pdfread.standardreader.ui;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hor.common.HttpManager;
import com.hor.common.StringUtils;
import com.pdfread.standardreader.R;
import com.pdfread.standardreader.bean.CatagoryData;
import com.pdfread.standardreader.contants.Contants;
import com.pdfread.standardreader.utils.CalculateUtil;
import com.pdfread.standardreader.utils.SoftInputUtils;
import com.pdfread.standardreader.views.BaseLibTopbarView;

public class StandardMainActivity extends Activity implements OnClickListener,OnKeyListener{

	private TextView tvContent;
	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
	
	private int totalFarming;
	private String nyName;
	private int totalEnergy;
	private String nahName;
	private int totalSynthesize;
	private String zhName;
	private int totalindustry;
	private String gyName;
	private int totalServe;
	private String fuName;
	private int totalTrip;
	private String lyName;
	private int totalBuilding;
	private String jzName;
	private int totalFood;
	private String spName;
	private EditText etSearch;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_layout);
		BaseLibTopbarView topbar=(BaseLibTopbarView)findViewById(R.id.topbar);
		topbar.recovery().setTitle("地方标准").setLeftText("返回", new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		topbar.setLeftImageButton(R.drawable.icon_back, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		initView();
		
		loadData();
		
	}

	private void loadData() {
		AsyncTask<String, String, String> loadData = new AsyncTask<String, String, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				String content= HttpManager.getUrlContent(Contants.FIRST_PAGE, "utf-8");
				
				return content;
			}

			@Override
			protected void onPostExecute(final String result) {
				super.onPostExecute(result);
				if (!StringUtils.isEmpty(result)) {
					try {
						
						JSONObject obj=new JSONObject(result);
						String nowTime=obj.getString("nowTime");
						int totalQuantity=obj.getInt("totalQuantity");
						int totalAbolish=obj.getInt("totalAbolish");
						int totalFeizhi=obj.getInt("totalFeizhi");
						
						tvContent.setText("截止"+nowTime+"全省累计发布地方标准数量"+totalQuantity+"个，其中：现行有效"+totalAbolish
								+"个，废止ֹ"+totalFeizhi+"个。");
						
						JSONArray array=obj.getJSONArray("result");
						
						List<CatagoryData> list=JSON.parseArray(array.toString(), CatagoryData.class);
						
						totalFarming = list.get(0).getNum();
						nyName = list.get(0).getName();
						
						tv3.setText(nyName+totalFarming+"项");
						
						totalEnergy = list.get(1).getNum();
						nahName = list.get(1).getName();
						
						tv5.setText(nahName+totalEnergy+"项");
						
						totalSynthesize = list.get(2).getNum();
						zhName = list.get(2).getName();
						
						tv1.setText(zhName+totalSynthesize+"项");
						
						totalindustry = list.get(3).getNum();
						gyName = list.get(3).getName();
						
						tv2.setText(gyName+totalindustry+"项");
						
						totalServe = list.get(4).getNum();
						fuName = list.get(4).getName();
						
						tv4.setText(fuName+totalServe+"项");
						
						totalTrip = list.get(5).getNum();
						lyName = list.get(5).getName();
						
						tv6.setText(lyName+totalTrip+"项");
						
						totalBuilding = list.get(6).getNum();
						jzName = list.get(6).getName();
						
						tv7.setText(jzName+totalBuilding+"项");
						
						totalFood = list.get(7).getNum();
						spName = list.get(7).getName();
						
						tv8.setText(spName+totalFood+"项");
						
						int total2015=obj.getInt("total2015");
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				} else {
					boolean internet = HttpManager
							.isConnectingToInternet(StandardMainActivity.this);
					if (internet) {
						Toast.makeText(StandardMainActivity.this, "获取数据失败",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(StandardMainActivity.this,
								"无网络连接。", Toast.LENGTH_SHORT).show();
					}
				}
			}
		};
		
		loadData.execute("");
	}

	private void initView() {
		View top_layout = findViewById(R.id.top_layout);
		CalculateUtil.setTitleParams(top_layout);
		
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("地方标准查询");
		
		TextView tv_left=(TextView) findViewById(R.id.tv_toleft);
		tv_left.setOnClickListener(this);
		
		CalculateUtil.calculateTextSize(tv_title, Contants.TITLE_SIZE);
		
		ImageView img_search=(ImageView) findViewById(R.id.img_search);
		img_search.setOnClickListener(this);
		
		etSearch = (EditText) findViewById(R.id.editText_main);
		etSearch.setOnKeyListener(this);
		
		tvContent = (TextView) findViewById(R.id.tv_main_sum);
		
		RelativeLayout rl_zong=(RelativeLayout) findViewById(R.id.rl_zong);
		RelativeLayout rl_gong=(RelativeLayout) findViewById(R.id.rl_gong);
		RelativeLayout rl_nong=(RelativeLayout) findViewById(R.id.rl_nong);
		RelativeLayout rl_fuwu=(RelativeLayout) findViewById(R.id.rl_fu);
		RelativeLayout rl_nengyuan=(RelativeLayout) findViewById(R.id.rl_nengyuan);
		RelativeLayout rl_lv=(RelativeLayout) findViewById(R.id.rl_lvyou);
		RelativeLayout rl_jian=(RelativeLayout) findViewById(R.id.rl_jian);
		RelativeLayout rl_shi=(RelativeLayout) findViewById(R.id.rl_shi);
		RelativeLayout rl_down=(RelativeLayout) findViewById(R.id.rl_down);
		
		rl_zong.setOnClickListener(this);
		rl_gong.setOnClickListener(this);
		rl_nong.setOnClickListener(this);
		rl_fuwu.setOnClickListener(this);
		rl_nengyuan.setOnClickListener(this);
		rl_lv.setOnClickListener(this);
		rl_jian.setOnClickListener(this);
		rl_shi.setOnClickListener(this);
		rl_down.setOnClickListener(this);
		
		tv1 = (TextView) findViewById(R.id.tv_1);
		tv2 = (TextView) findViewById(R.id.tv_2);
		tv3 = (TextView) findViewById(R.id.tv_3);
		tv4 = (TextView) findViewById(R.id.tv_4);
		tv5 = (TextView) findViewById(R.id.tv_5);
		tv6 = (TextView) findViewById(R.id.tv_6);
		tv7 = (TextView) findViewById(R.id.tv_7);
		tv8 = (TextView) findViewById(R.id.tv_8);
		
	}

	@Override
	public void onClick(View v) {
		int id=v.getId();
		Intent intent;
		
		if(id == R.id.tv_toleft){
			finish();
		}else if( id == R.id.rl_zong){
			intent=new Intent(StandardMainActivity.this, CatagoryActivity.class);
			intent.putExtra("name", zhName);
			intent.putExtra("num", totalSynthesize);
			startActivity(intent);
		}else if(id == R.id.rl_gong){
			intent=new Intent(StandardMainActivity.this, CatagoryActivity.class);
			intent.putExtra("name", gyName);
			intent.putExtra("num", totalindustry);
			startActivity(intent);
		}else if(id == R.id.rl_nong){
			intent=new Intent(StandardMainActivity.this, CatagoryActivity.class);
			intent.putExtra("name", nyName);
			intent.putExtra("num", totalFarming);
			startActivity(intent);
		}else if(id == R.id.rl_fu){
			intent=new Intent(StandardMainActivity.this, CatagoryActivity.class);
			intent.putExtra("name", fuName);
			intent.putExtra("num", totalServe);
			startActivity(intent);
		}else if(id == R.id.rl_nengyuan){
			intent=new Intent(StandardMainActivity.this, CatagoryActivity.class);
			intent.putExtra("name", nahName);
			intent.putExtra("num", totalEnergy);
			startActivity(intent);
		}else if(id == R.id.rl_lvyou){
			intent=new Intent(StandardMainActivity.this, CatagoryActivity.class);
			intent.putExtra("name", lyName);
			intent.putExtra("num", totalTrip);
			startActivity(intent);
		}else if(id == R.id.rl_jian){
			intent=new Intent(StandardMainActivity.this, CatagoryActivity.class);
			intent.putExtra("name", jzName);
			intent.putExtra("num", totalBuilding);
			startActivity(intent);
		}else if(id == R.id.rl_shi){
			intent=new Intent(StandardMainActivity.this, CatagoryActivity.class);
			intent.putExtra("name", spName);
			intent.putExtra("num", totalFood);
			startActivity(intent);
		}else if(id == R.id.rl_down){
			intent=new Intent(StandardMainActivity.this, MyDownLoadedActivity.class);
			startActivity(intent);
		}else if(id == R.id.img_search){
			intent=new Intent(StandardMainActivity.this, StandardSearchActivity.class);
			startActivity(intent);
		}
		
	}

	@Override
	public boolean onKey(View arg0, int keycode, KeyEvent event) {
		if (KeyEvent.KEYCODE_ENTER == keycode
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			String text = etSearch.getText().toString();
			if (text != null && !text.trim().equals("")) {
				SoftInputUtils.closeSoftInput(StandardMainActivity.this);
				
				//确认搜索
				Intent intent=new Intent(StandardMainActivity.this, StandardSearchActivity.class);
				intent.putExtra("keywords", text);
				startActivity(intent);
				
			}
		}
		return false;
	}

}
