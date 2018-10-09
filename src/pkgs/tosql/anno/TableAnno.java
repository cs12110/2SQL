package pkgs.tosql.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Table
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
public @interface TableAnno {

	/**
	 * 数据表名称
	 * 
	 * @return String
	 */
	String name();

	/**
	 * 备注
	 * 
	 * @return String
	 */
	String comment() default "";
}
