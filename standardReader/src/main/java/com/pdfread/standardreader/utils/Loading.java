package com.pdfread.standardreader.utils;

import android.content.Context;
import android.text.format.DateUtils;

import com.pdfread.standardreader.views.XListView;

public class Loading {
	public static void loadFinish(XListView listView,Context context) {
		listView.stopRefresh();
		listView.stopLoadMore();
		String time = DateUtils.formatDateTime(context,
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME);
		listView.setRefreshTime(time);
	}
}
