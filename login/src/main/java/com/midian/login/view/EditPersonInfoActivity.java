package com.midian.login.view;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.midian.login.R;

/**
 * 编辑个人资料页面
 * 
 * @author chu
 * 
 */
public class EditPersonInfoActivity extends BaseActivity {

	private BaseLibTopbarView topbar;
	private EditText content_ed;
	private Button modifiyPhone_bt;

	private String title;
	private String content;
	// 完成事件
	private OnClickListener rightClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// 把获取到的输入内容返回前一页
			String content = content_ed.getText().toString().trim();
			Bundle bundle = new Bundle();
			bundle.putString("text", content);
			System.out.println("text::::" + content);
			setResult(RESULT_OK, bundle);
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_person_info);
		// Bundle mBundle=getIntent().getExtras();
		// 获取要编辑的标题、内容，显示在控件上
		title = mBundle.getString("title");
		content = mBundle.getString("content");
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		topbar.setTitle(title)
				.setLeftImageButton(R.drawable.icon_back, UIHelper.finish(this))
				.setRightText("完成", rightClickListener);
		content_ed = (EditText) findViewById(R.id.content_et);
		content_ed.setHint("请输入" + title);
		content_ed.setText(content);
		// 弹窗提示修改手机号
		// modifiyPhone_bt = (Button) findViewById(R.id.modifiy_phone);
		// 如果为isPhone,则只能输入数字
		// if (mBundle.getBoolean("isPhone")) {
		// content_ed.setInputType(InputType.TYPE_CLASS_PHONE);
		// modifiyPhone_bt.setVisibility(View.VISIBLE);
		// modifiyPhone_bt.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// VerificationDialog verificationDialog = new
		// VerificationDialog(_activity, "修改手机号成功", "知道了", null);
		// verificationDialog.show();
		// verificationDialog.setClicklistener(new VerificationDialogInterface()
		// {
		//
		// @Override
		// public void doConfirm(String password) {
		// System.out.println("password:::::::" + password);
		// UIHelper.t(_activity, "验证");
		// }
		//
		// @Override
		// public void doCancel() {
		// // TODO Auto-generated method stub
		// UIHelper.t(_activity, "取消");
		// finish();
		// }
		// });
		// }
		// });
		//
		// }

		// 设置限制输入的数字长度
		content_ed.setSelection(content.length());
		content_ed.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				11) });

	}

}
