package gizmo.business.keyword.boundary;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import gizmo.business.keyword.entity.Keyword;


@Stateless
public class KeywordManager {

	@PersistenceContext(unitName="primary")
	EntityManager em;
	
	public Collection<Keyword> getAllKeywords() {
		return (Collection<Keyword>) em
				.createNamedQuery("getAllKeywords", Keyword.class)
				.getResultList();
	}
	
	public Keyword update(Long id, Keyword request) {
		Keyword keyword = em.find(Keyword.class, id);
		if (keyword != null) {
			keyword.setName(request.getName());
			return em.merge(keyword);
		}
		return null;
	}
	
	public Keyword createNewKeyword(Keyword request) {
		return em.merge(request);
	}

	public boolean doesKeywordExist(String s) {
		Query query = em.createNamedQuery("countKeywordsByName");
		query.setParameter("name", s.toUpperCase());
		long value = (long) query.getSingleResult();
		return (value == 1l);
	}
	
}