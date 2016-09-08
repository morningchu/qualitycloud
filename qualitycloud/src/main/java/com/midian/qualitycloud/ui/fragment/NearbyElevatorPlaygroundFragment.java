package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;

import midian.baselib.base.BaseListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.ScreenUtils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.NearFacilitiesBean;
import com.midian.qualitycloud.datasource.MyAttentionDatasource;
import com.midian.qualitycloud.datasource.NearbyElevatorPlaygroundDatasource;
import com.midian.qualitycloud.itemview.MyAttentionItemTpl;
import com.midian.qualitycloud.itemview.NearbyElevatorPlaygroundItemTpl;

/**
 * 附近电梯列表
 * 
 * @author MIDIAN
 * 
 */

public class NearbyElevatorPlaygroundFragment extends
		BaseListFragment<NearFacilitiesBean.Content> {
	int type = 1;
	String ids = "";
	String str = "";
	NearbyElevatorPlaygroundDatasource mNearbyElevatorPlaygroundDatasource;
	NearbyElevatorPlaygroundItemTpl itemTpl;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		type = getArguments().getInt("type");
		if (type < 1)
			type = 1;
		ids = getArguments().getString("ids");
		str = getArguments().getString("key");
		mNearbyElevatorPlaygroundDatasource = new NearbyElevatorPlaygroundDatasource(
				_activity, type, str, ids);
		// itemTpl = new NearbyElevatorPlaygroundItemTpl(_activity,
		// String.valueOf(type));
		View main = super.onCreateView(inflater, container, savedInstanceState);

		listView.setDivider(new ShapeDrawable(new Shape() {

			@Override
			public void draw(Canvas arg0, Paint arg1) {
				// TODO Auto-generated method stub
				arg1.setColor(getResources().getColor(R.color.window_bg));
				arg0.drawPaint(arg1);
			}
		}));
		listView.setDividerHeight(ScreenUtils.dpToPxInt(_activity, 15));
		return main;
	}

	public void search(String key) {
		mNearbyElevatorPlaygroundDatasource.setKeyWords(key);
		listViewHelper.refresh();
	}

	@Override
	protected IDataSource<ArrayList<NearFacilitiesBean.Content>> getDataSource() {
		// TODO Auto-generated method stub
		return mNearbyElevatorPlaygroundDatasource;
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return NearbyElevatorPlaygroundItemTpl.class;
	}

	@Override
	public void onStartRefresh(
			IDataAdapter<ArrayList<NearFacilitiesBean.Content>> adapter) {
		// TODO Auto-generated method stub
		// _activity.showLoadingDlg();
	}

	@Override
	public void onEndRefresh(
			IDataAdapter<ArrayList<NearFacilitiesBean.Content>> adapter,
			ArrayList<NearFacilitiesBean.Content> result) {
		// TODO Auto-generated method stub
		// _activity.hideLoadingDlg();
	}

	@Override
	public void onStartLoadMore(
			IDataAdapter<ArrayList<NearFacilitiesBean.Content>> adapter) {
		// TODO Auto-generated method stub
		_activity.showLoadingDlg();
	}

	@Override
	public void onEndLoadMore(
			IDataAdapter<ArrayList<NearFacilitiesBean.Content>> adapter,
			ArrayList<NearFacilitiesBean.Content> result) {
		// TODO Auto-generated method stub
		_activity.hideLoadingDlg();
	}

}
