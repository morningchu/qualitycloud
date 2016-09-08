package com.midian.qualitycloud.ui.common;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.adapter.CPagerTabAdapter;
import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.common.WebViewActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.PagerSlidingTabStrip;
import midian.baselib.widget.dialog.ConfirmDialog;
import midian.baselib.widget.dialog.ConfirmDialog1;
import midian.baselib.widget.dialog.ConfirmDialogState;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.midian.UMengUtils.UMengLoginUtil;
import com.midian.UMengUtils.UMengShareUtil;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.FacilityDetailBean;
import com.midian.qualitycloud.bean.FacilityForbiddensBean;
import com.midian.qualitycloud.bean.FacilityForbiddensBean.ContentFacilityForbiddens;
import com.midian.qualitycloud.utils.AppUtil;
import com.midian.qualitycloud.widget.ShareDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 扫描结果
 * 
 * @author MIDIAN
 * 
 */
public class ScanResultActivity extends BaseActivity {
	private BaseLibTopbarView mBaseLibTopbarView;
	ViewPager pager;
	PagerSlidingTabStrip mPagerSlidingTabStrip;
	TextView inform, state_btn, time;
	int type;// 1是符合要求；2是不符合要求；3是超期；
	View result, like;
	ImageView state_iv, state_tv;
	String facility_id, typeString;
	TextView next_repair_date, last_repair_date, repair_company, test_result,
			next_date, test_date, code, company, use_company, record_company,
			use_date, device_code,test_organization;
	RatingBar ratingBar1;
	RelativeLayout inform_relative;
	String report_url = "", lon = "", lat = "", name = "", share_url = "",
			typeStr = "";

