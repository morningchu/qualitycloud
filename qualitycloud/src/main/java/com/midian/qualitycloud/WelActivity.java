package com.midian.qualitycloud;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;

import com.midian.qualitycloud.ui.main.MainActivity;

public class WelActivity extends BaseActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wel);
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		// 一秒
		AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1);
		alphaAnimation.setDuration(1000);
		alphaAnimation.setAnimationListener(aListener);
		iv.setAnimation(alphaAnimation);

	}

	private AnimationListener aListener = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation arg0) {
			button.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					UIHelper.jump(WelActivity.this, MainActivity.class);
					finish();
				}
			}, 4000);
		
		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
		}

		@Override
		public void onAnimationStart(Animation arg0) {
		}
	};
	private Button button;
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		arg0.setEnabled(false);
		UIHelper.jump(WelActivity.this, MainActivity.class);
		finish();
	}
}
