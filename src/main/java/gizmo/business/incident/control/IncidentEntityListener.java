package gizmo.business.incident.control;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import gizmo.business.incident.entity.Incident;
import gizmo.business.incident.entity.IncidentHistory;
import gizmo.core.enums.Action;

import static javax.transaction.Transactional.TxType.MANDATORY;

import javax.ejb.Stateless;


@Stateless
public class IncidentEntityListener {
  
	public IncidentEntityListener() {}
	
	@PersistenceContext(unitName="primary")
	EntityManager em;
	
	@PrePersist
  public void prePersist(Incident target) {
		perform(target, Action.INSERTED);
  }

  @PreUpdate
  public void preUpdate(Incident target) {
  	perform(target, Action.UPDATED);
  }

  @PreRemove
  public void preRemove(Incident target) {
  	perform(target, Action.DELETED);
  }

  @Transactional(MANDATORY)
  private void perform(Incident target, Action action) {
  	em.persist(new IncidentHistory(target, action));
  }
  
}