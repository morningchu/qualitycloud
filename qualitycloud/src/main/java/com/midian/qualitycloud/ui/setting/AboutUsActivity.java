package com.midian.qualitycloud.ui.setting;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.TextView;

import com.midian.qualitycloud.R;

/**
 * 关于
 * 
 * @author MIDIAN
 * 
 */
public class AboutUsActivity extends BaseActivity {
	BaseLibTopbarView mBaseLibTopbarView;
	TextView version;
	private WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		initTitle();

	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setLeftText(R.string.back, null).setTitle("关于")
				.setMode(BaseLibTopbarView.MODE_2);
		version = (TextView) findViewById(R.id.version);
		version.setText(getVersionName());
		
		webview = (WebView)findViewById(R.id.webView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.loadUrl(ac.getUrl(ac.link_about));
		webview.setWebViewClient(webViewClient);
	}

	private WebViewClient webViewClient = new WebViewClient() {

		public void onPageFinished(WebView view, String url) {
//			hideLoadingDlg();
		};

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			// view.loadData("网络不给力!", "text/html", "UTF-8");
		}

	};
	
	public String getVersionName() {
		PackageManager manager = this.getPackageManager();
		PackageInfo info;
		String version = "beta";
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
			version = version + " " + info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

}
