package gizmo.business.incident.boundary;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import gizmo.business.incident.entity.Composite;
import gizmo.business.incident.entity.Incident;
import gizmo.business.incident.entity.IncidentKeyword;
import gizmo.business.keyword.entity.Keyword;
import gizmo.core.Util;


@Stateless
public class IncidentsManager {

	private static final Logger LOG = Logger.getLogger(IncidentsManager.class);
	
	@PersistenceContext(unitName="primary")
	EntityManager em;
	
	public Collection<Incident> getAllIncidents() {
		return (Collection<Incident>) em
				.createNamedQuery("getAllIncidents", Incident.class)
				.getResultList();
	}
	
	public Incident find(Long id) {
		return em.find(Incident.class, id);
	}
	
	public Incident updateAll(Long id, Incident request) {
		Incident incident = em.find(Incident.class, id);
		if (incident != null) {
			incident.setName(request.getName());
			incident.setSolution(request.getSolution());
			return em.merge(incident);
		}
		return null;
	}
	
	public Incident updateSolution(Long id, Incident request) {
		Incident incident = em.find(Incident.class, id);
		if (incident != null) {
			incident.setSolution(request.getSolution());
			return em.merge(incident);
		}
		return null;
	}
	
	public Incident createNewIncident(Composite request) {
		Incident incident = request.getIncident();
		incident = em.merge(incident);
		em.flush();
		int[] keywordIds = request.getKeywordIds();
		Collection<Keyword> allKeywords = new ArrayList<>();
		for (int i = 0, j = keywordIds.length; i < j; i++) {
			Keyword k = em.find(Keyword.class, new Long(keywordIds[i]));
			if (k != null) {
				allKeywords.add(k);
			}
		}
		incident.setKeywords(allKeywords);
		//em.persist(incident);
		return incident;
	}
	
	public Incident getByName(String name) {
		Query query = em.createNamedQuery("getIncidentByName");
		query.setParameter("name", name.toUpperCase());
		return (Incident) query.getSingleResult();
	}
	
	public Boolean doesIncidentExist(String s) {
		Query query = em.createNamedQuery("countIncidentsByName");
		query.setParameter("name", s.toUpperCase());
		long value = (long) query.getSingleResult();
		return (value == 1l);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Incident> getIncidentsByKeywords(List<String> keywords) {
		String sql = getIncidentsByKeywordsQuery(keywords);
		Query query = em.createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		Collection<Incident> values = new ArrayList<>();
		for (Object[] objs : list) {
			BigInteger id = (BigInteger)objs[0];
			String name = (String)objs[1];
			String description = (String)objs[2];
			String solution = (String)objs[3];
			values.add(new Incident(id.longValue(), name, description, solution));
		}
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Incident> getIncidentsByKeywordIds(List<Long> keywordIds) {
		String sql = getIncidentsByKeywordIdsQuery(keywordIds);
		Query query = em.createNativeQuery(sql);
		//query.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false);	// this only works in Hibernate 5.2.2 and higher
		List<Object[]> list = query.getResultList();
		Collection<Incident> values = new ArrayList<>();
		for (Object[] objs : list) {
			BigInteger id = (BigInteger)objs[0];
			String name = (String)objs[1];
			String description = (String)objs[2];
			String solution = (String)objs[3];
			values.add(new Incident(id.longValue(), name, description, solution));
		}
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<IncidentKeyword> getIncidentsAndKeywords() {
		String sql = getIncidentsAndKeywordsQuery();
		Query query = em.createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		Collection<IncidentKeyword> values = new ArrayList<>();
		for (Object[] objs : list) {
			String kName = (String)objs[0];
			String iName = (String)objs[1];
			String description = (String)objs[2];
			String solution = (String)objs[3];
			values.add(new IncidentKeyword(kName, iName, description, solution));
		}
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public void getLinkingTable() {
		Query query = em.createNativeQuery("SELECT * FROM Incident_Keyword");
		List<Object[]> list = query.getResultList();
		for (Object[] objs : list) {
			BigInteger a = (BigInteger)objs[0];
			BigInteger b = (BigInteger)objs[1];
			LOG.info("A = " + a.intValue() + ", B = " + b.intValue());
		}
	}
	
	private String getIncidentsByKeywordsQuery(List<String> keywords) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT i.id, i.name, i.description, i.solution ");
		sb.append("FROM Incident i ");
		sb.append("JOIN Incident_Keyword ik ON i.id = ik.incident_id ");
		sb.append("JOIN Keyword k ON ik.keyword_id = k.id ");
		sb.append("WHERE UPPER(k.name) IN ");
		sb.append(Util.convertStringListToTableIn(keywords));
		return sb.toString();
	}
	 
	private String getIncidentsByKeywordIdsQuery(List<Long> keywordIds) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT i.id, i.name, i.description, i.solution ");
		sb.append("FROM Incident i ");
		sb.append("JOIN Incident_Keyword ik ON i.id = ik.incident_id ");
		sb.append("JOIN Keyword k ON ik.keyword_id = k.id ");
		sb.append("WHERE k.id IN ");
		sb.append(Util.convertLongListToTableIn(keywordIds));
		return sb.toString();
	}
	
	private String getIncidentsAndKeywordsQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT k.name AS KeywordName, i.name AS IncidentName, i.description, i.solution ");
		sb.append("FROM Incident i ");
		sb.append("JOIN Incident_Keyword ik ON i.id = ik.incident_id ");
		sb.append("JOIN Keyword k ON ik.keyword_id = k.id ");
		sb.append("ORDER BY k.name ASC");
		return sb.toString();
	}
	
}