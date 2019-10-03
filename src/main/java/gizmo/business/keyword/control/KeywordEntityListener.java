package gizmo.business.keyword.control;

import static javax.transaction.Transactional.TxType.MANDATORY;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import gizmo.business.keyword.entity.Keyword;
import gizmo.business.keyword.entity.KeywordHistory;
import gizmo.core.enums.Action;


@Stateless
public class KeywordEntityListener {
  
	public KeywordEntityListener() {}
	
	@PersistenceContext(unitName="primary")
	EntityManager em;
	
	@PrePersist
  public void prePersist(Keyword target) {
		perform(target, Action.INSERTED);
  }

  @PreUpdate
  public void preUpdate(Keyword target) {
  	perform(target, Action.UPDATED);
  }

  @PreRemove
  public void preRemove(Keyword target) {
  	perform(target, Action.DELETED);
  }

  @Transactional(MANDATORY)
  private void perform(Keyword target, Action action) {
  	em.persist(new KeywordHistory(target, action));
  }
  
}