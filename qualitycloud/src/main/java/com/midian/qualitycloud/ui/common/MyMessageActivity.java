package com.midian.qualitycloud.ui.common;

import java.util.ArrayList;
import java.util.Collection;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListActivity;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;

import com.midian.baidupush.DeviceMessage;
import com.midian.baidupush.MessageTool;
import com.midian.baidupush.MyPushMessageReceiver;
import com.midian.baidupush.MyPushMessageReceiver.PushListener;
import com.midian.baidupush.PushMessage;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.datasource.MyMessageDatasource;
import com.midian.qualitycloud.itemview.MyMessageItemTpl;

/**
 * 消息中心
 * 
 * @author MIDIAN
 * 
 */
public class MyMessageActivity extends BaseListActivity<DeviceMessage> {
	BaseLibTopbarView mBaseLibTopbarView;
	MyMessageDatasource mMyMessageDatasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mMyMessageDatasource = new MyMessageDatasource(MyMessageActivity.this);
		ArrayList<DeviceMessage> list = (ArrayList<DeviceMessage>) MessageTool
				.getMessageTool(MyMessageActivity.this).getMessageList(
						((AppContext) getApplicationContext()).user_id);
		if (list != null) {
			System.out.println("list:::::::::" + list.size());
			for (DeviceMessage item : list)
				mMyMessageDatasource.getData().add(0, item);
		}
		super.onCreate(savedInstanceState);
		refreshListView.setPullRefreshEnabled(false);
		MyPushMessageReceiver.addPushListener(mPushListener);
		initTitle();
	}

	PushListener mPushListener = new PushListener() {

		@Override
		public void updateContent(PushMessage msg) {
			// TODO Auto-generated method stub
			DeviceMessage item = (DeviceMessage) msg.getMsg();
			System.out.println("item:::::::::" + item.getId());
			listViewHelper.getAdapter().getData().add(0, item);
			listViewHelper.getAdapter().notifyDataSetChanged();
			if (listViewHelper.getAdapter().getData().size() == 1)
				listViewHelper.getLoadView().restore();
		}
	};

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setLeftText(R.string.back, null).setTitle("消息中心");
	}

	@Override
	protected IDataSource<ArrayList<DeviceMessage>> getDataSource() {
		// TODO Auto-generated method stub
		return mMyMessageDatasource;
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return MyMessageItemTpl.class;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyPushMessageReceiver.removePushListener(mPushListener);
	}

}
