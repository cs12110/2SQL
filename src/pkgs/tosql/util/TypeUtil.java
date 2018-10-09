package pkgs.tosql.util;

/**
 * Oh shit
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月9日
 * @see
 * @since 1.0
 */
public class TypeUtil {

	public static String parseToJdbcType(Class<?> clazz) {
		if (clazz == int.class || clazz == Integer.class) {
			return "int";
		}

		if (clazz == float.class || clazz == Float.class) {
			return "float";
		}

		if (clazz == long.class || clazz == Long.class) {
			return "long";
		}

		if (clazz == double.class || clazz == Double.class) {
			return "double";
		}

		if (clazz == String.class) {
			return "varchar(215)";
		}

		return "varchar(215)";
	}

}
