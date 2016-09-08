package midian.baselib.db;

import java.util.List;

import android.content.Context;

import com.midian.fastdevelop.afinal.FinalDb;
import com.midian.fastdevelop.afinal.FinalDb.DaoConfig;

/**
 * 数据库操作工具类
 * 
 * @author admin
 * 
 */
public class DbUtil {

	private static DbUtil dbUtil;
	private FinalDb finalDb;
	private static final String DB_NAME = "midian_qualitycloud";
	public DbUtil(Context context) {
		DaoConfig daoConfig = new DaoConfig();
		daoConfig.setDbName(DB_NAME);
		daoConfig.setContext(context);
		finalDb = FinalDb.create(daoConfig);
	}

	public static DbUtil getDbUtil(Context context) {
		if (dbUtil == null) {
			dbUtil = new DbUtil(context);
		}
		return dbUtil;
	}

	/**
	 * 增加一条记录,若是没有这个对象则创建表
	 * 
	 * @param clazz
	 */
	public void add(Object obj) {
		try{			
			finalDb.save(obj);
		}catch(Exception e){
		}
	}

	/**

	 * 删除一条记录
	 * 
	 * @param clazz
	 */

	public void remove(Object obj) {
		finalDb.delete(obj);
	}

	/**
	 * 删除所有记录
	 * @param <T>
	 * 
	 * @param clazz
	 */
	public <T> void removeAll(Class<T> clz) {
		finalDb.deleteAll(clz);
	}


	/**
	 * 查询一条记录是否存在
	 * @param <T>
	 * 
	 * @param obj
	 */
	public <T> boolean check(Class<T> clz,String strWhere)
	{   
		boolean isExit=false;
	    List<T> list=(List<T>)finalDb.findAllByWhere(clz, strWhere);
		isExit=list.size()>0;
		return isExit;
	}

	/**

	 * 更新或增加一条记录
	 * 
	 * @param clazz
	 */
	public void updateOrAdd(int id,Object obj) {
		
		if (finalDb.findById(id, obj.getClass()) != null) {
			//更新
			finalDb.update(obj);
		} else {
			//增加
			finalDb.save(obj);
		}
	}
	/**

	 * 更新或增加一条记录
	 * 
	 * @param clazz
	 */
	public void updateOrAdd(String id,Object obj) {
		
		if (finalDb.findById(id, obj.getClass()) != null) {
			//更新
			
			finalDb.update(obj);
		} else {
			//增加
		
			finalDb.save(obj);
		}
	}
	/**
	 * 更新一条记录
	 * 
	 * @param clazz
	 */
	public void update(Object obj) {
			finalDb.update(obj);
	}

	/**
	 * 不存在就增加一条记录
	 * 
	 * @param clazz
	 */
	public void addNotExist(String id,Object obj) {
		if (finalDb.findById(id, obj.getClass()) == null) {
			//增加
			finalDb.save(obj);
		}else{
		}
	}

	/**

	 * 增加多条记录
	 * 
	 * @param al
	 */
	public <T> void  addAll(List<T> al) {
		for (Object obj : al) {
			add(obj);
		}
	}

	/**
	 * 取FinalDb操作实例
	 * 
	 * @return
	 */
	public FinalDb getFinalDb() {
		return finalDb;
	}

	public <T> List<T> getAll(Class<T> clz) {
		return finalDb.findAll(clz);
	}
	public <T> List<T> getAll(Class<T> clz,String strWhere ) {
		return finalDb.findAllByWhere(clz, strWhere);
	}

}
