package midian.baselib.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import midian.baselib.api.ApiClient;
import midian.baselib.utils.EncryptionUtil;
import midian.baselib.utils.ResourceUtil;
import midian.baselib.utils.UIHelper;
import midian.baselib.utils.UMengStatistticUtil;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.midian.baselib.R;
import com.midian.configlib.ServerConstant;
import com.midian.fastdevelop.afinal.http.AjaxParams;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by XuYang on 15/4/13.
 */
public class AppContext extends Application {

	public static String PAGE_SIZE = "20";

	public String androidKey = "A_iAS5d583PiR3kt2UQA";
	public String user_id = "";// 用户id
	public String access_token = "";// 访问token
	public String name = "";// 姓名
	public String portrait = "";// 头像链接
	public String account = "";// 账号
	public String password = "";// 密码
	public String head_pic = "";// 头像链接
	public boolean ischange = false;
	public Activity mHome;
	public DisplayImageOptions options;
	public ImageLoader imageLoader;// 图片加载类
	public ApiClient api;// 网络请求帮助类
	private Class login;
	public String device_token = "";// 设备号
	public ResourceUtil resourceUtil;
	public String link_standard = "";
	public String link_geo_pro = "";
	public String link_brand = "";
	public String link_help = "";
	public String link_about = "";
	public String lat = "";// 当前经纬度
	public String lon = "";// 当前经纬度
	public boolean isClosePush = false;// 是否接收推送
	public boolean isHasNewVersion = false;// 是否接收推送

