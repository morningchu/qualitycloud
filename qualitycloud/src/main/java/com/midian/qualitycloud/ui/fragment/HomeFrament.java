package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.api.ApiCallback;
import midian.baselib.base.BaseFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.common.WebViewActivity;
import midian.baselib.utils.DateUtil;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.dialog.ConfirmDialog1;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.SysConfListBean;
import com.midian.qualitycloud.ui.common.ScanActivity;
import com.midian.qualitycloud.ui.geographical.GeographicalIndicationActivity;
import com.midian.qualitycloud.ui.guizhoubrand.GuizhouBrandMainActivity;
import com.midian.qualitycloud.ui.main.NearbyElevatorPlaygroundListActivity;
import com.midian.qualitycloud.ui.main.NearbyElevatorPlaygroundMap;
import com.midian.qualitycloud.ui.testorganization.QueryTestOrganizationActivity;
import com.midian.qualitycloud.utils.AppUtil;
import com.pdfread.standardreader.ui.StandardMainActivity;

/**
 * 
 * 首页
 * 
 * @author MIDIAN
 * 
 */
public class HomeFrament extends BaseFragment implements OnClickListener,
		ApiCallback {
	View main;
	SysConfListBean listBean;
	private String link_brand;
	private String link_standard;
	private String link_geo_pro;
	private String link_help;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (main == null)
			main = inflater.inflate(R.layout.fragment_home, null);
		main.findViewById(R.id.item1).setOnClickListener(this);
		main.findViewById(R.id.item2).setOnClickListener(this);
		main.findViewById(R.id.item3).setOnClickListener(this);
		main.findViewById(R.id.item4).setOnClickListener(this);
		main.findViewById(R.id.item5).setOnClickListener(this);
		main.findViewById(R.id.item6).setOnClickListener(this);
		main.findViewById(R.id.item1).setOnClickListener(this);
		main.findViewById(R.id.find_nearby_device_ll).setOnClickListener(this);
		main.findViewById(R.id.scan).setOnClickListener(this);
		main.findViewById(R.id.help).setOnClickListener(this);
		TextView phone_tv = (TextView) main.findViewById(R.id.phone);
		SpannableString ss = new SpannableString("12365服务热线");
		ss.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 5,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// ss.setSpan(new ForegroundColorSpan(Color.BLACK), 5, 9,
		// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		phone_tv.setText(ss);
		// phone_tv.setText(Html.fromHtml("<font size=\"3\" color=\"dp_blue\">12306</font><font size=\"3\" color=\"dp_black\">服务热线</font>"));
		main.findViewById(R.id.phone_ll).setOnClickListener(this);

		AppUtil.getQualityCloudApiClient(ac).getSysConfList(this);
		return main;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
//		try {
//			if (DateUtil.oldDate()) {
//				ConfirmDialog1.makeText(_activity, "", "有效期至：" + DateUtil.old
//						+ "\n今天" + DateUtil.today() + "使用期限已到", "知道了",
//						new OnClickListener() {
//
//							@Override
//							public void onClick(View arg0) {
//								// TODO Auto-generated method stub
//
//							}
//						});
//				return;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		switch (arg0.getId()) {
		case R.id.item1:// 电梯质量
			Bundle elevator = new Bundle();
			elevator.putInt("type", 1);
			UIHelper.jump(_activity,
					NearbyElevatorPlaygroundListActivity.class, elevator);
			break;
		case R.id.item2:// 游乐设施
			Bundle playground = new Bundle();
			playground.putInt("type", 2);
			UIHelper.jump(_activity,
					NearbyElevatorPlaygroundListActivity.class, playground);
			break;
		case R.id.item3:// 地理标识
			UIHelper.jump(_activity, GeographicalIndicationActivity.class);
			break;
		case R.id.item4:// 贵州名牌
			UIHelper.jump(_activity, GuizhouBrandMainActivity.class);
			break;
		case R.id.item5:// 检验服务
			UIHelper.jump(_activity, QueryTestOrganizationActivity.class);
			break;
		case R.id.item6:// 地方标准
			UIHelper.jump(_activity, StandardMainActivity.class);
			// WebViewActivity.gotoActivity(_activity, "地方标准",
			// ServerConstant.BASEURL + ac.link_standard);
			break;
		case R.id.find_nearby_device_ll:// 附近电梯和游乐地图
			UIHelper.jump(_activity, NearbyElevatorPlaygroundMap.class);
			break;
		case R.id.scan:

			if (check()) {
				UIHelper.jump(_activity, ScanActivity.class);
			} else {
				UIHelper.t(_activity, "请开启应用拍照权限");
			}

			break;
		case R.id.phone_ll:// 打热线电话
			Intent intent = new Intent(Intent.ACTION_DIAL);

			Uri data = Uri.parse("tel:" + "12365");

			intent.setData(data);

			startActivity(intent);

			// UIHelper.jump(_activity, InformActivity.class);

			break;

		default:
			break;
		}
	}

	public boolean check() {
		try {
			Camera mCamera = Camera.open();
			Camera.Parameters p = mCamera.getParameters();
			if (p == null)
				return false;
			mCamera.release();
			mCamera = null;
		} catch (Exception e) {
			return false;
		}
		return true;
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
		// TODO Auto-generated method stub
		if (res.isOK()) {
			this.listBean = (SysConfListBean) res;
			if ("getSysConfList".equals(tag)) {
				link_brand = listBean.getContent().getLink_brand();
				link_standard = listBean.getContent().getLink_standard();
				link_geo_pro = listBean.getContent().getLink_geo_pro();
				link_help = listBean.getContent().getLink_help();
				ac.saveLink(link_standard, link_geo_pro, link_brand, link_help,
						listBean.getContent().getLink_app_about());
				// ac.saveLinkStandard(link_standard);
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
