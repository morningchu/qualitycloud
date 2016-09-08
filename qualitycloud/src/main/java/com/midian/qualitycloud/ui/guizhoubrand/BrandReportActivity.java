package com.midian.qualitycloud.ui.guizhoubrand;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.BrandReportDetailBean;
import com.midian.qualitycloud.utils.AppUtil;

import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

public class BrandReportActivity extends BaseFragmentActivity {
	private BaseLibTopbarView mBaseLibTopbarView;
	ImageView tv;
	private TextView title, time, content;
	String report_id;
	private String url;
	private WebView webview;
	private BrandReportDetailBean detailBean;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brand_resport_detail);
		report_id = getIntent().getStringExtra("report_id");
		initTitle();

	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar_brand_resport);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle("申报指南")
				.setLeftText("返回", null).setMode(BaseLibTopbarView.MODE_1);
		title = (TextView) findViewById(R.id.title_brand_resport);
		time = (TextView) findViewById(R.id.time_brand_resport);
//		content = (TextView) findViewById(R.id.content_brand_resport);
		webview = (WebView) findViewById(R.id.content_brand_resport);
		webview.setHorizontalScrollBarEnabled(false);// 水平不显示
		webview.setVerticalScrollBarEnabled(false); // 垂直不显示
		webview.getSettings().setJavaScriptEnabled(true);
	
		webview.getSettings().setUseWideViewPort(true);
		AppUtil.getQualityCloudApiClient(ac).getBrandReportDetail(report_id,
				this);
		showLoadingDlg();

	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		// TODO Auto-generated method stub
		super.onApiSuccess(res, tag);
		hideLoadingDlg();
		if (res.isOK()) {
			detailBean = (BrandReportDetailBean) res;
			if ("getBrandReportDetail".equals(tag)) {
				title.setText(detailBean.getContent().getTitle());
				time.setText(detailBean.getContent().getAdd_date());
				url = detailBean.getContent().getContent_url();
				webview.getSettings().setJavaScriptEnabled(true);
				// 加载需要显示的网页
				webview.loadUrl(ServerConstant.BASEURL + url);
				// 设置Web视图
				webview.setWebViewClient(new WebViewClient());
			}
		}
	}
}
