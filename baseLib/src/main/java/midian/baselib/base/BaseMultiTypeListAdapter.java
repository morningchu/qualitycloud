package midian.baselib.base;

import java.util.ArrayList;

import midian.baselib.bean.NetBase;
import midian.baselib.shizhefei.view.listviewhelper.ListViewHelper;
import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

public class BaseMultiTypeListAdapter<Model> extends BaseListAdapter {

	private ArrayList<Class> itemViewClazzs;

	public BaseMultiTypeListAdapter(AbsListView absListView, Context context, ArrayList<Model> data,
			Class itemViewClazz, ListViewHelper<Model> listViewHelper) {
		super(absListView, context, data, itemViewClazz, listViewHelper);
	}

	public BaseMultiTypeListAdapter(AbsListView absListView, Context context, ArrayList<Model> data,
			ArrayList<Class> itemViewClazzs, ListViewHelper<Model> listViewHelper) {
		this(absListView, context, data, itemViewClazzs.get(0), listViewHelper);
		this.itemViewClazzs = itemViewClazzs;
	}

	@Override
	public int getViewTypeCount() {
		return itemViewClazzs.size();
	}

	@Override
	public int getItemViewType(int position) {
		NetBase baseModel = (NetBase) getItem(position);
		return baseModel.getItemViewType();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			try {
				convertView = (View) itemViewClazzs.get(getItemViewType(position)).getConstructor(Context.class)
						.newInstance(context);
				((BaseTpl) convertView).config(this, data, absListView, listViewHelper);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!(convertView instanceof BaseTpl)) {
			if (convertView.getClass().getName().equals("android.view.View")) {
			}
			return convertView;
		}
		BaseTpl view = (BaseTpl) convertView;
		view.setBean(getItem(position), position);
		return convertView;
	}
}
