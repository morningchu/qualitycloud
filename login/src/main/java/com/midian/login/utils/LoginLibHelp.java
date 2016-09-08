package com.midian.login.utils;

import midian.baselib.app.AppContext;

import com.midian.login.api.LoginApiClient;
import com.midian.login.view.LoginActivity;

/**
 * 登录库帮助类
 * 
 * @author MIDIAN
 * 
 */
public class LoginLibHelp {
	/**
	 * 引用此库必须要调用此方法
	 * 
	 * @param appcontext
	 */
	public static void init(AppContext appcontext) {
		if (appcontext == null)
			return;
		LoginApiClient.init(appcontext);
		appcontext.setLogin(LoginActivity.class);
	}
}
