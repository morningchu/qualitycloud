package com.apppackage.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.midian.UMengUtils.Constant;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivityTMp extends Activity implements
		IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	static public String TICKET_TYPE = "TicketType";
	static public String TYPE = "Type";
	private IWXAPI api;
	String travel_id = "";
	String ticket_id = "";
	String scenery_id = "";
	String type = "";

	static public void gotoActivity(Context mContext, String type,
			String scenery_id, String travel_id, String ticket_id) {
		Intent intent = new Intent(mContext, WXPayEntryActivityTMp.class);
		intent.putExtra("flag", true);
		intent.putExtra(TYPE, type);
		intent.putExtra("scenery_id", scenery_id);
		intent.putExtra("travel_id", travel_id);
		intent.putExtra("ticket_id", ticket_id);
		mContext.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = new View(this);
		view.setBackgroundColor(getResources().getColor(
				android.R.color.transparent));
		setContentView(view);
		api = WXAPIFactory.createWXAPI(this, Constant.weixinAppId, false);
		api.handleIntent(getIntent(), this);
		boolean flag = getIntent().getBooleanExtra("flag", false);
		type = getIntent().getStringExtra(TYPE);
		if (flag) {
			weixinPay();
		}
	}

	private void weixinPay() {
		// AjaxParams params = new AjaxParams();
		// params.put("user_id", SaveUserUtil.getInstance(this).getUserId());
		// params.put("access_token",
		// SaveUserUtil.getInstance(this).getToken());
		//
		// if (DrawnMapManager.getInstance(this).getSame_scenic_ticket() == null
		// || FDValidateUtil.isEmptyString(DrawnMapManager
		// .getInstance(this).getSame_scenic_ticket()
		// .getTicket_id())) {// 单景点激活
		// params.put("scenic_id", DrawnMapManager.getInstance(this).getId());
		// } else {// 绑定景点激活
		// params.put("ticket_id", DrawnMapManager.getInstance(this)
		// .getSame_scenic_ticket().getTicket_id());
		// params.put("scenic_id", DrawnMapManager.getInstance(this)
		// .getSame_scenic_ticket().getScenic_ids());
		// }
		// NetFactory.post(this, Api.WX_PAY_CODE.api, params,
		// new NetCallBack<String>() {
		//
		// @Override
		// public void onSuccess(String t) {
		// super.onSuccess(t);
		// String ret = FDJsonUtil.getString(t, "ret");
		// if ("success".equals(ret)) {
		// String content = FDJsonUtil.getString(t, "content");
		//
		// PayReq req = new PayReq();
		// req.appId = FDJsonUtil.getString(content, "appid");
		// req.partnerId = FDJsonUtil.getString(content,
		// "partnerid");
		// req.prepayId = FDJsonUtil.getString(content,
		// "prepayid");
		// req.nonceStr = FDJsonUtil.getString(content,
		// "noncestr");
		// req.timeStamp = FDJsonUtil.getString(content,
		// "timestamp");
		// req.packageValue = FDJsonUtil.getString(content,
		// "package");
		// req.sign = FDJsonUtil.getString(content, "sign");
		// api.sendReq(req);
		// finish();
		// } else {
		// fail();
		// }
		// }
		//
		// @Override
		// public void onFailed(String t) {
		// // super.onFailed(t);
		// fail();
		// }

		// });
	}

	private void pay() {// 订单接口返回调用此方法
		// PayReq req = new PayReq();
		// req.appId = FDJsonUtil.getString(content, "appid");
		// req.partnerId = FDJsonUtil.getString(content,
		// "partnerid");
		// req.prepayId = FDJsonUtil.getString(content,
		// "prepayid");
		// req.nonceStr = FDJsonUtil.getString(content,
		// "noncestr");
		// req.timeStamp = FDJsonUtil.getString(content,
		// "timestamp");
		// req.packageValue = FDJsonUtil.getString(content,
		// "package");
		// req.sign = FDJsonUtil.getString(content, "sign");
		// api.sendReq(req);
		// finish();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	private void fail() {
		// if (TICKET_TYPE.equals(type)) {
		// TipActivity.gotoActivity(getContext(), TipActivity.TYPE_PAY_FAIL);
		// } else {
		// FDToastUtil.show(getApplicationContext(),
		// getString(R.string.pay_fail));
		// new MMapDialog(WXPayEntryActivity.this).show(
		// MMapDialogType.activate, new ActivateCallBack() {
		//
		// @Override
		// public void complete(boolean stat) {
		// if (stat) {
		// FDToastUtil.show(getApplicationContext(),
		// getString(R.string.activate_success));
		// } else {
		// FDToastUtil.show(getApplicationContext(),
		// getString(R.string.activate_fail));
		// }
		//
		// }
		//
		// @Override
		// public void cancel() {
		// finish();
		// }
		// });
		// }
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (resp.errCode == BaseResp.ErrCode.ERR_OK) {// 支付成功

			} else {
				fail();
			}
		}
		finish();
	}

}