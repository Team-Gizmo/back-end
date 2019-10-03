package gizmo.core.db;

import java.util.List;


public class IncidentKeywordDAO extends AbstractDAO {

	@Override
	public List<Object[]> getRecords(int maxRows) {
		String query = getRecordsQuery();
		List<Object[]> allRows = getRows(maxRows, query, DbConnectionType.POSTGRESQL);
		return allRows;
	}

	private String getRecordsQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT incident_id, keyword_id ");
		sb.append("FROM Incident_Keyword");
		return sb.toString();
	}

}