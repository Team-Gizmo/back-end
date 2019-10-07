package gizmo.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


@Singleton
public class DbBackup {

	private static final Logger LOG = Logger.getLogger(DbBackup.class);
	
	private static final String SINGLE_LINE_BREAK = "\n";
	private static final String DOUBLE_LINE_BREAK = "\n\n";
	
	private static final String FILE_LOCATION = "C:\\Users\\lfrederi\\OneDrive - Paychex\\DbBackup\\";
	
	private EntityManager em;
	
	public DbBackup() {}
	
	public DbBackup(EntityManager em) {
		this.em = em;
	}
	
	public void run() {
		LOG.info("Running...");
		
		Collection<Object[]> incidents = getAllIncidents();
		Collection<Object[]> keywords = getAllKeywords();
		Collection<Object[]> incidentKeywords = getAllIncidentKeywords();
		BigInteger maxIncidentId = getIncidentSequence();
		String incidentInsert = createIncidentInsertStatement(incidents);
		String keywordInsert = createKeywordInsertStatement(keywords);
		String incidentKeywordInsert = createIncidentKeywordInsertStatement(incidentKeywords);
		String incidentSequenceInsert = createSequenceInsertStatement(maxIncidentId);
		
		StringBuilder sb = new StringBuilder();
		sb.append(incidentInsert);
		sb.append(DOUBLE_LINE_BREAK);
		sb.append(keywordInsert);
		sb.append(DOUBLE_LINE_BREAK);
		sb.append(incidentKeywordInsert);
		sb.append(DOUBLE_LINE_BREAK);
		sb.append(incidentSequenceInsert);
		
		String name = new SimpleDateFormat("yyyyMMdd'.sql'").format(new Date());
		String file = FILE_LOCATION + name;
		writeToFile(file, sb.toString());
	}
	
	@SuppressWarnings("unchecked")
	private Collection<Object[]> getAllIncidents() {
		String sql = getAllIncidentsQuery();
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	private Collection<Object[]> getAllKeywords() {
		String sql = getAllKeywordsQuery();
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	private Collection<Object[]> getAllIncidentKeywords() {
		String sql = getAllIncidentKeywordQuery();
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	private BigInteger getIncidentSequence() {
		String sql = getMaxIdQuery();
		Query query = em.createNativeQuery(sql);
		List<Object> results = query.getResultList();
		BigInteger value = (BigInteger) results.get(0);
		return value;
	}
	
	private String getAllIncidentsQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, name, description, solution, create_date ");
		sb.append("FROM Incident ");
		sb.append("ORDER BY id ASC");
		return sb.toString();
	}
	
	private String getAllKeywordsQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, name, create_date ");
		sb.append("FROM Keyword ");
		sb.append("ORDER BY id ASC");
		return sb.toString();
	}
	
	private String getAllIncidentKeywordQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT incident_id, keyword_id ");
		sb.append("FROM Incident_Keyword");
		return sb.toString();
	}
	
	private String getMaxIdQuery() {
		return "SELECT nextval('hibernate_sequence');";
	}
	
	private String createIncidentInsertStatement(Collection<Object[]> data) {
		StringBuilder allRecords = new StringBuilder();
		for (Object[] row : data) {
			StringBuilder sb = new StringBuilder();
			BigInteger id = (BigInteger)row[0];
			String name = (String)row[1];
			String desc = (String)row[2];
			String description = Util.escapeQuotes(desc);
			String solu = (String)row[3];
			String solution = Util.escapeQuotes(solu);
			Timestamp createDate = (Timestamp)row[4];
			sb.append("INSERT INTO Incident (id, name, description, solution, create_date) VALUES (");
			sb.append(id);
			sb.append(",'");
			sb.append(name);
			sb.append("','");
			sb.append(description);
			if (StringUtils.isEmpty(solution)) {
				sb.append("',null,'");
			}
			else {
				sb.append("','");
				sb.append(solution);
				sb.append("','");
			}
			sb.append(createDate);
			sb.append("');");
			sb.append(SINGLE_LINE_BREAK);
			allRecords.append(sb.toString());
		}
		return allRecords.toString();
	}
	
	private String createKeywordInsertStatement(Collection<Object[]> data) {
		StringBuilder allRecords = new StringBuilder();
		for (Object[] row : data) {
			StringBuilder sb = new StringBuilder();
			BigInteger id = (BigInteger)row[0];
			String s = (String)row[1];
			String name = Util.escapeQuotes(s);
			Timestamp createDate = (Timestamp)row[2];
			sb.append("INSERT INTO Keyword (id, name, create_date) VALUES (");
			sb.append(id);
			sb.append(",'");
			sb.append(name);
			sb.append("','");
			sb.append(createDate);
			sb.append("');");
			sb.append(SINGLE_LINE_BREAK);
			allRecords.append(sb.toString());
		}
		return allRecords.toString();
	}
	
	private String createIncidentKeywordInsertStatement(Collection<Object[]> data) {
		StringBuilder allRecords = new StringBuilder();
		for (Object[] row : data) {
			StringBuilder sb = new StringBuilder();
			BigInteger incidentId = (BigInteger)row[0];
			BigInteger keywordId= (BigInteger)row[1];
			sb.append("INSERT INTO Incident_Keyword (incident_id, keyword_id) VALUES (");
			sb.append(incidentId);
			sb.append(",");
			sb.append(keywordId);
			sb.append(");");
			sb.append(SINGLE_LINE_BREAK);
			allRecords.append(sb.toString());
		}
		return allRecords.toString();
	}
	
	private String createSequenceInsertStatement(BigInteger value) {
		return "SELECT setval('hibernate_sequence', (" + value.longValue() + "))";
	}
	
	private void writeToFile(String fileName, String s) {
		LOG.info("Starting file write...");
		File file = new File(fileName);
    try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file); 
	    writer.write(s); 
	    writer.flush();
	    writer.close();
	    LOG.info("Finished writing file");
		}
		catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
}