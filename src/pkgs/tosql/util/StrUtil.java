package pkgs.tosql.util;

/**
 * 字符串工具类
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月9日
 * @see
 * @since 1.0
 */
public class StrUtil {

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return null == str || "".equals(str.trim());
	}

	/**
	 * 将驼峰字符串转换成下划线字符串
	 * 
	 * @param value
	 *            value
	 * @return String
	 */
	public static String parseCamelToUnderline(String value) {
		StringBuilder str = new StringBuilder();
		char[] arr = value.toCharArray();
		for (int index = 0, len = arr.length; index < len; index++) {
			char each = arr[index];
			if (each >= 'A' && each <= 'Z') {
				str.append("_");
				str.append((char) (each + 32));
			} else {
				str.append((char) each);
			}
		}
		return str.toString();
	}

}
