/*
Copyright 2015 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package midian.baselib.shizhefei.view.listviewhelper;

import midian.baselib.shizhefei.utils.NetworkUtils;
import midian.baselib.shizhefei.view.listviewhelper.ILoadViewFactory.ILoadMoreView;
import midian.baselib.shizhefei.view.listviewhelper.ILoadViewFactory.ILoadView;
import midian.baselib.shizhefei.view.listviewhelper.imp.DeFaultLoadViewFactory;
import midian.baselib.widget.pulltorefresh.PullToRefreshBase;
import midian.baselib.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import midian.baselib.widget.pulltorefresh.PullToRefreshListView;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

/**
 * <h1>下拉刷新，上滑加载更多的控件的辅助类</h1><br>
 * <br>
 * 刷新，加载更多规则<br>
 * 当用户下拉刷新时，会取消掉当前的刷新，以及加载更多的任务<br>
 * 当用户加载更多的时候，如果有已经正在刷新或加载更多是不会再执行加载更多的操作。<br>
 * <br>
 * 注意:记得在Activity的Ondestroy方法调用destory <br>
 * 要添加 android.permission.ACCESS_NETWORK_STATE 权限，这个用来检测是否有网络
 * 
 * @author LuckyJayce
 * 
 * @param <DATA>
 */
@SuppressLint("InlinedApi")
public class ListViewHelper<DATA> implements OnRefreshListener<ListView> {
	private IDataAdapter<DATA> dataAdapter;
	private PullToRefreshListView plv;
	private IDataSource<DATA> dataSource;
	private ListView mListView;
	private Context context;
	private OnStateChangeListener<DATA> onStateChangeListener;
	private AsyncTask<Void, Void, DATA> asyncTask;
	private long loadDataTime = -1;
	/**
	 * 是否还有更多数据。如果服务器返回的数据为空的话，就说明没有更多数据了，也就没必要自动加载更多数据
	 */
	private boolean hasMoreData = true;
	public ILoadView mLoadView;
	private ILoadMoreView mLoadMoreView;
	public static ILoadViewFactory loadViewFactory = new DeFaultLoadViewFactory();

	public ListViewHelper(PullToRefreshListView pullToRefreshAdapterViewBase) {
		this(pullToRefreshAdapterViewBase, loadViewFactory.madeLoadView(),
				loadViewFactory.madeLoadMoreView());
	}

	@SuppressLint("NewApi")
	public ListViewHelper(PullToRefreshListView pullToRefreshAdapterViewBase,
			ILoadView loadView, ILoadMoreView loadMoreView) {
		super();
		this.context = pullToRefreshAdapterViewBase.getContext()
				.getApplicationContext();
		this.autoLoadMore = true;
		this.plv = pullToRefreshAdapterViewBase;
		mListView = plv.getRefreshableView();
		mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		mListView.setCacheColorHint(Color.TRANSPARENT);
		plv.setScrollLoadEnabled(true);
		plv.setOnRefreshListener(this);
		mListView.setOnScrollListener(onScrollListener);
		mListView.setOnItemSelectedListener(onItemSelectedListener);
		mLoadView = loadView;
		mLoadView.init(mListView, onClickRefresListener);
		mLoadMoreView = loadMoreView;
		mLoadMoreView.init(mListView, onClickLoadMoreListener);
	}

	/**
	 * 设置LoadView的factory，用于创建使用者自定义的加载失败，加载中，加载更多等布局
	 * 
	 * @param fractory
	 */
	public static void setLoadViewFractory(ILoadViewFactory fractory) {
		loadViewFactory = fractory;
	}

	public void setOnClickCustomRefresListener(OnClickListener onClickRefresListener) {
		this.onClickCustomRefresListener = onClickRefresListener;
	}

