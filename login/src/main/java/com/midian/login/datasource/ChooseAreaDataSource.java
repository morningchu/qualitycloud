package com.midian.login.datasource;

import java.util.ArrayList;

import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;
import android.content.Context;

public class ChooseAreaDataSource extends BaseListDataSource<NetResult> {

	public ChooseAreaDataSource(Context context) {
		super(context);
	}

	@Override
	protected ArrayList<NetResult> load(int page) throws Exception {
		ArrayList<NetResult> moreList = new ArrayList<NetResult>();
		moreList.add(new NetResult());
		moreList.add(new NetResult());
		moreList.add(new NetResult());
		moreList.add(new NetResult());
		hasMore = false;
		return moreList;
	}

}