	@Override
	public void onCreate() {
		super.onCreate();
		api = new ApiClient(this);
		resourceUtil = new ResourceUtil(this);
		initData();
		// 关闭友萌的自动统计分析功能
		UMengStatistticUtil.openActivityDurationTrack(false);
		initUImageLoader();
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);

	}

	private void initData() {
		user_id = getProperty("user_id");
		access_token = getProperty("access_token");
		name = getProperty("name");
		account = getProperty("account");
		portrait = getProperty("portrait");
		head_pic = getProperty("head_pic");
		password = getProperty("password");
		link_standard = getProperty("link_standard");
		link_geo_pro = getProperty("link_geo_pro");
		link_brand = getProperty("link_brand");
		link_help = getProperty("link_help");
		link_about = getProperty("link_about");
		lon = getProperty("lon");
		lat = getProperty("lat");
		isClosePush = getBoolean("isClosePush");
		isHasNewVersion = getBoolean("isHasNewVersion");
		device_token = getProperty("device_token");

	}

	public boolean isLogin() {
		return !TextUtils.isEmpty(access_token);
	}

	public String getClientKey() {
		return EncryptionUtil.getEncryptionStr();
	}

	public void setImage(final ImageView iv, String url) {
		if (url.contains(ServerConstant.BASEFILEURL)) {

		} else {
			url = ServerConstant.BASEFILEURL + url;
		}
		imageLoader.displayImage(url, iv, new SimpleImageLoadingListener() {

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				// iv.setImageBitmap(arg2);
			}

		});
	}

	/**
	 * 设置推送id
	 * 
	 * @param device_token
	 */
	public void saveDeviceToken(String device_token) {
		this.device_token = device_token;
		setProperty("device_token", device_token);
	}

	/**
	 * 设置是否接收
	 * 
	 * @param isReceive
	 */
	public void isClosePush(boolean isClosePush) {
		this.isClosePush = isClosePush;
		setBoolean("isClosePush", isClosePush);
	}

	/**
	 * 有新版本
	 * 
	 * @param isReceive
	 */
	public void isHasNewVersion(boolean isHasNewVersion) {
		this.isHasNewVersion = isHasNewVersion;
		setBoolean("isHasNewVersion", isHasNewVersion);
	}

	public void saveLocation(String lon, String lat) {
		this.lat = lat;
		this.lon = lon;
		setProperty("lat", lat);
		setProperty("lon", lon);
	}

	/**
	 * 获取通用的请求参数
	 * 
	 * @return
	 */
	public AjaxParams getCAjaxParams() {
		AjaxParams params = new AjaxParams();
		if (!TextUtils.isEmpty(user_id))
			params.put("user_id", user_id + "");

		if (!TextUtils.isEmpty(access_token))
			params.put("access_token", access_token + "");
		params.put("client_key", getClientKey());

		System.out.println(params.getParamString());
		return params;
	}

	public AjaxParams getClientKeyParams() {
		AjaxParams params = new AjaxParams();
		params.put("client_key", getClientKey());
		return params;
	}

	public void setImage(ImageView iv, int id) {
		imageLoader.displayImage("drawable://" + id, iv);
	}

	private void initUImageLoader() {
		Options decodingOptions = new Options();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.c_loading)
				.showImageForEmptyUri(R.drawable.c_loading_failure)
				.showImageOnFail(R.drawable.c_loading_failure)
				.decodingOptions(decodingOptions).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
		imageLoader = ImageLoader.getInstance();
	}

	/**
	 * 是否存在access_token
	 * 
	 * @return
	 */
	public boolean isAccess() {
		if (TextUtils.isEmpty(access_token)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 是否包含指定key的配置信息
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsProperty(String key) {
		SharedPreferences sp = AppConfig.getSharedPreferences(this);
		return sp.contains(key);
	}

	/**
	 * 设置指定key的配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		AppConfig.getAppConfig(this).set(key, value);
	}

	/**
	 * 设置指定key的配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void setBoolean(String key, boolean value) {
		AppConfig.getAppConfig(this).set(key, value);
	}

	/**
	 * 设置指定key的配置信息s
	 * 
	 * @param key
	 * @param value
	 */
	public void setInteger(String key, int value) {
		AppConfig.getAppConfig(this).set(key, value);
	}

	/**
	 * 获得指定key的配置信息
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return AppConfig.getAppConfig(this).get(key);
	}

	/**
	 * 获得指定key的配置信息
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		return AppConfig.getAppConfig(this).getBoolean(key);
	}

	/**
	 * 获得指定key的配置信息
	 * 
	 * @param key
	 * @return
	 */
	public int getInteger(String key) {
		return AppConfig.getAppConfig(this).getInteger(key);
	}

	/**
	 * 刪除指定key的配置信息
	 * 
	 * @param key
	 */
	public void removeProperty(String... key) {
		AppConfig.getAppConfig(this).remove(key);
	}

	/**
	 * 保存对象
	 * 
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public boolean saveObject(Serializable ser, String file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 读取对象
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String file) {
		if (!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable) ois.readObject();
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			e.printStackTrace();
			// 反序列化失败 - 删除缓存文件
			if (e instanceof InvalidClassException) {
				File data = getFileStreamPath(file);
				data.delete();
			}
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
			}
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 判断缓存是否存在
	 * 
	 * @param cachefile
	 * @return
	 */
	public boolean isExistDataCache(String cachefile) {
		boolean exist = false;
		File data = getFileStreamPath(cachefile);
		if (data.exists())
			exist = true;
		return exist;
	}

	/**
	 * 保存用户信息
	 * 
	 * @param res
	 */
	public void saveUserInfo(String user_id, String access_token, String name,
			String head_pic) {
		ischange = true;
		this.user_id = user_id;
		this.access_token = access_token;
		this.name = name;
		// if (!TextUtils.isEmpty(Headportrait)) {
		// this.portrait = ServerConstant.BASEFILEURL + Headportrait;
		// }
		setProperty("user_id", user_id);
		setProperty("access_token", access_token);
		setProperty("name", name);
		setProperty("head_pic", head_pic);
	}

	/**
	 * 清除用户信息
	 */
	public void clearUserInfo() {
		user_id = "";
		account = "";
		access_token = "";
		name = "";
		head_pic = "";
		removeProperty("user_id");
		removeProperty("account");
		removeProperty("access_token");
		removeProperty("name");
		removeProperty("head_pic");
	}

	/**
	 * 保存链接
	 * 
	 * @param res
	 */
	public void saveLink(String link_standard, String link_geo_pro,
			String link_brand, String link_help, String link_about) {
		this.link_standard = link_standard;
		this.link_geo_pro = link_geo_pro;
		this.link_brand = link_brand;
		this.link_help = link_help;
		this.link_about=link_about;
		setProperty("link_standard", link_standard);
		setProperty("link_geo_pro", link_geo_pro);
		setProperty("link_brand", link_brand);
		setProperty("link_help", link_help);
		setProperty("link_about", link_about);
	}

	/**
	 * 地方标准
	 * 
	 * @param link_standard
	 */
	public void saveLinkStandard(String link_standard) {
		this.link_standard = link_standard;
		setProperty("link_standard", link_standard);
	}

	/**
	 * 地理标志关于
	 * 
	 * @param link_geo_pro
	 */
	public void saveLinkGeo_pro(String link_geo_pro) {
		this.link_geo_pro = link_geo_pro;
		setProperty("link_geo_pro", link_geo_pro);
	}

	/**
	 * 名牌关于
	 * 
	 * @param link_brand
	 */
	public void saveLinkBrand(String link_brand) {
		this.link_brand = link_brand;
		setProperty("link_brand", link_brand);
	}

	/**
	 * 帮助中心
	 * 
	 * @param link_help
	 */
	public void saveLinkHelp(String link_help) {
		this.link_help = link_help;
		setProperty("link_help", link_help);
	}

	public void saveAccount(String account) {
		this.account = account;
		setProperty("account", account);
	}

	public void savePassword(String password) {
		this.password = password;
		setProperty("password", password);
	}

	/**
	 * 要求登录
	 * 
	 * @param context
	 * @return
	 */
	public boolean isRequireLogin(final Activity context) {
		if (TextUtils.isEmpty(user_id)) {
			if (login != null)
				UIHelper.jump(context, login);
			return false;
		} else {
			return true;
		}
	}

	public String getUrl(String url) {
		if (url.contains(ServerConstant.BASEURL) || url.contains("http://")
				|| url.contains("https://")) {
			
		}else{
			url = ServerConstant.BASEURL + url;
		}
		return url;

	}

	/**
	 * 设置登录页面
	 * 
	 * @param context
	 */
	public void setLogin(Class context) {
		this.login = context;
	}

	/**
	 * 处理后台返回的错误码
	 * 
	 * @param context
	 * @param error_code
	 */
	public void handleErrorCode(final Context context, final String error_code) {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				if (TextUtils.isEmpty(error_code)) {
					return;
				}
				// 登录过期
				if ("token_error".equals(error_code)
						|| "tokenTimeLimit".equals(error_code)
						|| "tokenError".equals(error_code)) {
					UIHelper.t(context, "您的账号已经在其他设备登陆，请重新登录");
					clearUserInfo();
					// logout();
					// BroadcastManager.sendLogoutBroadcast(context);

					Intent intent = new Intent(context, login);
					Bundle bundle = new Bundle();
					intent.putExtras(bundle);

					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_NEW_TASK);

					context.startActivity(intent);
				} else {
					UIHelper.t(context, resourceUtil.getString(error_code));
				}
			}
		};
		if (context instanceof Activity) {
			((Activity) context).runOnUiThread(runnable);
		} else if (context instanceof AppContext) {
			new Handler().post(runnable);
		}

	}

	/**
	 * 编辑个人资料的引用类
	 */
	public CityData p;
	public CityData city;
	public boolean addresschage;

}
