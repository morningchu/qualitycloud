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
package midian.baselib.shizhefei.view.listviewhelper.imp;

import midian.baselib.shizhefei.view.listviewhelper.ILoadViewFactory;
import midian.baselib.shizhefei.view.vary.VaryViewHelper;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.midian.baselib.R;

public class DeFaultLoadViewFactory implements ILoadViewFactory {

	@Override
	public ILoadMoreView madeLoadMoreView() {
		return new LoadMoreHelper();
	}

	@Override
	public ILoadView madeLoadView() {
		return new LoadViewHelper();
	}

	private class LoadMoreHelper implements ILoadMoreView {

		protected TextView footView;

		protected OnClickListener onClickRefreshListener;
		private Context context;
		@Override
		public void init(ListView listView, OnClickListener onClickRefreshListener) {
			footView = (TextView) LayoutInflater.from(listView.getContext()).inflate(R.layout.layout_listview_foot,
					listView, false);
			listView.addFooterView(footView);
			this.context = listView.getContext().getApplicationContext();
			this.onClickRefreshListener = onClickRefreshListener;
			showNormal();
		}

		@Override
		public void showNormal() {
			footView.setText(context.getString(R.string.onclick_load_more));
			footView.setOnClickListener(onClickRefreshListener);
		}

		@Override
		public void showLoading() {
			footView.setText(context.getString(R.string.loading));
			footView.setOnClickListener(null);
		}

		@Override
		public void showFail() {
			footView.setText(context.getString(R.string.onclick_reload_more));
			footView.setOnClickListener(onClickRefreshListener);
		}

		@Override
		public void showNomore() {
			footView.setVisibility(View.GONE);
			footView.setText(context.getString(R.string.load_finish));
			footView.setOnClickListener(null);
		}

	}

	private class LoadViewHelper implements ILoadView {
		private VaryViewHelper helper;
		private OnClickListener onClickRefreshListener;
		private Context context;

		@Override
		public void init(ListView mListView, OnClickListener onClickRefreshListener) {
			helper = new VaryViewHelper(mListView);
			this.context = mListView.getContext().getApplicationContext();
			this.onClickRefreshListener = onClickRefreshListener;
		}

		@Override
		public void restore() {
			helper.restoreView();
		}

		@Override
		public void showLoading() {
			View layout = helper.inflate(R.layout.load_ing);
			TextView textView = (TextView) layout.findViewById(R.id.textView1);
			textView.setText(context.getString(R.string.loading));
			helper.showLayout(layout);
		}

		@Override
		public void tipFail() {
			Toast.makeText(context,context.getString(R.string.loading_fail), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void showFail() {
			View layout = helper.inflate(R.layout.load_error);
			TextView textView = (TextView) layout.findViewById(R.id.textView1);
			textView.setText(context.getString(R.string.loading_fail));
			Button button = (Button) layout.findViewById(R.id.button1);
			button.setText(context.getString(R.string.try_again));
			button.setOnClickListener(onClickRefreshListener);
			helper.showLayout(layout);
		}

		@Override
		public void showEmpty() {
			View layout = helper.inflate(R.layout.load_empty);
			TextView textView = (TextView) layout.findViewById(R.id.textView1);
			textView.setText(context.getString(R.string.not_data));
			Button button = (Button) layout.findViewById(R.id.button1);
			button.setText(context.getString(R.string.try_again));
			button.setOnClickListener(onClickRefreshListener);
			helper.showLayout(layout);
		}

		@Override
		public void init(View mListView, OnClickListener onClickRefreshListener) {
			// TODO Auto-generated method stub
			helper = new VaryViewHelper(mListView);
			this.context = mListView.getContext().getApplicationContext();
			this.onClickRefreshListener = onClickRefreshListener;
		}

	}
}
