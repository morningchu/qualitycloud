package com.pdfread.standardreader.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hor.common.StringUtils;
import com.pdfread.standardreader.R;
import com.pdfread.standardreader.adapter.RelativeStandardAdapter;
import com.pdfread.standardreader.bean.RelativeSearchData;
import com.pdfread.standardreader.contants.Contants;
import com.pdfread.standardreader.net.AsyncHttpClient;
import com.pdfread.standardreader.net.AsyncHttpResponseHandler;
import com.pdfread.standardreader.utils.Loading;
import com.pdfread.standardreader.views.XListView;
import com.pdfread.standardreader.views.XListView.IXListViewListener;

public class RelativeStandardFragment extends Fragment implements OnItemClickListener,OnClickListener{
	
	private XListView listView;
	protected int current_page;
	protected boolean isLoading = false;
	
	private RelativeStandardAdapter adapter;
	
	private ArrayList<RelativeSearchData> datas=new ArrayList<RelativeSearchData>();
	
	private AsyncHttpClient client;
	private String key;
	private TextView tvNum;
	
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context=getActivity();
		
		key=getArguments().getString("key");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout, null);
		
		tvNum = (TextView) view.findViewById(R.id.localnum);
		
		listView = (XListView) view.findViewById(R.id.listViewsearch);
		listView.setFooterDividersEnabled(false);
		listView.setHeaderDividersEnabled(false);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(false);
		listView.setOnItemClickListener(RelativeStandardFragment.this);
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				current_page = 1;
				datas.clear();
				listView.setPullLoadEnable(false);
				initData(current_page);
			}

			@Override
			public void onLoadMore() {
				if (isLoading) {
					return;
				}
				isLoading = true;
				current_page++;
				initData(current_page);
			}
		});
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		client = new AsyncHttpClient(getActivity());
		
		initData(1);
		
	}

	private void initData(final int page) {
		client.get(context, String.format(Contants.STANDARD_SEARCH, key,1,page),
				new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				if (!StringUtils.isEmpty(content)){
					Loading.loadFinish(listView, context);
					isLoading = false;
					try {
						
						JSONObject obj=new JSONObject(content);
						
						String keyWords=obj.getString("keyWords");
						
						int totalRecord=obj.getInt("totalRecord1");
						
						JSONArray array=obj.getJSONArray("result1");
						
						List<RelativeSearchData> list= JSON.parseArray(array.toString(), RelativeSearchData.class);
						
						tvNum.setText("> 共有"+totalRecord+"条相关标准\n\u3000获取方式\n\u3000" +
								"地址：贵州省贵阳市头桥海马冲街111号\n\u3000电话：0851-86511472");
						
						if(list!=null && list.size()>0){
							RelativeSearchData sd;
							for(int i=0;i<list.size();i++){
								
								sd=new RelativeSearchData();
								sd.setCurrent1(list.get(i).getCurrent1());
								sd.setId(list.get(i).getId());
								sd.setMaterialDate(list.get(i).getMaterialDate());
								sd.setFileTitle(list.get(i).getFileTitle());
								sd.setStandardNumber(list.get(i).getStandardNumber());
								sd.setStandardStateItemName(list.get(i).getStandardStateItemName());
								sd.setTotal1(list.get(i).getTotal1());
								
								datas.add(sd);
								
							}
							
							if (page == 1) {
								adapter=new RelativeStandardAdapter(getActivity(), datas);
								listView.setAdapter(adapter);
							} else {
								if (adapter != null) {
									adapter.notifyDataSetChanged();
								}
							}
							
							if (datas.size() < 10) {
								listView.setPullLoadEnable(false);
							} else {
								listView.setPullLoadEnable(true);
							}
						} else {
							if (page == 1) {
								//LoadingDialog.finishLoading();
								listView.setAdapter(null);
							}
							listView.setPullLoadEnable(false);
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				Loading.loadFinish(listView, context);
				isLoading = false;
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		final Dialog dialog = new Dialog(context, R.style.Common_Dialog);
		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_relativebuy_layout, null);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		dialog.getWindow().setContentView(view);
		
		TextView tvContent=(TextView) view.findViewById(R.id.tv_relative_buy);
		
		tvContent.setText("\u3000地址：贵州省贵阳市头桥海马冲街111号\n\u3000电话：0851-86511472");
		
		view.findViewById(R.id.btn_buy_close).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
	}

}
