package com.pdfread.standardreader.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.pdfread.standardreader.adapter.SearchAdapter;
import com.pdfread.standardreader.bean.StandardData;
import com.pdfread.standardreader.contants.Contants;
import com.pdfread.standardreader.net.AsyncHttpClient;
import com.pdfread.standardreader.net.AsyncHttpResponseHandler;
import com.pdfread.standardreader.utils.Loading;
import com.pdfread.standardreader.views.XListView;
import com.pdfread.standardreader.views.XListView.IXListViewListener;

public class LocalStandardFragment extends Fragment implements OnItemClickListener,OnClickListener{
	
	private XListView listView;
	protected int current_page;
	protected boolean isLoading = false;
	
	private SearchAdapter adapter;
	
	private ArrayList<StandardData> datas=new ArrayList<StandardData>();
	
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
		listView.setOnItemClickListener(LocalStandardFragment.this);
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
		
		client.get(context, String.format(Contants.STANDARD_SEARCH, key,page,1),
				new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				if (!StringUtils.isEmpty(content)){
					Loading.loadFinish(listView, context);
					isLoading = false;
					
					Log.i("info", "content="+content);
					
					try {
						JSONObject obj=new JSONObject(content);
						
						String keyWords=obj.getString("keyWords");
						
						int totalRecord=obj.getInt("totalRecord");
						
						JSONArray array=obj.getJSONArray("result");
						
						List<StandardData> list= JSON.parseArray(array.toString(), StandardData.class);
						
						tvNum.setText(">共找到"+totalRecord+"条贵州省地方标准");
						
						if(list!=null && list.size()>0){
							StandardData sd;
							for(int i=0;i<list.size();i++){
								
								sd=new StandardData();
								sd.setAbolishBasis(list.get(i).getAbolishBasis());
								sd.setFileTitle(list.get(i).getFileTitle());
								sd.setFileUrl(list.get(i).getFileUrl());
								sd.setGid(list.get(i).getGid());
								sd.setMaterialDate(list.get(i).getMaterialDate());
								sd.setStandardNumber(list.get(i).getStandardNumber());
								sd.setYearNumber(list.get(i).getYearNumber());
								sd.setStandardStateItemName(list.get(i).getStandardStateItemName());
								sd.setSubmitTime(list.get(i).getSubmitTime());
								sd.setItemName(list.get(i).getItemName());
								
								datas.add(sd);
								
							}
							
							if (page == 1) {
								adapter=new SearchAdapter(getActivity(), datas);
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
}
