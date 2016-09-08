package midian.baselib.api;

import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;

import com.midian.fastdevelop.afinal.http.AjaxParams;
import com.midian.fastdevelop.afinal.http.HttpHandler;

/**
 * 网络请求客户端基类
 * 
 * @author moshouguan
 * 
 */
public class BaseApiClient {
	public ApiClient mApiClient;
	public AppContext ac;

	public BaseApiClient(AppContext ac) {
		// TODO Auto-generated constructor stub
		this.ac = ac;
		this.mApiClient = ac.api;
	}

	public NetResult getSync(String url, AjaxParams params, final Class clazz)
			throws Exception {
		return mApiClient.getSync(url, params, clazz);
	}

	public void get(final ApiCallback callback, final String url,
			final AjaxParams params, final Class clazz, final String tag) {
		mApiClient.get(callback, url, params, clazz, tag);
	}

	public HttpHandler<Object> getForCloseHttp(final ApiCallback callback, final String url,
			final AjaxParams params, final Class clazz, final String tag) {
		return mApiClient.getForCloseHttp(callback, url, params, clazz, tag);
	}
	
	public NetResult postSync(String url, AjaxParams params, final Class clazz)
			throws Exception {
		return mApiClient.postSync(url, params, clazz);
	}

	public void post(final ApiCallback callback, final String url,
			final AjaxParams params, final Class clazz, final String tag) {
		mApiClient.post(callback, url, params, clazz, tag);
	}

	public static String getMethodName(StackTraceElement ste[]) {
		String methodName = "";
		boolean flag = false;

		for (StackTraceElement s : ste) {

			if (flag) {

				methodName = s.getMethodName();
				break;
			}
			flag = s.getMethodName().equals("getStackTrace");
		}
		return methodName;
	}

}
