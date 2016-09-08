package com.midian.qualitycloud.ui.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.dialog.ConfirmDialog;
import midian.baselib.widget.dialog.ConfirmDialog1;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.ComplainReasonsBean;
import com.midian.qualitycloud.bean.FacilityReportsBean;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 
 * @author MIDIAN
 * 
 */
public class InformActivity extends BaseActivity {
	ListView lv;
	MyAdapter mMyAdapter;
	// int select = 0;
	BaseLibTopbarView mBaseLibTopbarView;
	int type;
	// HashSet<Integer> select = new HashSet<Integer>();
	Button submit;

	HashMap<Integer, String> select = new HashMap<Integer, String>();
	String facility_id;
	FacilityReportsBean mFacilityReportsBean;

	List<Item> list = new ArrayList<Item>();
	private String types;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inform);
		lv = (ListView) findViewById(R.id.listview);
		type = getIntent().getIntExtra("type", 0);
		facility_id = getIntent().getStringExtra("facility_id");
		types = getIntent().getStringExtra("types");
		System.out.println("-------"+types);
		submit = (Button) findViewById(R.id.submit);
		findViewById(R.id.submit).setOnClickListener(this);
		mMyAdapter = new MyAdapter();
		lv.setAdapter(mMyAdapter);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		if (type == 1) {
			mBaseLibTopbarView
					.setTitle("投诉")
					.setLeftText(R.string.back, null)
					.setLeftImageButton(R.drawable.icon_back,
							UIHelper.finish(_activity));
			submit.setText("提交");
			AppUtil.getQualityCloudApiClient(ac).getComplainRaeson(types,this);
		} else {
			mBaseLibTopbarView
					.setTitle("违法原因")
					.setLeftText(R.string.back, null)
					.setLeftImageButton(R.drawable.icon_back,
							UIHelper.finish(_activity));

			submit.setText("举报");

			AppUtil.getQualityCloudApiClient(ac).getFacilityReports(
					facility_id, this);
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.submit:
			if (type == 1) {
				if (select.isEmpty()) {
					UIHelper.t(_activity, "至少选择一条投诉原因");
					return;
				}
				String reason = "";

				int i = 0;
				for (String id : select.values()) {
					if (i == 0) {
						reason = id;
					} else {
						reason = reason + "," + id;
					}
					i++;
				}
				AppUtil.getQualityCloudApiClient(ac).postComplain(facility_id,
						reason, this);
			} else {
				AppUtil.getQualityCloudApiClient(ac)
						.postComplainReportComplain(facility_id, this);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		// TODO Auto-generated method stub
		super.onApiSuccess(res, tag);
		if (res.isOK()) {
			if ("getFacilityReports".equals(tag)) {
				mFacilityReportsBean = (FacilityReportsBean) res;
				list.clear();
				for (FacilityReportsBean.Content item : mFacilityReportsBean
						.getContent()) {

					list.add(new Item(item.getReason_id(), item.getReason()));
				}
				mMyAdapter.notifyDataSetChanged();

			} else if ("postComplainReportComplain".equals(tag)
					||"postComplain".equals(tag)) {
				dialog();
				
			} else if ("getComplainRaeson".equals(tag)) {

				ComplainReasonsBean mComplainReasonsBean = (ComplainReasonsBean) res;
				list.clear();
				for (ComplainReasonsBean.Content item : mComplainReasonsBean
						.getContent()) {

					list.add(new Item(item.getReason_id(), item
							.getReason_name()));
				}
				mMyAdapter.notifyDataSetChanged();
			}
			// else if ("postComplain".equals(tag)) {
			// UIHelper.t(_activity, "感谢您的监督！");
			// finish();
			// }
		} else {

			if ("postComplainReportComplain".equals(tag)
					|| "postComplain".equals(tag)) {
				UIHelper.t(_activity, "提交失败");
			} else {

				ac.handleErrorCode(_activity, res.error_code);
			}
		}

	}

	class Item {
		public Item() {
		}

		public Item(String id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		public String id;
		public String name;
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (list == null)
				return 0;
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder mViewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(_activity).inflate(
						R.layout.item_inform, null);
				mViewHolder = new ViewHolder();
				mViewHolder.init(convertView);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			if (type == 1) {
				mViewHolder.select.setVisibility(View.VISIBLE);
				if (select.containsKey(position)) {
					mViewHolder.select.setImageResource(R.drawable.item_select);
				} else {
					mViewHolder.select.setImageResource(R.drawable.unselect);
				}
			} else {
				mViewHolder.select.setVisibility(View.GONE);
			}
			mViewHolder.name.setText(list.get(position).name);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (type == 2)
						return;
					if (select.containsKey(position)) {
						select.remove(position);
					} else {
						select.put(position, list.get(position).id);
					}
					notifyDataSetChanged();
				}
			});
			return convertView;
		}

		class ViewHolder {
			public TextView name;
			public ImageView select;

			public void init(View convertView) {
				name = (TextView) convertView.findViewById(R.id.name);
				select = (ImageView) convertView.findViewById(R.id.select);
				convertView.setTag(this);
			}
		}
	}
	private void dialog(){  
		
		ConfirmDialog1.makeText(_activity, "提示:", "感谢您的监督！", "知道了", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 finish();
			}
		});
		// AlertDialog.Builder builder=new AlertDialog.Builder(this);
		// builder.setTitle("提示:");
		// builder.setMessage("感谢您的监督！");
		// builder.setNeutralButton("知道了", new DialogInterface.OnClickListener()
		// {//设置忽略按钮
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		// finish();
		// Toast.makeText(InformActivity.this, "感谢您的监督！",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		// builder.create().show();  
    }  
}
