package midian.baselib.base;

import midian.baselib.app.AppContext;
import midian.baselib.utils.UMengStatistticUtil;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Fragment基类
 */
public class BaseFragment extends Fragment {
	protected AppContext ac;
	protected BaseFragmentActivity _activity;
	protected Fragment _fragment;
	protected FragmentManager fm;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		_activity = (BaseFragmentActivity) activity;
		ac = (AppContext) _activity.getApplication();
		fm = getChildFragmentManager();
		_fragment = this;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		UMengStatistticUtil.onResumeForFragment(this.getActivity());

	}

	@Override
	public void onPause() {
		super.onPause();
		UMengStatistticUtil.onPauseForFragment(this.getActivity());
	}

}
