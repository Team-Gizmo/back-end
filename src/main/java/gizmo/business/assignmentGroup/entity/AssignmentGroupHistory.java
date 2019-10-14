package gizmo.business.assignmentGroup.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gizmo.core.enums.Action;


@Entity
@Table(name="assignment_group_history")
public class AssignmentGroupHistory {

	@Id
  @GeneratedValue
  private Long id;
	
	@ManyToOne
  @JoinColumn(name="assignment_group_id")
	private AssignmentGroup group;
	
	private Action action;
	
	@Column(name="modified_date", updatable=false, nullable=false)
  private LocalDateTime modifiedDate;
	
	public AssignmentGroupHistory() {}
	
	public AssignmentGroupHistory(AssignmentGroup group, Action action) {
		this.group = group;
		this.action = action;
		this.modifiedDate = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public AssignmentGroup getAssignmentGroup() {
		return group;
	}

	public Action getAction() {
		return action;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	
}