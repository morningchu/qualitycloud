package com.midian.UMengUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.midian.UMengUtils.UMengShareUtil.UMengShareUtilListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 使用友盟分享模版
 * 
 * @author MIDIAN
 * 
 */
public class TMPShare extends Activity {
	private UMengShareUtil mShareUtil;
	private ShareContent mImageContent = new ShareContent();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	private void initShareUtil() {
		mShareUtil = UMengShareUtil.getInstance(this);
		mShareUtil.setUMengShareUtilListener(mUMengShareUtilListener);
		mImageContent.setAppName("");
		mImageContent.setImage("图片链接");
		mImageContent.setmBitmap(null);// 传本地图片如果没一定传null
		mImageContent.setSummary("");
		mImageContent.setTitle("");
		mImageContent.setUrl("");// 分享链接
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

	// private OnClickListener mOnClickListener = new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// try {
	// switch (v.getId()) {
	// case R.id.qqRl:
	// // 分享qq好友
	// mShareUtil.shareToQQ(mImageContent);

	// break;
	// case R.id.qqzoneRl:
	// // 分享到qq空间
	// mShareUtil.shareToQQZone(mImageContent);

	// break;
	// case R.id.weixinRl:
	// // 分享到微信
	// mShareUtil.shareToWeixin(mImageContent);
	// break;
	// case R.id.weixinfriendsRl:
	// // 分享到微信朋友圈
	// mShareUtil.shareToWeixinCycle(mImageContent);
	// break;
	// case R.id.microblogRl:
	// // 分享到微博
	// mShareUtil.shareToWeibo(mImageContent);
	// break;
	//
	// default:
	// break;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// };

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent0) {
		UMengShareUtil.getInstance(this).onActivityResult(
				requestCode, resultCode, intent0);
	}
}
