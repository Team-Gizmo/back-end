package gizmo.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;


@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractDAO {

	protected static Logger LOG = Logger.getLogger(AbstractDAO.class);
	
	private static final String USER = "user";
	
	private static final String PASSWORD = "password";
	
	private static final int QUERY_TIMEOUT_SECONDS = 5 * 60;
	
	private int columnCount = 0;

	private int rowCount = 0;
	
	private String[] columnLabels = new String[0];
	
	private int[] columnTypes = new int[0];
	
	
	public abstract List<Object[]> getRecords(int maxRows);

	protected List<Object[]> getRows(int maxRows, String sql, DbConnectionType dbType) {
		List<Object[]> allRows = new ArrayList<Object[]>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection(dbType);
			st = conn.createStatement();
			st.setMaxRows(maxRows);
			if (dbType.implementsQueryTimeout()) {
				st.setQueryTimeout(QUERY_TIMEOUT_SECONDS);
			}
			rs = st.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			columnCount = metaData.getColumnCount();
			columnLabels = new String[columnCount];
			columnTypes = new int[columnCount];
			
			for (int i = 0, j = columnCount; i < j; i++) {
				columnLabels[i] = metaData.getColumnLabel(i + 1);
        columnTypes[i] = metaData.getColumnType(i + 1);
      }
			
			while (rs.next()) {
				Object[] row = new Object[columnCount];
				for (int i = 1, j = columnCount; i <= j; i++) {
					Object obj = rs.getObject(i);
					
					// if String, then trim
					if (obj instanceof String) {
            obj = ((String)obj).trim();
          }
					
					row[i-1] = obj;
				}
				allRows.add(row);
			}
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			DbUtils.closeQuietly(conn,st,rs);
		}
		rowCount = allRows.size();
		return allRows;
	}

	protected int insertOrUpdate(String sql, DbConnectionType dbType) {
		int value = -1;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection(dbType);
			st = conn.createStatement();
			value = st.executeUpdate(sql);
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			DbUtils.closeQuietly(conn,st,rs);
		}
		return value;
	}

	private Connection getConnection(DbConnectionType dbConnection) throws SQLException, ClassNotFoundException {
    Connection conn = null;
    Properties props = new Properties();
    String driver = dbConnection.getDriver();
    String db = dbConnection.getDb();
    String user = dbConnection.getUser();
    String password = dbConnection.getPassword();
    props.put(USER,user);
    props.put(PASSWORD,password);
    Class.forName(driver);
    conn = DriverManager.getConnection(db,props);
    return conn;
	}
	
	public int getColumnCount() {
		return columnCount;
	}
	
	public int getRowCount() {
		return rowCount;
	}
	
	public String[] getColumnLabels() {
		return columnLabels;
	}
	
	public int[] getColumnTypes() {
		return columnTypes;
	}
		
}