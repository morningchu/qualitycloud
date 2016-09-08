package com.midian.qualitycloud.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.midian.UMengUtils.ShareContent;
import com.midian.UMengUtils.UMengLoginUtil;
import com.midian.UMengUtils.UMengShareUtil;
import com.midian.UMengUtils.UMengShareUtil.UMengShareUtilListener;
import com.midian.qualitycloud.R;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by XuYang on 15/4/15.
 */
public class ShareDialog extends Dialog implements View.OnClickListener,
		Callback {
	private Context context;

	public static String defaultText = "分享给好友";
	public static String defaultTitle = "质量云";
	public static String defaultTitleUrl = "";
	TextView tip;
	// private DpLoadingDialog dlg;

	private String fileToPath;
	private UMengShareUtil mShareUtil;
	private ShareContent mImageContent = new ShareContent();

	public ShareDialog(Context context) {
		super(context, R.style.confirm_dialog);
		init(context);
	}

	public ShareDialog(Context context, int theme) {
		super(context, R.style.confirm_dialog);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		String cachePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/umeng/sharesdk/";
		fileToPath = cachePath + "logo.png";
		Window w = this.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;

		w.setAttributes(lp);
		this.setCanceledOnTouchOutside(true);

		View contentView = View
				.inflate(context, R.layout.dp_dialog_share, null);
		this.setContentView(contentView);
		tip = (TextView) contentView.findViewById(R.id.tip);
		findViewById(R.id.weixin).setOnClickListener(this);
		findViewById(R.id.weibo).setOnClickListener(this);
		findViewById(R.id.qq).setOnClickListener(this);
		findViewById(R.id.weixin_friend_circle).setOnClickListener(this);
		findViewById(R.id.qqzone).setOnClickListener(this);
		findViewById(R.id.note).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);

		try {
			initShareUtil();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void show(String url, String title, String content, String tipStr) {
		defaultTitleUrl = url;
		if (!TextUtils.isEmpty(title))
			defaultTitle = title;
		if (!TextUtils.isEmpty(content))
			defaultText = content;
		if (!TextUtils.isEmpty(tipStr))
			tip.setText(tipStr);
		refresh();
		super.show();
	}

	private void initShareUtil() {
		mShareUtil = UMengShareUtil.getInstance((Activity) context);
		mShareUtil.setUMengShareUtilListener(mUMengShareUtilListener);
		mImageContent.setAppName("质量云");
		mImageContent.setImage("");
		mImageContent.setmBitmap(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.ic_launcher));// 传本地图片如果没一定传null
		mImageContent.setSummary(defaultText);
		mImageContent.setTitle(defaultTitle);
		mImageContent.setUrl(defaultTitleUrl);// 分享链接
	}

	private void refresh() {
		mImageContent.setSummary(defaultText);
		mImageContent.setTitle(defaultTitle);
		mImageContent.setUrl(defaultTitleUrl);// 分享链接
	}

	UMengShareUtilListener mUMengShareUtilListener = new UMengShareUtilListener() {

	

		@Override
		public void onComplete(SHARE_MEDIA platform, int eCode
				) {
			// TODO Auto-generated method stub
			// if (SHARE_MEDIA.QQ == platform) {
			// qqRl.setEnabled(true);
			// } else if (SHARE_MEDIA.QZONE == platform) {
			// qqzoneRl.setEnabled(true);
			// } else if (SHARE_MEDIA.WEIXIN == platform) {
			// weixinRl.setEnabled(true);
			// } else if (SHARE_MEDIA.WEIXIN_CIRCLE == platform) {
			// weixinfriendsRl.setEnabled(true);
			// } else if (SHARE_MEDIA.SINA == platform) {
			// microblogRl.setEnabled(true);
			// }
		}

	};

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.weixin:
		
			mShareUtil.share(SHARE_MEDIA.WEIXIN, mImageContent);
			// ShareParams wechat_sp = new ShareParams();
			// wechat_sp.setTitle(defaultTitle);
			// wechat_sp.setText(defaultText);
			// wechat_sp.setShareType(Platform.SHARE_TEXT);
			// wechat_sp.setShareType(Platform.SHARE_WEBPAGE);
			// wechat_sp.setUrl(defaultTitleUrl);
			// wechat_sp.setTitleUrl(defaultTitleUrl);
			// Bitmap imageData = BitmapFactory.decodeResource(
			// ((ContextThemeWrapper) context).getResources(),
			// R.drawable.ic_launcher);
			// wechat_sp.setImageData(imageData);
			// Platform wechat = ShareSDK.getPlatform(context, Wechat.NAME);
			// wechat.setPlatformActionListener(DpShareDialog.this);
			// wechat.share(wechat_sp);
			// if (wechat.isValid()) {
			// wechat.removeAccount();
			// }
			break;
		case R.id.weibo:
			try {
			
				mShareUtil.share(SHARE_MEDIA.SINA, mImageContent);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			// Platform.ShareParams sinaWeibo_sp = new SinaWeibo.ShareParams();
			// sinaWeibo_sp.text = defaultText + defaultTitleUrl;
			// sinaWeibo_sp.imagePath = fileToPath;
			// Bitmap weiboimageData = BitmapFactory.decodeResource(
			// ((ContextThemeWrapper) context).getResources(),
			// R.drawable.ic_launcher);
			// sinaWeibo_sp.setImageData(weiboimageData);
			// sinaWeibo_sp.setUrl(defaultTitleUrl);
			// Platform weibo = ShareSDK.getPlatform(context, SinaWeibo.NAME);
			// weibo.setPlatformActionListener(DpShareDialog.this);
			// weibo.share(sinaWeibo_sp);
			// if (weibo.isValid()) {
			// weibo.removeAccount();
			// }
			break;
		case R.id.qq:
			mShareUtil.share(SHARE_MEDIA.QQ, mImageContent);
			// QQ.ShareParams qq_sp = new QQ.ShareParams();
			// qq_sp.title = defaultTitle;
			// qq_sp.titleUrl = defaultTitleUrl;
			// qq_sp.imagePath = fileToPath;
			// System.out.println("fileToPath::::::" + fileToPath);
			// qq_sp.setImageUrl(fileToPath);
			// Bitmap qqimageData = BitmapFactory.decodeResource(
			// ((ContextThemeWrapper) context).getResources(),
			// R.drawable.ic_launcher);
			// qq_sp.setImageData(qqimageData);
			// qq_sp.text = defaultText;
			// Platform qq = ShareSDK.getPlatform(context, QQ.NAME);
			// qq.setPlatformActionListener(DpShareDialog.this);
			// qq.share(qq_sp);
			// if (qq.isValid()) {
			// qq.removeAccount();
			// }
			break;

		case R.id.weixin_friend_circle:// 微信朋友圈
			mShareUtil.share(SHARE_MEDIA.WEIXIN_CIRCLE, mImageContent);
			// ShareParams wechat_friend_circle_sp = new ShareParams();
			// wechat_friend_circle_sp.setTitle(defaultTitle);
			// wechat_friend_circle_sp.setText(defaultText + defaultTitleUrl);
			// // wechat_friend_circle_sp.setShareType(Platform.SHARE_TEXT);
			// wechat_friend_circle_sp.setShareType(Platform.SHARE_WEBPAGE);
			// wechat_friend_circle_sp.setTitleUrl(defaultTitleUrl);
			// // wechat_friend_circle_sp.setSite(defaultTitle);
			// // wechat_friend_circle_sp.setSiteUrl(defaultTitleUrl);
			// // wechat_friend_circle_sp.imagePath=
			// Bitmap imageDatafriend = BitmapFactory.decodeResource(
			// ((ContextThemeWrapper) context).getResources(),
			// R.drawable.ic_launcher);
			// wechat_friend_circle_sp.setImageData(imageDatafriend);
			// Platform wechat_friend_circle = ShareSDK.getPlatform(context,
			// WechatMoments.NAME);
			// wechat_friend_circle.setPlatformActionListener(DpShareDialog.this);
			// wechat_friend_circle.share(wechat_friend_circle_sp);
			// if (wechat_friend_circle.isValid()) {
			// wechat_friend_circle.removeAccount();
			// }
			break;
		case R.id.qqzone:// qq空间
			mShareUtil.share(SHARE_MEDIA.QZONE, mImageContent);
			// QZone.ShareParams qqzone_sp = new QZone.ShareParams();
			// qqzone_sp.title = defaultTitle;
			// qqzone_sp.titleUrl = defaultTitleUrl;
			// qqzone_sp.imagePath = fileToPath;
			// // qq_sp.imageUrl = "";
			// qqzone_sp.text = defaultText;
			// Platform qqzone = ShareSDK.getPlatform(context, QZone.NAME);
			// qqzone.setPlatformActionListener(DpShareDialog.this);
			// qqzone.share(qqzone_sp);
			// if (qqzone.isValid()) {
			// qqzone.removeAccount();
			// }
			break;
		case R.id.note:// 短信
			mShareUtil.share(SHARE_MEDIA.SMS, mImageContent);
			// ShortMessage.ShareParams shortmessageSP = new
			// ShortMessage.ShareParams();
			// shortmessageSP.title = defaultTitle;
			// // shortmessageSP.titleUrl = defaultTitleUrl;
			// // shortmessageSP.imagePath = fileToPath;
			// // qq_sp.imageUrl = "";
			// shortmessageSP.text = defaultText + defaultTitleUrl;
			// Platform shortmessage = ShareSDK.getPlatform(context,
			// ShortMessage.NAME);
			// shortmessage.setPlatformActionListener(DpShareDialog.this);
			// shortmessage.share(shortmessageSP);
			// if (shortmessage.isValid()) {
			// shortmessage.removeAccount();
			// }
			break;
		case R.id.cancel:
			break;
		}
		dismiss();
	}

	public Dialog config(final String fileToPath) {
		return config(fileToPath, defaultText, defaultTitle, defaultTitleUrl);
	}

	public Dialog config(final String fileToPath, final String defaultText,
			final String defaultTitle, final String defaultTitleUrl) {
		this.fileToPath = fileToPath;
		this.defaultText = defaultText;
		this.defaultTitle = defaultTitle;
		this.defaultTitleUrl = defaultTitleUrl;
		return this;
	}

	@Override
	public boolean handleMessage(Message arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	// public void onComplete(Platform plat, int action,
	// HashMap<String, Object> res) {
	//
	// Message msg = new Message();
	// msg.arg1 = 1;
	// msg.arg2 = action;
	// msg.obj = plat;
	// UIHandler.sendMessage(msg, this);
	// }
	//
	// public void onCancel(Platform palt, int action) {
	// Message msg = new Message();
	// msg.arg1 = 3;
	// msg.arg2 = action;
	// msg.obj = palt;
	//
	// UIHandler.sendMessage(msg, this);
	// }
	//
	// public void onError(Platform palt, int action, Throwable t) {
	// t.printStackTrace();
	// Message msg = new Message();
	// msg.arg1 = 2;
	// msg.arg2 = action;
	// msg.obj = palt;
	// UIHandler.sendMessage(msg, this);
	// }
	//
	// public boolean handleMessage(Message msg) {
	// if (dlg != null)
	// dlg.dismiss();
	// Platform plat = (Platform) msg.obj;
	// String platName = plat.getName();
	// switch (msg.arg1) {
	// case 1: {
	// if (platName.equals(SinaWeibo.NAME) || platName.equals(Wechat.NAME)) {
	//
	// } else {
	// // 成功
	// DpUIHelper.t(context, "分享成功");
	// }
	// }
	// break;
	// case 2: {
	// // 失败
	// DpUIHelper.t(context, "分享失败");
	// }
	// break;
	// case 3: {
	// // 取消
	// DpUIHelper.t(context, "分享取消");
	// }
	// break;
	// }
	// return false;
	// }
	//
	// @Override
	// public void dismiss() {
	// super.dismiss();
	// if (dlg != null) {
	// dlg.dismiss();
	// }
	// }
}