	FacilityDetailBean res;
	private String types;
	private String typess = "";
	private String nameState, addressStr;
	private FacilityForbiddensBean bean;
	private boolean isJump;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_result);
		try {
			type = getIntent().getIntExtra("type", 1);
			types = getIntent().getStringExtra("types");
			facility_id = getIntent().getStringExtra("facility_id");
			 isJump = getIntent().getBooleanExtra("isJump", isJump);
			// addressStr = getIntent().getStringExtra("addressStr");
			res = (FacilityDetailBean) getIntent().getSerializableExtra(
					"detail");

			if (res == null) {
				init();
				initTitle();
				initViewPager();
				initListener();
				AppUtil.getQualityCloudApiClient(ac).getFacilityDetail(
						facility_id, this);
			} else {
				if ("1".equals(res.getContent().getStatus())) {
					type = 1;
				} else {
					type = 2;
				}
				facility_id = res.getContent().getFacility_id();
				init();
				initTitle();
				initViewPager();
				initListener();
				render(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
		inform_relative = (RelativeLayout) findViewById(R.id.inform_relative);
		inform = (TextView) findViewById(R.id.inform);
		state_btn = (TextView) findViewById(R.id.state_btn);
		time = (TextView) findViewById(R.id.time);
		state_iv = (ImageView) findViewById(R.id.state_iv);
		result = findViewById(R.id.result);
		state_tv = (ImageView) findViewById(R.id.state_tv);
		state_tv.setOnClickListener(this);
		 if (isJump) {
			 inform_relative.setVisibility(View.GONE);
			 } else {
			 inform_relative.setVisibility(View.VISIBLE);
			 }
		if (type == 1) {
			inform.setText("投诉");
			result.setBackgroundResource(R.color.green);
			state_btn.setText("符合使用要求");
			state_iv.setImageResource(R.drawable.icon_scan_good);
			state_tv.setVisibility(View.GONE);
		} else if(type == 2){
			inform.setText("举报");
			state_btn.setText("禁止使用");
			result.setBackgroundResource(R.color.red);
			state_iv.setImageResource(R.drawable.icon_scan_bad);
			state_tv.setVisibility(View.VISIBLE);
		}else{
			inform.setText("举报");
			state_btn.setText("维保超期");
			result.setBackgroundResource(R.color.yellow);
			state_iv.setImageResource(R.drawable.icon_scan_bad);
			state_tv.setVisibility(View.VISIBLE);
		}
	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setLeftText(R.string.back, null)
				.setRightImageButton(R.drawable.share_bg,
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								try {
//									UMImage image = new UMImage(_activity, "http://www.umeng.com/images/pic/social/integrated_3.png");
//									new ShareAction(_activity).setPlatform(SHARE_MEDIA.QQ)
//									.setCallback(null)
//									.withText("ghhsd")
//									.withMedia(image)
//									.withTitle("ghdhg")
//									.withTargetUrl("http://dev.umeng.com")
//									.share();
									new ShareDialog(_activity).show(share_url,
											"质量云", "设备状态分享", "");
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}).setMode(BaseLibTopbarView.MODE_2).setTitle("查询结果");
	}

	/**
	 * 初始化底部viewpager
	 */
	private void initViewPager() {
		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		View view1 = View.inflate(this, R.layout.page_baseinfo, null);
		View view2 = View.inflate(this, R.layout.page_testinfo, null);

		code = getTextView(view1, R.id.code);
		company = getTextView(view1, R.id.company);
		use_company = getTextView(view1, R.id.use_company);
		record_company = getTextView(view1, R.id.record_company);
		use_date = getTextView(view1, R.id.use_date);
		device_code = getTextView(view1, R.id.device_code);
		test_date = getTextView(view2, R.id.test_date);
		next_date = getTextView(view2, R.id.next_date);
		test_result = getTextView(view2, R.id.test_result);
		test_organization = getTextView(view2, R.id.test_organization);
		ArrayList<View> views = new ArrayList<View>();
		ArrayList<String> titles = new ArrayList<String>();
		if ("2".equals(types)) {
			titles.add("基础信息");
			titles.add("检验信息");
			views.add(view1);
			views.add(view2);
		} else {
			View view3 = View.inflate(this, R.layout.page_repairinfo, null);
			repair_company = getTextView(view3, R.id.repair_company);
			last_repair_date = getTextView(view3, R.id.last_repair_date);
			next_repair_date = getTextView(view3, R.id.next_repair_date);
			titles.add("基础信息");
			titles.add("检验信息");
			titles.add("维保信息");
			views.add(view1);
			views.add(view2);
			views.add(view3);
		}
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new CPagerTabAdapter(views, titles));
		mPagerSlidingTabStrip.tabTextColor = 0xFF202020;
		if (type == 1) {
			mPagerSlidingTabStrip.tabSelectColor = 0xFF84BF41;
			mPagerSlidingTabStrip.indicatorColor = 0xFF84BF41;
		} else if (type == 2) {
			mPagerSlidingTabStrip.tabSelectColor = 0xFFE26461;
			mPagerSlidingTabStrip.indicatorColor = 0xFFE26461;
		}else if (type == 3){
			mPagerSlidingTabStrip.tabSelectColor = 0xFFF1C40E;
			mPagerSlidingTabStrip.indicatorColor = 0xFFF1C40E;
		} 

		mPagerSlidingTabStrip.setViewPager(pager);
	}

	public TextView getTextView(View view, int id) {
		return (TextView) view.findViewById(id);
	}

	private void initListener() {
		like = findViewById(R.id.like);
		findViewById(R.id.inform).setOnClickListener(this);
		findViewById(R.id.exposure).setOnClickListener(this);
		findViewById(R.id.map).setOnClickListener(this);
		findViewById(R.id.like).setOnClickListener(this);

	}

	public void render(FacilityDetailBean res) {
		if (res == null)
			return;
		time.setText(res.getContent().getUpdate_time());

		code.setText(res.getContent().getBase().getName());
		company.setText(res.getContent().getBase().getMake_company());
		use_company.setText(res.getContent().getBase().getUse_company());
		record_company.setText(res.getContent().getBase().getInstall_address());
		use_date.setText(res.getContent().getBase().getYears());

		test_date.setText(res.getContent().getCheck().getPre_check_date());
		test_organization.setText(res.getContent().getCheck().getCheck_company());
		next_date.setText(res.getContent().getCheck().getNext_check_date());
		test_result.setText(res.getContent().getCheck().getCheck_result());

		// ratingBar1.setRating((float) FDDataUtils.getDouble(res.getContent()
		// .getRepair().getRepair_stars()));
		if ("1".equals(res.getContent().getBase().getType())) {
			repair_company.setText(res.getContent().getRepair()
					.getRepair_company());
			last_repair_date.setText(res.getContent().getRepair()
					.getPre_repair_date());
			next_repair_date.setText(res.getContent().getRepair()
					.getNext_repair_date());
			device_code.setText("电梯编号：");
		} else {
			device_code.setText("设备名称：");
		}
		if ("1".equals(res.getContent().getIs_collected())) {
			like.setSelected(true);
		} else {
			like.setSelected(false);
		}
		name = res.getContent().getBase().getName();
		typeStr = res.getContent().getBase().getType();
		report_url = res.getContent().getReport_url();
		share_url = res.getContent().getShare_url();
		typess = res.getContent().getBase().getType();
		lon = res.getContent().getLon();
		lat = res.getContent().getLat();
	}

	private ArrayList<ContentFacilityForbiddens> list;
	private ArrayList<String> data = new ArrayList<String>();

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		// TODO Auto-generated method stub
		super.onApiSuccess(res, tag);

		if (res.isOK()) {
			if ("getFacilityDetail".equals(tag)) {
				render((FacilityDetailBean) res);
				// record_company.setText(addressStr);
			} else if ("postCollect".equals(tag)) {
				like.setSelected(true);
				UIHelper.t(_activity, "关注成功");
			} else if ("postCancelCollect".equals(tag)) {
				like.setSelected(false);
				UIHelper.t(_activity, "取消关注");
			} else if ("getFacilityForbiddens".equals(tag)) {
				bean = (FacilityForbiddensBean) res;
				// nameState = bean.getContent()
				for (ContentFacilityForbiddens beanState : bean.getContent()) {
					nameState = beanState.getName();
					data.add(nameState);  
				}
				int i = 0;
				String name = "";
				StringBuilder builder = new StringBuilder();
				for (String str : data) {
					if (i == 0) {
						name = data.get(i);
					} else {
						name = name + "\n" + data.get(i);
					}
					i++;
				}
				dialogState(name);
				data.clear();
			}
		} else {
			ac.handleErrorCode(_activity, res.error_code);
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.inform:
			if (type == 2) {
				if ("1".equals(types)) {
					dialog("该电梯");
				} else {
					dialog("该游乐设施");
				}

			} else {
				Bundle b = new Bundle();
				b.putInt("type", type);
				b.putString("types", types);
				b.putString("facility_id", facility_id);
				UIHelper.jump(_activity, InformActivity.class, b);
			}

			break;
		case R.id.exposure:
			// if (ac.isRequireLogin(_activity))
			WebViewActivity.gotoActivity(_activity, "曝光台", report_url);
			// UIHelper.jump(_activity, ExposureActivity.class);
			break;
		case R.id.map:
			MyPostion.gotoMyPostion(_activity, name, lon, lat, typess);
			break;
		case R.id.like:
			if (ac.isRequireLogin(_activity)) {
				if (arg0.isSelected()) {
					AppUtil.getQualityCloudApiClient(ac).postCancelCollect(
							facility_id, typeStr, this);

				} else {

					AppUtil.getQualityCloudApiClient(ac).postCollect(
							facility_id, typeStr, this);
				}
			}
			break;
		case R.id.state_tv:
			if(type==2){
				AppUtil.getQualityCloudApiClient(ac).getFacilityForbiddens(
						facility_id, this);
			}else if(type==3){
				dialog1();
			}
			break;
		default:
			break;
		}
	}

	private void dialog(String edit) {
		ConfirmDialog.makeTextForCouseDialog(_activity, "依据《特种设备安全法》的相关规定，"
				+ edit + "禁止使用，如违法使用请举报！", "举报", new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AppUtil.getQualityCloudApiClient(ac)
						.postComplainReportComplain(facility_id,
								ScanResultActivity.this);
				UIHelper.t(_activity, "感谢您的监督!");
			}
		}, "取消", new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		// new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// finish();
		// }
		// });
	}
	private void dialog1() {
		ConfirmDialogState.makeText1(_activity, "提示:", "未按期维保",
				"关闭", new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

					}
				});
	}
	private void dialogState(String nameState) {
		ConfirmDialogState.makeText(_activity, "禁止使用原因目前有下列几种:", nameState,
				"关闭", new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent0) {
		try {
			UMengShareUtil.getInstance(this).onActivityResult(requestCode,
					resultCode, intent0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
