package pkgs.tosql.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pkgs.tosql.filter.Filter;

/**
 * 反射工具类
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月9日
 * @see
 * @since 1.0
 */
public class ReflectUtil {

	/**
	 * 本地缓存
	 */
	private static Map<String, Object> cache = new HashMap<String, Object>();

	/**
	 * 获取类的所有字段,包括从父类继承下来的字段
	 * 
	 * @param obj
	 * @return List
	 */
	public static List<Field> getFields(Object obj, Filter<Field> filter) {
		if (obj == null) {
			return Collections.emptyList();
		}
		String filterKey = "filter-" + obj.getClass().getName();
		List<Field> after = get(filterKey);
		if (null != after && !after.isEmpty()) {
			return after;
		}
		List<Field> list = getAllFieldFromObject(obj);
		after = new ArrayList<Field>(list.size());
		for (Field f : list) {
			if (filter.isOk(f)) {
				after.add(f);
			}
		}
		cache.put(filterKey, after);
		return after;

	}

	/**
	 * 获取类的所有字段,包括从父类继承下来的字段
	 * 
	 * @param obj
	 * @return List
	 */
	public static List<Field> getAllFieldFromObject(Object obj) {
		if (obj == null) {
			return Collections.emptyList();
		}
		Class<? extends Object> clazz = obj.getClass();
		String key = clazz.getName();
		List<Field> fieldList = get(key);
		if (fieldList != null && !fieldList.isEmpty()) {
			return fieldList;
		}
		fieldList = new ArrayList<Field>();
		while (clazz != Object.class) {
			Field[] fieldArr = clazz.getDeclaredFields();
			if (fieldArr != null) {
				for (Field f : fieldArr) {
					f.setAccessible(true);
					fieldList.add(f);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return fieldList;
	}

	/**
	 * 获取本地缓存
	 * 
	 * @param key
	 *            key
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	private static <T> T get(String key) {
		Object value = cache.get(key);
		return null != value ? (T) value : null;
	}

}
