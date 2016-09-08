package com.pdfread.standardreader.pdf;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hor.common.StringUtils;
import com.pdfread.standardreader.R;
import com.pdfread.standardreader.fragment.PdfFragment;
import com.pdfread.standardreader.utils.CalculateUtil;
import com.pdfread.standardreader.views.BaseLibTopbarView;

public class PDFReadActivity extends Activity implements OnClickListener{

	private static final String TAG = "SampleActivity";
    private static final String SAMPLE_FILE = "sample.pdf";
    private static final String FILE_PATH = "filepath";
    private static final String SEARCH_TEXT = "text";
    private PdfFragment fragment;
    private static Context context;
	private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readpdf_layout);
        context = PDFReadActivity.this;
        
        String path=getIntent().getStringExtra("path");
        title = getIntent().getStringExtra("title");
        
        intiView(title);
        
        openPdfWithFragment(path);

    }

    private void intiView(final String title) {
    	
    	BaseLibTopbarView topbar=(BaseLibTopbarView)findViewById(R.id.topbar_pdf);
    	
    	if(!StringUtils.isEmpty(title)){
    		
    		if(title.length()>6){
    			topbar.recovery().setTitle(title.substring(0, 6)+"...").setLeftText("返回", new OnClickListener() {
    				
    				@Override
    				public void onClick(View arg0) {
    					finish();
    				}
    			});
    		}else{
    			topbar.recovery().setTitle(title).setLeftText("返回", new OnClickListener() {
    				
    				@Override
    				public void onClick(View arg0) {
    					finish();
    				}
    			});
    		}
    		
    		topbar.setLeftImageButton(R.drawable.icon_back, new OnClickListener() {
    			
    			@Override
    			public void onClick(View arg0) {
    				finish();
    			}
    		});
    		
    		topbar.setRightImageButton(R.drawable.show_dialog, new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Dialog dialog = new Dialog(context, R.style.Common_Dialog);
					View view = LayoutInflater.from(context).inflate(
							R.layout.dialog_ds_layout, null);
					dialog.setCanceledOnTouchOutside(false);
					dialog.show();
					dialog.getWindow().setContentView(view);
					dsDialog(dialog, view ,title);
				}
			});
    		
    	}
    	
    	
    	View top_layout = findViewById(R.id.title_readpdf_layout);
		CalculateUtil.setTitleParams(top_layout);
		
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		
		if(!StringUtils.isEmpty(title)){
			if(title.length()>6){
				tv_title.setText(title.substring(0, 6)+"...");
			}else{
				tv_title.setText(title);
			}
		}
		
		TextView tv_left=(TextView) findViewById(R.id.tv_toleft);
		tv_left.setOnClickListener(this);
		
		CalculateUtil.calculateTextSize(tv_title, 28);
		
		ImageButton imgBtn=(ImageButton) findViewById(R.id.right_btn);
		
		CalculateUtil.calculateViewSize(imgBtn, 40, 38);
		
		imgBtn.setVisibility(View.VISIBLE);
		imgBtn.setOnClickListener(this);
		
	}

    public void openPdfWithFragment(String path) {
        fragment = new PdfFragment();
        Bundle args = new Bundle();
        args.putString(FILE_PATH, path);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.readpdf_frame, fragment).commit();
    }

	@Override
	public void onClick(View v) {
		int id=v.getId();
		if (id == R.id.tv_toleft) {
			finish();
		} else if (id == R.id.right_btn) {
			Dialog dialog = new Dialog(context, R.style.Common_Dialog);
			View view = LayoutInflater.from(context).inflate(
					R.layout.dialog_ds_layout, null);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			dialog.getWindow().setContentView(view);
			dsDialog(dialog, view ,title);
		} else {
			
		}
	}

	protected void dsDialog(final Dialog dialog, View view, final String titles) {
		TextView tvCode=(TextView) view.findViewById(R.id.txt_ds_title);
		
		Button btn_down = (Button) view.findViewById(R.id.btn_ds_down);
		Button btn_fx = (Button) view.findViewById(R.id.btn_ds_fx);
		
		CalculateUtil.calculateTextSize(btn_down, 35);
		CalculateUtil.calculateTextSize(btn_fx, 35);
		
		CalculateUtil.calculateTextSize(tvCode, 30);
		
		if(!StringUtils.isEmpty(titles)){
			tvCode.setText(titles);
		}
		
		btn_down.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				dialog.dismiss();
				
			}
		});
		
		btn_fx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				dialog.dismiss();
				
				showShareDialog();
				
			}
		});
		
		view.findViewById(R.id.btn_ds_close).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
			
		});
		
	}

	protected void showShareDialog() {
		// TODO Auto-generated method stub
		
		Dialog dialog = new Dialog(context, R.style.Common_Dialog);
		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_share_layout, null);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		dialog.getWindow().setContentView(view);
		
		shareDialog(dialog , view);
		
	}

	private void shareDialog(final Dialog dialog, View view) {
		// TODO Auto-generated method stub
		ImageButton btn_close = (ImageButton) view.findViewById(R.id.btn_share_close);
		
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		ImageView imgPyq = (ImageView) view.findViewById(R.id.img_pyq);
		ImageView imgWx = (ImageView) view.findViewById(R.id.img_wx);
		ImageView imgQq = (ImageView) view.findViewById(R.id.img_qq);
		ImageView imgKj = (ImageView) view.findViewById(R.id.img_kj);
		ImageView imgXin = (ImageView) view.findViewById(R.id.img_xinlang);
		ImageView imgDuan = (ImageView) view.findViewById(R.id.img_duanxin);
		
		CalculateUtil.calculateViewSize(imgPyq, 100, 100);
		CalculateUtil.calculateViewSize(imgWx, 100, 100);
		CalculateUtil.calculateViewSize(imgQq, 100, 100);
		CalculateUtil.calculateViewSize(imgKj, 100, 100);
		CalculateUtil.calculateViewSize(imgXin, 100, 100);
		CalculateUtil.calculateViewSize(imgDuan, 100, 100);
		
	}
	
}
