package com.midian.UMengUtils;

import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.handler.QZoneSsoHandler;
import com.umeng.socialize.handler.SinaSsoHandler;
import com.umeng.socialize.handler.TencentWBSsoHandler;
import com.umeng.socialize.handler.UMQQSsoHandler;
import com.umeng.socialize.handler.UMWXHandler;
import com.umeng.socialize.utils.Log;

/**
 * 友盟登录
 * 
 * @author MIDIAN
 * 
 */
public class UMengLoginUtil {


	private Activity mContext;
	
	DataListener mDataListener;
	String uId;
	public static UMengLoginUtil mLoginUtil = null;
	UMShareAPI mShareAPI=null;
	
	private UMengLoginUtil(final Activity context) {
		this.mContext = context;
		 mShareAPI = UMShareAPI.get(context);
		
	}
	
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(mContext, "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( mContext, "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( mContext, "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

//	public void sinaSso() {
//
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//		mController.getConfig().setSinaCallbackUrl("http://open.weibo.com/");
//		// SocializeConfig config = mController.getConfig();
//		// config.addFollow(SHARE_MEDIA.SINA, "900966085");
//		// // 添加follow 时的回调
//		// config.setOauthDialogFollowListener(new MulStatusListener() {
//		// @Override
//		// public void onStart() {
//		// }
//		//
//		// @Override
//		// public void onComplete(MultiStatus multiStatus, int st,
//		// SocializeEntity entity) {
//		// if (st == 200) {
//		//
//		// }
//		// }
//		// });
//	}

	public static UMengLoginUtil getInstance(Activity context) {

		if (mLoginUtil == null)
			mLoginUtil = new UMengLoginUtil(context);

		return mLoginUtil;
	}

	public void clear() {
		mLoginUtil = null;
	}

//	public void configPlatforms() {
//		// 添加新浪SSO授权
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//		// 添加腾讯微博SSO授权
//		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
//
//		// 添加QQ、QZone平台
//		addQQQZonePlatform();
//
//		// 添加微信、微信朋友圈平台
//		addWXPlatform();
//	}

//	/**
//	 * @功能描述 : 添加微信平台分享
//	 * @return
//	 */
//	private void addWXPlatform() {
//		// 注意：在微信授权的时候，必须传递appSecret
//		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
//		String appId = Constant.weixinAppId;
//		String appSecret = Constant.weixinAppSecret;
//		// String appId = "wx90c6a7bcba68e783";
//		// String appSecret = "b2a4552aa9821d36bfe4d9eae47c5aae";
//		// 添加微信平台
//		UMWXHandler wxHandler = new UMWXHandler(mContext, appId, appSecret);
//		wxHandler.addToSocialSDK();
//
//		// 支持微信朋友圈
//		UMWXHandler wxCircleHandler = new UMWXHandler(mContext, appId,
//				appSecret);
//		wxCircleHandler.setToCircle(true);
//		wxCircleHandler.addToSocialSDK();
//	}

	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
//	private void addQQQZonePlatform() {
//		String appId = Constant.qqAppId;
//
//		String appKey = Constant.qqAppKEY;
//		// 添加QQ支持
//		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mContext, appId,
//				appKey);
//		qqSsoHandler.addToSocialSDK();
//
//		// 添加QZone平台
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mContext, appId,
//				appKey);
//		qZoneSsoHandler.addToSocialSDK();
//	}

	/**
	 * 注销本次登录</br>
	 */
	public void logout(final SHARE_MEDIA platform) {
		mShareAPI.deleteOauth(mContext, platform,new UMAuthListener() {
			
			@Override
			public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancel(SHARE_MEDIA arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * 授权。如果授权成功，则获取用户信息</br>
	 */
	public void login(final SHARE_MEDIA platform) {

		mShareAPI.doOauthVerify(mContext, platform, new UMAuthListener() {
			
			@Override
			public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(SHARE_MEDIA platform, int arg1, Map<String, String> value) {
				// TODO Auto-generated method stub
				System.out.println("value:::::::::"+value.toString());
				uId = value.get("uid");
				if (!TextUtils.isEmpty(uId)) {
					getUserInfo(platform);

				} else {
					Toast.makeText(mContext, "授权失败...", Toast.LENGTH_SHORT)
							.show();
				}
			}
			
			@Override
			public void onCancel(SHARE_MEDIA arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	// }

	/**
	 * 获取授权平台的用户信息</br>
	 */
	private void getUserInfo(final SHARE_MEDIA platform) {
		mShareAPI.getPlatformInfo(mContext, platform, new UMAuthListener() {
			
			@Override
			public void onError(SHARE_MEDIA platform, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				if (mDataListener != null) {
					mDataListener.onFail(platform);
				}
			}
			
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> info) {
				// TODO Auto-generated method stub
				if (info == null) {
				
					return;
				}

				String nickName = "";
				String headurl = "";
				System.out.println("info:::::::::"+info.toString());
				if (platform == SHARE_MEDIA.QQ) {
					nickName = (String) info.get("screen_name");
					headurl = (String) info.get("profile_image_url");

				} else if (platform == SHARE_MEDIA.WEIXIN) {
					nickName = (String) info.get("nickname");
					headurl = (String) info.get("headimgurl");
					System.out.println("nickName" + nickName + "headurl"
							+ headurl);
				} else if (platform == SHARE_MEDIA.SINA) {
					nickName = (String) info.get("screen_name");
					headurl = (String) info.get("profile_image_url");
					System.out.println("screen_name" + nickName + "headurl"
							+ headurl);
				}
				if (mDataListener != null) {
					mDataListener.onSuccess(platform, uId, nickName,
							headurl);
				}
				
			}
			
			@Override
			public void onCancel(SHARE_MEDIA arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public String getTag(SHARE_MEDIA platform) {
		String tag = "";
		if (platform == SHARE_MEDIA.QQ) {
			tag = "2";
		} else if (platform == SHARE_MEDIA.WEIXIN) {
			tag = "4";
		} else {
			tag = "3";
		}
		return tag;
	}

	public void setDataListener(DataListener mDataListener) {
		this.mDataListener = mDataListener;
	}

	public interface DataListener {
		public void onStart(SHARE_MEDIA platform);

		public void onSuccess(SHARE_MEDIA platform, String uid,
				String nickName, String headurl);

		public void onFail(SHARE_MEDIA platform);

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		mShareAPI.onActivityResult(requestCode, resultCode, data);
	}
}
