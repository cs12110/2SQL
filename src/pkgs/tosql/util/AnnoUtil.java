package pkgs.tosql.util;

import java.lang.reflect.Field;

import pkgs.tosql.anno.FieldAnno;
import pkgs.tosql.anno.TableAnno;

/**
 * 自定义注解工具类
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月9日
 * @see
 * @since 1.0
 */
public class AnnoUtil {

	/**
	 * 获取表名称
	 * 
	 * @param obj
	 *            实体类对象
	 * @return String
	 */
	public static String getTableName(Object obj) {
		Class<? extends Object> clazz = obj.getClass();
		if (clazz.isAnnotationPresent(TableAnno.class)) {
			TableAnno tableAnno = clazz.getAnnotation(TableAnno.class);
			return tableAnno.name();
		}
		return clazz.getSimpleName();
	}

	/**
	 * 获取属性名称
	 * 
	 * @param f
	 *            field对象
	 * @return String
	 */
	public static String getFieldName(Field f) {
		if (f.isAnnotationPresent(FieldAnno.class)) {
			FieldAnno fieldAnno = f.getAnnotation(FieldAnno.class);
			String annoName = fieldAnno.name();
			if (!StrUtil.isEmpty(annoName)) {
				return annoName.trim();
			}

			if (fieldAnno.isCamelToUndeline()) {
				return StrUtil.parseCamelToUnderline(f.getName());
			}
		}
		return f.getName();
	}
}
