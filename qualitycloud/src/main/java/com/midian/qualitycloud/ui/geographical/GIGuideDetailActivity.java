package com.midian.qualitycloud.ui.geographical;

import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.GeoProReportDetailBean;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 地理标识申报指南详情
 * 
 * @author MIDIAN
 * 
 */
public class GIGuideDetailActivity extends BaseFragmentActivity {
	private BaseLibTopbarView mBaseLibTopbarView;
	private GeoProReportDetailBean detailBean;
	ImageView tv;
	private TextView title, time;
	String report_id;
	private WebView webview;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gi_guide_detail);
		report_id = getIntent().getStringExtra("report_id");
		initTitle();

	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle("申报指南")
				.setLeftText(R.string.back, null)
				.setMode(BaseLibTopbarView.MODE_1);
		title = (TextView) findViewById(R.id.title_gi_guide);
		time = (TextView) findViewById(R.id.time_gi_guide);
		// content = (TextView) findViewById(R.id.content_gi_guide);
		webview = (WebView) findViewById(R.id.content_gi_guide);
		webview.setHorizontalScrollBarEnabled(false);// 水平不显示
		webview.setVerticalScrollBarEnabled(false); // 垂直不显示
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		AppUtil.getQualityCloudApiClient(ac).getGeoProReportDetail(report_id,
				this);
		showLoadingDlg();
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		// TODO Auto-generated method stub
		super.onApiSuccess(res, tag);
		hideLoadingDlg();
		if (res.isOK()) {
			detailBean = (GeoProReportDetailBean) res;
			if ("getGeoProReportDetail".equals(tag)) {
				title.setText(detailBean.getContent().getTitle());
				time.setText(detailBean.getContent().getAdd_date());
				url = detailBean.getContent().getContent_url();
				// webview.setText(Html.fromHtml(detailBean.getContent()
				// .getContent()));
				// 设置WebView属性，能够执行JavaScript脚本
				webview.getSettings().setJavaScriptEnabled(true);
				// 加载需要显示的网页
				webview.loadUrl(ServerConstant.BASEURL + url);
				// 设置Web视图
				webview.setWebViewClient(new WebViewClient());
			}
		}
	}
	

}
