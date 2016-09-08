package com.midian.login.app;

import midian.baselib.app.AppContext;

import android.text.TextUtils;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.midian.login.api.LoginApiClient;
import com.midian.login.utils.LoginLibHelp;

/**
 * Created by XuYang on 15/4/13.
 */
public class MAppContext extends AppContext {

	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
//		 SDKInitializer.initialize(this);
		LoginLibHelp.init(this);
		LoginApiClient.init(this);
	}

	/**
	 * 启动推送
	 */
	public void startPush() {
		System.out.println("startWork" + isClosePush);
		if (isClosePush && !TextUtils.isEmpty(device_token)) {

		} else {
			System.out.println("startWork");
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY,
					"tkg3nZfd6mTRDY1XR9oPOEw9");
		}
	}

	public void changePush() {
		if (isClosePush) {
			stopPush();
		} else
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY,
					"tkg3nZfd6mTRDY1XR9oPOEw9");
	}

	/**
	 * 停用推送
	 */
	public void stopPush() {
		System.out.println("stopPush");
		PushManager.stopWork(getApplicationContext());
	}
}
