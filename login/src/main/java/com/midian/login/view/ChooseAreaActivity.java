package com.midian.login.view;

import java.util.ArrayList;

import midian.baselib.base.BaseListActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;

import com.midian.login.R;
import com.midian.login.datasource.ChooseAreaDataSource;
import com.midian.login.itemView.ChooseAreaTpl;

/**
 * 选择地区
 * 
 * @author chu
 *
 */
public class ChooseAreaActivity extends BaseListActivity<NetResult> {

	private BaseLibTopbarView topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		topbar.setTitle("选择地区");
		topbar.setLeftImageButton(R.drawable.icon_back, UIHelper.finish(this));
	}

	@Override
	protected IDataSource<ArrayList<NetResult>> getDataSource() {
		return new ChooseAreaDataSource(_activity);
	}

	@Override
	protected Class getTemplateClass() {

		return ChooseAreaTpl.class;
	}

}
