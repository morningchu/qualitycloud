package com.midian.baidupush;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.app.AppContext;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.example.baidupush.R;

/*
 * Push消息处理receiver。请编写您需要的回调函数， 一般来说： onBind是必须的，用来处理startWork返回值；
 *onMessage用来接收透传消息； onSetTags、onDelTags、onListTags是tag相关操作的回调；
 *onNotificationClicked在通知被点击时回调； onUnbind是stopWork接口的返回值回调

 * 返回值中的errorCode，解释如下：
 *0 - Success
 *10001 - Network Problem
 *10101  Integrate Check Error
 *30600 - Internal Server Error
 *30601 - Method Not Allowed
 *30602 - Request Params Not Valid
 *30603 - Authentication Failed
 *30604 - Quota Use Up Payment Required
 *30605 -Data Required Not Found
 *30606 - Request Time Expires Timeout
 *30607 - Channel Token Timeout
 *30608 - Bind Relation Not Found
 *30609 - Bind Number Too Many

 * 当您遇到以上返回错误时，如果解释不了您的问题，请用同一请求的返回值requestId和errorCode联系我们追查问题。
 *
 */

public class MyPushMessageReceiver extends PushMessageReceiver {
	/** TAG to Log */
	public static final String TAG = MyPushMessageReceiver.class
			.getSimpleName();
	private static final int NOTIFICATION_FLAG = 1;
	public static List<PushListener> list = new ArrayList<PushListener>();

	public static void addPushListener(PushListener mPushListener) {
		if (!list.contains(mPushListener) && mPushListener != null)
			list.add(mPushListener);
	}

	public static void removePushListener(PushListener mPushListener) {
		if (list.contains(mPushListener))
			list.remove(mPushListener);
	}

	/**
	 * 调用PushManager.startWork后，sdk将对push
	 * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel
	 * id和user id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
	 * 
	 * @param context
	 *            BroadcastReceiver的执行Context
	 * @param errorCode
	 *            绑定接口返回值，0 - 成功
	 * @param appid
	 *            应用id。errorCode非0时为null
	 * @param userId
	 *            应用user id。errorCode非0时为null
	 * @param channelId
	 *            应用channel id。errorCode非0时为null
	 * @param requestId
	 *            向服务端发起的请求id。在追查问题时有用；
	 * @return none
	 */
	@Override
	public void onBind(Context context, int errorCode, String appid,
			String userId, String channelId, String requestId) {
		System.out.println("onBind");

		String responseString = "onBind errorCode=" + errorCode + " appid="
				+ appid + " userId=" + userId + " channelId=" + channelId
				+ " requestId=" + requestId;
		System.out.println("responseString" + responseString);
		Log.d(TAG, responseString);

		if (errorCode == 0) {
			// 绑定成功
			((AppContext) context.getApplicationContext())
					.saveDeviceToken(channelId);
		}
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		// updateContent(context, responseString);
	}

	/**
	 * 接收透传消息的函数。
	 * 
	 * @param context
	 *            上下文
	 * @param message
	 *            推送的消息
	 * @param customContentString
	 *            自定义内容,为空或者json字符串
	 */
	@Override
	public void onMessage(Context context, String message,
			String customContentString) {
		String messageString = "透传消息 message=\"" + message
				+ "\" customContentString=" + customContentString;
		Log.d(TAG, messageString);
		System.out.println("messageString" + messageString);
		// 自定义内容获取方式，mykey和myvalue对应透传消息推送时自定义内容中设置的键和值
		// if (!TextUtils.isEmpty(customContentString)) {
		// JSONObject customJson = null;
		// try {
		// customJson = new JSONObject(customContentString);
		// String myvalue = null;
		// if (!customJson.isNull("mykey")) {
		// myvalue = customJson.getString("mykey");
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		updateContent(context, message);
	}

