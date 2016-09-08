package midian.baselib.base;

import midian.baselib.api.ApiCallback;
import midian.baselib.app.AppContext;
import midian.baselib.app.AppManager;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.utils.UMengStatistticUtil;
import midian.baselib.widget.dialog.LoadingDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.midian.baselib.R;

/**
 * Activity基类
 * 
 * @author XuYang
 * 
 */
public class BaseActivity extends Activity implements ApiCallback,OnClickListener{

	protected AppContext ac;

	protected Intent _Intent;
	protected Bundle mBundle;
	protected Activity _activity;

	protected LoadingDialog dlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);

		_activity = this;
		ac = (AppContext) getApplication();
		_Intent = getIntent();
		if (_Intent != null) {
			mBundle = _Intent.getExtras();
		}
		

		
	}

	public void showLoadingDlg(String msg, final boolean isNotBackFinish) {
		if (dlg != null && dlg.isShowing()) {
			return;
		}
		if (dlg == null) {
			dlg = new LoadingDialog(this, R.layout.dialog_msg,
					R.style.dialog_msg);
		}
		dlg.setCanceledOnTouchOutside(isNotBackFinish);
		dlg.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				if (!isNotBackFinish) {
					finish();
				}
			}
		});
		dlg.showMessage(msg);
	}

	public void showLoadingDlg() {
		showLoadingDlg("", true);
	}

	public void hideLoadingDlg() {
		if (dlg != null) {
			dlg.dismiss();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
		if (dlg != null) {
			dlg.dismiss();
		}
	}

	/**
	 * 返回结果到上一个activity
	 * 
	 * @param resultCode
	 * @param bundle
	 */
	protected void setResult(int resultCode, Bundle bundle) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		setResult(resultCode, intent);
	}

	protected <T extends View> T findView(int id) {
		View child = findViewById(id);
		return (T) child;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UMengStatistticUtil.onResumeForActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		UMengStatistticUtil.onPauseForActivity(this);
	}

	@Override
	public void onApiStart(String tag) {
	}

	@Override
	public void onApiLoading(long count, long current, String tag) {

	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {

	}

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		t.printStackTrace();
		UIHelper.t(_activity, getString(R.string.net_error));
	}

	@Override
	public void onParseError(String tag) {
		UIHelper.t(_activity, getString(R.string.parser_error));
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}


}
