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
public class PoliticDocumentActivity extends BaseFragmentActivity {
	private BaseLibTopbarView mBaseLibTopbarView;
	ImageView tv;
	private TextView title_tv, time_tv;
	String report_id;
	private GeoProReportDetailBean detailBean;
	private WebView webview;
	private String url;
	private String name;
	private String time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gi_guide_detail);
		report_id = getIntent().getStringExtra("report_id");
		name = getIntent().getStringExtra("name");
		time = getIntent().getStringExtra("time");
		url = getIntent().getStringExtra("url");
		initTitle();

	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle("政策文件")
				.setLeftText(R.string.back, null)
				.setMode(BaseLibTopbarView.MODE_1);
		title_tv = (TextView) findViewById(R.id.title_gi_guide);
		time_tv = (TextView) findViewById(R.id.time_gi_guide);
		title_tv.setText(name);
		time_tv.setText(time);
		webview = (WebView) findViewById(R.id.content_gi_guide);
		webview.setHorizontalScrollBarEnabled(false);// 水平不显示
		webview.setVerticalScrollBarEnabled(false); // 垂直不显示
		// 设置WebView属性，能够执行JavaScript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setUseWideViewPort(true);
		// 加载需要显示的网页
		webview.loadUrl(ac.getUrl(url));
		// 设置Web视图
		webview.setWebViewClient(new WebViewClient());
	}
}
