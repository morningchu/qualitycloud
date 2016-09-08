package com.midian.login.api;

import java.io.File;
import java.io.FileNotFoundException;

import midian.baselib.api.ApiCallback;
import midian.baselib.api.BaseApiClient;
import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Md5Utils;
import android.text.TextUtils;

import com.midian.configlib.LoginConstant;
import com.midian.fastdevelop.afinal.http.AjaxParams;
import com.midian.login.bean.RegisterBean;
import com.midian.login.bean.SaveDevice;
import com.midian.login.bean.ThirdUserLoginBean;
import com.midian.login.bean.UpdatePhoneBean;
import com.midian.login.bean.UpdatePwdBean;
import com.midian.login.bean.UpdateReceiveStatusBean;
import com.midian.login.bean.UpdateUserBean;
import com.midian.login.bean.UserDetailBean;
import com.midian.login.bean.Userse;
import com.midian.login.bean.ValidateCodeBean;
import com.midian.login.bean.ValidatePwdBean;

/**
 * 网络请求客户端基类
 * 
 * @author moshouguan
 * 
 */
public class LoginApiClient extends BaseApiClient {

	public LoginApiClient(AppContext api) {
		// TODO Auto-generated constructor stub
		super(api);
	}

	/**
	 * 1.1.注册
	 * 
	 * @param account
	 * @param password
	 * @param code
	 * @param head
	 * @param name
	 * @param sex
	 * @param device_token
	 * @param callback
	 * @throws FileNotFoundException
	 */
	public void register(String account, String password, String code,
			File head, String name, String sex, String device_token,
			ApiCallback callback) throws FileNotFoundException {

		AjaxParams params = ac.getCAjaxParams();
		params.setHasFile(true);
		params.put("account", account);
		params.put("password", Md5Utils.md5(password));
		params.put("code", code);
		if (head != null) {
			params.put("head", head);
		}
		if (!TextUtils.isEmpty(name)) {
			params.put("name", name);
		}
		if (!TextUtils.isEmpty(sex)) {
			params.put("sex", sex);
		}
		params.put("device_token", device_token);
		post(callback, LoginConstant.REGISTER, params, RegisterBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 1.2登录
	 * 
	 * @param account
	 * @param password
	 * @param device_token
	 * @param callback
	 */
	public void login(String account, String password, String device_token,
			ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("account", account);
		params.put("password", Md5Utils.md5(password));
		params.put("device_token", device_token);
		post(callback, LoginConstant.LOGIN, params, Userse.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 1.3第三方登录
	 * 
	 * @param third_id
	 * @param nickname
	 * @param head
	 * @param user_from
	 * @param device_token
	 * @param client_key
	 * @param callback
	 */
	public void thirdUserLogin(String third_id, String nickname, String head,
			String user_from, String device_token, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("third_id", third_id);
		params.put("nickname", nickname);
		params.put("head", head);
		params.put("user_from", user_from);
		params.put("device_token", device_token);
		post(callback, LoginConstant.THIRD_USER_LOGIN, params,
				ThirdUserLoginBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 1.4发送验证码
	 * 
	 * @param phone
	 * @param callback
	 */
	public void sendCode(String phone, String type, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("phone", phone);
		params.put("type", type);
		post(callback, LoginConstant.SEND_CODE, params, NetResult.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 1.5验证验证码
	 * 
	 * @param phone
	 * @param code
	 * @param callback
	 */
	public void validateCode(String phone, String code, ApiCallback callback) {
		// if (TextUtils.isEmpty(phone)) {
		// UIHelper.t(ac,
		// ac.getResources().getString(R.string.phone_cannot_empty));
		// return;
		// }
		// if (!Func.isMobileNO(phone)) {
		// UIHelper.t(ac,
		// ac.getResources().getString(R.string.phone_format_error));
		// return;
		// }
		// if (TextUtils.isEmpty(validCode)) {
		// UIHelper.t(
		// ac,
		// ac.getResources().getString(
		// R.string.verification_cannot_empty));
		// return;
		// }

		AjaxParams params = ac.getCAjaxParams();
		params.put("phone", phone);
		params.put("code", code);
		get(callback, LoginConstant.VALIDATE_CODE, params,
				ValidateCodeBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 1.6验证原密码
	 * 
	 * @param user_id
	 * @param pwd
	 * @param callback
	 */
	public void validatePwd(String pwd, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("pwd", pwd);
		get(callback, LoginConstant.VALIDATE_PWD, params,
				ValidatePwdBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 1.7修改密码
	 * 
	 * @param user_id
	 * @param pwd
	 * @param callback
	 */
	public void updatePwd(String pwd, ApiCallback callback) {

		// if (TextUtils.isEmpty(pwd)) {
		// DpUIHelper
		// .t(ac,
		// ac.getResources().getString(
		// R.string.password_cannot_empty));
		// return;
		// }
		// if (!pwd.equals(pwd_again)) {
		// DpUIHelper.t(
		// ac,
		// ac.getResources().getString(
		// R.string.two_time_password_not_same));
		// return;
		// }
		AjaxParams params = ac.getCAjaxParams();
		params.put("pwd", Md5Utils.md5(pwd));
		post(callback, LoginConstant.UPDATE_PWD, params, UpdatePwdBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	static public void init(AppContext appcontext) {
		if (appcontext == null)
			return;
		appcontext.api.addApiClient(new LoginApiClient(appcontext));
	}

	/**
	 * 1.8个人资料
	 * 
	 * @param user_id
	 * @param pwd
	 * @param callback
	 */
	public void getUserDetail(ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		get(callback, LoginConstant.USER_DETAIL, params, UserDetailBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 1.9修改个人资料
	 * 
	 * @param user_id
	 * @param pwd
	 * @param callback
	 */
	public void postUpdateUser(File head, String name, String sex,
			String province_id, String province_name, String city_id,
			String city_name, String area_id, String area_name, String address,
			ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.setHasFile(true);
		if (head != null) {
			try {
				params.put("head", head);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (!TextUtils.isEmpty(name)) {
			params.put("name", name);
		}
		if (!TextUtils.isEmpty(sex)) {
			params.put("sex", sex);
		}
		if (!TextUtils.isEmpty(province_id)) {
			params.put("province_id", province_id);
		}
		if (!TextUtils.isEmpty(province_name)) {
			params.put("province_name", province_name);
		}
		if (!TextUtils.isEmpty(city_id)) {
			params.put("city_id", city_id);
		}
		if (!TextUtils.isEmpty(city_name)) {
			params.put("city_name", city_name);
		}
		if (!TextUtils.isEmpty(area_id)) {
			params.put("area_id", area_id);
		}
		if (!TextUtils.isEmpty(area_name)) {
			params.put("area_name", area_name);
		}
		if (!TextUtils.isEmpty(address)) {
			params.put("address", address);
		}
		post(callback, LoginConstant.UPDATE_USER, params, UpdateUserBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 1.10修改手机号
	 * 
	 * @param user_id
	 * @param pwd
	 * @param callback
	 */
	public void getUpdatePhone(String new_phone, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("new_phone", new_phone);
		post(callback, LoginConstant.UPDATE_PHONE, params,
				UpdatePhoneBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 1.11更改会员推送接收状态
	 * 
	 * @param user_id
	 * @param pwd
	 * @param callback
	 */
	public void getUpdateReceiveStatus(String receive_status,
			ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("receive_status", receive_status);
		post(callback, LoginConstant.UPDATE_RECEIVE_STATUS, params,
				UpdateReceiveStatusBean.class, getMethodName(Thread
						.currentThread().getStackTrace()));
	}

	/**
	 * 1.12找回密码
	 * 
	 * @param user_id
	 * @param pwd
	 * @param callback
	 */
	public void getFindPwd(String phone, String pwd, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("phone", phone);
		params.put("pwd", Md5Utils.md5(pwd));
		post(callback, LoginConstant.FIND_PWD, params, NetResult.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 2.4发送用户设备号/清空设备号
	 * 
	 * @param phone
	 * @param pwd
	 * @param callback
	 */
	public void saveDevice(String device_token, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("device_token", device_token);
		post(callback, LoginConstant.SAVE_DEVICE, params, SaveDevice.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

}