	/**
	 * 设置数据源，用于加载数据
	 * 
	 * @param dataSource
	 */
	public void setDataSource(IDataSource<DATA> dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 设置适配器，用于显示数据
	 * 
	 * @param adapter
	 */
	public void setAdapter(IDataAdapter<DATA> adapter) {
		mListView.setAdapter(adapter);
		this.dataAdapter = adapter;
	}

	/**
	 * 设置状态监听，监听开始刷新，刷新成功，开始加载更多，加载更多成功
	 * 
	 * @param onStateChangeListener
	 */
	public void setOnStateChangeListener(
			OnStateChangeListener<DATA> onStateChangeListener) {
		this.onStateChangeListener = onStateChangeListener;
	}

	/**
	 * 刷新，开启异步线程，并且显示加载中的界面，当数据加载完成自动还原成加载完成的布局，并且刷新列表数据
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void refresh() {
		plv.restoreView();
		if (dataAdapter == null || dataSource == null) {
			if (plv != null) {
				plv.onPullUpRefreshComplete();
			}
			return;
		}
		if (asyncTask != null
				&& asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
			asyncTask.cancel(true);
		}
		asyncTask = new AsyncTask<Void, Void, DATA>() {
			protected void onPreExecute() {
				mLoadMoreView.showNormal();
				if (dataAdapter.isEmpty()) {
					mLoadView.showLoading();
					plv.onPullUpRefreshComplete();
				} else {
					mLoadView.restore();
				}
				if (onStateChangeListener != null) {
					onStateChangeListener.onStartRefresh(dataAdapter);
				}
			};

			@Override
			protected DATA doInBackground(Void... params) {
				try {
					return dataSource.refresh();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(DATA result) {
				if (result == null) {
					if (dataAdapter.isEmpty()) {
						mLoadView.showFail();
					} else {
						mLoadView.tipFail();
					}
				} else {
					loadDataTime = System.currentTimeMillis();
					dataAdapter.setData(result, true);
					dataAdapter.notifyDataSetChanged();
					if (dataAdapter.isEmpty()) {
						mLoadView.showEmpty();
					} else {
						mLoadView.restore();
						plv.getRefreshableView().setSelection(0);
					}
					hasMoreData = dataSource.hasMore();
					if (hasMoreData) {
						mLoadMoreView.showNormal();
					} else {
						mLoadMoreView.showNomore();
					}
				}
				if (onStateChangeListener != null) {
					onStateChangeListener.onEndRefresh(dataAdapter, result);
				}

				plv.onPullDownRefreshComplete();
				plv.onPullUpRefreshComplete();
			};

		};
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			asyncTask.execute();
		}
	}

	/**
	 * 加载更多，开启异步线程，并且显示加载中的界面，当数据加载完成自动还原成加载完成的布局，并且刷新列表数据
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void loadMore() {
		if (isLoading()) {
			return;
		}
		if (dataAdapter.isEmpty()) {
			refresh();
			return;
		}

		if (dataAdapter == null || dataSource == null) {
			if (plv != null) {
				plv.onPullUpRefreshComplete();
			}
			return;
		}
		if (asyncTask != null
				&& asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
			asyncTask.cancel(true);
		}
		asyncTask = new AsyncTask<Void, Void, DATA>() {
			protected void onPreExecute() {
				if (onStateChangeListener != null) {
					onStateChangeListener.onStartLoadMore(dataAdapter);
				}
				mLoadMoreView.showLoading();
			}

			@Override
			protected DATA doInBackground(Void... params) {
				try {
					return dataSource.loadMore();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(DATA result) {
				if (result == null) {
					mLoadView.tipFail();
					mLoadMoreView.showFail();
				} else {
					dataAdapter.setData(result, false);
					dataAdapter.notifyDataSetChanged();
					if (dataAdapter.isEmpty()) {
						mLoadView.showEmpty();
					} else {
						mLoadView.restore();
					}
					hasMoreData = dataSource.hasMore();
					if (hasMoreData) {
						mLoadMoreView.showNormal();
					} else {
						mLoadMoreView.showNomore();
					}
				}
				if (onStateChangeListener != null) {
					onStateChangeListener.onEndLoadMore(dataAdapter, result);
				}
			};
		};
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			asyncTask.execute();
		}
	}

	/**
	 * 做销毁操作，比如关闭正在加载数据的异步线程等
	 */
	public void destory() {
		if (asyncTask != null
				&& asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
			asyncTask.cancel(true);
			asyncTask = null;
		}
	}

	/**
	 * 是否正在加载中
	 * 
	 * @return
	 */
	public boolean isLoading() {
		return asyncTask != null
				&& asyncTask.getStatus() != AsyncTask.Status.FINISHED;
	}

	public ListView getListView() {
		return plv.getRefreshableView();
	}

	/**
	 * 获取上次刷新数据的时间（数据成功的加载），如果数据没有加载成功过，那么返回-1
	 * 
	 * @return
	 */
	public long getLoadDataTime() {
		return loadDataTime;
	}

	public OnStateChangeListener<DATA> getOnStateChangeListener() {
		return onStateChangeListener;
	}

	public IDataAdapter<DATA> getAdapter() {
		return dataAdapter;
	}

	public IDataSource<DATA> getDataSource() {
		return dataSource;
	}

	public PullToRefreshListView getPullToRefreshAdapterViewBase() {
		return plv;
	}

	public ILoadView getLoadView() {
		return mLoadView;
	}

	public ILoadMoreView getLoadMoreView() {
		return mLoadMoreView;
	}

	public void setAutoLoadMore(boolean autoLoadMore) {
		this.autoLoadMore = autoLoadMore;
		if (!isLoading()) {
			mLoadMoreView.showNormal();
		}
	}

	private boolean autoLoadMore = true;

	public boolean isAutoLoadMore() {
		return autoLoadMore;
	}

	private OnClickListener onClickLoadMoreListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			loadMore();
		}
	};

	OnClickListener onClickCustomRefresListener;
	private OnClickListener onClickRefresListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			System.out.println("onClick");
			if (onClickCustomRefresListener == null)
				refresh();
			else {
				onClickCustomRefresListener.onClick(v);
			}
		}
	};
	/**
	 * 滚动到底部自动加载更多数据
	 */
	private OnScrollListener onScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView listView, int scrollState) {
			if (autoLoadMore) {
				if (hasMoreData) {
					if (!plv.isPullRefreshing()) {// 如果不是刷新状态

						if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
								&& listView.getLastVisiblePosition() + 1 == listView
										.getCount()) {// 如果滚动到最后一行

							// 如果网络可以用

							if (NetworkUtils.hasNetwork(context)) {
								loadMore();
							} else {
								if (!isLoading()) {
									mLoadMoreView.showFail();
								}
							}
						}
					}
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	};
	/**
	 * 针对于电视 选择到了底部项的时候自动加载更多数据
	 */
	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> listView, View view,
				int position, long id) {
			if (autoLoadMore) {
				if (hasMoreData) {
					if (listView.getLastVisiblePosition() + 1 == listView
							.getCount()) {// 如果滚动到最后一行
						// 如果网络可以用
						if (NetworkUtils.hasNetwork(context)) {
							loadMore();
						}
					}
				}
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	};

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		refresh();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		loadMore();
	}

	public void doPullRefreshing(final boolean smoothScroll,
			final long delayMillis) {
		plv.doPullRefreshing(smoothScroll, delayMillis);
	}

}