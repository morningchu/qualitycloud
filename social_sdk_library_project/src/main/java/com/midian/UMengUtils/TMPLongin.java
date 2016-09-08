//package com.midian.UMengUtils;
//
//import android.app.Activity;
//import android.content.Intent;
//
//import com.midian.UMengUtils.UMengLoginUtil.DataListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//
///**
// * 使用友盟登录模版
// * 
// * @author MIDIAN
// * 
// */
//public class TMPLongin extends Activity {
//
//	/**
//	 * QQ登陆
//	 */
//	private void qq() {
//
//		UMengLoginUtil.getInstance(this).setDataListener(
//				mDataListener);
//		UMengLoginUtil.getInstance(this).login(
//				SHARE_MEDIA.QQ);
//
//	}
//
//	/**
//	 * 新浪微博
//	 */
//	private void weibo() {
//		UMengLoginUtil.getInstance(this).setDataListener(
//				mDataListener);
//		UMengLoginUtil.getInstance(this).login(
//				SHARE_MEDIA.SINA);
//	}
//
//	/**
//	 * 微信
//	 */
//	private void weixin() {
//		UMengLoginUtil.getInstance(this).setDataListener(
//				mDataListener);
//		UMengLoginUtil.getInstance(this).login(
//				SHARE_MEDIA.WEIXIN);
//	}
//
//	DataListener mDataListener = new DataListener() {
//
//		@Override
//		public void onStart(SHARE_MEDIA platform) {
//			// TODO Auto-generated method stub
//		}
//
//		@Override
//		public void onSuccess(SHARE_MEDIA platform, String uid,
//				String nickName, String headurl) {
//			// TODO Auto-generated method stub
//			thirdLogin(uid, nickName, headurl, getTag(platform));
//		}
//
//		@Override
//		public void onFail(SHARE_MEDIA platform) {
//			// TODO Auto-generated method stub
//
//		}
//
//	};
//
//	public String getTag(SHARE_MEDIA platform) {
//		String tag = "";
//		if (platform == SHARE_MEDIA.QQ) {
//			tag = "2";
//		} else if (platform == SHARE_MEDIA.WEIXIN) {
//			tag = "4";
//		} else {
//			tag = "3";
//		}
//		return tag;
//	}
//
//	private void thirdLogin(String uid, String nickName, String headurl,
//			String tag) {
//
//	};
//	
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		UMengLoginUtil.getInstance(this).clear();// 内存没有回收微博登录有问题
//	}
//
//
//
//	/**
//	 * 当 SSO 授权 Activity 退出时，该函数被调用。
//	 * 
//	 * @see {@link Activity#onActivityResult}
//	 */
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//
//		System.out.println("requestCode" + requestCode + "resultCode"
//				+ resultCode + "data" + data);
//		UMengLoginUtil.getInstance(this).onActivityResult(
//				requestCode, resultCode, data);
//	}
//}
