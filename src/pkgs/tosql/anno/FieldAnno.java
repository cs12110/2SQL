package pkgs.tosql.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 字段
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月9日
 * @see
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnno {

	/**
	 * 名称
	 * 
	 * @return String
	 */
	String name() default "";

	/**
	 * 类型
	 * 
	 * @return
	 */
	String type() default "";

	/**
	 * 备注
	 * 
	 * @return
	 */
	String comment() default "";

	/**
	 * 驼峰转换成下划线
	 * 
	 * @return boolean
	 */
	boolean isCamelToUndeline() default false;

	/**
	 * 是否忽略
	 * 
	 * @return boolean
	 */
	boolean isIgnore() default false;

}
