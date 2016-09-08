package midian.baselib.view;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListAdapter;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.ListViewHelper;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * 列表条目基础模板
 */
public abstract class BaseTpl<Model extends NetResult> extends LinearLayout {
	protected AppContext ac;
	protected Activity _activity;

	protected BaseListAdapter<Model> adapter;
	protected ArrayList<Model> data;
	protected AbsListView absListView;
	protected ListViewHelper<Model> listViewHelper;

	protected View root;

	public BaseTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BaseTpl(Context context) {
		super(context);
		init(context);
	}

	public void config(BaseListAdapter adapter, ArrayList<Model> data, AbsListView absListView,
			ListViewHelper<Model> listViewHelper) {
		this.adapter = adapter;
		this.data = data;
		this.absListView = absListView;
		this.listViewHelper = listViewHelper;
	}

	protected void init(Context context) {
		this._activity = (Activity) context;
		ac = (AppContext) context.getApplicationContext();

		root = View.inflate(context, getLayoutId(), null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(root, params);
		initView();
		
	}

	protected <T extends View> T findView(int id) {
		SparseArray<View> holder = new SparseArray<View>();
		if (holder == null) {
			holder = new SparseArray<View>();

		}
		View child = holder.get(id);
		if (child == null) {
			child = findViewById(id);
			holder.put(id, child);
		}
		return (T) child;
	}

	protected abstract void initView();

	protected abstract int getLayoutId();

	public abstract void setBean(Model bean, int position);

}
