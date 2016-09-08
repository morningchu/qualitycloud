package midian.baselib.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

public class CommonDialog extends BaseDialog {

	private Context context;

	private int id;

	private View mainView;

	public CommonDialog(Context context) {
		super(context);
	}

	public CommonDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	public void show() {

		super.show();
		Window bottomDialogWindow = getWindow();

		WindowManager.LayoutParams bottomDialoglp = bottomDialogWindow
				.getAttributes();

		bottomDialoglp.gravity = Gravity.BOTTOM;

		bottomDialoglp.width = LayoutParams.MATCH_PARENT; // 宽度
		bottomDialogWindow.setAttributes(bottomDialoglp);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(mainView);
	}

	public View setContentID(int id) {
		mainView = LayoutInflater.from(context).inflate(id, null);
		setContentView(mainView);
		return mainView;
	}

}
