package com.midian.qualitycloud.ui.fragment;

import midian.baselib.api.ApiCallback;
import midian.baselib.base.BaseFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.GeoProFirstDetailBean;
import com.midian.qualitycloud.ui.geographical.GIDetailActivity;
import com.midian.qualitycloud.ui.geographical.GIMoreDetailActivity;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 产品详情
 * 
 * @author MIDIAN
 * 
 */
public class ProductDetailFragment extends BaseFragment implements
		OnClickListener, ApiCallback {
	View main;
	private TextView address, content, name_tv, code_relate, content_relate,
			add_like, see_detail;
	private ImageView img_relate, img_add_like;
	private RelativeLayout add_like_relative, see_detail_relative;
	GeoProFirstDetailBean mFirstDetaiBean;
	private String is_collected;
	private String geo_pro_id;
	private String geo_pro_type_id;
	private String name;
	private WebView webview;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (main == null) {
			main = inflater.inflate(R.layout.fragment_gi_detail, null);
			// code_tv = (TextView) main.findViewById(R.id.code);
			address = (TextView) main.findViewById(R.id.address);
			webview = (WebView) main.findViewById(R.id.content_gi_detail);
//			webview.setHorizontalScrollBarEnabled(false);// 水平不显示
//			webview.setVerticalScrollBarEnabled(false); // 垂直不显示
			
			webview.getSettings().setJavaScriptEnabled(true);
			webview.getSettings().setDomStorageEnabled(true);
			webview.getSettings().setUseWideViewPort(true);
			webview.getSettings().setLoadWithOverviewMode(true);
			webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

			name_tv = (TextView) main.findViewById(R.id.name_relate);
			code_relate = (TextView) main.findViewById(R.id.code_relate);
			content_relate = (TextView) main.findViewById(R.id.content_relate);
			img_relate = (ImageView) main.findViewById(R.id.img_relate);
			img_add_like = (ImageView) main.findViewById(R.id.img_add_like);
			add_like = (TextView) main.findViewById(R.id.add_like);
			see_detail = (TextView) main.findViewById(R.id.see_detail);
			add_like_relative = (RelativeLayout) main
					.findViewById(R.id.add_like_relative);
			see_detail_relative = (RelativeLayout) main
					.findViewById(R.id.see_detail_relative);
			add_like_relative.setOnClickListener(this);
			see_detail_relative.setOnClickListener(this);
			// add_like.setOnClickListener(this);
			// see_detail.setOnClickListener(this);
		}
		String geo_id = getArguments().getString("geo_id");
		try {
			AppUtil.getQualityCloudApiClient(ac).getGeoProFirstDetail(geo_id,
					this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return main;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.add_like_relative:
			String type = "3";// 1：电梯，2：游乐，3：地理标识，4：名牌，5：检测机构
			if (ac.isRequireLogin(_activity)) {
				if ("1".equals(is_collected)) {
					AppUtil.getQualityCloudApiClient(ac).postCancelCollect(
							geo_pro_id,type, this);// 删除收藏
				}
				if ("0".equals(is_collected)) {

					AppUtil.getQualityCloudApiClient(ac).postCollect(
							geo_pro_id, type, this);// 收藏

				}
				((GIDetailActivity) _activity).showLoadingDlg();
			}
			break;
		case R.id.see_detail_relative:
			Bundle bundle = new Bundle();
			bundle.putString("geo_pro_id", geo_pro_id);
			bundle.putString("name", name);
			UIHelper.jump(_activity, GIMoreDetailActivity.class, bundle);
			break;
		default:
			break;
		}
	}

	@Override
	public void onApiStart(String tag) {
		((GIDetailActivity) _activity).showLoadingDlg();

	}

	@Override
	public void onApiLoading(long count, long current, String tag) {
		// TODO Auto-generated method stub

	}
	private String getUrl(String url) {
		if (url.equals(ServerConstant.BASEURL) || url.contains("http://")
				|| url.contains("https://")) {
			return url;
		} else {
			return ServerConstant.BASEURL + url;
		}
	}
	@Override
	public void onApiSuccess(NetResult res, String tag) {

		((GIDetailActivity) _activity).hideLoadingDlg();
		if (res.isOK()) {
			if ("getGeoProFirstDetail".equals(tag)) {
				this.mFirstDetaiBean = (GeoProFirstDetailBean) res;
				mFirstDetaiBean = (GeoProFirstDetailBean) res;
				geo_pro_id = mFirstDetaiBean.getContent().getGeo_pro_id();
				geo_pro_type_id = mFirstDetaiBean.getContent()
						.getGeo_pro_type_id();
				name = mFirstDetaiBean.getContent().getGeo_pro_name();
				ac.setImage(img_relate, mFirstDetaiBean.getContent()
						.getLogo_pic_thumb_name());

				name_tv.setText(mFirstDetaiBean.getContent().getGeo_pro_name());// 产品名称
				code_relate.setText(mFirstDetaiBean.getContent()
						.getProtect_notice_num());// 保护公告号
				content_relate.setText(mFirstDetaiBean.getContent().getArea());// 所属区域
				address.setText(mFirstDetaiBean.getContent().getProtect_range());// 保护范围
				// System.out.println("mFirstDetaiBean.getContent()::::::::"
				// + mFirstDetaiBean.getContent().getIntro());
				// String source = mFirstDetaiBean.getContent().getIntro();
				// source = source.replaceAll("span", "font")
				// .replaceAll("style=\"", "").replace(";\"", "")
				// .replace("color:", "color=");
				// System.out.println("source:::::::" + source);
				String url = mFirstDetaiBean.getContent().getIntro_url();
				// 设置WebView属性，能够执行JavaScript脚本
//				webview.getSettings().setJavaScriptEnabled(true);
				// 加载需要显示的网页
				webview.loadUrl(getUrl(url));
				// 设置Web视图
				webview.setWebViewClient(webViewClient);
				is_collected = mFirstDetaiBean.getContent().getIs_collected();
				if (is_collected.equals("1")) {
					img_add_like
							.setBackgroundResource(R.drawable.icon_brand_like_p);
				} else {
					img_add_like
							.setBackgroundResource(R.drawable.icon_add_like_n);
				}

			}
			if ("postCollect".equals(tag)) {
				img_add_like
						.setBackgroundResource(R.drawable.icon_brand_like_p);
				is_collected = "1";
				UIHelper.t(_activity, "已收藏");
			}

			if ("postCancelCollect".equals(tag)) {
				img_add_like.setBackgroundResource(R.drawable.icon_add_like_n);
				is_collected = "0";
				UIHelper.t(_activity, "已取消收藏");
			}

		} else {
			ac.handleErrorCode(_activity, res.error_code);
		}
	}
	private WebViewClient webViewClient = new WebViewClient() {

		public void onPageFinished(WebView view, String url) {
			
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
	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		((GIDetailActivity) _activity).hideLoadingDlg();

	}

	@Override
	public void onParseError(String tag) {

		((GIDetailActivity) _activity).hideLoadingDlg();

	}

}
