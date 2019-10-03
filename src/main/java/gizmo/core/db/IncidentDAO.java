package gizmo.core.db;

import java.util.Collection;
import java.util.List;


public class IncidentDAO extends AbstractDAO {

	@Override
	public List<Object[]> getRecords(int maxRows) {
		String query = getRecordsQuery();
		List<Object[]> allRows = getRows(maxRows, query, DbConnectionType.POSTGRESQL);
		return allRows;
	}

	public Long getMaxId() {
		String query = "SELECT nextval('hibernate_sequence');";
		List<Object[]> allRows = (List<Object[]>) getRows(0, query, DbConnectionType.POSTGRESQL);
		Object[] firstRow = allRows.get(0);
		Object firstColumn = firstRow[0];
		Long value = (Long)firstColumn;
		return value;
	}
	
	private String getRecordsQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, name, description, solution, create_date ");
		sb.append("FROM Incident ");
		sb.append("ORDER BY id ASC");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		AbstractDAO dao = new IncidentDAO();
		Collection<Object[]> objs = dao.getRecords(0);
		for (Object[] obj : objs) {
			System.out.println(obj[0]);
		}
	}
	
}