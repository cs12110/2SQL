package pkgs.tosql.filter;

import java.lang.reflect.Field;

import pkgs.tosql.anno.FieldAnno;

/**
 * 字段过滤器
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月9日
 * @see
 * @since 1.0
 */
public class FieldFilter implements Filter<Field> {

	@Override
	public boolean isOk(Field obj) {
		if (obj.isAnnotationPresent(FieldAnno.class)) {
			FieldAnno anno = obj.getAnnotation(FieldAnno.class);
			return !anno.isIgnore();
		}
		return true;
	}

}
