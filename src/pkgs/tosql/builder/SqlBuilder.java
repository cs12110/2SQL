package pkgs.tosql.builder;

import java.lang.reflect.Field;
import java.util.List;

import pkgs.tosql.enums.OpType;
import pkgs.tosql.filter.FieldFilter;
import pkgs.tosql.util.AnnoUtil;
import pkgs.tosql.util.ReflectUtil;
import pkgs.tosql.util.TypeUtil;

/**
 * Sql builder
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月9日
 * @see
 * @since 1.0
 */
public class SqlBuilder {

	/**
	 * 创建表语句
	 * 
	 * @param target
	 * @return String
	 */
	public static String create(Object target) {
		return parse(target, OpType.CREATE);
	}

	/**
	 * 删除表语句
	 * 
	 * @param target
	 * @return String
	 */
	public static String drop(Object target) {
		return parse(target, OpType.DROP);
	}

	/**
	 * 新增语句
	 * 
	 * @param target
	 * @return String
	 */
	public static String insert(Object target) {
		return parse(target, OpType.INSERT);
	}

	/**
	 * 更新语句
	 * 
	 * @param target
	 * @return String
	 */
	public static String update(Object target) {
		return parse(target, OpType.UPDATE);
	}

	/**
	 * 删除语句
	 * 
	 * @param target
	 * @return String
	 */
	public static String delete(Object target) {
		return parse(target, OpType.DELETE);
	}

	/**
	 * 选择语句
	 * 
	 * @param target
	 * @return String
	 */
	public static String select(Object target) {
		return parse(target, OpType.SELECT);
	}

	/**
	 * 转换成sql
	 * 
	 * @param target
	 *            对象
	 * @param type
	 *            类型
	 * @return String
	 */
	public static String parse(Object target, OpType type) {
		if (target == null || type == null) {
			return null;
		}
		try {
			if (type == OpType.CREATE) {
				return buildCreateSql(target);
			} else if (type == OpType.DROP) {
				return buildDropSql(target);
			} else if (type == OpType.SELECT) {
				return buildSelectSql(target);
			} else if (type == OpType.INSERT) {
				return buildInsertSql(target);
			} else if (type == OpType.UPDATE) {
				return buildUpdateSql(target);
			} else if (type == OpType.DELETE) {
				return buildDeleteSql(target);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create sql
	 * 
	 * @param target
	 *            对象
	 * @return String
	 */
	private static String buildCreateSql(Object target) {
		StringBuilder sql = new StringBuilder();
		sql.append(" CREATE TABLE IF NOT EXISTS  ");
		sql.append(AnnoUtil.getTableName(target));
		sql.append("(");
		List<Field> fieldList = ReflectUtil.getFields(target, new FieldFilter());
		for (int index = 0, size = fieldList.size(); index < size; index++) {
			Field field = fieldList.get(index);
			sql.append("`").append(AnnoUtil.getFieldName(field)).append("`").append(" ");
			sql.append(TypeUtil.parseToJdbcType(field.getType())).append(" ");
			sql.append(" COMMENT ").append("\"").append("").append("\"");
			if (index < size - 1) {
				sql.append(" , ");
			}
		}
		sql.append(")ENGINE=INNODB charset='utf8'");
		return sql.toString();
	}

	/**
	 * drop sql
	 * 
	 * @param target
	 *            对象
	 * @return String
	 */
	private static String buildDropSql(Object target) {
		StringBuilder sql = new StringBuilder();
		sql.append(" DROP TABLE IF EXISTS  ");
		sql.append(AnnoUtil.getTableName(target));
		return sql.toString();
	}

	/**
	 * Select
	 * 
	 * @param target
	 *            类
	 * @return String
	 * @throws Exception
	 */
	private static String buildSelectSql(Object target) throws Exception {
		StringBuilder sql = new StringBuilder();
		StringBuilder where = new StringBuilder();
		sql.append(" SELECT ");
		where.append(" WHERE 1=1 ");

		List<Field> fieldList = ReflectUtil.getFields(target, new FieldFilter());
		for (int index = 0, size = fieldList.size(); index < size; index++) {
			Field field = fieldList.get(index);
			String fieldName = AnnoUtil.getFieldName(field);
			sql.append(fieldName);
			if (index < size - 1) {
				sql.append(" , ");
			}
			Object fieldValue = field.get(target);
			if (null != fieldValue) {
				where.append(" AND ");
				where.append(fieldName);
				where.append(" = ");
				where.append("\"").append(fieldValue).append("\" ");
			}
		}
		sql.append(" FROM ");
		sql.append(AnnoUtil.getTableName(target));
		sql.append(where);
		return sql.toString();
	}

	/**
	 * 插入语句
	 * 
	 * @param target
	 *            对象
	 * @return String
	 * @throws Exception
	 */
	private static String buildInsertSql(Object target) throws Exception {
		StringBuilder sql = new StringBuilder();
		StringBuilder values = new StringBuilder();
		sql.append(" INSERT INTO ");
		sql.append(AnnoUtil.getTableName(target));
		sql.append("(");
		values.append("(");

		List<Field> fieldList = ReflectUtil.getFields(target, new FieldFilter());
		boolean isAdd = false;
		for (int index = 0, size = fieldList.size(); index < size; index++) {
			Field field = fieldList.get(index);
			Object fieldValue = field.get(target);
			isAdd = false;
			if (null != fieldValue) {
				String fieldName = AnnoUtil.getFieldName(field);
				sql.append("`").append(fieldName).append("`");
				values.append("\"").append(fieldValue).append("\"");
				isAdd = true;
			}
			if (isAdd && index < size - 1) {
				sql.append(" , ");
				values.append(" , ");
			}
		}
		sql.append(") VALUES");
		values.append(") ");
		sql.append(values);
		return sql.toString();
	}

	/**
	 * Update sql
	 * 
	 * @param target
	 *            对象
	 * @return String
	 * @throws Exception
	 */
	private static String buildUpdateSql(Object target) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ");
		sql.append(AnnoUtil.getTableName(target));
		sql.append(" SET ");

		List<Field> fieldList = ReflectUtil.getFields(target, new FieldFilter());
		boolean isAdd = false;
		for (int index = 0, size = fieldList.size(); index < size; index++) {
			Field field = fieldList.get(index);
			Object fieldValue = field.get(target);
			isAdd = false;
			if (null != fieldValue) {
				String fieldName = AnnoUtil.getFieldName(field);
				sql.append("`").append(fieldName).append("`");
				sql.append(" =\"");
				sql.append(fieldValue);
				sql.append("\"");
				isAdd = true;
			}
			if (isAdd && index < size - 1) {
				sql.append(" , ");
			}
		}
		return sql.toString();
	}

	/**
	 * delete sql
	 * 
	 * @param target
	 *            对象
	 * @return String
	 * @throws Exception
	 */
	private static String buildDeleteSql(Object target) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM ");
		sql.append(AnnoUtil.getTableName(target));
		sql.append(" WHERE 1=1 ");
		List<Field> fieldList = ReflectUtil.getFields(target, new FieldFilter());
		for (int index = 0, size = fieldList.size(); index < size; index++) {
			Field field = fieldList.get(index);
			Object fieldValue = field.get(target);
			if (null != fieldValue) {
				String fieldName = AnnoUtil.getFieldName(field);
				sql.append(" AND ");
				sql.append("`").append(fieldName).append("`");
				sql.append(" =\"");
				sql.append(fieldValue);
				sql.append("\"");
			}
		}
		return sql.toString();
	}
}
