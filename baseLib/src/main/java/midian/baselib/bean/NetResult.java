package midian.baselib.bean;

import midian.baselib.app.AppException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * 结果
 * 
 * @author XuYang
 * 
 */
public class NetResult extends NetBase {
	public String ret = "";
	public String error_code = "";

	public static Gson gson = new Gson();

	public boolean isOK() {
		return "success".equals(ret);
	}

	public static NetResult parse(String json) throws AppException {
		NetResult res = new NetResult();
		try {
			res = gson.fromJson(json, NetResult.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

}
