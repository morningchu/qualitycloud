package com.midian.fastdevelop.afinal.http;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import android.os.SystemClock;

import com.midian.fastdevelop.afinal.core.AsyncTask;
import com.midian.fastdevelop.afinal.http.entityhandler.EntityCallBack;
import com.midian.fastdevelop.afinal.http.entityhandler.FileEntityHandler;
import com.midian.fastdevelop.afinal.http.entityhandler.StringEntityHandler;

public class HttpHandler<T> extends AsyncTask<Object, Object, Object> implements
		EntityCallBack {

	private final AbstractHttpClient client;
	private final HttpContext context;

	private final StringEntityHandler mStrEntityHandler = new StringEntityHandler();
	private final FileEntityHandler mFileEntityHandler = new FileEntityHandler();

	private final AjaxCallBack<T> callback;

	private int executionCount = 0;
	private String targetUrl = null; // 下载的路径
	private boolean isResume = false; // 是否断点续传
	private String charset;
	private HttpUriRequest request;
	
	private String requestTag; //请求标志，做POST缓存用
	

	public HttpHandler(AbstractHttpClient client, HttpContext context,
			AjaxCallBack<T> callback, String charset,String requestTag) {
		this.client = client;
		this.context = context;
		this.callback = callback;
		this.charset = charset;
		this.requestTag=requestTag;
	}

	private void makeRequestWithRetries(HttpUriRequest request)
			throws IOException {
		if (isResume && targetUrl != null) {
			File downloadFile = new File(targetUrl);
			long fileLen = 0;
			if (downloadFile.isFile() && downloadFile.exists()) {
				fileLen = downloadFile.length();
			}
			if (fileLen > 0)
				request.setHeader("Range", "bytes=" + fileLen + "-");
		}
		
		boolean retry = true;
		IOException cause = null;
		HttpRequestRetryHandler retryHandler = client
				.getHttpRequestRetryHandler();
		while (retry) {
			try {
				if (!isCancelled()) {
					HttpResponse response = client.execute(request, context);
					if (!isCancelled()) {
						handleResponse(response);
					}
				}
				return;
			} catch (UnknownHostException e) {
				publishProgress(UPDATE_FAILURE, e, 0,
						"unknownHostException：can't resolve host");
				return;
			} catch (IOException e) {
				cause = e;
				retry = retryHandler.retryRequest(cause, ++executionCount,
						context);
			} catch (NullPointerException e) {
				// HttpClient 4.0.x 之前的一个bug
				// http://code.google.com/p/android/issues/detail?id=5255
				cause = new IOException("NPE in HttpClient" + e.getMessage());
				retry = retryHandler.retryRequest(cause, ++executionCount,
						context);
			} catch (Exception e) {
				cause = new IOException("Exception" + e.getMessage());
				retry = retryHandler.retryRequest(cause, ++executionCount,
						context);
			}
		}
		if (cause != null)
			throw cause;
		else
			throw new IOException("未知网络错误");
	}

	@Override
	protected Object doInBackground(Object... params) {
		if (params != null && params.length == 3) {
			targetUrl = String.valueOf(params[1]);
			isResume = (Boolean) params[2];
		}
		try {
			publishProgress(UPDATE_START); // 开始
			request=(HttpUriRequest) params[0];
			makeRequestWithRetries(request);

		} catch (IOException e) {
			publishProgress(UPDATE_FAILURE, e, 0, e.getMessage()); // 结束
		}

		return null;
	}
	
	public void close()
	{
		cancel(true);
		request.abort();
	}

	private final static int UPDATE_START = 1;
	private final static int UPDATE_LOADING = 2;
	private final static int UPDATE_FAILURE = 3;
	private final static int UPDATE_SUCCESS = 4;

	@SuppressWarnings("unchecked")
	@Override
	protected void onProgressUpdate(Object... values) {
		int update = Integer.valueOf(String.valueOf(values[0]));
		switch (update) {
		case UPDATE_START:
			if (callback != null)
				callback.onStart();
			break;
		case UPDATE_LOADING:
			if (callback != null)
				callback.onLoading(Long.valueOf(String.valueOf(values[1])),
						Long.valueOf(String.valueOf(values[2])));
			break;
		case UPDATE_FAILURE:
			if (callback != null)
				callback.onFailure((Throwable) values[1], (Integer) values[2],
						(String) values[3],requestTag);
			break;
		case UPDATE_SUCCESS:
			if (callback != null)
				callback.onSuccess((T) values[1],requestTag);
			break;
		default:
			break;
		}
		super.onProgressUpdate(values);
	}

	public boolean isStop() {
		return mFileEntityHandler.isStop();
	}

	/**
	 * @param stop
	 *            停止下载任务
	 */
	public void stop() {
		mFileEntityHandler.setStop(true);
	}

	private void handleResponse(HttpResponse response) {
		StatusLine status = response.getStatusLine();
		if (status.getStatusCode() >= 300) {
			String errorMsg = "response status error code:"
					+ status.getStatusCode();
			if (status.getStatusCode() == 416 && isResume) {
				errorMsg += " \n maybe you have download complete.";
			}
			publishProgress(
					UPDATE_FAILURE,
					new HttpResponseException(status.getStatusCode(), status
							.getReasonPhrase()), status.getStatusCode(),
					errorMsg);
		} else {
			try {
				HttpEntity entity = response.getEntity();
				Object responseBody = null;
				if (entity != null) {
					time = SystemClock.uptimeMillis();
					if (targetUrl != null) {
						responseBody = mFileEntityHandler.handleEntity(entity,
								this, targetUrl, isResume);
					} else {
						responseBody = mStrEntityHandler.handleEntity(entity,
								this, charset);
					}

				}
				publishProgress(UPDATE_SUCCESS, responseBody);

			} catch (IOException e) {
				publishProgress(UPDATE_FAILURE, e, 0, e.getMessage());
			}

		}
	}

	private long time;

	@Override
	public void callBack(long count, long current, boolean mustNoticeUI) {
		if (callback != null && callback.isProgress()) {
			if (mustNoticeUI) {
				publishProgress(UPDATE_LOADING, count, current);
			} else {
				long thisTime = SystemClock.uptimeMillis();
				if (thisTime - time >= callback.getRate()) {
					time = thisTime;
					publishProgress(UPDATE_LOADING, count, current);
				}
			}
		}
	}

}