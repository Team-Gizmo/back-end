package gizmo.core.db;

import java.util.List;


public class KeywordDAO extends AbstractDAO {

	@Override
	public List<Object[]> getRecords(int maxRows) {
		String query = getRecordsQuery();
		List<Object[]> allRows = getRows(maxRows,query,DbConnectionType.POSTGRESQL);
		return allRows;
	}

	private String getRecordsQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, name, create_date ");
		sb.append("FROM Keyword ");
		sb.append("ORDER BY id ASC");
		return sb.toString();
	}
	
}