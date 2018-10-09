package pkgs.tosql.filter;

/**
 * 过滤器
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月9日
 * @see
 * @since 1.0
 * @param <T>
 */
public interface Filter<T> {

	/**
	 * 判断是否符合
	 * 
	 * @param obj
	 *            对象
	 * @return boolean
	 */
	public boolean isOk(T obj);
}
