package midian.baselib.version;

import java.io.File;

import midian.baselib.app.AppConfig;
import midian.baselib.app.AppContext;
import midian.baselib.base.BaseActivity;
import midian.baselib.utils.EncryptionUtil;
import midian.baselib.utils.ScreenUtils;
import midian.baselib.widget.dialog.BaseDialog;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.baidu.autoupdatesdk.AppUpdateInfo;
import com.baidu.autoupdatesdk.AppUpdateInfoForInstall;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.CPCheckUpdateCallback;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.midian.baselib.R;
import com.midian.configlib.ServerConstant;
import com.midian.fastdevelop.afinal.FinalHttp;
import com.midian.fastdevelop.afinal.http.AjaxCallBack;
import com.midian.fastdevelop.afinal.http.AjaxParams;
import com.midian.fastdevelop.afinal.http.HttpHandler;

public class VersionUpdateUtil implements OnClickListener {

	Context context;
	VersionUpdateCallBack callback;
	BaseDialog newVersionDialog;
	Notification mNotification;
	NotificationManager mNotificationManager;

	static final String key_vcode = "key_vcode";
	static final String key_filename = "key_filename";
	String updateUrl = ServerConstant.BASEURL + "version";
	FinalHttp finalHttp;
	String TAG = this.getClass().getSimpleName();
	int notification_id;
	VersionUpdateCallBack callBack;
	AppContext ac;
	String detail;
	boolean isforce_status;

	public VersionUpdateUtil(Context context) {
		this.context = context;
		ac = (AppContext) context.getApplicationContext();
		finalHttp = new FinalHttp();
	}

	public void setVersionUpdateCallBack(
			VersionUpdateCallBack versionUpdateCallBack) {
		this.callback = versionUpdateCallBack;
	}

	// BaseActivity baseActivity;

	public void executeForAboutUs() {
		// this.baseActivity = baseActivity;
		if (isHaveNewVersion()) {
			showNewVersionDialog();
		} else {
			// if (baseActivity != null) {
			// baseActivity.showLoadDialog();
			// }
			checkNewVersion();
		}
	}

	public void executeForAppStart() {
		checkNewVersion();
	}

	// 检测是否有新版本
	private void checkNewVersion() {
		AjaxParams params = new AjaxParams();
		params.put("client_key", EncryptionUtil.getEncryptionStr());
		checkNewVersion(updateUrl, params);
	}

	// 更新软件
	private void updateVersion() {
		System.out.println("updateVersion");
		updateVersion(ServerConstant.BASEFILEURL, R.drawable.ic_launcher,
				context.getResources().getString(R.string.app_name),
				R.layout.update_app_notification, R.id.name_tv,
				R.id.progress_tv, R.id.progress_pb, getRootPath(context) + "/"
						+ "updata");
	}

