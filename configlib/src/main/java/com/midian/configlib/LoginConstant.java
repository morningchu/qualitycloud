package com.midian.configlib;

import android.app.Activity;
import android.os.Bundle;

/**
 * 注册登录url
 * 
 * @author MIDIAN
 * 
 */
public class LoginConstant {
	public static final String BASEURL = ServerConstant.HOST + "QCApp/";// 社区
	public static final String BASEFILEURL = ServerConstant.BASEFILEURL;
	/**
	 * 1.1.注册
	 */
	public static final String REGISTER = BASEURL + "register";
	/**
	 * 1.2登录
	 */
	public static final String LOGIN = BASEURL + "login";
	/**
	 * 1.3第三方登录
	 */
	public static final String THIRD_USER_LOGIN = BASEURL + "third_user_login";
	/**
	 * 1.4发送验证码
	 */
	public static final String SEND_CODE = BASEURL + "send_code";
	/**
	 * 1.5验证验证码
	 */
	public static final String VALIDATE_CODE = BASEURL + "validate_code";
	/**
	 * 1.6验证原密码
	 */
	public static final String VALIDATE_PWD = BASEURL + "validate_pwd";
	/**
	 * 1.7修改密码
	 */
	public static final String UPDATE_PWD = BASEURL + "update_pwd";
	/**
	 * 1.8个人资料
	 */
	public static final String USER_DETAIL = BASEURL + "user_detail";
	/**
	 * 1.9修改个人资料
	 */
	public static final String UPDATE_USER = BASEURL + "update_user";
	/**
	 * 1.10修改手机号
	 */
	public static final String UPDATE_PHONE = BASEURL + "update_phone";
	/**
	 * 1.11更改会员推送接收状态
	 */
	public static final String UPDATE_RECEIVE_STATUS = BASEURL
			+ "update_receive_status";
	/**
	 * 1.12找回密码
	 */
	public static final String FIND_PWD = BASEURL + "find_pwd";
	
	/**
	 * 2.4发送用户设备号/清空设备号
	 */
	public static final String SAVE_DEVICE = BASEURL + "save_device";
}
