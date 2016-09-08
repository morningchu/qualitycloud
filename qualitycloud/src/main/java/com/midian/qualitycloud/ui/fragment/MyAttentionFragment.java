package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;

import midian.baselib.base.BaseListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.MyCollectsBean.CollectContent;
import com.midian.qualitycloud.datasource.MyAttentionDatasource;
import com.midian.qualitycloud.itemview.MyAttentionItemTpl;
import com.midian.qualitycloud.widget.SelectFilterTab;

/**
 * 我的关注
 * 
 * @author MIDIAN
 * 
 */

public class MyAttentionFragment extends BaseListFragment<CollectContent>
		implements SelectFilterTab.onTabChangeListener {
	SelectFilterTab mSelectFilterTab;
	MyAttentionDatasource mMyAttentionDatasource;
	boolean isLogin;
	int index;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		index = 0;
		mMyAttentionDatasource = new MyAttentionDatasource(_activity, "");
		View footer = super.onCreateView(inflater, container,
				savedInstanceState);
		mSelectFilterTab = (SelectFilterTab) footer.findViewById(R.id.filter);
		mSelectFilterTab.setOnTabChangeListener(this);
		isLogin = ac.isAccess();
		return footer;
	}

	protected int getLayoutId() {
		return R.layout.fragment_myattention;
	}

	@Override
	protected IDataSource<ArrayList<CollectContent>> getDataSource() {
		// TODO Auto-generated method stub
		return mMyAttentionDatasource;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (isLogin != ac.isAccess() || ac.ischange) {// 登录状态变化
			ac.ischange = false;
			isLogin = ac.isAccess();
			refresh(index, false);
		}

	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return MyAttentionItemTpl.class;
	}

	@Override
	public void onTabChange(int index, boolean isSelect) {
		// TODO Auto-generated method stub
		this.index = index;
		mMyAttentionDatasource.setType(String.valueOf(index));
		listViewHelper.refresh();
	}

	public void refresh(int index, boolean isSelect) {
		mMyAttentionDatasource.setType(String.valueOf(index));
		listViewHelper.refresh();
	}

}
