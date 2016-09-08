package midian.baselib.base;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import android.content.Context;

public abstract class BaseListDataSource<Model extends NetResult> implements
		IDataSource<ArrayList<Model>> {

	protected AppContext ac;
	protected Context context;

	protected int page = 0;
	protected boolean hasMore = true;
public boolean isCloseTip=false;
	protected ArrayList<Model> data = new ArrayList<Model>();

	public BaseListDataSource(Context context) {
		this.context = context;
		this.ac = (AppContext) context.getApplicationContext();
	}

	@Override
	public ArrayList<Model> refresh() throws Exception {
		return load(0);
	}

	@Override
	public ArrayList<Model> loadMore() throws Exception {
		return load(page + 1);
	}

	@Override
	public boolean hasMore() {
		return hasMore;
	}

	public void onLoading() {

	}

	public void onError() {

	}

	public void onEmpty() {

	}

	@Override
	public ArrayList<Model> getResultList() {
		return data;
	}

	protected abstract ArrayList<Model> load(int page) throws Exception;

}
