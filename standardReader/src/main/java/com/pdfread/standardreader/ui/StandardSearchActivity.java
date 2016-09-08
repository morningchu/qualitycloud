package com.pdfread.standardreader.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.pdfread.standardreader.R;
import com.pdfread.standardreader.contants.Contants;
import com.pdfread.standardreader.fragment.LocalStandardFragment;
import com.pdfread.standardreader.fragment.RelativeStandardFragment;
import com.pdfread.standardreader.utils.CalculateUtil;
import com.pdfread.standardreader.views.BaseLibTopbarView;

public class StandardSearchActivity extends FragmentActivity implements OnClickListener,OnItemClickListener{

	private TextView tvLocalstandard;
	private TextView tvRelativestandard;
	
	Drawable drawable;
	private String keywords;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standardsearch);
		
		drawable=getResources().getDrawable(R.drawable.blue_bar);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		
		keywords = getIntent().getStringExtra("keywords");
		
		LocalStandardFragment lsf=new LocalStandardFragment();
		Bundle bundle=new Bundle();
		bundle.putString("key", keywords);
		lsf.setArguments(bundle);
		FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fl_search, lsf);
		transaction.commit();
		
		initView();
		
	}

	private void initView() {
		View top_layout = findViewById(R.id.top_layout);
		CalculateUtil.setTitleParams(top_layout);
		
		BaseLibTopbarView topbar=(BaseLibTopbarView)findViewById(R.id.topbar_search);
		topbar.recovery().setTitle("地方标准查询").setLeftText("返回", new OnClickListener() {
			
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
		tv_title.setText("地方标准查询");
		
		TextView tv_left=(TextView) findViewById(R.id.tv_toleft);
		tv_left.setOnClickListener(this);
		
		CalculateUtil.calculateTextSize(tv_title, Contants.TITLE_SIZE);
		
		tvLocalstandard = (TextView) findViewById(R.id.tv_localstandard);
		tvRelativestandard = (TextView) findViewById(R.id.tv_relativestandard);
		
		tvLocalstandard.setOnClickListener(this);
		tvRelativestandard.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		int id=v.getId();
		if (id == R.id.tv_toleft) {
			finish();
		} else if (id == R.id.tv_localstandard) {
			tvLocalstandard.setCompoundDrawables(null, null, null, drawable);
			tvRelativestandard.setCompoundDrawables(null, null, null, null);
			LocalStandardFragment lsf=new LocalStandardFragment();
			Bundle bundle=new Bundle();
			bundle.putString("key", keywords);
			lsf.setArguments(bundle);
			FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.fl_search, lsf);
			transaction.commit();
		} else if (id == R.id.tv_relativestandard) {
			tvLocalstandard.setCompoundDrawables(null, null, null, null);
			tvRelativestandard.setCompoundDrawables(null, null, null, drawable);
			RelativeStandardFragment rsf=new RelativeStandardFragment();
			Bundle bundle2=new Bundle();
			bundle2.putString("key", keywords);
			rsf.setArguments(bundle2);
			FragmentTransaction transaction2=getSupportFragmentManager().beginTransaction();
			transaction2.replace(R.id.fl_search, rsf);
			transaction2.commit();
		} else {
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
}
