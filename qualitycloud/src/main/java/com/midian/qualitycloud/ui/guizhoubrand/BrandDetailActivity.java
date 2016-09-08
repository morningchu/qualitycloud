package com.midian.qualitycloud.ui.guizhoubrand;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.adapter.CPagerTabAdapter;
import midian.baselib.adapter.CsPagerTabAdapter;
import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.PagerSlidingTabStrip;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.UMengUtils.ShareContent;
import com.midian.UMengUtils.UMengShareUtil;
import com.midian.UMengUtils.UMengShareUtil.UMengShareUtilListener;
import com.midian.configlib.ServerConstant;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.BrandDetailBean;
import com.midian.qualitycloud.bean.BrandDetailBean.Brand_pics;
import com.midian.qualitycloud.utils.AppUtil;
import com.midian.qualitycloud.widget.ShareDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 名牌详情
 * 
 * @author MIDIAN
 * 
 */
public class BrandDetailActivity extends BaseActivity {
	private BaseLibTopbarView mBaseLibTopbarView;
	ViewPager pager;
	ViewPager img_pager;
	PagerSlidingTabStrip mPagerSlidingTabStrip;
	String brand_id, shareUrl;
	private TextView name_details, content_details, name_product,
			address_product, tel_product, fax_product, email_product;
	private ImageView like_img;
	private String is_collected;
	private String id;
	private String name;
	private WebView webview;
	private LinearLayout phone, share, like, mail;
	BrandDetailBean detailBean;
	private boolean isLoop = true;// 是否自定循环
	private ArrayList<View> list;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				// 设置ViewPager的当前页面
				if (img_pager.getAdapter() != null
						&& img_pager.getAdapter().getCount() > 1) {

					img_pager.setCurrentItem(img_pager.getCurrentItem() + 1);
				}
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brand_detail);
		brand_id = getIntent().getStringExtra("brand_id");
		name = getIntent().getStringExtra("name");
		initTitle();
		initViewPager();
		initListener();
	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setMode(BaseLibTopbarView.MODE_1).setTitle(name)
				.setLeftText("返回", null).getTitle_tv()
				.setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#FFFFFF"));
	}

	/**
	 * 初始化底部viewpager
	 */
	private void initViewPager() {
		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		View view1 = View.inflate(this, R.layout.page_product_info, null);
		View view2 = View.inflate(this, R.layout.page_company_info, null);
		// View view3 = View.inflate(this, R.layout.page_repairinfo, null);
		ArrayList<View> views = new ArrayList<View>();
		ArrayList<String> titles = new ArrayList<String>();
		titles.add("产品概述");
		titles.add("企业简介");
		views.add(view1);
		views.add(view2);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new CPagerTabAdapter(views, titles));
		img_pager = (ViewPager) view1.findViewById(R.id.img_product);
		img_pager.setAdapter(new CsPagerTabAdapter(null, null));
		// img_pager.set
		list = new ArrayList<View>();

		mPagerSlidingTabStrip.indicatorColor = 0xFF47A0DB;
		mPagerSlidingTabStrip.tabTextColor = 0xFF202020;
		mPagerSlidingTabStrip.tabSelectColor = 0xFF202020;
		mPagerSlidingTabStrip.setViewPager(pager);

		name_details = (TextView) view2.findViewById(R.id.name_details);
		webview = (WebView) view2.findViewById(R.id.content_details);
		webview.setHorizontalScrollBarEnabled(false);// 水平不显示
		webview.setVerticalScrollBarEnabled(false); // 垂直不显示
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setUseWideViewPort(true);
		name_product = (TextView) view1.findViewById(R.id.name_product);
		address_product = (TextView) view1.findViewById(R.id.address_product);
		tel_product = (TextView) view1.findViewById(R.id.tel_product);
		fax_product = (TextView) view1.findViewById(R.id.fax_product);
		email_product = (TextView) view1.findViewById(R.id.email_product);
		try {
			AppUtil.getQualityCloudApiClient(ac).getBrandDetail(brand_id, this);
			showLoadingDlg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initListener() {
		phone = (LinearLayout) findViewById(R.id.phone);
		share = (LinearLayout) findViewById(R.id.share);
		mail = (LinearLayout) findViewById(R.id.mail);
		like = (LinearLayout) findViewById(R.id.like);
		like_img = (ImageView) findViewById(R.id.like_img);
		phone.setOnClickListener(this);
		share.setOnClickListener(this);
		mail.setOnClickListener(this);
		like.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.phone:
			Intent intent = new Intent(Intent.ACTION_DIAL);

			Uri data = Uri.parse("tel:" + tel_product.getText());

			intent.setData(data);

			startActivity(intent);
			break;
		case R.id.share:
			new ShareDialog(_activity).show(shareUrl, "贵州名牌", "贵州名牌产品分享",
					"关注品牌，向亲友友们分享贵州名牌产品。");
			break;
		case R.id.mail:
			try {
				initShareUtil();
				mShareUtil.share(SHARE_MEDIA.EMAIL,mImageContent);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case R.id.like:
			String type = "4";
			if (ac.isRequireLogin(_activity)) {
				if ("1".equals(is_collected)) {
					AppUtil.getQualityCloudApiClient(ac).postCancelCollect(id,type,
							this);
				}
				if ("0".equals(is_collected)) {
					AppUtil.getQualityCloudApiClient(ac).postCollect(id, type,
							this);

				}
				showLoadingDlg();
			}
			break;
		default:
			break;
		}
	}

	private UMengShareUtil mShareUtil;
	private ShareContent mImageContent = new ShareContent();

	private void initShareUtil() {
		mShareUtil = UMengShareUtil.getInstance(_activity);
		mShareUtil.setUMengShareUtilListener(mUMengShareUtilListener);
		mImageContent.setAppName("质量云");
		mImageContent.setImage("");
		mImageContent.setmBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher));// 传本地图片如果没一定传null
		mImageContent.setSummary("贵州名牌产品分享");
		mImageContent.setTitle("贵州名牌");
		mImageContent.setUrl(shareUrl);// 分享链接
	}

	UMengShareUtilListener mUMengShareUtilListener = new UMengShareUtilListener() {

	

		@Override
		public void onComplete(SHARE_MEDIA platform, int eCode) {
		}

	};

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		hideLoadingDlg();
		if (res.isOK()) {
			if ("getBrandDetail".equals(tag)) {
				this.detailBean = (BrandDetailBean) res;
				detailBean = (BrandDetailBean) res;
				List<String> pics = new ArrayList<String>();
				for (Brand_pics item : detailBean.getContent().getBrand_pics()) {
					pics.add(item.getBrand_pic_name());
				}

				if (pics.size() == 2) {
					pics.addAll(pics);
				}

				for (String url : pics) {
					ImageView img = new ImageView(this);

					ac.setImage(img, url);
					img.setScaleType(ScaleType.FIT_XY);
					list.add(img);
				}
				// img_pager.setOffscreenPageLimit(3);
				img_pager.setAdapter(new CsPagerTabAdapter(list, null));
				name_details.setText(detailBean.getContent().getCompany_name());
				String url = detailBean.getContent().getIntro_url();
				// 设置WebView属性，能够执行JavaScript脚本
				webview.getSettings().setJavaScriptEnabled(true);
				webview.getSettings().setUseWideViewPort(true);
				// 加载需要显示的网页
				webview.loadUrl(ServerConstant.BASEURL + url);
				// 设置Web视图
				webview.setWebViewClient(new WebViewClient());
				name_product.setText(detailBean.getContent().getCompany_name());
				address_product.setText(detailBean.getContent().getAddress());
				tel_product.setText(detailBean.getContent().getTel());
				fax_product.setText(detailBean.getContent().getFax());
				email_product.setText(detailBean.getContent().getEmail());
				is_collected = detailBean.getContent().getIs_collected();
				id = detailBean.getContent().getBrand_id();
				shareUrl = detailBean.getContent().getShare_url();
				System.out.println("::::is_collected::"+is_collected);
				if (is_collected.equals("1")) {
					like_img.setBackgroundResource(R.drawable.icon_brand_like_p);
				} else {
					like_img.setBackgroundResource(R.drawable.icon_brand_like_n);
				}
			}
			if ("postCollect".equals(tag)) {
				like_img.setBackgroundResource(R.drawable.icon_brand_like_p);
				is_collected = "1";
				UIHelper.t(_activity, "已收藏");
			}

			if ("postCancelCollect".equals(tag)) {
				like_img.setBackgroundResource(R.drawable.icon_brand_like_n);
				is_collected = "0";
				UIHelper.t(_activity, "已取消收藏");
			} else {
				ac.handleErrorCode(_activity, res.error_code);
			}
		}
		if (img_pager.getAdapter() != null
				&& img_pager.getAdapter().getCount() > 1) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (isLoop) {
						try {

							Thread.sleep(3000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						// SystemClock.sleep(3000);

						handler.sendEmptyMessage(0);
					}
				}

			}).start();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent0) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent0);
		try {
			UMengShareUtil.getInstance(this).onActivityResult(requestCode,
					resultCode, intent0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
