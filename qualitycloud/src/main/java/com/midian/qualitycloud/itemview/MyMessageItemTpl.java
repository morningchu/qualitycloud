package com.midian.qualitycloud.itemview;

import midian.baselib.api.ApiCallback;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import midian.baselib.widget.dialog.ConfirmDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.baidupush.DeviceMessage;
import com.midian.baidupush.MessageTool;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.FacilityDetailBean;
import com.midian.qualitycloud.ui.common.MyMessageActivity;
import com.midian.qualitycloud.ui.common.ScanResultActivity;
import com.midian.qualitycloud.utils.AppUtil;

public class MyMessageItemTpl extends BaseTpl<DeviceMessage> implements
		OnClickListener, OnLongClickListener, ApiCallback {

	DeviceMessage mDeviceMessage;
	View read;
	TextView name, content, time, see;
	private LinearLayout my_message_ll;

	public MyMessageItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyMessageItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		read = findViewById(R.id.read);
		name = (TextView) findViewById(R.id.name);
		content = (TextView) findViewById(R.id.content);
		time = (TextView) findViewById(R.id.time);
		see = (TextView) findViewById(R.id.see);
		my_message_ll = (LinearLayout) findViewById(R.id.my_message_ll);
		see.setOnClickListener(this);
		// my_message_ll.setOnClickListener(this);
		this.setOnLongClickListener(this);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_my_message_tpl;
	}

	@Override
	public void setBean(DeviceMessage bean, int position) {
		// TODO Auto-generated method stub
		if (bean instanceof DeviceMessage) {
			mDeviceMessage = (DeviceMessage) bean;
			read.setSelected("0".equals(mDeviceMessage.getIsRead()));
			name.setText(mDeviceMessage.getTitle());
			content.setText(String.format("您关注的%s设备违规使用，请停止使用或者与相关单位联系",
					mDeviceMessage.getContent()));
			time.setText(mDeviceMessage.getTime());

		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.see:
			MessageTool.getMessageTool(_activity).setMessageReaded(
					mDeviceMessage);
			read.setSelected(false);
			adapter.notifyDataSetChanged();
			((MyMessageActivity) _activity).showLoadingDlg();
			AppUtil.getQualityCloudApiClient(ac).getFacilityDetail(
					mDeviceMessage.getDevice_id(), this);

			System.out.println("mDeviceMessage:::::::::"
					+ mDeviceMessage.getId());
			break;
		default:
			break;
		}

	}

	@Override
	public void onApiStart(String tag) {
		// TODO Auto-generated method stub
		// ConfirmDialog.makeText(mContext, title, content, rightText,
		// rightOnClickListener)
	}

	@Override
	public void onApiLoading(long count, long current, String tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		// TODO Auto-generated method stub
		((MyMessageActivity) _activity).hideLoadingDlg();
		if (res.isOK()) {
			if ("getFacilityDetail".equals(tag)) {
				FacilityDetailBean mFacilityDetailBean = (FacilityDetailBean) res;
				Bundle b = new Bundle();
				b.putSerializable("detail", mFacilityDetailBean);
				UIHelper.jump(_activity, ScanResultActivity.class, b);
			}
		} else {
			ac.handleErrorCode(_activity, res.error_code);
		}

	}

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onParseError(String tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		ConfirmDialog.makeText(_activity, "", "是否要删除该条消息？", "删除",
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						MessageTool.getMessageTool(_activity).removeMessage(
								mDeviceMessage);
						adapter.getData().remove(mDeviceMessage);
						adapter.notifyDataSetChanged();
						if (adapter.getData().isEmpty())
							listViewHelper.getLoadView().showEmpty();
					}
				});
		return false;
	}
}
