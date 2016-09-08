package com.midian.qualitycloud.itemview;

import java.io.File;
import java.net.URISyntaxException;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.midian.login.view.LoginActivity;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.CheckOrgsBean.ContentCheckOrgs;
import com.midian.qualitycloud.ui.testorganization.QueryTestOrganizationResultActivity1;
import com.midian.qualitycloud.ui.testorganization.TestOrganizationDetailActivity;

public class QueryTestOrganizationItemTpl extends BaseTpl<ContentCheckOrgs>
		implements OnClickListener {

	private TextView name_query_test, distance_query_test, address_query_test,
			phone_query_test, navigation_query_test, state;
	ContentCheckOrgs checkOrgs;
	private String name;
	private String check_org_id;
	private String tel;
private String stateStr;
	public QueryTestOrganizationItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public QueryTestOrganizationItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.item_query_test).setOnClickListener(this);
		findViewById(R.id.phone_query_test_rl).setOnClickListener(this);
		findViewById(R.id.navigation_query_test_rl).setOnClickListener(this);
		name_query_test = (TextView) findViewById(R.id.name_query_test);
		distance_query_test = (TextView) findViewById(R.id.distance_query_test);
		address_query_test = (TextView) findViewById(R.id.address_query_test);
		phone_query_test = (TextView) findViewById(R.id.phone_query_test);
		navigation_query_test = (TextView) findViewById(R.id.navigation_query_test);
		state = (TextView) findViewById(R.id.state);
		findViewById(R.id.like_test_img).setVisibility(View.GONE);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_query_test_organization_tpl;
	}

	@Override
	public void setBean(ContentCheckOrgs bean, int position) {
		this.checkOrgs = bean;
//		if (checkOrgs.getArea_name().equals("")) {
//			name_query_test.setText(getName(checkOrgs.getCheck_org_name()));
//		} else {

			name_query_test.setText(getName(checkOrgs.getCheck_org_name()));
//		}
		distance_query_test.setText(checkOrgs.getDistance());
		address_query_test.setText(checkOrgs.getAddress());
		tel = checkOrgs.getTel();
		name = checkOrgs.getCheck_org_name();
		check_org_id = checkOrgs.getCheck_org_id();
		setState(checkOrgs.getEffective());
	}
	public void setState(String state) {
		if ("1".equals(state)) {
			this.state.setText("资质有效");
			this.state.setBackgroundResource(R.drawable.green_oval_bg);
		} else if("0".equals(state)){
			this.state.setText("资质超期");
			this.state.setBackgroundResource(R.drawable.red_oval_bg);
		}
//		else if("3".equals(state)){
//			this.state.setText("超期");
//			this.state.setBackgroundResource(R.drawable.yellow_oval_bg);
//		}
	}
	
	
	private String getName(String str) {
		String name = "";
		if (str.length() > 16) {
			name = str.substring(0, 16) + "\n"
					+ str.substring(16, str.length());
		}else{
			name=str;
		}
		return name;
	}

	@Override
	public void onClick(View arg0) {

		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.item_query_test:
			Bundle bundle = new Bundle();
			bundle.putString("name", name);
			bundle.putString("check_org_id", check_org_id);
			UIHelper.jump(_activity, TestOrganizationDetailActivity.class,
					bundle);
			break;
		case R.id.phone_query_test_rl:
			Intent intent = new Intent(Intent.ACTION_DIAL);
			Uri data = Uri.parse("tel:" + tel);
			intent.setData(data);
			((QueryTestOrganizationResultActivity1) _activity)
					.startActivity(intent);
			break;
		case R.id.navigation_query_test_rl:
			gotoBaiduMap();
			break;
		default:
			break;
		}

	}

	private void gotoBaiduMap() {
		try {

			Intent intent = Intent
					.getIntent("intent://map/direction?origin=latlng:"
							+ ac.lat
							+ ","
							+ ac.lon
							+ "|name:我的位置&destination="
							+ checkOrgs.getLat()
							+ ","
							+ checkOrgs.getLon()
							+ "&mode=driving#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
			if (isInstallByread("com.baidu.BaiduMap")) {
				_activity.startActivity(intent); // 启动调用

			} else {
				UIHelper.t(_activity, "请安装百度地图客户端");
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 判断是否安装目标应用
	 * 
	 * @param packageName
	 *            目标应用安装后的包名
	 * @return 是否已安装目标应用
	 */
	private boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
	}

}
