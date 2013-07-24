package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBUtil {

	private static Log log = LogFactory.getLog(DBUtil.class);

	private static QueryRunner queryrunner = new QueryRunner();
	static Statement st = null;

	/**
	 * 获取数据库连接
	 * 
	 * @return 数据库连接
	 */
	public static Connection getConnection() throws Exception {
		return ConnectionManager.getInstance().getConnection();
	}

	/**
	 * 释放数据库操作资源
	 * 
	 * @param conn
	 *            数据库连接
	 * @param stmt
	 *            数据库语句
	 * @param rs
	 *            数据库结果集
	 */
	public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		DbUtils.closeQuietly(conn, stmt, rs);
	}

	public static void closeAll(Connection conn, Statement stmt) {
		closeAll(conn, stmt, null);
	}

	public static void closeAll(Statement stmt, ResultSet rs) {
		closeAll(null, stmt, rs);
	}

	public static void closeAll(Connection conn) {
		closeAll(conn, null);
	}

	public static void closeAll(Statement stmt) {
		closeAll(null, stmt);
	}

	/**
	 * 执行一个只返回一个整型数字的SQL语句 比如：select count(*) from dual
	 * 
	 * @param sql
	 *            SQL语句
	 * @return SQL查询结果
	 */
	public static int getIntFromDatabase(String sql) {
		int ret = 0;
		Connection conn = null;

		try {
			conn = getConnection();
			ret = getIntFromDatabase(sql, conn);
		} catch (Exception ex) {
			log.error("",ex);
		} finally {
			closeAll(conn);
			conn = null;
		}
		return ret;
	}

	/**
	 * 执行一个只返回一个整型数字的SQL语句 比如：select count(*) from dual
	 * 
	 * @param sql
	 *            SQL语句
	 * @param conn
	 *            数据库连接
	 * @return SQL查询结果
	 */
	public static int getIntFromDatabase(String sql, Connection conn) {
		int ret = 0;

		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			if (rs.next()) {
				ret = rs.getInt(1);
			}
		} catch (Exception ex) {
			log.error("Error SQL: " + sql.toString());
			log.error("",ex);
		} finally {
			closeAll(st, rs);
			rs = null;
			st = null;
		}
		return ret;
	}

	/**
	 * 执行一个只返回一个长整型数字的SQL语句 比如：select count(*) from dual
	 * 
	 * @param sql
	 *            SQL语句
	 * @return SQL查询结果
	 */
	public static long getLongFromDatabase(String sql) {
		long ret = 0;
		Connection conn = null;

		try {
			conn = getConnection();
			ret = getLongFromDatabase(sql, conn);
		} catch (Exception ex) {
			log.error("Error SQL = "+sql);
			log.error(ex);
		} finally {
			closeAll(conn);
			conn = null;
		}
		return ret;
	}
	
	public static double getDoubleFromDatabase(String sql) {
		double ret = 0;
		Connection conn = null;

		try {
			conn = getConnection();
			ret = getDoubleFromDatabase(sql, conn);
		} catch (Exception ex) {
			log.error("Error SQL = "+sql);
			log.error(ex);
		} finally {
			closeAll(conn);
			conn = null;
		}
		return ret;
	}
	
	public static double getDoubleFromDatabase(String sql,Connection conn) {
		double ret = 0;

		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			if (rs.next()) {
				ret = rs.getDouble(1);
			}
		} catch (Exception ex) {
			log.error("Error SQL: " + sql.toString());
			log.error("",ex);
		} finally {
			closeAll(st, rs);
			rs = null;
			st = null;
		}
		return ret;
	}

	/**
	 * 执行一个只返回一个长整型数字的SQL语句 比如：select count(*) from dual
	 * 
	 * @param sql
	 *            SQL语句
	 * @param conn
	 *            数据库连接
	 * @return SQL查询结果
	 */
	public static long getLongFromDatabase(String sql, Connection conn) {
		long ret = 0;

		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			if (rs.next()) {
				ret = rs.getLong(1);
			}
		} catch (Exception ex) {
			log.error("Error SQL: " + sql.toString());
			log.error("",ex);
		} finally {
			closeAll(st, rs);
			rs = null;
			st = null;
		}
		return ret;
	}

	/**
	 * 执行一条SQL，从数据库中查询一个String的结果
	 * 
	 * @param sql
	 *            数据库sql语句
	 * @return 字符串的结果
	 */
	public static String getStringFromDatabase(String sql) {
		String ret = null;
		Connection conn = null;

		try {
			conn = getConnection();
			ret = getStringFromDatabase(sql, conn);
		} catch (Exception ex) {
			log.error("Error SQL: " + sql.toString());
			log.error("",ex);
		} finally {
			closeAll(conn);
			conn = null;
		}

		return ret;
	}

	/**
	 * 执行一条SQL，从数据库中查询一个String的结果
	 * 
	 * @param sql
	 *            数据库sql语句
	 * @param conn
	 *            数据库连接
	 * @return 字符串的结果
	 */
	public static String getStringFromDatabase(String sql, Connection conn) {
		String ret = null;

		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				ret = rs.getString(1);
			}
		} catch (Exception ex) {
			log.error("Error SQL: " + sql.toString());
			log.error("",ex);
		} finally {
			closeAll(st, rs);
			rs = null;
			st = null;
		}

		return ret;
	}

	public static int executeSql(Connection conn, String sql) throws SQLException  {
		return queryrunner.update(conn, sql);
	}
	
	public static int executeSql(Connection conn,String sql,Object[] param) throws SQLException{
		return queryrunner.update(conn, sql, param);
	}

	public static int executeSql(String sql,Object[] param) throws Exception{
		Connection conn = null;
		try{
			conn = getConnection();
			return queryrunner.update(conn, sql, param);	
		}finally{
			closeAll(conn);
		}
	}
	
	public static List<Object[]> query(Connection conn, String sql, Object[] param) throws SQLException{
		return getQueryList(sql, param, conn);
	}

	public static List<Object[]> getQueryList(String sql, Connection conn) throws SQLException{
		return getQueryList(sql, null, conn);
	}
	@SuppressWarnings("unchecked")
	public static List<Object[]> getQueryList(String sql,Object[] param, Connection conn) throws SQLException{
		List<Object[]> result =  (ArrayList<Object[]>)queryrunner.query(conn, sql, new ArrayListHandler(),param);
		return result;
	}
	
}
