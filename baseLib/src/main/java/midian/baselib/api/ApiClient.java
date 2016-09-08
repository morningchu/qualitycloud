package midian.baselib.api;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Func;
import android.util.Log;

import com.midian.fastdevelop.afinal.FinalHttp;
import com.midian.fastdevelop.afinal.http.AjaxCallBack;
import com.midian.fastdevelop.afinal.http.AjaxParams;
import com.midian.fastdevelop.afinal.http.HttpHandler;

/**
 * http网络请求客户端
 * 
 * @author moshouguan
 * 
 */
public class ApiClient {

	private FinalHttp httpUtils = new FinalHttp();
	private AppContext ac;
	private List<BaseApiClient> mlist;

	public ApiClient(AppContext ac) {
		this.ac = ac;
		mlist = new ArrayList<BaseApiClient>();
	}

	public void addApiClient(BaseApiClient t) {
		mlist.add(t);
	}

	public <Api extends BaseApiClient> Api getApiClient(Class<Api> clazz) {
		for (BaseApiClient item : mlist) {
			if (item.getClass().equals(clazz)) {
				return (Api) item;
			}
		}
		return null;
	}

	/**
	 * 获取http工具
	 * @return
	 */
	public FinalHttp getFinalHttp(){
		return httpUtils;
	}
	
	public NetResult getSync(String url, AjaxParams params, final Class clazz)
			throws Exception {
		String t = (String) httpUtils.getSync(url, params);
		Log.i("api", "\n" + url + "\n");
		Log.i("api",
				"\n----params start-----\n"
						+ params.getParamString().replace("&", "\n")
								.replace("=", " = ").replace("&", "\n")
								.replace("=", " = ")
						+ "\n----params end-----\n");
		Log.i("api", params.toString());
		Log.i("api", "\n===res start===\n" + Func.formatJson(t, "\n")
				+ "\n===res end===\n");
		NetResult res = (NetResult) clazz.getMethod("parse", String.class)
				.invoke(clazz, t);

		return res;
	}

	public HttpHandler<Object> getForCloseHttp(final ApiCallback callback, final String url,
			final AjaxParams params, final Class clazz, final String tag) {
		return httpUtils.get(url, params, new AjaxCallBack<String>() {
			@Override
			public void onStart() {
				callback.onApiStart(tag);
			}

			@Override
			public void onLoading(long count, long current) {
				callback.onApiLoading(count, current, tag);
			}

			@Override
			public void onSuccess(String t, String requestTag) {
				Log.i("api", "\n" + url + "\n");
				Log.i("api",
						"\n----params start-----\n"
								+ params.getParamString().replace("&", "\n")
										.replace("=", " = ")
								+ "\n----params end-----\n");
				Log.i("api", params.toString());
				Log.i("api", "\n===res start===\n" + Func.formatJson(t, "\n")
						+ "\n===res end===\n");
				NetResult res = null;
				try {
					res = (NetResult) clazz.getMethod("parse", String.class)
							.invoke(clazz, t);
				} catch (Exception e) {
					e.printStackTrace();
					callback.onParseError(tag);
				}
				callback.onApiSuccess(res, tag);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg,
					String requestTag) {
				callback.onApiFailure(t, errorNo, strMsg, tag);
			}

		});
	}
	
	public void get(final ApiCallback callback, final String url,
			final AjaxParams params, final Class clazz, final String tag) {
		httpUtils.get(url, params, new AjaxCallBack<String>() {
			@Override
			public void onStart() {
				callback.onApiStart(tag);
			}

			@Override
			public void onLoading(long count, long current) {
				callback.onApiLoading(count, current, tag);
			}

			@Override
			public void onSuccess(String t, String requestTag) {
				Log.i("api", "\n" + url + "\n");
				Log.i("api",
						"\n----params start-----\n"
								+ params.getParamString().replace("&", "\n")
										.replace("=", " = ")
								+ "\n----params end-----\n");
				Log.i("api", params.toString());
				Log.i("api", "\n===res start===\n" + Func.formatJson(t, "\n")
						+ "\n===res end===\n");
				NetResult res = null;
				try {
					res = (NetResult) clazz.getMethod("parse", String.class)
							.invoke(clazz, t);
				} catch (Exception e) {
					e.printStackTrace();
					callback.onParseError(tag);
				}
				callback.onApiSuccess(res, tag);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg,
					String requestTag) {
				callback.onApiFailure(t, errorNo, strMsg, tag);
			}

		});
	}

	public NetResult postSync(String url, AjaxParams params, final Class clazz)
			throws Exception {
		String t = (String) httpUtils.postSync(url, params);
		Log.i("api", "\n" + url + "\n");
		Log.i("api", "\n----params start-----\n"
				+ params.getParamString().replace("&", "\n")
						.replace("=", " = ") + "\n----params end-----\n");
		Log.i("api", params.toString());
		Log.i("api", "\n===res start===\n" + Func.formatJson(t, "\n")
				+ "\n===res end===\n");
		NetResult res = (NetResult) clazz.getMethod("parse", String.class)
				.invoke(clazz, t);
		return res;
	}

	public void post(final ApiCallback callback, final String url,
			final AjaxParams params, final Class clazz, final String tag) {
		Log.i("api", "\n" + url + "\n");
		Log.i("api", "\n----params start-----\n"
				+ params.getParamString().toString()
				+ "\n----params end-----\n");
		httpUtils.post(url, params, new AjaxCallBack<String>() {
			@Override
			public void onStart() {
				callback.onApiStart(tag);
			}

			@Override
			public void onLoading(long count, long current) {
				callback.onApiLoading(count, current, tag);
			}

			@Override
			public void onSuccess(String t, String requestTag) {
				Log.i("api", "\n" + url + "\n");
				Log.i("api",
						"\n----params start-----\n"
								+ params.getParamString().replace("&", "\n")
										.replace("=", " = ")
								+ "\n----params end-----\n");
				Log.i("api", params.toString());
				Log.i("api", "\n===res start===\n" + Func.formatJson(t, "\n")
						+ "\n===res end===\n");
				NetResult res = null;
				try {
					res = (NetResult) clazz.getMethod("parse", String.class)
							.invoke(clazz, t);
				} catch (Exception e) {
					e.printStackTrace();
					callback.onParseError(tag);
				}
				callback.onApiSuccess(res, tag);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg,
					String requestTag) {
				
				System.out.println("errorNo:::::::"+errorNo);
				callback.onApiFailure(t, errorNo, strMsg, requestTag);
			}

		}, "");
	}

}
