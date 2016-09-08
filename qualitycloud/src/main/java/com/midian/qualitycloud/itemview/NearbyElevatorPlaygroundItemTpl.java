package com.midian.qualitycloud.itemview;

import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.login.view.LoginActivity;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.NearFacilitiesBean;
import com.midian.qualitycloud.ui.common.ScanResultActivity;

public class NearbyElevatorPlaygroundItemTpl extends BaseTpl<NetResult>
		implements OnClickListener {

	NearFacilitiesBean.Content res;
	TextView name, state, distance, use_unit, address, name_tv;
	String types, facility_id;
	private ImageView img_near_elevator;
	private boolean isjump;
	private String addressStr;

	public NearbyElevatorPlaygroundItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public NearbyElevatorPlaygroundItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.item).setOnClickListener(this);
		name = (TextView) findViewById(R.id.name);
		name_tv = (TextView) findViewById(R.id.name_tv);
		state = (TextView) findViewById(R.id.state);
		distance = (TextView) findViewById(R.id.distance);
		use_unit = (TextView) findViewById(R.id.use_unit);
		address = (TextView) findViewById(R.id.address);
		img_near_elevator = (ImageView) findViewById(R.id.img_near_elevator);

	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_nearby_elevator_playground_tpl;
	}

	@Override
	public void setBean(NetResult bean, int position) {
		// TODO Auto-generated method stub
		if (bean instanceof NearFacilitiesBean.Content) {
			res = (NearFacilitiesBean.Content) bean;
			name.setText(res.getName());
			setState(res.getStatus());
			distance.setText(res.getDistance());
			use_unit.setText(res.getUse_company());
			address.setText(res.getAddress());
			types = res.getType();
			addressStr = res.getAddress();
			facility_id = res.getFacility_id();
			if ("1".equals(types)) {
				name_tv.setText("电梯编号：");
			} else if("2".equals(types)){
				name_tv.setText("设备名称：");
			}
			if (TextUtils.isEmpty(res.getLogo_pic_thumb_name())) {
				if ("1".equals(types)) {
					img_near_elevator
							.setImageResource(R.drawable.icon_neardy_elevator);

				} else {
					img_near_elevator
							.setImageResource(R.drawable.icon_neardy_play);
				}
			} else {
				ac.setImage(img_near_elevator, res.getLogo_pic_thumb_name());
			}
		}

	}

	public void setState(String state) {
		if ("1".equals(state)) {
			this.state.setText("良好");
			this.state.setBackgroundResource(R.drawable.green_oval_bg);
		} else if("2".equals(state)){
			this.state.setText("禁用");
			this.state.setBackgroundResource(R.drawable.red_oval_bg);
		}else if("3".equals(state)){
			this.state.setText("超期");
			this.state.setBackgroundResource(R.drawable.yellow_oval_bg);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.item:// 查看详情
			try {
				int type;
				Bundle b = new Bundle();
				if ("1".equals(res.getStatus())) {
					type = 1;
				} else if ("2".equals(res.getStatus())){
					type = 2;
				}else {
					type = 3;
				}
				isjump = true;
				b.putInt("type", type);
				b.putString("types", types);
				b.putString("facility_id", facility_id);
				b.putString("addressStr", addressStr);
				b.putBoolean("isJump", isjump);
				UIHelper.jump(_activity, ScanResultActivity.class, b);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}
}