	/**
	 * 接收通知点击的函数。
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            推送的通知的标题
	 * @param description
	 *            推送的通知的描述
	 * @param customContentString
	 *            自定义内容，为空或者json字符串
	 */
	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContentString) {
		String notifyString = "通知点击 title=\"" + title + "\" description=\""
				+ description + "\" customContent=" + customContentString;
		// Log.d(TAG, notifyString);
		//
		// // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
		// if (!TextUtils.isEmpty(customContentString)) {
		// JSONObject customJson = null;
		// try {
		// customJson = new JSONObject(customContentString);
		// String myvalue = null;
		// if (!customJson.isNull("mykey")) {
		// myvalue = customJson.getString("mykey");
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		// updateContent(context, notifyString);
	}

	/**
	 * 接收通知到达的函数。
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            推送的通知的标题
	 * @param description
	 *            推送的通知的描述
	 * @param customContentString
	 *            自定义内容，为空或者json字符串
	 */

	@Override
	public void onNotificationArrived(Context context, String title,
			String description, String customContentString) {

		String notifyString = "onNotificationArrived  title=\"" + title
				+ "\" description=\"" + description + "\" customContent="
				+ customContentString;
		Log.d(TAG, notifyString);

		// 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
		// if (!TextUtils.isEmpty(customContentString)) {
		// JSONObject customJson = null;
		// try {
		// customJson = new JSONObject(customContentString);
		// String myvalue = null;
		// if (!customJson.isNull("mykey")) {
		// myvalue = customJson.getString("mykey");
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		// 你可以參考 onNotificationClicked中的提示从自定义内容获取具体值
		// updateContent(context, notifyString);
	}

	/**
	 * setTags() 的回调函数。
	 * 
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
	 * @param successTags
	 *            设置成功的tag
	 * @param failTags
	 *            设置失败的tag
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onSetTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
		String responseString = "onSetTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		// updateContent(context, responseString);
	}

	/**
	 * delTags() 的回调函数。
	 * 
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
	 * @param successTags
	 *            成功删除的tag
	 * @param failTags
	 *            删除失败的tag
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onDelTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
		String responseString = "onDelTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		// updateContent(context, responseString);
	}

	/**
	 * listTags() 的回调函数。
	 * 
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示列举tag成功；非0表示失败。
	 * @param tags
	 *            当前应用设置的所有tag。
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onListTags(Context context, int errorCode, List<String> tags,
			String requestId) {
		String responseString = "onListTags errorCode=" + errorCode + " tags="
				+ tags;
		Log.d(TAG, responseString);

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		// updateContent(context, responseString);
	}

	/**
	 * PushManager.stopWork() 的回调函数。
	 * 
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示从云推送解绑定成功；非0表示失败。
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
		String responseString = "onUnbind errorCode=" + errorCode
				+ " requestId = " + requestId;
		Log.d(TAG, responseString);

		if (errorCode == 0) {
			// 解绑定成功
		}
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		// updateContent(context, responseString);
	}

	private void updateContent(Context context, String content) {
		if (TextUtils.isEmpty(content)) {
			return;
		}
		Log.d(TAG, "updateContent");
		// String logText = "" + Utils.logStringCache;
		//
		// if (!logText.equals("")) {
		// logText += "\n";
		// }
		//
		// SimpleDateFormat sDateFormat = new SimpleDateFormat("HH-mm-ss");
		// logText += sDateFormat.format(new Date()) + ": ";
		// logText += content;
		//
		// Utils.logStringCache = logText;
		//
		// Intent intent = new Intent();
		// intent.setClass(context.getApplicationContext(), MainActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// context.getApplicationContext().startActivity(intent);

		// “k”:”标题”,
		// “v”:”内容”,
		// “u”:”用户id”,
		// “r”:”设备id”,
		// “d”:”添加时间（格式：yyyy-MM-dd HH:mm）”

		PushMessage msg = new PushMessage();
		System.out.println("updateContent:::" + content);
		try {
			JSONObject contentJson = new JSONObject(content);
			if (content.contains("r")) {
				msg.setId(contentJson.getJSONObject("description").getString(
						"r"));
				msg.setType("1");
				DeviceMessage mDeviceMessage = new DeviceMessage(
						contentJson.getJSONObject("description").getString("u"),
						contentJson.getJSONObject("description").getString("r"),
						contentJson.getJSONObject("description").getString("d"),
						contentJson.getString("title"), contentJson
								.getJSONObject("description").getString("v"),
						"0");

				MessageTool.getMessageTool(context).saveMessage(mDeviceMessage);

				msg.setMsg(MessageTool.getMessageTool(context).getMessage(
						mDeviceMessage));
				if (mDeviceMessage.getUser_id().equals(
						((AppContext) context.getApplicationContext()).user_id)) {
					NotificationManager manager = (NotificationManager) context
							.getSystemService(Context.NOTIFICATION_SERVICE);
					 Intent intent = new Intent();
					 intent.setClassName("com.midian.qualitycloud",
					 "com.midian.qualitycloud.ui.common.MyMessageActivity");
					PendingIntent pendingIntent = PendingIntent.getActivity(
							context, 0, intent, 0);
					Notification notification = new Notification();
					notification.icon = R.drawable.ic_launcher;
					notification.tickerText = "新的消息:您有新短消息，请注意查收！";
					notification.when = System.currentTimeMillis();
					notification.setLatestEventInfo(context, mDeviceMessage
							.getTitle(), String.format(
							"您关注的%s设备违规使用，请停止使用或者与相关单位联系",
							mDeviceMessage.getContent()), pendingIntent);
					notification.number = 1;
					notification.flags = Notification.FLAG_AUTO_CANCEL;
					manager.notify(NOTIFICATION_FLAG, notification);
				}

				System.out.println("MessageTool.getMessageTool");
				for (PushListener mPushListener : list) {
					if (mPushListener != null)
						mPushListener.updateContent(msg);
				}
			}
		} catch (JSONException e) {
			System.out.println("JSONException");
			e.printStackTrace();
		}

	}

	public interface PushListener {
		public void updateContent(PushMessage msg);
	}

}
