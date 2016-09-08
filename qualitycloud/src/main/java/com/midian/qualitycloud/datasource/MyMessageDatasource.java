package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;
import android.content.Context;

import com.midian.baidupush.DeviceMessage;

public class MyMessageDatasource extends BaseListDataSource<DeviceMessage> {

	public MyMessageDatasource(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	ArrayList<DeviceMessage> morelist = new ArrayList<DeviceMessage>();

	public void setData(ArrayList<DeviceMessage> data) {
		this.morelist = data;
	}

	public ArrayList<DeviceMessage> getData() {
		return this.morelist;
	}

	@Override
	protected ArrayList<DeviceMessage> load(int page) throws Exception {
		// TODO Auto-generated method stub

		hasMore = false;
		return morelist;
	}
}