	private void showNewVersionDialog() {
		try {
			newVersionDialog = new BaseDialog(context, R.style.dialog_msg);
			View mView = LayoutInflater.from(context).inflate(
					R.layout.dialog_update_app_tip, null);
			newVersionDialog.setCanceledOnTouchOutside(!isforce_status);
			newVersionDialog.setContentView(mView);
			newVersionDialog.show();
			Window dialogWindow = newVersionDialog.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.CENTER);

			lp.width = (int) (ScreenUtils.GetScreenWidthPx((Activity) context) - ScreenUtils
					.dpToPx(context, 30));
			lp.height = (int) (lp.width * 0.63);

			dialogWindow.setAttributes(lp);

			TextView contentTv = (TextView) mView.findViewById(R.id.tip_tv);
			if (!TextUtils.isEmpty(detail))
				contentTv.setText("发现新版本：\n" + detail);
			Button leftBtn = (Button) mView.findViewById(R.id.left_btn);
			Button rightBtn = (Button) mView.findViewById(R.id.right_btn);
			leftBtn.setOnClickListener(this);
			leftBtn.setEnabled(!isforce_status);
			rightBtn.setOnClickListener(this);
			ac.isHasNewVersion(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View view) {
		if (newVersionDialog != null && newVersionDialog.isShowing()) {
			newVersionDialog.dismiss();
		}
		int id = view.getId();
		if (id == R.id.left_btn) {
		} else if (id == R.id.right_btn) {
			updateVersion();
		}

	}

	private void saveNewVersionInfo(String vcode, String fileName) {
		AppConfig.getAppConfig(context).set(key_vcode, vcode);
		AppConfig.getAppConfig(context).set(key_filename, fileName);
	}

	public boolean isHaveNewVersion() {
		String vCode = getVCode();
		if (TextUtils.isEmpty(vCode)) {

		} else {
			int version = Integer.parseInt(vCode);
			if (version > getAppVersionCode()) {
				return true;
			}
		}
		return false;
	}

	private String getVCode() {
		return AppConfig.getAppConfig(context).get(key_vcode);
	}

	private String getFileName() {
		return AppConfig.getAppConfig(context).get(key_filename);
	}

	/**
	 * 检测是否有新版本，结果会通过回调方法返回给调用方 ，接口返回的json格式需为： { "vcode":"1",
	 * "update_url":"http://....../adf.apk",
	 * 
	 * }
	 * 
	 * @param url
	 *            检测新版本的接口 get方式请求
	 * @param params
	 *            参数
	 * @param callBack
	 *            回调接口
	 * @return
	 */
	private HttpHandler<Object> checkNewVersion(String url, AjaxParams params) {
		System.out.println("checkNewVersion:::::" + url + "?"
				+ params.toString());

		HttpHandler<Object> httpHandler = null;
		if (params != null) {
			httpHandler = finalHttp.get(url, params,
					new AjaxCallBack<String>() {
						@Override
						public void onSuccess(String t, String requestTag) {
							super.onSuccess(t, requestTag);
							System.out.println("updateVersion::::::::::::" + t);
							String content = getJsonValue(t, "content");
							dealReasult(content);

						}

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg, String requestTag) {
							super.onFailure(t, errorNo, strMsg, requestTag);
							if (callBack != null)
								callBack.isNewestVersion();
							// if (baseActivity != null) {
							// baseActivity.hideLoadDialog();
							// }
						}
					});
		} else {
			httpHandler = finalHttp.get(url, new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t, String requestTag) {
					super.onSuccess(t, requestTag);
					dealReasult(t);
				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg,
						String requestTag) {
					super.onFailure(t, errorNo, strMsg, requestTag);
					if (callBack != null)
						callBack.isNewestVersion();
					// if (baseActivity != null) {
					// baseActivity.hideLoadDialog();
					// }
				}
			});
		}
		return httpHandler;
	}

	private void dealReasult(String t) {
		// if (baseActivity != null) {
		// baseActivity.hideLoadDialog();
		// }
		if (!TextUtils.isEmpty(t)) {
			String vcode = getJsonValue(t, "version");
			String force_status = getJsonValue(t, "force_status");
			detail = getJsonValue(t, "detail");
			isforce_status = "1".equals(force_status);// 强制下载；
			if (!TextUtils.isEmpty(vcode)) {
				int version = Integer.parseInt(vcode);
				if (version > getAppVersionCode()) {
					String fileName = getJsonValue(t, "file_name");
					saveNewVersionInfo(vcode, fileName);
					showNewVersionDialog();
					if (callBack != null)
						callBack.discoverNewVersion(vcode);
				} else {
					ac.isHasNewVersion(false);
					if (callBack != null)
						callBack.isNewestVersion();
				}
			} else {
				ac.isHasNewVersion(false);
				if (callBack != null)
					callBack.isNewestVersion();
			}
		} else {
			ac.isHasNewVersion(false);
			callBack.isNewestVersion();
		}
	}

	private String getJsonValue(String json, String key) {
		String value = "";
		try {
			JSONObject mJSONObject = new JSONObject(json);
			value = mJSONObject.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 发现新版本时调用，此方法会自动完成apk文件的下载及安装
	 * 
	 * @param base_url
	 *            下载apk文件的基础url(域名部分),
	 * @param notificationIcon
	 *            通知栏icon
	 * @param tickerText
	 *            弹通知时状态栏显示文字
	 * @param notification_layout
	 *            下载apk文件时通知栏显示的布局
	 * @param tv_apk_name_id
	 *            notification_layout中用于显示文件名的TextView的id
	 * @param tv_progress_id
	 *            notification_layout中用于显示下载百分比的TextView的id
	 * @param progressbar_id
	 *            notification_layout中用于显示下载进度的ProgressBar的idF
	 * @param apk_storage_dir
	 *            apk文件存放的目录
	 */
	private void updateVersion(String base_url, final int notificationIcon,
			String tickerText, final int notification_layout,
			int tv_apk_name_id, final int tv_progress_id,
			final int progressbar_id, String apk_storage_dir) {

		setUpdateNotification(notificationIcon, tickerText,
				notification_layout, tv_apk_name_id, tv_progress_id,
				progressbar_id);
		String fileName = getFileName();
		System.out.println("base_url + fileName" + base_url + fileName);
		if (!TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(base_url)) {

			AjaxCallBack<File> callback = new AjaxCallBack<File>() {
				@Override
				public void onLoading(long count, long current) {
					super.onLoading(count, current);
					// FDDebug.d(TAG + "   count", count + "");
					// FDDebug.d(TAG + "   current", current + "");
					RemoteViews contentView = mNotification.contentView;
					int progress = (int) (current * 100 / count);
					contentView.setTextViewText(tv_progress_id, progress + "%");
					contentView.setProgressBar(progressbar_id, 100, progress,
							false);
					mNotificationManager.notify(getNotificationId(),
							mNotification);

				}

				@Override
				public void onSuccess(File t, String requestTag) {
					super.onSuccess(t, requestTag);

					mNotificationManager.cancel(getNotificationId());
					try {
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setDataAndType(
								Uri.parse("file://" + t.getAbsolutePath()),
								"application/vnd.android.package-archive");
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(i);
					} catch (Exception e) {

						// Intent i = new Intent(Intent.ACTION_VIEW);
						// i.setDataAndType(Uri.parse("file://" + ""),
						// "application/vnd.android.package-archive");
						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						// context.startActivity(i);
					}

				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg,
						String requestTag) {
					super.onFailure(t, errorNo, strMsg, requestTag);
					mNotificationManager.cancel(getNotificationId());
					// FDDebug.e(TAG, "errorNo:::" + errorNo + ",strMsg:::"
					// + strMsg);
					if (callBack != null) {
						callBack.updateVersionFailure(errorNo, strMsg);
					}
				}
			};
			callback.progress(true, 1000);
			File dir = new File(apk_storage_dir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File target = new File(apk_storage_dir,
					(base_url + fileName).hashCode() + "");

			System.out.println("base_url + fileName" + base_url + fileName);
			finalHttp.download(base_url + fileName, target.getAbsolutePath(),
					callback);
		} else {
			// FDDebug.d(TAG, "update_url or base_url is null");
		}
	}

	private void setUpdateNotification(int icon, String tickerText,
			int notification_layout, int tv_apk_name_id, int tv_progress_id,
			int progressbar_id) {
		mNotification = new Notification();
		mNotification.icon = icon;
		mNotification.tickerText = tickerText;
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		RemoteViews contentView = new RemoteViews(context.getPackageName(),
				notification_layout);
		// fileName.substring(fileName.lastIndexOf("/") + 1)
		String fileName = getFileName();
		contentView.setTextViewText(tv_apk_name_id, mNotification.tickerText
				+ "正在下载...");
		contentView.setTextViewText(tv_progress_id, "0%");
		contentView.setProgressBar(progressbar_id, 100, 0, false);
		mNotification.contentView = contentView;
		mNotificationManager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(getNotificationId(), mNotification);
	}

	private int getAppVersionCode() {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info.versionCode;

	}

	private int getNotificationId() {
		if (notification_id == 0) {
			notification_id = (int) System.currentTimeMillis();
		}
		return notification_id;
	}

	public interface VersionUpdateCallBack {

		public void discoverNewVersion(String versionCode);

		public void isNewestVersion();

		public void updateVersionFailure(int errorNo, String strMsg);
	}

	/**
	 * 取到内部存储的根路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getRootPath(Context context) {

		String path;
		File fileDir;
		File sdcardDir;
		fileDir = context.getFilesDir();
		sdcardDir = Environment.getExternalStorageDirectory();
		if (Environment.MEDIA_REMOVED.equals(Environment
				.getExternalStorageState())) {
			path = fileDir.getParent() + java.io.File.separator
					+ fileDir.getName();
		} else {
			path = sdcardDir.getParent() + "/" + sdcardDir.getName();
		}
		return path;
	}

	/**
	 * 百度自动升级sdk
	 */
	public void BDCheckUpdate() {

		BDAutoUpdateSDK.cpUpdateCheck(context, new MyCPCheckUpdateCallback());
	}

	private class MyUICheckUpdateCallback implements UICheckUpdateCallback {

		@Override
		public void onCheckComplete() {

		}
	}

	private class MyCPCheckUpdateCallback implements CPCheckUpdateCallback {

		@Override
		public void onCheckUpdateCallback(AppUpdateInfo info,
				AppUpdateInfoForInstall infoForInstall) {
			if (infoForInstall != null
					&& !TextUtils.isEmpty(infoForInstall.getInstallPath())) {
				BDAutoUpdateSDK.cpUpdateInstall(ac,
						infoForInstall.getInstallPath());
			} else if (info != null) {
				ac.isHasNewVersion(true);
				BDAutoUpdateSDK.uiUpdateAction(context,
						new MyUICheckUpdateCallback());
			} else {
				ac.isHasNewVersion(false);
			}
		}

	}

}
