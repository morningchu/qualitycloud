package midian.baselib.common;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.midian.baselib.R;
import com.midian.configlib.ServerConstant;

/**
 * 通用网页浏览页面 Created by XuYang on 15/4/20.
 */
public class WebViewActivity extends BaseActivity {

	private BaseLibTopbarView topbar;
	private WebView webview;
	private String title = "";
	private String url = "";

	public static void gotoActivity(Activity mContext, String title, String url) {
		Bundle bundle = new Bundle();
		bundle.putString("title", title);
		bundle.putString("url", url);
		UIHelper.jump(mContext, WebViewActivity.class, bundle);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

		if (mBundle != null) {
			title = mBundle.getString("title");
			url = mBundle.getString("url");

			if (url.contains(ServerConstant.BASEURL)) {
				
			} else {
				if (url.contains("http://")) {

				} else {
					url = ServerConstant.BASEURL + url;
				}
			}
		}
		initView();
	}

	private void initView() {
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		topbar.setTitle(title)
				.setLeftImageButton(R.drawable.icon_back,
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								if (webview != null)
									webview = null;
								if (webViewClient != null)
									webViewClient = null;
								System.out.println("finish");
								finish();
							}
						}).setLeftText("返回", null);
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.loadUrl(url);
		topbar.showProgressBar();
		webview.setWebViewClient(webViewClient);
		// webview.setWebChromeClient(new WebChromeClient() {
		// public void onProgressChanged(WebView view, int progress) {
		// if (progress == 100) {
		// topbar.hideProgressBar();
		// }
		// }
		// });

//		webview.getSettings().setUserAgentString("joyrill.homepro");

	}

	private WebViewClient webViewClient = new WebViewClient() {

		public void onPageFinished(WebView view, String url) {
			topbar.hideProgressBar();
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
			// view.loadDataWithBaseURL(null, "网络不给力!", "text/html", "UTF-8",
			// null);
		}

	};

	protected void onDestroy() {
		super.onDestroy();
		if (webview != null)
			webview = null;
		if (webViewClient != null)
			webViewClient = null;
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_HOME) {
		}
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webview.canGoBack()) {
				webview.goBack();
			} else {
				this.finish();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
