package com.midian.qualitycloud.ui.testorganization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import midian.baselib.adapter.CPagerTabAdapter;
import midian.baselib.adapter.CsPagerTabAdapter;
import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.CustomIndicator;
import midian.baselib.widget.SearchEditText;
import midian.baselib.widget.SearchEditText.SearchEditTextListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.CheckTypesBean;
import com.midian.qualitycloud.bean.CheckTypesBean.ContentCheckTypes;
import com.midian.qualitycloud.ui.geographical.QueryGIResultActivity;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 查询测试机构
 * 
 * @author MIDIAN
 * 
 */
public class QueryTestOrganizationActivity extends BaseActivity implements
		SearchEditTextListener {

	private BaseLibTopbarView mBaseLibTopbarView;
	ViewPager pager;
	CustomIndicator mCustomIndicator;
	SearchEditText mSearchEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_test_organization);
		initTitle();
		initViewPager();
		mSearchEditText = (SearchEditText) findViewById(R.id.search_query);
		// mSearchEditText.getEditText().setFocusable(false);
		// mSearchEditText.setOnClickListener(this);
		// mSearchEditText.getEditText().setOnClickListener(this);
		mSearchEditText.setSearchEditTextListener(this);
	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setMode(BaseLibTopbarView.MODE_1).setTitle("检验检测服务")
				.setLeftText("返回", null).getTitle_tv()
				.setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#ffffff"));
	}

	/**
	 * 初始化底部viewpager
	 */
	private void initViewPager() {
		View view1 = View.inflate(this, R.layout.page_query_test_organization1,
				null);
		// View view2 = View.inflate(this,
		// R.layout.page_query_test_organization2,
		// null);
		// View view3 = View.inflate(this,
		// R.layout.page_query_test_organization3,
		// null);

		ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		// views.add(view2);
		// views.add(view3);

		view1.findViewById(R.id.test1).setOnClickListener(this);
		view1.findViewById(R.id.test2).setOnClickListener(this);
		view1.findViewById(R.id.test3).setOnClickListener(this);
		view1.findViewById(R.id.test4).setOnClickListener(this);
		view1.findViewById(R.id.test5).setOnClickListener(this);
		view1.findViewById(R.id.test6).setOnClickListener(this);
		view1.findViewById(R.id.test7).setOnClickListener(this);
		view1.findViewById(R.id.test8).setOnClickListener(this);
		view1.findViewById(R.id.test9).setOnClickListener(this);

		// view2.findViewById(R.id.test10).setOnClickListener(this);
		// view2.findViewById(R.id.test11).setOnClickListener(this);
		// view2.findViewById(R.id.test12).setOnClickListener(this);
		// view2.findViewById(R.id.test13).setOnClickListener(this);
		// view2.findViewById(R.id.test14).setOnClickListener(this);
		// view2.findViewById(R.id.test15).setOnClickListener(this);
		// view2.findViewById(R.id.test16).setOnClickListener(this);
		// view2.findViewById(R.id.test17).setOnClickListener(this);
		// view2.findViewById(R.id.test18).setOnClickListener(this);
		//
		// view3.findViewById(R.id.test19).setOnClickListener(this);
		// view3.findViewById(R.id.test20).setOnClickListener(this);
		// view3.findViewById(R.id.test21).setOnClickListener(this);
		// view3.findViewById(R.id.test22).setOnClickListener(this);
		// view3.findViewById(R.id.test23).setOnClickListener(this);
		// view3.findViewById(R.id.test24).setOnClickListener(this);

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new CPagerTabAdapter(views, null));
		mCustomIndicator = (CustomIndicator) findViewById(R.id.indicator);
		mCustomIndicator.initIndicator(_activity, pager);

		try {
			AppUtil.getQualityCloudApiClient(ac).getCheckFields(this);
			showLoadingDlg();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		if (arg0 instanceof TextView) {
			TextView tv = (TextView) arg0;
			Bundle b = new Bundle();
			b.putString("text", tv.getText().toString());
			String id = String.valueOf(map.get(tv.getText().toString()));
			b.putString("field_id", id);
			UIHelper.jump(_activity,
					QueryTestOrganizationResultActivity1.class, b);

		}
	}

	private Map<String, String> map = new HashMap<String, String>();
	private String id;
	private String name;
	private String field_id;

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		hideLoadingDlg();
		CheckTypesBean typesBean = (CheckTypesBean) res;
		if (res.isOK()) {
			for (ContentCheckTypes item : typesBean.getContent()) {
				id = item.getCheck_field_id();
				name = item.getCheck_field_name();
				map.put(name, id);
			}
		}
	}

	@Override
	public void search(String key) {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		String id = "";
		if (map.containsKey(key)) {
			id = map.get(key);
		}
		b.putString("text", key);
		b.putString("field_id", id);
		UIHelper.jump(_activity, QueryTestOrganizationResultActivity1.class, b);
	}
}
