package midian.baselib.base;

import java.util.ArrayList;

import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.shizhefei.view.listviewhelper.ListViewHelper;
import midian.baselib.shizhefei.view.listviewhelper.OnStateChangeListener;
import midian.baselib.widget.pulltorefresh.PullToRefreshListView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.midian.baselib.R;

/**
 * 列表activity基类 Created by XuYang on 15/4/15.
 */
public abstract class BaseMultiTypeListActivity<Model extends NetResult>
		extends BaseActivity implements OnItemClickListener,
		OnStateChangeListener<ArrayList<Model>> {

	protected ListViewHelper<ArrayList<Model>> listViewHelper;

	protected PullToRefreshListView refreshListView;

	protected ListView listView;

	protected IDataSource<ArrayList<Model>> dataSource;

	protected ArrayList<Model> resultList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getLayoutId() == -1) {
			setContentView(R.layout.c_list_activity);
		} else {
			setContentView(getLayoutId());
		}
		refreshListView = findView(R.id.pullToRefreshListView);
		listViewHelper = new ListViewHelper<ArrayList<Model>>(refreshListView);
		// 设置数据源
		dataSource = getDataSource();
		listViewHelper.setDataSource(this.dataSource);
		listView = refreshListView.getRefreshableView();
		listView.setOnItemClickListener(this);
		resultList = dataSource.getResultList();
		// 设置适配器
		listViewHelper.setAdapter(new BaseMultiTypeListAdapter(listView, this,
				resultList, getTemplateClasses(), listViewHelper));
		listViewHelper.setOnStateChangeListener(this);
		listViewHelper.refresh();
	}


	/**
	 * 默认
	 * 
	 * @return
	 */
	protected final int getDefaultLayoutId() {
		return -1;
	}

	protected int getLayoutId() {
		return getDefaultLayoutId();
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	protected abstract IDataSource<ArrayList<Model>> getDataSource();

	/**
	 * 获取条目模板
	 * 
	 * @return
	 */
	protected abstract ArrayList<Class> getTemplateClasses();

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 释放资源
		listViewHelper.destory();
	}

	public void refresh() {
		listViewHelper.doPullRefreshing(true, 0);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onStartRefresh(IDataAdapter<ArrayList<Model>> adapter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndRefresh(IDataAdapter<ArrayList<Model>> adapter,
			ArrayList<Model> result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartLoadMore(IDataAdapter<ArrayList<Model>> adapter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndLoadMore(IDataAdapter<ArrayList<Model>> adapter,
			ArrayList<Model> result) {
		// TODO Auto-generated method stub

	}
}
