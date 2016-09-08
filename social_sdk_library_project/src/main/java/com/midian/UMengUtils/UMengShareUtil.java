package com.midian.UMengUtils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.midian.configlib.ServerConstant;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 友盟分享
 * 
 * @author MIDIAN
 * 
 */
public class UMengShareUtil {

	private Activity mContext;
	private static UMengShareUtil mShareUtil = null;
	UMengShareUtilListener mUMengShareUtilListener;

	private UMengShareUtil(Activity context) {
		this.mContext = context;
	}

	public static UMengShareUtil getInstance(Activity context) {

		mShareUtil = new UMengShareUtil(context);

		return mShareUtil;
	}


	public static void initConfig() {

		PlatformConfig
				.setWeixin(Constant.weixinAppId, Constant.weixinAppSecret);// 微信
																			// appid
																			// appsecret
		PlatformConfig.setSinaWeibo(Constant.weiboAppId,
				Constant.weiboAppSecret);
		// 新浪微博 appkey appsecret
		PlatformConfig.setQQZone(Constant.qqAppId, Constant.qqAppKEY);// QQ和Qzone
																		// appid
																		// appkey
		// PlatformConfig.setAlipay("2015111700822536");
		// // 支付宝 appid
		// PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");

		// // 人人 appid appkey appsecret
		// PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi","MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
		// // Twitter appid appkey
		// PlatformConfig.setPinterest("1439206");
		// // Pinterest appid
		// PlatformConfig.setLaiwang("laiwangd497e70d4","d497e70d4c3e4efeab1381476bac4c5e");
		// // 来往 appid appkey
	}

	public void share(SHARE_MEDIA platform,
			ShareContent frontiaSocialShareContent) {
		UMImage image = null;
		if (frontiaSocialShareContent.getmBitmap() != null) {
			image = new UMImage(mContext,
					frontiaSocialShareContent.getmBitmap());
		} else {
			image = new UMImage(mContext,
					getShareUrl(frontiaSocialShareContent.getImage()));
		}
		String content=frontiaSocialShareContent.getSummary();
		if(SHARE_MEDIA.SINA==platform||SHARE_MEDIA.SMS==platform){
			content+=getShareUrl(frontiaSocialShareContent.getUrl());
		}
		
		
		new ShareAction(mContext).setPlatform(platform)
				.setCallback(umShareListener)
				.withText(content)
				.withMedia(image)
				.withTitle(frontiaSocialShareContent.getTitle())
				.withTargetUrl(getShareUrl(frontiaSocialShareContent.getUrl()))
				.share();
	}

	private UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {
			Toast.makeText(mContext, getPlatformString(platform) + " 分享成功", Toast.LENGTH_SHORT)
					.show();
			if (mUMengShareUtilListener != null) {
				mUMengShareUtilListener.onComplete(platform, 0);
			}
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(mContext, getPlatformString(platform) + " 分享失败", Toast.LENGTH_SHORT)
					.show();
			if (mUMengShareUtilListener != null) {
				mUMengShareUtilListener.onComplete(platform, 1);
			}
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(mContext, getPlatformString(platform) + " 分享取消了", Toast.LENGTH_SHORT)
					.show();
			if (mUMengShareUtilListener != null) {
				mUMengShareUtilListener.onComplete(platform, 2);
			}
		}
	};

	public String getPlatformString(SHARE_MEDIA platform) {
		if (SHARE_MEDIA.QQ == platform) {
			return "QQ";
		} else if (SHARE_MEDIA.QZONE == platform) {
			return "QZONE";
		}else if (SHARE_MEDIA.WEIXIN == platform) {
			return "微信";
		}else if (SHARE_MEDIA.WEIXIN_CIRCLE == platform) {
			return "微信朋友圈";
		}else if (SHARE_MEDIA.SINA == platform) {
			return "新浪微博";
		}else if (SHARE_MEDIA.SMS == platform) {
			return "短信";
		}else{
			return platform+"";
		}

	}


	/**
	 * 显示下载与分享信息
	 * 
	 * @param frontiaSocialShareContent
	 * @return
	 */
	private String getShareUrl(ShareContent frontiaSocialShareContent) {
		if (frontiaSocialShareContent.getUrl().contains(ServerConstant.BASEURL)
				|| frontiaSocialShareContent.getUrl().contains("http://")
				|| frontiaSocialShareContent.getUrl().contains("https://")) {
			return frontiaSocialShareContent.getUrl();
		} else {
			frontiaSocialShareContent.setUrl(ServerConstant.BASEURL
					+ frontiaSocialShareContent.getUrl());
		}

		return frontiaSocialShareContent.getUrl();
	}

	private String getShareUrl(String url) {
		if (url.contains(ServerConstant.BASEURL) || url.contains("http://")
				|| url.contains("https://") || url.contains("file://")) {
			return url;
		} else {
			url = ServerConstant.BASEURL + url;
		}

		return url;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		UMShareAPI.get(mContext)
				.onActivityResult(requestCode, resultCode, data);

	}

	public void setUMengShareUtilListener(
			UMengShareUtilListener mUMengShareUtilListener) {
		this.mUMengShareUtilListener = mUMengShareUtilListener;
	}

	public interface UMengShareUtilListener {
	

		public void onComplete(SHARE_MEDIA platform, int eCode);
	}

}
