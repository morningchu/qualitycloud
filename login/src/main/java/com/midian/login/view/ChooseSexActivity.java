package com.midian.login.view;

import midian.baselib.base.BaseActivity;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.midian.login.R;

public class ChooseSexActivity extends BaseActivity {

	private BaseLibTopbarView topbar;
	private TextView sex_man, sex_girl;
	private String title, sex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_sex);
		Bundle mBundle = getIntent().getExtras();
		title = mBundle.getString("title");
		topbar = findView(R.id.topbar);
		topbar.setTitle(title);

		sex_man = findView(R.id.sex_man);
		sex_girl = findView(R.id.sex_girl);
		sex_man.setOnClickListener(this);
		sex_girl.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Bundle mBundle = new Bundle();
		int id = v.getId();
		if (id == R.id.sex_man) {
			sex = sex_man.getText().toString();
			System.out.println("sex:::::::::" + sex);
			mBundle.putString("sex", sex);
			setResult(RESULT_OK, mBundle);
			finish();
		} else if (id == R.id.sex_girl) {
			sex = sex_girl.getText().toString();
			mBundle.putString("sex", sex);
			System.out.println("sex:::::::::" + sex);
			setResult(RESULT_OK, mBundle);
			finish();
		} else {
		}
	}
}
