package midian.baselib.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class FDDataUtils {

    private static final String EMPTY_STRING = "";

    private static final int DEFAULT_INT = 0;
    private static final double DEFAULT_DOUBLE = 0;
    private static final long DEFAULT_LONG = 0;
    private static final float DEFAULT_FLOAT = 0;

    private static final ArrayList<?> EMPTY_ARRAY_LIST = new ArrayList<Object>();

    /**
     * Get string from a map, if not exists, return empty string
     * 
     * @param map
     * @param key
     * @return
     */
    public static String getString(Map<String, ?> map, String key) {
        if (map == null || !map.containsKey(key))
            return EMPTY_STRING;
        return getString(map.get(key));
    }

    /**
     * Get string from a object, if not exists, return empty string
     * 
     * @param map
     * @param key
     * @return
     */
    public static String getString(Object obj) {
        if (obj == null)
            return EMPTY_STRING;
        return obj.toString();
    }

    public static List<?> getArrayList(Map<String, ?> map, String key) {
        if (map == null || !map.containsKey(key) || !map.get(key).getClass().isAssignableFrom(ArrayList.class))
            return EMPTY_ARRAY_LIST;
        return (map.get(key) == null ? EMPTY_ARRAY_LIST : (ArrayList<?>) map.get(key));
    }

    /**
     * string转int，默认返回DEFAULT_INT
     * @param str
     * @return
     */
    public static int getInteger(String str) {
        return getInteger(str,DEFAULT_INT);
    }
    /**
     * string转int
     * @param str
     * @return
     */
    public static int getInteger(String str,int defaultInt) {
    	if (!ValidateUtil.isDigit(str, 0)) {
    		return defaultInt;
    	}
    	Integer integer;
    	integer = Integer.valueOf(str);
    	return integer.intValue();
    }
    /**
     * string转double，默认返回DEFAULT_DOUBLE
     * @param str
     * @return
     */
    public static double getDouble(String str) {
    	return getDouble(str,DEFAULT_DOUBLE);
    }
    /**
     * string转double
     * @param str
     * @return
     */
    public static double getDouble(String str,double defaultDouble) {
    	if (!ValidateUtil.isDecimal(str)) {
    		return defaultDouble;
    	}
    	Double mDouble;
    	mDouble=Double.valueOf(str);
    	return mDouble.doubleValue();
    }
    /**
     * string转float，默认返回DEFAULT_FLOAT
     * @param str
     * @return
     */
    public static float getFloat(String str) {
    	return getFloat(str,DEFAULT_FLOAT);
    }
    /**
     * string转float
     * @param str
     * @return
     */
    public static float getFloat(String str,float defaultFloat) {
    	if (!ValidateUtil.isDecimal(str)) {
    		return defaultFloat;
    	}
    	Float mFloat;
    	mFloat=Float.valueOf(str);
    	return mFloat.floatValue();
    }
    /**
     * string转long，默认返回DEFAULT_LONG
     * @param str
     * @return
     */
    public static long getLong(String str) {
    	return getLong(str,DEFAULT_LONG);
    }
    /**
     * string转long
     * @param str
     * @return
     */
    public static long getLong(String str,long defaultDouble) {
    	if (!ValidateUtil.isDigit(str,0)) {
    		return defaultDouble;
    	}
    	Long mLong;
    	mLong=Long.valueOf(str);
    	return mLong.longValue();
    }
}