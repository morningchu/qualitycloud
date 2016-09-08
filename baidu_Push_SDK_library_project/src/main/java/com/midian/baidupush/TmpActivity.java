package com.midian.baidupush;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.jni.BaiduAppSSOJni;

import android.os.Bundle;
import midian.baselib.base.BaseActivity;

/**
 * 使用模版
 * 
 * @author MIDIAN
 * 
 */
public class TmpActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY, "nKGGUGBtOTPyAINAX0HduZHf");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PushManager.stopWork(getApplicationContext());
	}
}
