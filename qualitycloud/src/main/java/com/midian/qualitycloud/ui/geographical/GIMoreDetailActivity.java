package com.midian.qualitycloud.ui.geographical;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.adapter.CsPagerTabAdapter;
import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.GeoProSecondDetailBean;
import com.midian.qualitycloud.bean.GeoProSecondDetailBean.LogoPics;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 地理标志更多详情
 * 
 * @author MIDIAN
 * 
 */
public class GIMoreDetailActivity extends BaseActivity {
	BaseLibTopbarView mBaseLibTopbarView;
	private WebView webview;
	private boolean isLoop = true;// 是否自定循环
	ImageView img_pager;
	private ArrayList<View> list;

	// private Handler handler = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// switch (msg.what) {
	// case 0:
	// // 设置ViewPager的当前页面
	// if (img_pager.getAdapter() != null
	// && img_pager.getAdapter().getCount() > 1) {
	//
	// img_pager.setCurrentItem(img_pager.getCurrentItem() + 1);
	// }
	//
	// break;
	//
	// default:
	// break;
	// }
	// };
	// };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gi_more_detail);
		initTitle();
		showLoadingDlg();
	}

	public void initTitle() {
		geo_pro_id = getIntent().getStringExtra("geo_pro_id");
		String name = getIntent().getStringExtra("name");
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setLeftText("返回", null)
				.setTitle(name).setMode(BaseLibTopbarView.MODE_1).getTitle_tv()
				.setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#FFFFFF"));
		name_gi_more = (TextView) findViewById(R.id.name_gi_more);
		code_gi_more = (TextView) findViewById(R.id.code_gi_more);
		protect_range = (TextView) findViewById(R.id.protect_range);
		number_gi_more = (TextView) findViewById(R.id.number_gi_more);
		area_gi_more = (TextView) findViewById(R.id.area_gi_more);
		img_pager = (ImageView) findViewById(R.id.pic_gi_more);
		list = new ArrayList<View>();

		webview = (WebView) findViewById(R.id.webView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		try {
			AppUtil.getQualityCloudApiClient(ac).getGeoProSecondDetail(
					geo_pro_id, this);
			showLoadingDlg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		hideLoadingDlg();
		if (res.isOK()) {
			if ("getGeoProSecondDetail".equals(tag)) {
				this.mDetailBean = (GeoProSecondDetailBean) res;
				mDetailBean = (GeoProSecondDetailBean) res;
				mDetailBean.getContent().getGeo_pro_id();// 产品id
				// List<String> pics = new ArrayList<String>();
				// for (LogoPics item : mDetailBean.getContent().getLogo_pics())
				// {
				// pics.add(item.getLogo_pic_name());
				// }
				//
				// if (pics.size() == 2) {
				// pics.addAll(pics);
				// }
				// for (String name : pics) {
				// ImageView img = new ImageView(this);
				// ac.setImage(img, name);
				// img.setScaleType(ScaleType.FIT_XY);
				// list.add(img);
				// }
				// img_pager.setAdapter(new CsPagerTabAdapter(list, null));
				// ac.setImage(pic_img, mDetailBean.getContent()
				// .getLogo_pic_name());// 产品logo名称
				// mDetailBean.getContent().getLogo_pic_suffix();// 产品logo后缀
				// name_gi_more
				// .setText(mDetailBean.getContent().getGeo_pro_name());// 产品名称
				// code_gi_more.setText(mDetailBean.getContent()
				// .getProtect_notice_num());// 保护公告号
				// protect_range.setText(mDetailBean.getContent()
				// .getProtect_range());// 保护范围
				// number_gi_more.setText(mDetailBean.getContent()
				// .getProduct_num());// 产品标准号
				// area_gi_more.setText(mDetailBean.getContent().getArea());//
				// 所属区域
				// ac.setImage(img_pager, );
				// img_pager.
				webDetail = mDetailBean.getContent().getDetail();
				webview.loadUrl(getUrl(webDetail));
				webview.setWebViewClient(webViewClient);
			}
		}
		// if (img_pager.getAdapter() != null
		// && img_pager.getAdapter().getCount() > 1) {
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// while (isLoop) {
		// try {
		// Thread.sleep(3000);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// // SystemClock.sleep(3000);
		// handler.sendEmptyMessage(0);
		// }
		// }
		// }).start();
		// }
	}

	private String getUrl(String url) {
		if (url.equals(ServerConstant.BASEURL) || url.contains("http://")
				|| url.contains("https://")) {
			return url;
		} else {
			return ServerConstant.BASEURL + url;
		}
	}

	private WebViewClient webViewClient = new WebViewClient() {

		public void onPageFinished(WebView view, String url) {
			hideLoadingDlg();

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
		}

	};
	private TextView name_gi_more, code_gi_more, protect_range, number_gi_more,
			area_gi_more;
	private String geo_pro_id;
	private GeoProSecondDetailBean mDetailBean;
	private String webDetail;
}
