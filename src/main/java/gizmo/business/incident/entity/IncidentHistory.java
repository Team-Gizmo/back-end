package gizmo.business.incident.entity;

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
@Table(name="incident_history")
public class IncidentHistory {

	@Id
  @GeneratedValue
  private Long id;
	
	@ManyToOne
  @JoinColumn(name="incident_id")
	private Incident incident;
	
	private Action action;
	
	@Column(name="modified_date", updatable=false, nullable = false)
  private LocalDateTime modifiedDate;
	
	public IncidentHistory() {}
	
	public IncidentHistory(Incident incident, Action action) {
		this.incident = incident;
		this.action = action;
		this.modifiedDate = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}
	public Incident getIncident() {
		return incident;
	}

	public Action getAction() {
		return action;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	
}