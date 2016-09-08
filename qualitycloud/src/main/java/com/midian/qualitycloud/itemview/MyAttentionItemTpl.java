package com.midian.qualitycloud.itemview;

import midian.baselib.api.ApiCallback;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import midian.baselib.widget.dialog.ConfirmDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.baidupush.DeviceMessage;
import com.midian.baidupush.MessageTool;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.MyCollectsBean.CollectContent;
import com.midian.qualitycloud.ui.common.ScanResultActivity;
import com.midian.qualitycloud.ui.fragment.NearbyElevatorPlaygroundFragment;
import com.midian.qualitycloud.ui.geographical.GIDetailActivity;
import com.midian.qualitycloud.ui.geographical.GIMoreDetailActivity;
import com.midian.qualitycloud.ui.guizhoubrand.BrandDetailActivity;
import com.midian.qualitycloud.ui.testorganization.TestOrganizationDetailActivity;
import com.midian.qualitycloud.utils.AppUtil;

public class MyAttentionItemTpl extends BaseTpl<NetResult> implements
		View.OnClickListener, View.OnLongClickListener, ApiCallback {
	CollectContent collectContent;
	private TextView name_my_attention, state_my_attention, time_my_attention,
			address_my_attention, distance_my_attention, time_attention,
			attentionz_tv,tv_address;
	private String type, record_id;
	private String status;
	private LinearLayout time_my_attention_ll;
	private String geo_pro_id;
	private String name;
	DeviceMessage mDeviceMessage;
	private String collect_id;
	private ImageView img_near_elevator;
	private LinearLayout address_my_attention_ll;

	public MyAttentionItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyAttentionItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		tv_address = (TextView) findViewById(R.id.tv_address);
		name_my_attention = (TextView) findViewById(R.id.name_my_attention);
		state_my_attention = (TextView) findViewById(R.id.state_my_attention);
		time_my_attention = (TextView) findViewById(R.id.time_my_attention);
		time_attention = (TextView) findViewById(R.id.time_attention);
		address_my_attention = (TextView) findViewById(R.id.address_my_attention);
		distance_my_attention = (TextView) findViewById(R.id.distance_my_attention);
		address_my_attention_ll = (LinearLayout) findViewById(R.id.address_my_attention_ll);
		state_my_attention = (TextView) findViewById(R.id.state_my_attention);
		attentionz_tv = (TextView) findViewById(R.id.attentionz_tv);
		time_my_attention_ll = (LinearLayout) findViewById(R.id.time_my_attention_ll);
		img_near_elevator = (ImageView) findViewById(R.id.img_near_elevator);
		setOnClickListener(this);
		this.setOnLongClickListener(this);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_myattention_tpl;
	}

	@Override
	public void setBean(NetResult bean, int position) {
		// TODO Auto-generated method stub
		this.collectContent = (CollectContent) bean;
		name_my_attention.setText(collectContent.getName());
		state_my_attention.setText(collectContent.getStatus());
		address_my_attention.setText(collectContent.getAddress());
		distance_my_attention.setText(collectContent.getDistance());
		time_my_attention.setText(collectContent.getUse_company());
		type = collectContent.getType();
		status = collectContent.getStatus();
		record_id = collectContent.getRecord_id();
		if (TextUtils.isEmpty(collectContent.getLogo_pic_thumb_name())) {
			if (type.equals("1")) {
				img_near_elevator
						.setImageResource(R.drawable.icon_neardy_elevator);

			} else {
				img_near_elevator.setImageResource(R.drawable.icon_neardy_play);

			}
		} else {
			ac.setImage(img_near_elevator,
					collectContent.getLogo_pic_thumb_name());
		}

		name = collectContent.getName();
		collect_id = collectContent.getCollect_id();
		showDis();
	}

	public void showDis() {
		if (type.equals("1")) {
			if (status.equals("1")) {
				tv_address.setText("设备地址：");
				this.state_my_attention.setText("良好");
				this.attentionz_tv.setText("电梯编号：");
				this.state_my_attention
						.setBackgroundResource(R.drawable.green_oval_bg);
				// img_near_elevator
				// .setImageResource(R.drawable.icon_neardy_elevator);
				address_my_attention_ll.setVisibility(View.VISIBLE);
				img_near_elevator.setVisibility(View.VISIBLE);
				state_my_attention.setVisibility(View.VISIBLE);
				time_my_attention_ll.setVisibility(View.VISIBLE);
				distance_my_attention.setVisibility(View.VISIBLE);
				attentionz_tv.setVisibility(View.VISIBLE);
			}
			if (status.equals("2")) {
				this.state_my_attention.setText("禁用");
				this.attentionz_tv.setText("电梯编号：");
				this.state_my_attention
						.setBackgroundResource(R.drawable.red_oval_bg);
				// img_near_elevator
				// .setImageResource(R.drawable.icon_neardy_elevator);
				address_my_attention_ll.setVisibility(View.VISIBLE);
				img_near_elevator.setVisibility(View.VISIBLE);
				state_my_attention.setVisibility(View.VISIBLE);
				time_my_attention_ll.setVisibility(View.VISIBLE);
				distance_my_attention.setVisibility(View.VISIBLE);
				attentionz_tv.setVisibility(View.VISIBLE);
			}
			if (status.equals("3")) {
				this.state_my_attention.setText("超期");
				this.attentionz_tv.setText("电梯编号：");
				this.state_my_attention
						.setBackgroundResource(R.drawable.yellow_oval_bg);
				// img_near_elevator
				// .setImageResource(R.drawable.icon_neardy_elevator);
				address_my_attention_ll.setVisibility(View.VISIBLE);
				img_near_elevator.setVisibility(View.VISIBLE);
				state_my_attention.setVisibility(View.VISIBLE);
				time_my_attention_ll.setVisibility(View.VISIBLE);
				distance_my_attention.setVisibility(View.VISIBLE);
				attentionz_tv.setVisibility(View.VISIBLE);
			}
		}
		if (type.equals("2")) {
			tv_address.setText("设备地址：");
			if (status.equals("1")) {
				this.state_my_attention.setText("良好");
				attentionz_tv.setText("设备名称：");
				this.state_my_attention
						.setBackgroundResource(R.drawable.green_oval_bg);
				// img_near_elevator.setImageResource(R.drawable.icon_neardy_play);
				address_my_attention_ll.setVisibility(View.VISIBLE);
				img_near_elevator.setVisibility(View.VISIBLE);
				state_my_attention.setVisibility(View.VISIBLE);
				time_my_attention_ll.setVisibility(View.VISIBLE);
				distance_my_attention.setVisibility(View.VISIBLE);
				attentionz_tv.setVisibility(View.VISIBLE);
			}
			if (status.equals("2")) {
				this.state_my_attention.setText("禁用");
				this.state_my_attention
						.setBackgroundResource(R.drawable.red_oval_bg);
				attentionz_tv.setText("设备名称：");
				// img_near_elevator.setImageResource(R.drawable.icon_neardy_play);
				address_my_attention_ll.setVisibility(View.VISIBLE);
				img_near_elevator.setVisibility(View.VISIBLE);
				state_my_attention.setVisibility(View.VISIBLE);
				time_my_attention_ll.setVisibility(View.VISIBLE);
				distance_my_attention.setVisibility(View.VISIBLE);
				attentionz_tv.setVisibility(View.VISIBLE);
			}
			if (status.equals("3")) {
				this.state_my_attention.setText("超期");
				this.state_my_attention
						.setBackgroundResource(R.drawable.yellow_oval_bg);
				attentionz_tv.setText("设备名称：");
				// img_near_elevator.setImageResource(R.drawable.icon_neardy_play);
				address_my_attention_ll.setVisibility(View.VISIBLE);
				img_near_elevator.setVisibility(View.VISIBLE);
				state_my_attention.setVisibility(View.VISIBLE);
				time_my_attention_ll.setVisibility(View.VISIBLE);
				distance_my_attention.setVisibility(View.VISIBLE);
				attentionz_tv.setVisibility(View.VISIBLE);
			}
		}
		if (type.equals("3")) {
			tv_address.setText("所属地区：");
			address_my_attention_ll.setVisibility(View.VISIBLE);
			distance_my_attention.setVisibility(View.GONE);
			state_my_attention.setVisibility(View.GONE);
			time_my_attention_ll.setVisibility(View.GONE);
			img_near_elevator.setVisibility(View.GONE);
			attentionz_tv.setVisibility(View.GONE);
		}
		if (type.equals("4")) {
			tv_address.setText("企业名称：");
			address_my_attention_ll.setVisibility(View.VISIBLE);
			distance_my_attention.setVisibility(View.GONE);
			state_my_attention.setVisibility(View.GONE);
			time_my_attention_ll.setVisibility(View.GONE);
			img_near_elevator.setVisibility(View.GONE);
			attentionz_tv.setVisibility(View.GONE);
		}
		if (type.equals("5")) {
			tv_address.setText("设备地址：");
			address_my_attention_ll.setVisibility(View.VISIBLE);
			time_my_attention_ll.setVisibility(View.GONE);
			state_my_attention.setVisibility(View.GONE);
			distance_my_attention.setVisibility(View.VISIBLE);
			img_near_elevator.setVisibility(View.GONE);
			attentionz_tv.setVisibility(View.GONE);
		}
	}

	public void setState(String state) {
		if ("1".equals(status)) {
			this.state_my_attention.setText("良好");
			this.state_my_attention
					.setBackgroundResource(R.drawable.green_oval_bg);
		} else if ("2".equals(status)){
			this.state_my_attention.setText("禁用");
			this.state_my_attention
					.setBackgroundResource(R.drawable.red_oval_bg);
		} else if ("3".equals(status)){
			this.state_my_attention.setText("超期");
			this.state_my_attention
					.setBackgroundResource(R.drawable.yellow_oval_bg);
		}
	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		switch (type) {
		case "1":
			int types;
			if ("1".equals(collectContent.getStatus())) {
				types = 1;
			} else if ("2".equals(collectContent.getStatus())) {
				types = 2;
			}else{
				types = 3;
			}
			bundle.putInt("type", types);
			bundle.putString("types", type);
			bundle.putString("facility_id", record_id);
			bundle.putBoolean("isJump", true);
			UIHelper.jump(_activity, ScanResultActivity.class, bundle);
			break;
		case "2":
			int typess;
			if ("1".equals(collectContent.getStatus())) {
				typess = 1;
			} else if ("2".equals(collectContent.getStatus())) {
				typess = 2;
			}else{
				typess = 3;
			}
			bundle.putInt("type", typess);
			bundle.putString("types", type);
			bundle.putString("facility_id", record_id);
	
			UIHelper.jump(_activity, ScanResultActivity.class, bundle);
			break;
		case "3":
			bundle.putString("id", record_id);
			bundle.putString("geo_pro_type_id", collect_id);
			bundle.putString("name", name);
			UIHelper.jump(_activity, GIDetailActivity.class, bundle);
			break;
		case "4":
			bundle.putString("name", name);
			bundle.putString("brand_id", record_id);
			UIHelper.jump(_activity, BrandDetailActivity.class, bundle);
			break;
		case "5":
			bundle.putString("name", name);
			bundle.putString("check_org_id", record_id);
			UIHelper.jump(_activity, TestOrganizationDetailActivity.class,
					bundle);
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onLongClick(View v) {
		ConfirmDialog.makeText(_activity, "", "是否取消该关注？", "确定",
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						AppUtil.getQualityCloudApiClient(ac).postCancelCollect(
								record_id, type, MyAttentionItemTpl.this);
					}
				});
		return false;
	}

	@Override
	public void onApiStart(String tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onApiLoading(long count, long current, String tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		if (res.isOK()) {
			if ("postCancelCollect".equals(tag)) {
				adapter.getData().remove(collectContent);
				adapter.notifyDataSetChanged();
				if (adapter.getData().isEmpty())
					listViewHelper.getLoadView().showEmpty();

			}
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
}
