package gizmo.business.assignmentGroup.control;

import static javax.transaction.Transactional.TxType.MANDATORY;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import gizmo.business.assignmentGroup.entity.AssignmentGroup;
import gizmo.business.assignmentGroup.entity.AssignmentGroupHistory;
import gizmo.core.enums.Action;


@Stateless
public class AssignmentGroupEntityListener {
  
	public AssignmentGroupEntityListener() {}
	
	@PersistenceContext(unitName="primary")
	EntityManager em;
	
	@PrePersist
  public void prePersist(AssignmentGroup target) {
		perform(target, Action.INSERTED);
  }

  @PreUpdate
  public void preUpdate(AssignmentGroup target) {
  	perform(target, Action.UPDATED);
  }

  @PreRemove
  public void preRemove(AssignmentGroup target) {
  	perform(target, Action.DELETED);
  }

  @Transactional(MANDATORY)
  private void perform(AssignmentGroup target, Action action) {
  	em.persist(new AssignmentGroupHistory(target, action));
  }
  
}