package midian.baselib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Window;
import android.widget.EditText;

import com.midian.baselib.R;


public class BaseDialog extends Dialog {

	public BaseDialog(Context context) {
		super(context);
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
	}

	
	
	public BaseDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 弹出动画 et不为空时弹出输入法
	 * 
	 * @param et
	 */
	public void showAmin(final EditText et) {
		Window dialogWindow = getWindow();
		dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);

		if(et==null)
			return;
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// 弹出软键盘
//				ViewUtil.showInputMethod(et);
			}
		}, 400);
	}

}
