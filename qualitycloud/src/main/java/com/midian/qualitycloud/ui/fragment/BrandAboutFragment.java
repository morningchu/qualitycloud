package com.midian.qualitycloud.ui.fragment;

import midian.baselib.base.BaseFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.midian.qualitycloud.R;

/**
 * 关于名牌
 * 
 * @author MIDIAN
 * 
 */
public class BrandAboutFragment extends BaseFragment {
	private WebView webview;

	String url = "";

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		url = getArguments().getString("url");
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View main = inflater.inflate(R.layout.fragment_brand_about, null);
		webview = (WebView) main.findViewById(R.id.webView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.loadUrl(url);
		webview.setWebViewClient(webViewClient);
		_activity.showLoadingDlg();
		return main;
	}

	private WebViewClient webViewClient = new WebViewClient() {

		public void onPageFinished(WebView view, String url) {
			_activity.hideLoadingDlg();
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
}
