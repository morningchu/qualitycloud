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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.midian.baselib.R;

/**
 * Activity基类
 * 
 * @author XuYang
 * 
 */
public class BaseFragmentActivity extends FragmentActivity implements
		ApiCallback {

	protected AppContext ac;
	protected FragmentManager fm;

	protected Intent _Intent;
	protected Bundle mBundle;
	protected Activity _activity;

	protected LoadingDialog dlg;

	public Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);

		_activity = this;
		ac = (AppContext) getApplication();
		fm = getSupportFragmentManager();
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
		UMengStatistticUtil.onResumeForFragmentActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		UMengStatistticUtil.onPauseForFragmentActivity(this);
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

	/**
	 * 需要显示FragmentContentId
	 * 
	 * @return
	 */
	public int getFragmentContentId() {
		return -1;
	}

	/**
	 * 切换显示Fragment
	 * 
	 * @param to
	 */
	public void switchFragment(Fragment to) {
		FragmentTransaction mTransaction = fm.beginTransaction();
		if (getFragmentContentId() == -1 || to == null)
			return;
		if (mFragment == null)
			mFragment = new Fragment();
		Fragment from = mFragment;
		if (mFragment != to) {
			mFragment = to;
			System.out.println("to.isAdded()" + to.isAdded());
			if (to.isAdded()) {
				mTransaction.hide(from).show(to).commit();
			} else {
				mTransaction.hide(from).add(getFragmentContentId(), to)
						.commit();
			}

		}
	}

}
