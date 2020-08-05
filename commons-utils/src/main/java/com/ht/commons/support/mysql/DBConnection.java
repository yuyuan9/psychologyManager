package com.ht.commons.support.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 数据库连类
 * 
 * @author jied@2019.1.16
 *
 */
public class DBConnection {

	private static Logger logger = Logger.getLogger(DBConnection.class);

	private static DBConnection dbconn;

	private Database database = new Database();

	private Connection conn;

	private ResultSet rs;

	private PreparedStatement pstmt;

	private Statement statement;

	public DBConnection() {
	}
	
	public DBConnection(boolean dataupload) {
		database = new Database(dataupload);
	}
	
	public DBConnection(Database _database) {
		this.database = _database;
	}

	public static DBConnection getInstance() throws Exception {
		if (dbconn == null) {
			dbconn = new DBConnection(new Database());
		}
		return dbconn;
	}

	/**
	 * @throws Exception
	 */
	public void getConnection() throws Exception {
		try {
			if (conn == null) {
				Class.forName(database.getDriverClass());
				conn = DriverManager.getConnection(database.getUrl(), database.getUsername(), database.getPassword());
			}
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
	}

	private void pstmt(String sql) throws Exception {
		try {
			if (conn == null) {
				getConnection();
			}
			pstmt = conn.prepareStatement(sql);

		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
	}

	public void addBatch(List<String> sqls) throws Exception {
		try {
			if (conn == null) {
				getConnection();
			}
			conn.setAutoCommit(false);
			statement = conn.createStatement();
			if (sqls != null) {
				for (String sql : sqls) {
					statement.addBatch(sql);
				}
				statement.executeBatch();
			}
			conn.commit();
			conn.setAutoCommit(true);
			close();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private void rs(String sql) throws Exception {
		try {
			if (pstmt == null) {
				pstmt(sql);
			}
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
	}

	/**
	 * 执行查询sql语句
	 * 
	 * @param sql
	 */
	public ResultSet executeQuerySql(String sql) throws Exception {
		try {
			pstmt(sql);
			rs = pstmt.executeQuery();
			return rs;
		} catch (Exception e) {
			close();
			logger.error(e);
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 */
	public void executeSql(String sql) throws Exception {
		try {
			if (pstmt == null) {
				pstmt(sql);
			}
			pstmt.execute();

			close();
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
	}
	public void executeSql2(String sql) throws Exception {
		try {
			//if (pstmt == null) {
				pstmt(sql);
			//}
			pstmt.execute();

			//close();
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
	}

	public void executeSqls(List<String> sqls) {
		try {
			if (conn == null) {
				getConnection();
			}
			for (String sql : sqls) {
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.execute();
					// logger.info(DBConnection.class.getName()+"类执行execute："+sql+",成功");
				} catch (Exception ee) {
					// logger.error(DBConnection.class.getName()+"类执行execute："+sql+",失败");
					logger.error(ee);
					// ee.printStackTrace();
				}
			}
			close();
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
	}

	/**
	 * 查表是否存在
	 * 
	 * @param databaseName
	 * @param tableName
	 * @return
	 */
	public boolean ifExists(String databaseName, String tableName) {
		try {
			if (conn == null) {
				getConnection();
			}
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rsTables = meta.getTables(databaseName, null, tableName, new String[] { "TABLE" });
			return rsTables.next();
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
		return false;
	}

	public boolean exits(String sql) throws Exception {
		try {
			if (rs == null) {
				rs(sql);
			}

			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
		return false;
	}

	public void close(Connection _conn, PreparedStatement _pstmt, ResultSet _rs) {
		try {
			if (_rs != null) {
				_rs.close();
				_rs = null;
			}
			if (_pstmt != null) {
				_pstmt.close();
				_pstmt = null;
			}
			if (_conn != null) {
				_conn.close();
				_conn = null;
			}

		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		} finally {
			try {
				if (_rs != null) {
					_rs.close();
					_rs = null;
				}
				if (_pstmt != null) {
					_pstmt.close();
					_pstmt = null;
				}
				if (_conn != null) {
					_conn.close();
					_conn = null;
				}
			} catch (Exception e) {
				logger.error(e);
				// e.printStackTrace();
			}
		}
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}

	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public PreparedStatement getPstmt() {
		return pstmt;
	}

	public void setPstmt(PreparedStatement pstmt) {
		this.pstmt = pstmt;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public static void main(String[] args) throws Exception {
		DBConnection dbconn = new DBConnection();
		dbconn.getConnection();
		if (dbconn.getConn() != null) {
			System.out.println("success");
		} else {
			System.out.println("fail");
		}
		// String tableName = "sale_shop";
		// String databaseName = "dfasdf";
		// System.out.println(dbconn.ifExists(databaseName, tableName));
		// try {
		//
		// DatabaseMetaData meta = dbconn.getConn().getMetaData();
		//
		// // 第一个参数catalog在MySQL中对应数据库名：iwode
		// ResultSet rsTables = meta.getTables(null, null, tableName,
		// new String[] { "TABLE" });
		//
		// // 第二个参数schemaPattern在ORACLE中对应用户名：demo
		// // ResultSet rsTables = meta.getTables(null, "DEMO", tableName,
		// // new String[] { "TABLE" });
		//
		// System.out.println("getTables查询信息如下�?");
		// System.out
		// .println("TABLE_CAT \t TABLE_SCHEM \t TABLE_NAME \t TABLE_TYPE");
		//
		// while (rsTables.next()) {
		// System.out.println(rsTables.getString("TABLE_CAT") + "\t"
		// + rsTables.getString("TABLE_SCHEM") + "\t"
		// + rsTables.getString("TABLE_NAME") + "\t"
		// + rsTables.getString("TABLE_TYPE"));
		// }
		// rsTables.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// if (null != dbconn.getConn()) {
		// dbconn.getConn().close();
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
	}

}
